package com.sudzhaev.NumbersBot;

public class MathCommand extends BotCommand<Integer> {

    public MathCommand(Integer data) {
        super(data);
    }

    @Override
    public String getAnswer() {
        return NumbersAPI.getMathFact(getData());
    }
}
