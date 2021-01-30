package tictactoe.controller;

import tictactoe.ai.IAi;
import tictactoe.ai.impl.EasyAi;
import tictactoe.ai.impl.HardAi;
import tictactoe.ai.impl.MediumAi;
import tictactoe.model.Field;
import tictactoe.view.Console;

import java.util.Map;

import static tictactoe.constant.Constants.*;

public class TicTacToe {

    private String command;

    private String xPlayer;

    private String oPlayer;

    private final Console console = new Console();

    private final Map<String, IAi> aiPlayer;

    private final Field field = new Field();

    public TicTacToe() {

        IAi easyAi = new EasyAi();

        IAi mediumAi = new MediumAi();

        IAi hardAi = new HardAi();

        aiPlayer = Map.of(AI_EASY, easyAi,
                          AI_MEDIUM, mediumAi,
                          AI_HARD, hardAi);

    }

    public void run() {

        setParams();

        if (command.equals(EXIT_COMMAND)) {

            return;

        }

        while (!command.equals(EXIT_COMMAND)) {

            field.init();

            console.displayField(field);

            int move = field.getCurrentLevel();

            while (move <= FIELD_SIZE * FIELD_SIZE) {

                if (move % 2 == 0) {

                    if (xPlayer.equals(USER)) {

                        userMove(X);

                    } else {

                        aiMove(xPlayer, X);

                    }

                } else {

                    if (oPlayer.equals(USER)) {

                        userMove(O);

                    } else {

                        aiMove(oPlayer, O);

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

    private void aiMove(String aiLevel, char ch) {

        console.displayAiLevel(aiLevel);

        aiPlayer.get(aiLevel).move(field, ch);

    }

    private void userMove(char ch) {

        console.setCoordinates(field, ch);

    }

    private boolean isGameOver() {

        if (field.getStatus().equals(GAME_NOT_FINISHED)) {

            return false;

        }

        console.displayStatus(field.getStatus());

        return true;

    }

}
