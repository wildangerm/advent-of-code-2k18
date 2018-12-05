package advent_of_code_2k18.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import advent_of_code_2k18.BaseDay;

public class Day2 extends BaseDay {

	List<String> inputList = new ArrayList<>();

	public Day2() {
		readFile();
	}

	@Override
	public void part1() {
		int twice = 0;
		int thrice = 0;

		for (String input : inputList) {

			HashMap<Character, Integer> hashMap = new HashMap<>();

			for (int i = 0; i < input.length(); i++) {

				char charAt = input.charAt(i);
				hashMap.put(charAt, hashMap.get(charAt) == null ? 1 : hashMap.get(charAt) + 1);

			}

			if (hashMap.values().contains(2)) {
				twice++;
			}
			if (hashMap.values().contains(3)) {
				thrice++;
			}
		}

		System.out.println("checksum: " + twice * thrice);
	}

	@Override
	public void part2() {
		String common = null;

		for (int i = 0; i < inputList.size(); i++) {
			for (int j = 0; j < inputList.size(); j++) {
				if (i != j) {
					int mistakesAllowed = 1;
					String word1 = inputList.get(i);
					String word2 = inputList.get(j);

					int saved = 0;
					for (int k = 0; k < word1.length(); k++) {

						if (word1.charAt(k) != word2.charAt(k)) {
							mistakesAllowed--;
							saved = k;
							if (mistakesAllowed < 0) {
								break;
							}
						}
						if (k == (word1.length() - 1) && mistakesAllowed == 0) {
							common = word1.substring(0, saved) + word1.substring(saved + 1);
							break;
						}
					}
				}
			}
		}

		System.out.println("common: " + common);
	}

	private void readFile() {
		String pathString = getPathStringToInput("input.txt");

		try (Stream<String> stream = Files.lines(Paths.get(pathString))) {
			inputList = stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
