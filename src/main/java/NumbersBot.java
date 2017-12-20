import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class NumbersBot extends TelegramLongPollingBot {
    private static final Logger log = LoggerFactory.getLogger(NumbersBot.class);
    private final String token;

    public NumbersBot(String token) {
        this.token = token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            log.info("new message <{}> from chat <{}>", messageText, chatId);

            BotCommand command = CommandManager.getImplementation(messageText);

            log.info("getting response message");
            String response = command.getAnswer();
            log.info("response = <{}>", response);

            SendMessage message = new SendMessage()
                    .setChatId(chatId)
                    .setText(response);

            try {
                log.info("sending response");
                execute(message);
                log.info("response successfully sent");
            } catch (TelegramApiException e) {
                log.error("response hasn't been sent");
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "number_facts_bot";
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
