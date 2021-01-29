package tictactoe.ai;

import tictactoe.model.Field;

import java.util.Random;

import static tictactoe.constant.Constants.*;

public class AIPlayer {

    private final Random random;

    public AIPlayer() {

        random = new Random();

    }

    public void moveEasyLevel(Field field, char ch) {

        int idx = random.nextInt(FIELD_SIZE * FIELD_SIZE);

        while (!field.isSpace(idx)) {

            idx = idx == FIELD_SIZE * FIELD_SIZE - 1 ? 0 : ++idx;

        }

        field.setChar(idx, ch);

    }

    public void moveMediumLevel(Field field, char ch) {

        int idx = field.getIdxOfPossibleCompleteMatch(ch);

        if (idx > -1) {

            field.setChar(idx, ch);

        } else {

            ch = oppositeChar(ch);

            if ((idx = field.getIdxOfPossibleCompleteMatch(ch)) > -1) {

                ch = oppositeChar(ch);

                field.setChar(idx, ch);

            } else {

                ch = oppositeChar(ch);

                moveEasyLevel(field, ch);

            }

        }

    }

    public void moveHardLevel(Field field, char ch) {

        int idx;

        if (field.getCurrentLevel() == 0) {

            idx = random.nextInt(FIELD_SIZE * FIELD_SIZE);

        } else {

            HardMove hardMove = doMiniMax(field, ch);

            idx = hardMove.idxBestScore;

        }

        field.setChar(idx, ch);

    }

    private HardMove doMiniMax(Field field, char ch) {

        int bestScore;

        int idxBestScore = -1;

        if (!field.status().equals(GAME_NOT_FINISHED)) {

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

        if (field.status().equals(X_WINS)) {

            return 10;

        } else if (field.status().equals(O_WINS)) {

            return -10;

        }

        return 0;

    }

    private char oppositeChar(char ch) {

        return ch == X ? O : X;

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
