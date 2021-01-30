package tictactoe.ai.impl;

import tictactoe.ai.IAi;
import tictactoe.model.Field;

import java.util.Random;

import static tictactoe.constant.Constants.FIELD_SIZE;

public class EasyAi implements IAi {

    private final Random random = new Random();

    @Override
    public void move(Field field, char ch) {

        int idx = random.nextInt(FIELD_SIZE * FIELD_SIZE);

        while (!field.isSpace(idx)) {

            idx = idx == FIELD_SIZE * FIELD_SIZE - 1 ? 0 : ++idx;

        }

        field.setChar(idx, ch);

    }

}
