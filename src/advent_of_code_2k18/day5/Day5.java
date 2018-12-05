package advent_of_code_2k18.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import advent_of_code_2k18.BaseDay;

public class Day5 extends BaseDay {

	private List<Character> inputList;

	public Day5() {
		readFile();
	}

	@Override
	public void part1() {

		int shortestPolymer = react(inputList);

		System.out.println("shortest: " + shortestPolymer);

	}

	@Override
	public void part2() {

		Set<Character> charSet = new HashSet<>();

		inputList.stream().map(Character::toLowerCase).forEach(ch -> {
			charSet.add(ch);
		});

		Map<Character, Integer> charReduced = new HashMap<>();

		charSet.forEach(cse -> {
			List<Character> reduced = new ArrayList<>(inputList);
			reduced.removeAll(new ArrayList<>(Arrays.asList(cse, Character.toUpperCase(cse))));
			charReduced.put(cse, react(reduced));
		});

		Integer shortestPolymer = charReduced.values().stream().min(Comparator.naturalOrder()).get();

		System.out.println("shortest: " + shortestPolymer);
	}

	private Integer react(List<Character> inputList) {
		List<Character> list = new ArrayList<>(inputList);
		boolean finished = false;

		while (!finished) {

			int size = list.size();
			Character previous = Character.MIN_VALUE;
			List<Integer> toDelete = new ArrayList<>();

			for (int i = 0; i < list.size(); i++) {

				Character c = list.get(i);
				if (!c.equals(previous)
						&& (c.equals(Character.toUpperCase(previous)) || c.equals(Character.toLowerCase(previous)))) {

					if (i != 0) {
						toDelete.add(i);
						toDelete.add(i - 1);
						break;
					}
				}
				previous = c;
			}

			for (Integer integer : toDelete) {
				list.remove(integer.intValue());
			}

			if (list.size() == size) {
				finished = true;
			}
		}

		return list.size();
	}

	private void readFile() {
		String pathString = getPathStringToInput("input.txt");

		try {
			String input = new String(Files.readAllBytes(Paths.get(pathString)));
			inputList = input.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
