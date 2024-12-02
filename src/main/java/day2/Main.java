package day2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Main {
        static int validLines = 0;

    public static void main(String[] args) {
        Path filePath = Paths.get("src/main/java/day2/data.txt");
        List<Integer> integerList = new ArrayList<>();

        try {
            Files.lines(filePath)
                    .forEach(line -> {
                        int[] numbers = Arrays.stream(line.trim().split(" "))
                                .mapToInt(Integer::parseInt)
                                .toArray();
                        if (isValid(numbers)) {
                            System.out.println("order is valid");
                            validLines++;
                        }
                    });
        } catch (IOException _) {
        }
        System.out.println(validLines);
    }

    private static boolean isValid(int[] array) {
        //check if it is in descending order
        int counter = 0;
        for (int i = 1; i < array.length; i++) {
            //smaller than index before && difference to index before smaller 3 && larger than 1
            int difference = array[i-1] - array[i];
            if (array[i] < array[i - 1] && ((difference <= 3) && (difference >= 1))) {
                counter++;
            }
        }
        //System.out.println("Counter descending is " + counter);
        if (counter == array.length - 1) return true;
        counter = 0;
        //check if it is in ascending order
        for (int i = 1; i < array.length; i++) {
            int difference = array[i] - array[i - 1];
            if (array[i] > array[i - 1] && ((difference <= 3) && (difference >= 1))) {
                counter++;
            }
        }
        //System.out.println("Counter ascending is " + counter);
        if (counter == array.length - 1) return true;
        return false;
    }
}
