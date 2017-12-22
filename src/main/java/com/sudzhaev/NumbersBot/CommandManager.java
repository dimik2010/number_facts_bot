package com.sudzhaev.NumbersBot;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandManager {
    private static final Logger log = LoggerFactory.getLogger(CommandManager.class);
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MM yy");
    private static final String MAX_INT_AS_STRING = String.valueOf(Integer.MAX_VALUE);
    private static String helpMessage;

    public static BotCommand getImplementation(String message) {
        message = message.trim();
        int spaceIndex = message.indexOf(' ');
        if (spaceIndex < 0) {
            log.info("command contains one word");
            if (message.equals("/help") || message.equals("/start")) {
                log.info("command <{}>", message);
                return handleStartHelp();
            }
            log.info("command has invalid syntax or does not exist");
            return new InvalidCommand("invalid command");
        }
        String command = message.substring(0, spaceIndex);
        String data = message.substring(spaceIndex + 1, message.length()).trim();

        switch (command) {
        case "/math":
            if (!isValidNumber(data)) {
                log.info("invalid command /math: not a valid number <{}>", data);
                return new InvalidCommand("invalid number");
            }
            log.info("implementation <MathCommand> chosen");
            return new MathCommand(Integer.parseInt(data));
        case "/trivia":
            if (!isValidNumber(data)) {
                log.info("invalid command /trivia: not a valid number <{}>", data);
                return new InvalidCommand("invalid number");
            }
            log.info("implementation <TriviaCommand> chosen");
            return new TriviaCommand(Integer.parseInt(data));
        case "/date":
            if (!isValidDate(data)) {
                log.info("invalid command /date: not a valid date <{}>", data);
                return new InvalidCommand("invalid date: enter date in format 'dd MM'");
            }
            data += " 00"; // necessary to parse with LocalDate.parse
            LocalDate localDate = LocalDate.parse(data, DATE_FORMATTER);
            log.info("implementation <DateCommand> chosen");
            return new DateCommand(localDate);
        default:
            log.info("<{}>: command does not exits", command);
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

    private static boolean isValidDate(String dateString) {
        String[] date = dateString.split(" +");
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

    private static BotCommand handleStartHelp() {
        if (helpMessage == null) {
            log.info("helpMessage is null");
            Properties props = new Properties();
            try {
                log.info("loading helpMessage from props");
                props.load(new FileInputStream("application.properties"));
                helpMessage = props.getProperty("help_message");
                log.info("helpMessage loaded");
            } catch (IOException e) {
                log.error("error loading helpMessage from props");
                helpMessage = "error loading help";
                log.error("help message set to error value <{}>", helpMessage);
            }
        }
        log.info("implementation <HelpCommand> chosen");
        return new HelpCommand(helpMessage);
    }
}
