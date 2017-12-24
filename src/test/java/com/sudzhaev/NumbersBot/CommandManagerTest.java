package com.sudzhaev.NumbersBot;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CommandManagerTest {

    @Test
    public void emptyInputTest() {
        BotCommand implementation = CommandManager.getImplementation("");
        assertTrue(implementation instanceof InvalidCommand);
    }

    @Test
    public void invalidInputTest() {
        BotCommand implementation = CommandManager.getImplementation("    sfdg afdsfgd   d  ");
        assertTrue(implementation instanceof InvalidCommand);
    }

    @Test
    public void startTest() {
        BotCommand implementation = CommandManager.getImplementation("   /start   ");
        assertTrue(implementation instanceof HelpCommand);
    }

    @Test
    public void startExtraWordsTest() {
        BotCommand implementation = CommandManager.getImplementation("   /start  dsfsd ");
        assertTrue(implementation instanceof InvalidCommand);
    }

    @Test
    public void helpTest() {
        BotCommand implementation = CommandManager.getImplementation("   /help         ");
        assertTrue(implementation instanceof HelpCommand);
    }

    @Test
    public void helpExtraWordsTest() {
        BotCommand implementation = CommandManager.getImplementation("   /help  dsfsd ");
        assertTrue(implementation instanceof InvalidCommand);
    }

    @Test
    public void mathSimpleTest() {
        BotCommand implementation = CommandManager.getImplementation("/math 23");
        assertTrue(implementation instanceof MathCommand && ((MathCommand) implementation).getData().equals(23));
    }

    @Test
    public void mathSpacesTest() {
        BotCommand implementation = CommandManager.getImplementation("  /math     23    ");
        assertTrue(implementation instanceof MathCommand && ((MathCommand) implementation).getData().equals(23));
    }

    @Test
    public void mathInvalidDataTest() {
        BotCommand implementation = CommandManager.getImplementation("  /math     2g3    ");
        assertTrue(implementation instanceof InvalidCommand);
    }

    @Test
    public void mathBigIntTest() {
        BotCommand implementation = CommandManager.getImplementation("  /math     236783465874658346857348534    ");
        assertTrue(implementation instanceof InvalidCommand);
    }

    @Test
    public void mathNoDataTest() {
        BotCommand implementation = CommandManager.getImplementation("/math");
        assertTrue(implementation instanceof InvalidCommand);
    }

    @Test
    public void triviaSimpleTest() {
        BotCommand implementation = CommandManager.getImplementation("/trivia 23");
        assertTrue(implementation instanceof TriviaCommand && ((TriviaCommand) implementation).getData().equals(23));
    }

    @Test
    public void triviaSpacesTest() {
        BotCommand implementation = CommandManager.getImplementation("  /trivia     23    ");
        assertTrue(implementation instanceof TriviaCommand && ((TriviaCommand) implementation).getData().equals(23));
    }

    @Test
    public void triviaInvalidDataTest() {
        BotCommand implementation = CommandManager.getImplementation("/trivia     2g3");
        assertTrue(implementation instanceof InvalidCommand);
    }

    @Test
    public void triviaBigIntTest() {
        BotCommand implementation = CommandManager.getImplementation("/trivia     236783465874658346857348534");
        assertTrue(implementation instanceof InvalidCommand);
    }

    @Test
    public void triviaNoDataTest() {
        BotCommand implementation = CommandManager.getImplementation("/trivia");
        assertTrue(implementation instanceof InvalidCommand);
    }

    @Test
    public void dateSimpleTest() {
        BotCommand implementation = CommandManager.getImplementation("/date 12 04");
        assertTrue(implementation instanceof DateCommand);
    }


    @Test
    public void dateSpacesTest() {
        BotCommand implementation = CommandManager.getImplementation("        /date           31 03   ");
        assertTrue(implementation instanceof DateCommand);
    }

    @Test
    public void dateInvalidDayTest() {
        BotCommand implementation = CommandManager.getImplementation("   /date   31 02    ");
        assertTrue(implementation instanceof InvalidCommand);
    }


    @Test
    public void dateInvalidDataTest() {
        BotCommand implementation = CommandManager.getImplementation("/date 31 hgf");
        assertTrue(implementation instanceof InvalidCommand);
    }


    @Test
    public void dateInvalidFormatTest() {
        BotCommand implementation = CommandManager.getImplementation("   /date   12    02   ");
        assertTrue(implementation instanceof InvalidCommand);
    }

    @Test
    public void dateNoDataTest() {
        BotCommand implementation = CommandManager.getImplementation("/date");
        assertTrue(implementation instanceof InvalidCommand);
    }
}