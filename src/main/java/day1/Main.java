package day1;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Integer> list1 = new ArrayList<>();
    static ArrayList<Integer> list2 = new ArrayList<>();

    public static void main(String[] args) {
        readInputFile();
        /* Part One:
        list1.sort(null);
        list2.sort(null);
        int distance = 0;
        for (int i = 0; i < list1.size(); i++) {
            int difference = list1.get(i) - list2.get(i);
            if (difference < 0) {
                difference = difference * -1;
            }
            distance += difference;
        }
        System.out.println(distance); //1223326
         */
        //Part two:
        list1.sort(null);
        list2.sort(null);
        int similarity = 0;
        for (int n : list1) {
            int counter = 0;
            for (int x : list2) {
                if (n == x) counter++;
            }
            similarity += n * counter;
        }
        System.out.println(similarity); //21070419
    }


    private static void readInputFile() {
        try {
            File input = new File("src/main/java/day1/input.txt");
            Scanner fileReader = new Scanner(input);
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                parseLine(line);
            }
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseLine(String line) {
        String[] parts = line.split("   ");
        list1.add(Integer.parseInt(parts[0]));
        list2.add(Integer.parseInt(parts[1]));
    }
}
