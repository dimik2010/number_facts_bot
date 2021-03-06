package com.sudzhaev.NumbersBot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Main {
    private final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Properties props = new Properties();
        log.info("loading props...");
        try {
            props.load(new FileInputStream("application.properties"));
        } catch (IOException e) {
            log.error("can't load properties file");
            System.exit(1);
        }

        log.info("initializing bot");
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            String token = props.getProperty("telegram_token");
            String botUsername = props.getProperty("bot_username");
            api.registerBot(new NumbersBot(token, botUsername));
            log.info("bot successfully registered");
        } catch (TelegramApiRequestException e) {
            log.error("bot not registered");
            log.error(e.getApiResponse());
            System.exit(1);
        }
    }
}
