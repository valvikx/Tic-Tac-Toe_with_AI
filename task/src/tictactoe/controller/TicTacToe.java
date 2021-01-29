package tictactoe.controller;

import tictactoe.model.Field;
import tictactoe.view.Console;
import tictactoe.ai.AIPlayer;

import static tictactoe.constant.Constants.*;

public class TicTacToe {

    private String command;

    private String xPlayer;

    private String oPlayer;

    private final Console console = new Console();

    private final AIPlayer AIPlayer;

    private final Field field = new Field();

    public TicTacToe() {

        AIPlayer = new AIPlayer();

    }

    public void run() {

        setParams();

        if (command.equals(START_COMMAND)) {

            while (!command.equals(EXIT_COMMAND)) {

                field.charsInit();

                console.displayField(field);

                int move = field.getCurrentLevel();

                while (move <= FIELD_SIZE * FIELD_SIZE) {

                    if (move % 2 == 0) {

                        if (xPlayer.equals(USER)) {

                            setUserCoordinates(X);

                        } else {

                            setAICoordinates(xPlayer, X);

                        }

                    } else {

                        if (oPlayer.equals(USER)) {

                            setUserCoordinates(O);

                        } else {

                            setAICoordinates(oPlayer, O);

                        }

                    }

                    console.displayField(field);

                    if (isGameOver()) {

                        break;

                    }

                    move = field.getCurrentLevel();

                }

                setParams();

            }

        }

    }

    private void setParams() {

        String[] params = console.getParams();

        if (params.length == 1) {

            command = params[0];

        } else if (params.length == 3) {

            command = params[0];

            xPlayer = params[1];

            oPlayer = params[2];
        }

    }

    private void setAICoordinates(String aiLevel, char ch) {

        console.displayAiLevel(aiLevel);

        switch (aiLevel) {

            case AI_EASY:

                AIPlayer.moveEasyLevel(field, ch);
                break;

            case AI_MEDIUM:

                AIPlayer.moveMediumLevel(field, ch);
                break;

            case AI_HARD:

                AIPlayer.moveHardLevel(field, ch);
                break;

        }

    }

    private void setUserCoordinates(char ch) {

        console.setCoordinates(field, ch);

    }

    private boolean isGameOver() {

        if (field.status().equals(GAME_NOT_FINISHED)) {

            return false;

        }

        console.displayStatus(field.status());

        console.printEmptyLine();

        return true;

    }

}
