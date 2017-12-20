import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class NumbersBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            BotCommand command = CommandManager.getImplementation(messageText);
            String response = command.getAnswer();
            SendMessage message = new SendMessage()
                    .setChatId(chatId)
                    .setText(response);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "number_facts_bot";
    }

    @Override
    public String getBotToken() {
        return "462368299:AAG70WuxwFvh0hhstKpap9jUGCe1Ku_yhHw";
    }
}
