import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class CommandManager {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MM yy");
    private static final String MAX_INT_AS_STRING = String.valueOf(Integer.MAX_VALUE);

    public static BotCommand getImplementation(String message) {
        int spaceIndex = message.indexOf(' ');
        if (spaceIndex < 0) {
            return new InvalidCommand("invalid command");
        }
        String command = message.trim().substring(0, spaceIndex);
        String data = message.trim().substring(spaceIndex + 1, message.length());

        switch (command) {
        case "/math":
            if (!isValidNumber(data)) {
                return new InvalidCommand("invalid number");
            }
            return new MathCommand(Integer.parseInt(data));
        case "/trivia":
            if (!isValidNumber(data)) {
                return new InvalidCommand("invalid number");
            }
            return new TriviaCommand(Integer.parseInt(data));
        case "/date":
            if (!isValidDate(data)) {
                return new InvalidCommand("invalid date: enter date in format 'dd MM'");
            }
            data += " 00"; // necessary to parse with LocalDate.parse
            LocalDate localDate = LocalDate.parse(data, DATE_FORMATTER);
            return new DateCommand(localDate);
        default:
            return new InvalidCommand("invalid command");
        }
    }

    private static boolean isValidNumber(String numberString) {
        if (!NUMBER_PATTERN.matcher(numberString).matches()
                || numberString.length() > MAX_INT_AS_STRING.length()) {
            return false;
        }
        return MAX_INT_AS_STRING.length() != numberString.length()
                || MAX_INT_AS_STRING.compareTo(numberString) >= 0;
    }

    public static void main(String[] args) {
        BotCommand implementation = CommandManager.getImplementation("/trivia 228");
        System.out.println(implementation.getClass());
    }

    private static boolean isValidDate(String dateString) {
        String[] date = dateString.split(" ");
        if (date.length <= 1) {
            return false;
        }
        if (date[0].length() != 2 || date[1].length() != 2) {
            return false;
        }
        if (!NUMBER_PATTERN.matcher(date[0]).matches() || !NUMBER_PATTERN.matcher(date[1]).matches()) {
            return false;
        }
        int day = Integer.parseInt(date[0]);
        if (day < 1 || day > 31) {
            return false;
        }
        int month = Integer.parseInt(date[1]);
        if (month < 1 || month > 12) {
            return false;
        }
        if (month == 2) {
            return day <= 29;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return day <= 30;
        }
        return true;
    }
}
