package tictactoe.view;

import tictactoe.model.Field;

import java.util.Arrays;
import java.util.Scanner;

import static tictactoe.consts.GameConstants.*;

public class View {

    private static final String FRAMING_LINE = "-".repeat(FIELD_SIZE * FIELD_SIZE);

    private static final String INPUT_COMMAND = "Input command: ";

    private static final String BAD_PARAMETERS = "Bad parameters!";

    private static final String ENTER_COORDINATES = "Enter the coordinates: > ";

    private static final String ERROR_OCCUPIED = "This cell is occupied! Choose another one!";

    private static final String ERROR_NUMBERS = "You should enter numbers!";

    private static final String ERROR_COORDINATES = "Coordinates should be from 1 to 3!";

    private static final String MAKING_MOVE = "Making move level ";

    private static final char BOUNDARY_CHAR = '|';

    private final char[] chars;

    private final Scanner scanner;

    private static final String[] PARAMETERS = {USER, AI_EASY, AI_MEDIUM, AI_HARD};

    public View() {

        scanner = new Scanner(System.in);

        chars = new char[FIELD_SIZE * FIELD_SIZE];

        charsInit();

    }

    public void printField(Field field) {

        int fillingStep = 2;

        System.out.println(FRAMING_LINE);

        int idxChars = fillingStep;

        for (int idx = 0; idx < FIELD_SIZE * FIELD_SIZE; idx++) {

            chars[idxChars] = field.getChar(idx);

            idxChars += fillingStep;

            if ((idx + 1) % FIELD_SIZE == 0) {

                System.out.println(chars);

                idxChars = fillingStep;

            }

        }

        System.out.println(FRAMING_LINE);

    }

    public void printEmptyLine() {

        System.out.println();

    }

    public void printStatus(String status) {

        System.out.println(status);

    }

    public void printAILevel(String aiLevel) {

        System.out.println(MAKING_MOVE + "\"" + aiLevel + "\"");

    }

    public String[] inputParameters() {

        String[] params = {EXIT_COMMAND};

        boolean isInputParams = false;

        while (!isInputParams) {

            System.out.print(INPUT_COMMAND);

            String paramsLine = scanner.nextLine();

            if (paramsLine.equals("")) {

               paramsLine = scanner.nextLine();

            }

            params = paramsLine.split(String.valueOf(SPACE));

            if (params[0].equals(START_COMMAND)) {

                if (params.length == 3) {

                    if (checkParameter(params[1]) & checkParameter(params[2])) {

                        isInputParams = true;

                    } else {

                        System.out.println(BAD_PARAMETERS);

                    }

                } else {

                    System.out.println(BAD_PARAMETERS);

                }

            } else if (params[0].equals(EXIT_COMMAND)) {

                isInputParams = true;

            } else {

                System.out.println(BAD_PARAMETERS);

            }

        }

        return params;

    }

    public void enterCoordinates(Field field, char ch) {

        int idxRowField;

        int idxColField;

        int idxFieldChars;

        int minCoordinate = 1;

        int maxCoordinate = 3;

        boolean isEnterCoordinates = false;

        while(!isEnterCoordinates) {

            System.out.print(ENTER_COORDINATES);

            if (scanner.hasNextInt()) {

                idxColField = scanner.nextInt();

                if (scanner.hasNextInt()) {

                    idxRowField = scanner.nextInt();

                    if (idxRowField < minCoordinate || idxRowField > maxCoordinate ||
                        idxColField < minCoordinate || idxColField > maxCoordinate) {

                        System.out.println(ERROR_COORDINATES);

                    } else {

                        idxFieldChars = convertToIdxFieldChars(idxRowField, idxColField);

                        if (field.isSpace(idxFieldChars)) {

                            field.setChar(idxFieldChars, ch);

                            isEnterCoordinates = true;

                        } else {

                            System.out.println(ERROR_OCCUPIED);

                        }

                    }

                } else {

                    System.out.println(ERROR_NUMBERS);

                    scanner.next();

                }

            } else {

                System.out.println(ERROR_NUMBERS);

                scanner.next();

            }

        }

    }

    private void charsInit() {

        Arrays.fill(chars, SPACE);

        chars[0] = BOUNDARY_CHAR;

        chars[chars.length - 1] = BOUNDARY_CHAR;

    }

    private int convertToIdxFieldChars(int idxRowField, int idxColField) {

        int rowFactor = 3;

        int colFactor = 1;

        return rowFactor * (rowFactor - idxRowField) + (idxColField - colFactor);

    }

    private boolean checkParameter(String param) {

        for (String p : PARAMETERS) {

            if (p.equals(param)) {

                return true;

            }

        }

        return false;
    }

}
