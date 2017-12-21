package com.sudzhaev.NumbersBot;

import java.io.IOException;

public class HelpCommand extends BotCommand<String> {

    public HelpCommand(String data) {
        super(data);
    }

    @Override
    public String getAnswer() {
        return getData();
    }
}
