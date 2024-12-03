package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    //regex used: /mul\([0-9]{1,3},[0-9]{1,3}\)/gm
    public static void main(String[] args) {
        List<String> matches = new ArrayList<>();
        readFile(matches);
        System.out.println(matches);
        multiply(matches);
    }

    private static void multiply(List<String> matches) {
        int sum = 0;
        for (String match : matches) {
            List<Integer> numbers = parseNumbers(match);
            System.out.println(numbers);
            if (numbers.size() == 2) {
                sum += numbers.get(0) * numbers.get(1);
            } else System.out.println("Theres only "+ numbers.size() + " numbers " + numbers + match);
        }
        System.out.println(sum);
    }

    private static List<Integer> parseNumbers(String match) {
        //String regex = "[0-9]{1,3}[0-9]{1,3}";
        String regex = "\\d{1,3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(match);
        List<Integer> numbers = new ArrayList<>();
        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }
        return numbers;
    }

    private static void readFile(List<String> matches) {
        String filePath = "src/main/java/day3/data.txt";
        String regex = "mul\\([0-9]{1,3},[0-9]{1,3}\\)";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Pattern pattern = Pattern.compile(regex);
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    matches.add(matcher.group());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
