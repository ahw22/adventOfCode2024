package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static int index = -1;
    static int row = -1;
    static int mapLength;
    static int mapHeight;
    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    static ArrayList<String> map = new ArrayList<>();
    static Direction currentDirection = Direction.UP;

    public static void main(String[] args) {
        String filePath = "src/main/java/day6/data.txt";
        readFile(filePath);

        mapLength = map.getFirst().length()-1;
        mapHeight = map.size()-1;

        getStartingIndex();
        while (navigateMap());
        countPathLength();
    }

    private static void countPathLength() {
        int length = 0;
        for (String line : map) {
            for (char c : line.toCharArray()) {
                if (c == 'X') {
                    length++;
                }
            }
        }
        System.out.println("Length is: " + (length + 1));
    }

    private static boolean navigateMap() {
        //0 = row 1 = index
        int[] nextMove = {row, index};
        switch (currentDirection) {
            case UP -> nextMove[0] = row - 1;
            case DOWN -> nextMove[0] = row + 1;
            case LEFT -> nextMove[1] = index - 1;
            case RIGHT -> nextMove[1] = index + 1;
        }
        if (nextMove[0] < 0 || nextMove[0] > mapHeight || nextMove[1] < 0 || nextMove[1] > mapLength) {
            return false;
        }
        if (map.get(nextMove[0]).charAt(nextMove[1]) == '#') {
            switch (currentDirection) {
                case UP -> currentDirection = Direction.RIGHT;
                case DOWN -> currentDirection = Direction.LEFT;
                case LEFT -> currentDirection = Direction.UP;
                case RIGHT -> currentDirection = Direction.DOWN;
            }
        } else {
            //move one space in currentDirection and change current pos to new pos
            //System.out.println("Going " + currentDirection + " next Move is Row " + nextMove[0] + " Index " + nextMove[1]);
            String currentLine = map.get(row);
            //System.out.println("CurrentLine: " + currentLine);
            char[] tempArray = currentLine.toCharArray();
            tempArray[index] = 'X';
            currentLine = String.copyValueOf(tempArray);
            map.set(row, currentLine);

            String nextLine = map.get(nextMove[0]);
            //System.out.println("NextLine" + nextLine);
            tempArray = nextLine.toCharArray();
            tempArray[nextMove[1]] = '^';
            nextLine = String.copyValueOf(tempArray);
            map.set(nextMove[0], nextLine);
            row = nextMove[0];
            index = nextMove[1];
        }
        return true;
    }

    private static void getStartingIndex() {
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i).contains("^")) {
                index = map.get(i).indexOf("^");
                row = i;
            }
        }
    }


    private static void readFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                map.add(line);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong reading in the new line");
        }
    }

    private static void drawMap() {
        for (String line : map) {
            System.out.println(line);
        }
        System.out.println();
    }
}
