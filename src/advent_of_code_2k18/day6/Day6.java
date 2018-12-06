package advent_of_code_2k18.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import advent_of_code_2k18.BaseDay;

public class Day6 extends BaseDay {

	private List<Coordinate> inputList;
	private int[][] grid;

	public Day6() {
		readFile();
		
		int maxCoordX = inputList.stream().max((c1, c2) -> Integer.compare(c1.getX(), c2.getX())).get().getX();
		int maxCoordY = inputList.stream().max((c1, c2) -> Integer.compare(c1.getY(), c2.getY())).get().getY();

		grid = new int[maxCoordX][maxCoordY];
	}

	@Override
	public void part1() {
		// TODO Auto-generated method stub

	}

	@Override
	public void part2() {
		// TODO Auto-generated method stub

	}

	private void readFile() {
		String pathString = getPathStringToInput("input.txt");

		try (Stream<String> stream = Files.lines(Paths.get(pathString))) {
			inputList = stream.map(line -> createCoordinateFromRegex(line)).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Coordinate createCoordinateFromRegex(String line) {
		String re1 = "(\\d+)"; // Integer Number 1
		String re2 = "(,)"; // Any Single Character 1
		String re3 = "( )"; // White Space 1
		String re4 = "(\\d+)"; // Integer Number 2

		Pattern p = Pattern.compile(re1 + re2 + re3 + re4, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(line);

		if (m.find()) {
			int x = Integer.parseInt(m.group(1));
			int y = Integer.parseInt(m.group(4));

			return new Coordinate(x, y);
		}
		return null;
	}

}
