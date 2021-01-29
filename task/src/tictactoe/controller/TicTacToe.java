package tictactoe.controller;

import tictactoe.model.Field;
import tictactoe.view.View;
import tictactoe.ai.AIPlayer;

import static tictactoe.constant.Constants.*;

public class TicTacToe {

    private String command;

    private String xPlayer;

    private String oPlayer;

    private final View view;

    private final AIPlayer AIPlayer;

    private final Field field;

    public TicTacToe() {

        field = new Field();

        view = new View();

        AIPlayer = new AIPlayer();

    }

    public void run() {

        getParameters();

        if (command.equals(START_COMMAND)) {

            while (!command.equals(EXIT_COMMAND)) {

                field.charsInit();

                view.printField(field);

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

                    view.printField(field);

                    if (isGameOver()) {

                        break;

                    }

                    move = field.getCurrentLevel();

                }

                getParameters();

            }

        }

    }

    private void getParameters() {

        int numberOfExitParams = 1;

        int numberOfStartParams = 3;

        String[] params = view.inputParameters();

        if (params.length == numberOfExitParams) {

            command = params[0];

        } else if (params.length == numberOfStartParams) {

            command = params[0];

            xPlayer = params[1];

            oPlayer = params[2];
        }

    }

    private void setAICoordinates(String aiLevel, char ch) {

        view.printAILevel(aiLevel);

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

        view.enterCoordinates(field, ch);

    }

    private boolean isGameOver() {

        if (field.status().equals(GAME_NOT_FINISHED)) {

            return false;

        }

        view.printStatus(field.status());

        view.printEmptyLine();

        return true;

    }

}
