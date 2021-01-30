package tictactoe.ai.impl;

import tictactoe.ai.IAi;
import tictactoe.model.Field;


import static tictactoe.constant.Constants.*;

public class HardAi implements IAi {

    private final EasyAi easyAi = new EasyAi();

    @Override
    public void move(Field field, char ch) {

        if (field.getCurrentLevel() == 0) {

            easyAi.move(field, ch);

        } else {

            HardMove hardMove = doMiniMax(field, ch);

            int idx = hardMove.idxBestScore;

            field.setChar(idx, ch);

        }

    }

    private HardMove doMiniMax(Field field, char ch) {

        int bestScore;

        int idxBestScore = -1;

        if (!field.getStatus().equals(GAME_NOT_FINISHED)) {

            return new HardMove(getScore(field));

        }

        if (ch == X) {

            bestScore = Integer.MIN_VALUE;

            for (int idx = 0; idx < FIELD_SIZE * FIELD_SIZE; idx++) {

                if (field.isSpace(idx)) {

                    field.setChar(idx, ch);

                    HardMove hardMove = doMiniMax(field, O);

                    if (hardMove.bestScore > bestScore) {

                        bestScore = hardMove.bestScore;

                        idxBestScore = idx;

                    }

                    field.setSpace(idx);

                }

            }

        } else {

            bestScore = Integer.MAX_VALUE;

            for (int idx = 0; idx < FIELD_SIZE * FIELD_SIZE; idx++) {

                if (field.isSpace(idx)) {

                    field.setChar(idx, ch);

                    HardMove hardMove = doMiniMax(field, X);

                    if (hardMove.bestScore < bestScore) {

                        bestScore = hardMove.bestScore;

                        idxBestScore = idx;

                    }

                    field.setSpace(idx);

                }

            }

        }

        return new HardMove(bestScore, idxBestScore);

    }

    private int getScore(Field field) {

        if (field.getStatus().equals(X_WINS)) {

            return 10;

        } else if (field.getStatus().equals(O_WINS)) {

            return -10;

        }

        return 0;

    }

    private static class HardMove {

        int bestScore;

        int idxBestScore;

        public HardMove(int bestScore, int idxBestScore) {

            this.bestScore = bestScore;

            this.idxBestScore = idxBestScore;

        }

        public HardMove(int bestScore) {

            this.bestScore = bestScore;

        }

    }

}
