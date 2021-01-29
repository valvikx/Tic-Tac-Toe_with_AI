package tictactoe.model;

import java.util.Arrays;
import java.util.stream.Stream;

import static tictactoe.constant.Constants.*;

public class Field {

    private final char[] chars;

    private int currentLevel;

    private final int idxOffsetByRows = 1;

    private final int idxOffsetByCols = FIELD_SIZE;

    private final int mainDiagStartIdx = 0;

    private final int mainDiagIdxOffset = 4;

    private final int sideDiagStartIdx = 2;

    private final int sideDiagIdxOffset = 2;

    public Field() {

        chars = new char[FIELD_SIZE * FIELD_SIZE];

        charsInit();

    }

    public void charsInit() {

        Arrays.fill(chars, SPACE);

        currentLevel = 0;

    }

    public char getChar(int idx) {

        return chars[idx];

    }

    public void setChar(int idx, char ch) {

        chars[idx] = ch;

        currentLevel++;

    }

    public void setSpace(int idx) {

        chars[idx] = SPACE;

        currentLevel--;

    }

    public boolean isSpace(int idx) {

        return chars[idx] == SPACE;

    }

    public int getCurrentLevel() {

        return currentLevel;

    }

    public String status() {

        int minGameOverLevel = 6;

        if (currentLevel < minGameOverLevel) {

            return GAME_NOT_FINISHED;

        }

        boolean isWinsX = hasAllMatchByRowsAndCols(X) || hasAllMatchByDiagonals(X);

        boolean isWinsO = hasAllMatchByRowsAndCols(O) || hasAllMatchByDiagonals(O);

        if (!isWinsX && !isWinsO && currentLevel == FIELD_SIZE * FIELD_SIZE) {

            return DRAW;

        } else if (isWinsX) {

            return X_WINS;

        } else if (isWinsO) {

            return O_WINS;

        }

        return GAME_NOT_FINISHED;

    }

    public int getIdxOfPossibleMatch(char ch) {

        int idx = getIdxOfPossibleMatchByRowsAndCols(ch);

        if (idx > -1) {

            return idx;

        }

        return getIdxOfPossibleMatchByDiagonals(ch);

    }

    private boolean hasAllMatchByRowsAndCols(char ch) {

        if (Stream.iterate(0, i -> i + FIELD_SIZE)
                  .limit(FIELD_SIZE)
                  .allMatch(i -> hasAllMatch(i, idxOffsetByRows, ch))) {

            return true;

        }

        return Stream.iterate(0, i -> i + 1)
                     .limit(FIELD_SIZE)
                     .allMatch(i -> hasAllMatch(i, idxOffsetByCols, ch));

    }

    private boolean hasAllMatchByDiagonals(char ch) {

        return hasAllMatch(mainDiagStartIdx, mainDiagIdxOffset, ch) ||
               hasAllMatch(sideDiagStartIdx, sideDiagIdxOffset, ch);

    }

    private boolean hasAllMatch(int startIdx, int idxOffset, char ch) {

        return chars[startIdx] == ch &&
               chars[startIdx + idxOffset] == ch &&
               chars[startIdx + 2 * idxOffset] == ch;

    }

    private int getIdxOfPossibleMatchByRowsAndCols(char ch) {

        int idx = Stream.iterate(0, i -> i + FIELD_SIZE)
                        .limit(FIELD_SIZE)
                        .map(i -> getIdx(i, idxOffsetByRows, ch))
                        .filter(i -> i > -1)
                        .findFirst()
                        .orElse(-1);

        if (idx > -1) {

            return idx;

        }

        return Stream.iterate(0, i -> i + 1)
                     .limit(FIELD_SIZE)
                     .map(i -> getIdx(i, idxOffsetByCols, ch))
                     .filter(i -> i > -1)
                     .findFirst()
                     .orElse(-1);

    }

    private int getIdxOfPossibleMatchByDiagonals(char ch) {

        int idx = getIdx(mainDiagStartIdx, mainDiagIdxOffset, ch);

        if (idx > -1) {

            return idx;

        }

        return getIdx(sideDiagStartIdx, sideDiagIdxOffset, ch);

    }

    private int getIdx(int startIdx, int idxOffset, char ch) {

        if (chars[startIdx] == SPACE &&
            chars[startIdx + idxOffset] == ch &&
            chars[startIdx + 2 * idxOffset] == ch) {

            return startIdx;

        } else if (chars[startIdx] == ch &&
                   chars[startIdx + idxOffset] == SPACE &&
                   chars[startIdx + 2 * idxOffset] == ch) {

            return startIdx + idxOffset;

        } else if (chars[startIdx] == ch &&
                   chars[startIdx + idxOffset] == ch &&
                   chars[startIdx + 2 * idxOffset] == SPACE) {

            return startIdx + 2 * idxOffset;

        }

        return -1;

    }

}
