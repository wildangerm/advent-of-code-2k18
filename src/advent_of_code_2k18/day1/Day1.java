package advent_of_code_2k18.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import advent_of_code_2k18.BaseDay;

public class Day1 extends BaseDay {
	List<Integer> inputList = new ArrayList<>();

	public Day1() {
		readFile();
	}

	@Override
	public void part1() {
		System.out.println(inputList.stream().mapToInt(Integer::intValue).sum());

	}

	@Override
	public void part2() {
		Set<Integer> treeSet = new TreeSet<>();
		int sum = 0;
		boolean finished = false;

		while (!finished) {
			for (Integer inputValue : inputList) {
				if (!treeSet.add(sum)) {
					finished = true;
					break;
				}
				sum += inputValue;
			}
		}
		System.out.println("Duplikatum: " + sum);

	}

	private void readFile() {
		String pathString = getPathStringToInput("input.txt");

		try (Stream<String> stream = Files.lines(Paths.get(pathString))) {
			inputList = stream.map(Integer::parseInt).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
