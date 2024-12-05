package day4;

import java.io.*;
import java.util.*;

public class Main {
    // Define directions: {row offset, col offset}
    private static final int[][] DIRECTIONS = {
            {0, 1},  // Right
            {1, 0},  // Down
            {1, 1},  // Down-Right
            {1, -1}, // Down-Left
            {0, -1}, // Left
            {-1, 0}, // Up
            {-1, 1}, // Up-Right
            {-1, -1} // Up-Left
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the file path containing the letter grid:");
        String filePath = scanner.nextLine();

        char[][] grid;
        try {
            // Step 1: Read the grid from the file
            grid = readGridFromFile(filePath);
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return;
        }

        System.out.println("Enter the target word to search for:");
        String targetWord = scanner.nextLine();

        // Step 2: Count occurrences of the word in the grid
        int count = countWordOccurrences(grid, targetWord);

        // Step 3: Display the result
        if (count > 0) {
            System.out.println("The word \"" + targetWord + "\" was found " + count + " times in the grid!");
        } else {
            System.out.println("The word \"" + targetWord + "\" was not found in the grid.");
        }
    }

    private static char[][] readGridFromFile(String filePath) throws IOException {
        List<char[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                rows.add(line.replaceAll("\\s+", "").toCharArray()); // Remove spaces
            }
        }
        return rows.toArray(new char[0][]);
    }

    private static int countWordOccurrences(char[][] grid, String word) {
        int rows = grid.length;
        int cols = grid[0].length;
        int wordLength = word.length();
        int count = 0;

        // Traverse each cell in the grid
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == word.charAt(0)) {
                    // Try all 8 directions
                    for (int[] direction : DIRECTIONS) {
                        if (searchFrom(grid, r, c, word, direction)) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    private static boolean searchFrom(char[][] grid, int startRow, int startCol, String word, int[] direction) {
        int rows = grid.length;
        int cols = grid[0].length;
        int wordLength = word.length();

        // Check each character in the word
        for (int i = 0; i < wordLength; i++) {
            int newRow = startRow + i * direction[0];
            int newCol = startCol + i * direction[1];

            // Check boundaries
            if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
                return false;
            }

            // Check character match
            if (grid[newRow][newCol] != word.charAt(i)) {
                return false;
            }
        }
        return true; // Word matched
    }
}


