package day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/main/java/day5/data.txt";
        String regexRules = "(\\d{2}\\|\\d{2})";
        List<String> rulesStrings = new ArrayList<>();
        List<String> pageOrders = new ArrayList<>();
        HashMap<Integer, List<Integer>> rules = new HashMap<>();

        readFile(rulesStrings, regexRules, filePath);
        populateRulesList(rulesStrings, rules);
        readFile(pageOrders, filePath);

        System.out.println(rules);
        System.out.println(pageOrders);
        int pageCount = 0;
        for (String line : pageOrders) {
            List<Integer> tempList = Arrays.stream(line.split(","))
                    .toList()
                    .stream()
                    .map(Integer::parseInt)
                    .toList();
            if(checkPageOrder(tempList, rules)) {
                pageCount += tempList.get(tempList.size()/2);
            }
        }
        System.out.println(pageCount);
    }

    private static boolean checkPageOrder(List<Integer> tempList, HashMap<Integer, List<Integer>> rules) {
        System.out.println("TempList:" + tempList);
        //index of the page that is currently being checked
        for (int i = tempList.size() - 1; i > 0; i--) {
            //value of the page currently checked
            int page = tempList.get(i);
            //ruleset of the page
            List<Integer> ruleSet = rules.get(page);
            if (ruleSet == null) continue;
            for (int target = i - 1; target >= 0; target--) {
                if (ruleSet.contains(tempList.get(target))) {
                    System.out.println("Line is invalid.");
                    return false;
                }
            }

        }
        return true;
    }


    private static void populateRulesList(List<String> rulesStrings, HashMap<Integer, List<Integer>> rules) {
        for (String string : rulesStrings) {
            String[] tempString = string.split("\\|");
            int[] temp = Arrays.stream(tempString)
                    .flatMapToInt(num -> IntStream.of(Integer.parseInt(num)))
                    .toArray();
            if (rules.containsKey(temp[0])) {
                List<Integer> tempList = rules.get(temp[0]);
                tempList.add(temp[1]);
            } else {
                List<Integer> tempList = new ArrayList<>();
                tempList.add(temp[1]);
                rules.put(temp[0], tempList);
            }
        }
    }

    private static void readFile(List<String> matches, String regex, String filePath) {
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
            System.out.println("Something went wrong reading in the new line");
        }
    }

    private static void readFile(List<String> matches, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("|") && !line.isEmpty()) {
                    matches.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Something went wrong reading in the new line");
        }
    }
}
