package tictactoe.view;

import tictactoe.model.Field;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Predicate;

import static tictactoe.constant.Constants.*;

public class Console {

    private static final String FRAMING_LINE = "-".repeat(FIELD_SIZE * FIELD_SIZE);

    private static final String INPUT_COMMAND = "Input command: ";

    private static final String BAD_PARAMETERS = "Bad parameters!";

    private static final String ENTER_COORDINATES = "Enter the coordinates: > ";

    private static final String OCCUPIED_CELL_ERROR = "This cell is occupied! Choose another one!";

    private static final String ENTER_NUMBERS_ERROR = "You should enter numbers!";

    private static final String COORDINATES_VALUES_ERROR = "Coordinates should be from 1 to 3!";

    private static final String MAKING_MOVE = "Making move level ";

    private static final char BOUNDARY_CHAR = '|';

    private final Scanner scanner = new Scanner(System.in);

    private final char[] displayedChars = new char[FIELD_SIZE * FIELD_SIZE];

    private static final String[] PARAMETERS =
            {START_COMMAND, EXIT_COMMAND, USER, AI_EASY, AI_MEDIUM, AI_HARD};

    public Console() {

        charsInit();

    }

    public void displayField(Field field) {

        int fillingStep = 2;

        System.out.println(FRAMING_LINE);

        int idxChars = fillingStep;

        for (int idx = 0; idx < FIELD_SIZE * FIELD_SIZE; idx++) {

            displayedChars[idxChars] = field.getChar(idx);

            idxChars += fillingStep;

            if ((idx + 1) % FIELD_SIZE == 0) {

                System.out.println(displayedChars);

                idxChars = fillingStep;

            }

        }

        System.out.println(FRAMING_LINE);

    }

    public void printEmptyLine() {

        System.out.println();

    }

    public void displayStatus(String status) {

        System.out.println(status);

    }

    public void displayAiLevel(String aiLevel) {

        System.out.println(MAKING_MOVE + "\"" + aiLevel + "\"");

    }

    public String[] getParams() {

        String[] params;

        while (true) {

            System.out.print(INPUT_COMMAND);

            params = getArgs(this::isParamValid);

            if (params != null) {

                if (params[0].equals(EXIT_COMMAND) ||
                   (params[0].equals(START_COMMAND) && params.length == 3)) {

                    break;

                }

            }

            System.out.println(BAD_PARAMETERS);

        }

        return params;

    }

    public void setCoordinates(Field field, char ch) {

        while(true) {

            System.out.print(ENTER_COORDINATES);

            String[] coordinates = getArgs(this::isCoordinateDigit);

            if (coordinates != null) {

                int[] values = Arrays.stream(coordinates)
                                     .mapToInt(Integer::parseInt)
                                     .filter(this::isCoordinateValid)
                                     .toArray();

                if (values.length == 2) {

                    int idx = toIdxOfField(values[0], values[1]);

                    if (field.isSpace(idx)) {

                        field.setChar(idx, ch);

                        break;

                    }

                    System.out.println(OCCUPIED_CELL_ERROR);

                } else {

                    System.out.println(COORDINATES_VALUES_ERROR);

                }

            } else  {

                System.out.println(ENTER_NUMBERS_ERROR);

            }

        }

    }

    private void charsInit() {

        Arrays.fill(displayedChars, SPACE);

        displayedChars[0] = BOUNDARY_CHAR;

        displayedChars[displayedChars.length - 1] = BOUNDARY_CHAR;

    }

    private String[] getArgs(Predicate<String> predicate) {

        String[] tokens = scanner.nextLine().split("\\s+");

        if ("".equals(tokens[0])) {

            return null;

        }

        String[] args = Arrays.stream(tokens)
                              .filter(predicate)
                              .toArray(String[]::new);

        return args.length > 0 ? args : null;

    }

    private int toIdxOfField(int idxRow, int idxCol) {

        int rowFactor = 3;

        int colFactor = 1;

        return rowFactor * (rowFactor - idxRow) + (idxCol - colFactor);

    }

    private boolean isParamValid(String param) {

        return Arrays.asList(PARAMETERS).contains(param);

    }

    private boolean isCoordinateDigit(String value) {

        try {

            Integer.parseInt(value);

            return true;

        } catch (NumberFormatException e) {

            return false;

        }

    }

    private boolean isCoordinateValid(int value) {

        int minCoordinate = 1;

        int maxCoordinate = 3;

        return value <= maxCoordinate && value >= minCoordinate;

    }

}
