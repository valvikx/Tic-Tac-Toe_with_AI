package tictactoe.ai.impl;

import tictactoe.ai.IAi;
import tictactoe.model.Field;

import static tictactoe.constant.Constants.O;
import static tictactoe.constant.Constants.X;

public class MediumAi implements IAi {

    private final EasyAi easyAi = new EasyAi();

    @Override
    public void move(Field field, char ch) {

        int idx = field.getIdxOfPossibleMatch(ch);

        if (idx > -1) {

            field.setChar(idx, ch);

        } else {

            ch = opposite(ch);

            idx = field.getIdxOfPossibleMatch(ch);

            ch = opposite(ch);

            if (idx > -1) {

                field.setChar(idx, ch);

            } else {

                easyAi.move(field, ch);

            }

        }

    }

    private char opposite(char ch) {

        return ch == X ? O : X;

    }

}
