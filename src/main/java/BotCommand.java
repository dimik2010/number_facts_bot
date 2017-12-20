public abstract class BotCommand<T> {
    private final T data;

    public BotCommand(T data) {
        this.data = data;
    }

    public abstract String getAnswer();

    protected T getData() {
        return data;
    }
}
