package tictactoe;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        char[][] gamefield = new char[3][3];
        boolean xIsNext = true;
        for (char[] row : gamefield) { // column
            Arrays.fill(row, '_');
        }
        // first winner possible after 4 successful moves
        for (int i = 1; i <= 4; i++) {
            int[] inputCoord = inputCoordinates(gamefield);
            printField(changeGamefield(gamefield, inputCoord, xIsNext));
            xIsNext = !xIsNext;
        }
        // winner check activated
        while (!gameCheck(gamefield)) {
            int[] inputCoord = inputCoordinates(gamefield);
            printField(changeGamefield(gamefield, inputCoord, xIsNext));
            xIsNext = !xIsNext;
        }
    }

    private static void printField(char[][] gamefield) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                switch (j) {
                    case 0:
                        System.out.print("| " + gamefield[i][j] + " ");
                        break;
                    case 1:
                        System.out.print(gamefield[i][j] + " ");
                        break;
                    case 2:
                        System.out.print(gamefield[i][j]+ " |\n");
                        break;
                }
            }
        }
        System.out.println("---------");
    }

    private static int[] inputCoordinates(char[][] gamefield) {
        boolean retry = false;
        int[] inputCoord = new int[]{-1, -1};
        while (!retry) {
            Scanner scan = new Scanner(System.in);
            inputCoord = new int[]{-1, -1};
            System.out.print("Enter the coordinates: ");
            try {
                boolean coordinatesPossible = (inputCoord[0] <= 3 && inputCoord[0] >= 1 &&
                        inputCoord[1] <= 3 && inputCoord[1] >= 1);
                while (!coordinatesPossible) {
                    inputCoord[0] = scan.nextInt(); // left - right
                    inputCoord[1] = scan.nextInt(); // bottom - up
                    coordinatesPossible = (inputCoord[0] <= 3 && inputCoord[0] >= 1 &&
                            inputCoord[1] <= 3 && inputCoord[1] >= 1);
                    if (!coordinatesPossible) {
                        System.out.println("Coordinates should be from 1 to 3!");
                        System.out.print("Enter the coordinates: ");
                    }
                }
                retry = checkCoordinates(gamefield, inputCoord);
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
                retry = false;
            }
        }
        return inputCoord;
    }

    private static boolean checkCoordinates(char[][] gamefield, int[] inputCoordinates) {
        int i = 3 - inputCoordinates[1]; // row
        int j = inputCoordinates[0] - 1; // column

        if (gamefield[i][j] == 'X' || gamefield[i][j] == 'O') {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        } else {
            return true;
        }
    }

    private static char[][] changeGamefield (char[][] gamefield, int[] inputCoordinates, boolean xIsNext) {
        int i = 3 - inputCoordinates[1]; // row
        int j = inputCoordinates[0] - 1; // column
        if (xIsNext) {
            gamefield[i][j] = 'X';
        } else {
            gamefield[i][j] = 'O';
        }
        return gamefield;
    }

    private static boolean gameCheck(char[][] gamefield) {
        // check if possible by number of X and O
        int countX = 0;
        int countO = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                switch (gamefield[i][j]) {
                    case 'X':
                        countX++;
                        break;
                    case 'O':
                        countO++;
                        break;
                }
            }
        }
        // check rows for winner
        for (int i = 0; i < 3; i++) {
            if (gamefield[i][0] == gamefield[i][1] && gamefield[i][0] == gamefield[i][2] && gamefield[i][0] != '_') {
                System.out.println(gamefield[i][0] + " wins");
                return true;
            }
        }
        // check columns for winner
        for (int j = 0; j < 3; j++) {
            if (gamefield[0][j] == gamefield[1][j] && gamefield[0][j] == gamefield[2][j] && gamefield[0][j] != '_') {
                System.out.println(gamefield[0][j] + " wins");
                return true;
            }
        }
        // check diagonal for winner
        if (gamefield[0][0] == gamefield[1][1] && gamefield[0][0] == gamefield[2][2] && gamefield[0][0] != '_') {
            System.out.println(gamefield[1][1] + " wins");
            return true;
        } else if (gamefield[0][2] == gamefield[1][1] && gamefield[2][0] == gamefield[0][2] && gamefield[1][1] != '_') {
            System.out.println(gamefield[1][1] + " wins");
            return true;
        }
        // check if finished
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gamefield[i][j] == '_') {
                    return false; // "Game not finished"
                }
            }
        }
        System.out.println("Draw");
        return true;
    }
}
