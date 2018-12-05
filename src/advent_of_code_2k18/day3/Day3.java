package advent_of_code_2k18.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import advent_of_code_2k18.BaseDay;

public class Day3 extends BaseDay {

	List<Claim> inputList = new ArrayList<>();

	public Day3() {
		readFile();
	}

	@Override
	public void part1() {
		int[][] fabric = new int[1001][1001];
		int sqinch = 0;

		for (Claim claim : inputList) {
			for (int x = claim.left; x < claim.left + claim.width; x++) {
				for (int y = claim.top; y < claim.top + claim.height; y++) {
					if (fabric[x][y] == 0) {
						fabric[x][y] = claim.id;
					} else if (fabric[x][y] != -1) {
						fabric[x][y] = -1;
						sqinch++;
					}
				}
			}
		}
		System.out.println("sqinch: " + sqinch);
	}

	@Override
	public void part2() {
		int[][] fabric = new int[1001][1001];

		for (Claim claim : inputList) {
			for (int x = claim.left; x < claim.left + claim.width; x++) {
				for (int y = claim.top; y < claim.top + claim.height; y++) {
					if (fabric[x][y] == 0) {
						fabric[x][y] = claim.id;
					} else {
						fabric[x][y] = -1;
					}
				}
			}
		}
		int intactClaimId = -1;

		for (Claim claim : inputList) {
			boolean stillFree = true;
			for (int x = claim.left; x < claim.left + claim.width && stillFree; x++) {
				for (int y = claim.top; y < claim.top + claim.height && stillFree; y++) {
					if (fabric[x][y] != claim.id) {
						stillFree = false;
						break;
					} else if ((x == (claim.left + claim.width - 1)) && (y == (claim.top + claim.height - 1))
							&& stillFree) {
						intactClaimId = claim.id;
					}
				}
			}
		}

		System.out.println("Intact claimid: " + intactClaimId);
	}

	private void readFile() {
		String pathString = getPathStringToInput("input.txt");

		try (Stream<String> stream = Files.lines(Paths.get(pathString))) {
			inputList = stream.map(line -> createClaimFromRegex(line)).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Claim createClaimFromRegex(String line) {

		String re1 = "(#)"; // Any Single Character 1
		String re2 = "(\\d+)"; // Integer Number 1
		String re3 = "(\\s+)"; // White Space 1
		String re4 = "(@)"; // Any Single Character 2
		String re5 = "(\\s+)"; // White Space 2
		String re6 = "(\\d+)"; // Integer Number 2
		String re7 = "(,)"; // Any Single Character 3
		String re8 = "(\\d+)"; // Integer Number 3
		String re9 = "(:)"; // Any Single Character 4
		String re10 = "(\\s+)"; // White Space 3
		String re11 = "(\\d+)"; // Integer Number 4
		String re12 = "(x)"; // Any Single Word Character (Not Whitespace) 1
		String re13 = "(\\d+)"; // Integer Number 5

		Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8 + re9 + re10 + re11 + re12 + re13,
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(line);
		if (m.find()) {
			int id = Integer.parseInt(m.group(2));
			int left = Integer.parseInt(m.group(6));
			int top = Integer.parseInt(m.group(8));
			int width = Integer.parseInt(m.group(11));
			int height = Integer.parseInt(m.group(13));

			return new Claim(id, left, top, width, height);
		}

		return null;

	}

}
