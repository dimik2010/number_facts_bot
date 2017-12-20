public class InvalidCommand extends BotCommand<String> {

    public InvalidCommand(String data) {
        super(data);
    }

    @Override
    public String getAnswer() {
        return "Error: " + getData();
    }
}
