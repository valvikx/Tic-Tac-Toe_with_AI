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

        boolean isWinsX = hasCompleteMatchByRowsAndCols(X) || hasCompleteMatchByDiagonals(X);

        boolean isWinsO = hasCompleteMatchByRowsAndCols(O) || hasCompleteMatchByDiagonals(O);

        if (!isWinsX && !isWinsO && currentLevel == FIELD_SIZE * FIELD_SIZE) {

            return DRAW;

        } else if (isWinsX) {

            return X_WINS;

        } else if (isWinsO) {

            return O_WINS;

        }

        return GAME_NOT_FINISHED;

    }

    private boolean hasCompleteMatchByRowsAndCols(char ch) {

        if (Stream.iterate(0, i -> i + FIELD_SIZE)
                  .limit(FIELD_SIZE)
                  .allMatch(i -> hasCompleteMatch(i, idxOffsetByRows, ch))) {

            return true;

        }

        return Stream.iterate(0, i -> i + 1)
                     .limit(FIELD_SIZE)
                     .allMatch(i -> hasCompleteMatch(i, idxOffsetByCols, ch));

    }

    private boolean hasCompleteMatchByDiagonals(char ch) {

        return hasCompleteMatch(mainDiagStartIdx, mainDiagIdxOffset, ch) ||
               hasCompleteMatch(sideDiagStartIdx, sideDiagIdxOffset, ch);

    }

    private boolean hasCompleteMatch(int startIdx, int idxOffset, char ch) {

        return chars[startIdx] == ch &&
               chars[startIdx + idxOffset] == ch &&
               chars[startIdx + 2 * idxOffset] == ch;

    }

    public int getIdxOfPossibleCompleteMatch(char ch) {

        int idx = getIdxOfPossibleCompleteMatchByRowsAndCols(ch);

        if (idx > -1) {

            return idx;

        }

        return getIdxOfPossibleCompleteMatchByDiagonals(ch);

    }

    private int getIdxOfPossibleCompleteMatchByRowsAndCols(char ch) {

        int idx = Stream.iterate(0, i -> i + FIELD_SIZE)
                        .limit(FIELD_SIZE)
                        .map(i -> getIdxOfPossibleCompleteMatch(i, idxOffsetByRows, ch))
                        .filter(i -> i > -1)
                        .findFirst()
                        .orElse(-1);

        if (idx > -1) {

            return idx;

        }

        return Stream.iterate(0, i -> i + 1)
                     .limit(FIELD_SIZE)
                     .map(i -> getIdxOfPossibleCompleteMatch(i, idxOffsetByCols, ch))
                     .filter(i -> i > -1)
                     .findFirst()
                     .orElse(-1);

    }

    private int getIdxOfPossibleCompleteMatchByDiagonals(char ch) {

        int idx = getIdxOfPossibleCompleteMatch(mainDiagStartIdx, mainDiagIdxOffset, ch);

        if (idx > -1) {

            return idx;

        }

        return getIdxOfPossibleCompleteMatch(sideDiagStartIdx, sideDiagIdxOffset, ch);

    }

    private int getIdxOfPossibleCompleteMatch(int startIdx, int idxOffset, char ch) {

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
