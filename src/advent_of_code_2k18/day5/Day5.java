package advent_of_code_2k18.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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

	private class ReactedComparator implements Comparator<Character> {

		@Override
		public int compare(Character c1, Character c2) {
			List<Character> reducedList1 = new ArrayList<>(inputList);
			reducedList1.removeAll(new ArrayList<>(Arrays.asList(c1, Character.toUpperCase(c1))));

			List<Character> reducedList2 = new ArrayList<>(inputList);
			reducedList2.removeAll(new ArrayList<>(Arrays.asList(c2, Character.toUpperCase(c2))));

			return react(reducedList1).compareTo(react(reducedList2));
		}

	}

	@Override
	public void part2() {

		Set<Character> charSet = new HashSet<>();

		inputList.stream().map(Character::toLowerCase).forEach(ch -> {
			charSet.add(ch);
		});

		Character c = charSet.stream().min(new ReactedComparator()).get();

		List<Character> reducedList = new ArrayList<>(inputList);
		reducedList.removeAll(new ArrayList<>(Arrays.asList(c, Character.toUpperCase(c))));

		int shortestPolymer = react(reducedList);

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
