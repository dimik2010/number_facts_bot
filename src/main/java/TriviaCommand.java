public class TriviaCommand extends BotCommand<Integer> {

    public TriviaCommand(Integer data) {
        super(data);
    }

    @Override
    public String getAnswer() {
        return NumbersAPI.getTriviaFact(getData());
    }
}
