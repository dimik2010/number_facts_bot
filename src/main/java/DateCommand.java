import java.time.LocalDate;

public class DateCommand extends BotCommand<LocalDate> {

    public DateCommand(LocalDate data) {
        super(data);
    }

    @Override
    public String getAnswer() {
        return NumbersAPI.getDateFact(getData());
    }
}
