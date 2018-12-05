package advent_of_code_2k18.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import advent_of_code_2k18.BaseDay;

public class Day5 extends BaseDay {

	private List<Character> inputList;

	public Day5() {
		readFile();
	}

	@Override
	public void part1() {

		boolean finished = false;

		while (!finished) {

			int size = inputList.size();
			Character previous = Character.MIN_VALUE;
			List<Integer> toDelete = new ArrayList<>();

			for (int i = 0; i < inputList.size(); i++) {

				Character c = inputList.get(i);
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
				inputList.remove(integer.intValue());
			}

			if (inputList.size() == size) {
				finished = true;
			}
		}

		System.out.println(inputList.size());

	}

	@Override
	public void part2() {
		// TODO Auto-generated method stub

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
