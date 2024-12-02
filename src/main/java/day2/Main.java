package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

public class Main {
    public static void main(String[] args) throws Exception {
        // Read the contents of the data file
        String data = new String(Files.readAllBytes(Paths.get("src/main/java/day2/data.txt")));

        // Split the data into lines
        String[] lines = data.split("\n");

        // Calculate the number of safe reports for part 1 and part 2
        long part1Count = stream(lines)
                .mapToLong(line -> part1(line))
                .sum();
        long part2Count = stream(lines)
                .mapToLong(line -> part2(line))
                .sum();

        // Print the results
        System.out.println("Part 1: " + part1Count); //should be 314
        System.out.println("Part 2: " + part2Count); //should be 373
    }

    static long part1(String input) {
        return stream(input.split("\n"))
                .map(line -> stream(line.trim().split(" ")).map(Integer::parseInt).toList())
                .filter(report -> isSafe(report, false))
                .count();
    }

    static long part2(String input) {
        return stream(input.split("\n"))
                .map(line -> stream(line.trim().split(" ")).map(Integer::parseInt).toList())
                .filter(report -> isSafe(report, true))
                .count();
    }

    private static boolean isSafe(List<Integer> report, boolean useDampener) {
        Boolean isIncreasing = null;
        Set<Integer> faultyIndexes = new HashSet<>();
        for (var i = 0; i < report.size() - 1; i++) {
            var diff = report.get(i + 1) - report.get(i);
            if (abs(diff) > 3 || diff == 0 || (isIncreasing != null && diff > 0 != isIncreasing)) {
                if (useDampener) {
                    faultyIndexes.add(i);
                    faultyIndexes.add(i + 1);
                } else {
                    return false;
                }
            } else if (isIncreasing == null) {
                isIncreasing = diff > 0;
            }
        }
        return faultyIndexes.isEmpty() || faultyIndexes.stream()
                .map(idx -> range(0, report.size()).filter(i -> i != idx).mapToObj(report::get).toList())
                .anyMatch(filteredReport -> isSafe(filteredReport, false));
    }


}
