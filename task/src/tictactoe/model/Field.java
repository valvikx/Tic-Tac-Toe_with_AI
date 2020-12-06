package tictactoe.model;

import java.util.Arrays;

import static tictactoe.consts.GameConstants.*;

public class Field {

    private final char[] chars;

    private int fillingLevel;

    public Field() {

        chars = new char[FIELD_SIZE * FIELD_SIZE];

        fillingLevel = 0;

        charsInit();

    }

    public void charsInit() {

        Arrays.fill(chars, SPACE);

        fillingLevel = 0;

    }

    public char getChar(int idx) {

        return chars[idx];

    }

    public void setChar(int idx, char ch) {

        chars[idx] = ch;

        fillingLevel++;

    }

    public void setSpace(int idx) {

        chars[idx] = SPACE;

        fillingLevel--;

    }

    public boolean isSpace(int idx) {

        return chars[idx] == SPACE;

    }

    public int getFillingLevel() {

        return fillingLevel;

    }

    public String status() {

        int minCheckingLevel = 5;

        if (fillingLevel < minCheckingLevel) {

            return GAME_NOT_FINISHED;

        }

        boolean isWinsX = hasCompleteMatchByRowsAndCols(X) || hasCompleteMatchByDiagonals(X);

        boolean isWinsO = hasCompleteMatchByRowsAndCols(O) || hasCompleteMatchByDiagonals(O);

        if (!isWinsX && !isWinsO && fillingLevel == FIELD_SIZE * FIELD_SIZE) {

            return DRAW;

        } else if (isWinsX) {

            return X_WINS;

        } else if (isWinsO) {

            return O_WINS;

        }

        return GAME_NOT_FINISHED;

    }

    private boolean hasCompleteMatchByRowsAndCols(char ch) {

        int rowIdxOffset = 1;

        for (int startIdx = 0; startIdx < chars.length; startIdx += FIELD_SIZE) {

            if (isCompleteMatch(startIdx, rowIdxOffset, ch)) {

                return true;

            }

        }

        for (int startIdx = 0; startIdx < FIELD_SIZE; startIdx++) {

            if (isCompleteMatch(startIdx, FIELD_SIZE, ch)) {

                return true;

            }

        }

        return false;

    }

    private boolean hasCompleteMatchByDiagonals(char ch) {

        int mainDiagStartIdx = 0;

        int mainDiagOffset = 4;

        boolean isCompleteMatchByMainDiag = isCompleteMatch(mainDiagStartIdx, mainDiagOffset, ch);

        if (isCompleteMatchByMainDiag) {

            return true;

        }

        int sideDiagStartIdx = 2;

        int sideDiagOffset = 2;

        return isCompleteMatch(sideDiagStartIdx, sideDiagOffset, ch);

    }

    private boolean isCompleteMatch(int startIdx, int offset, char ch) {

        return chars[startIdx] == ch &&
               chars[startIdx + offset] == ch &&
               chars[startIdx + offset + offset] == ch;

    }

    public int idxSuitableMatch(char ch) {

        int idx = idxSuitableMatchByRowsAndCols(ch);

        if (idx > -1) {

            return idx;

        } else {

            idx = idxSuitableMatchByDiagonals(ch);

            if (idx > -1) {

                return idx;

            }

        }

        return -1;

    }

    private int idxSuitableMatchByRowsAndCols(char ch) {

        int rowIdxOffset = 1;

        for (int startIdx = 0; startIdx < FIELD_SIZE * FIELD_SIZE; startIdx += FIELD_SIZE) {

            int idx = idxSuitableMatch(startIdx, rowIdxOffset, ch);

            if (idx > -1) {

                return idx;

            }

        }

        for (int startIdx = 0; startIdx < FIELD_SIZE; startIdx++) {

            int idx = idxSuitableMatch(startIdx, FIELD_SIZE, ch);

            if (idx > -1) {

                return idx;

            }

        }

        return -1;

    }

    private int idxSuitableMatchByDiagonals(char ch) {

        int mainDiagStartIdx = 0;

        int mainDiagOffset = 4;

        int idx = idxSuitableMatch(mainDiagStartIdx, mainDiagOffset, ch);

        if (idx > -1) {

            return idx;

        }

        int sideDiagStartIdx = 2;

        int sideDiagOffset = 2;

        return idxSuitableMatch(sideDiagStartIdx, sideDiagOffset, ch);

    }

    private int idxSuitableMatch(int startIdx, int offset, char ch) {

        if (chars[startIdx] == SPACE &&
            hasCompleteMatchAfterSetChar(startIdx, startIdx, offset, ch)) {

            return startIdx;

        } else if (chars[startIdx + offset] == SPACE &&
                   hasCompleteMatchAfterSetChar(startIdx + offset, startIdx, offset, ch)) {

            return startIdx + offset;

        } else if (chars[startIdx + offset + offset] == SPACE &&
                   hasCompleteMatchAfterSetChar(startIdx + offset + offset, startIdx, offset, ch)) {

            return startIdx + offset + offset;

        }

        return -1;

    }

    private boolean hasCompleteMatchAfterSetChar(int idxOfChar, int startIdx, int offset, char ch) {

        setChar(idxOfChar, ch);

        boolean isCompleteMatch = isCompleteMatch(startIdx, offset, ch);

        setSpace(idxOfChar);

        return isCompleteMatch;

    }

}
