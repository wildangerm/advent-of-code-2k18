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

		grid = new int[maxCoordX + 1][maxCoordY + 1];
	}

	@Override
	public void part1() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				grid[x][y] = getClosestCoordinateID(x, y);
			}
		}
		for (int y = 0; y < grid[0].length; y++) {
			for (int x = 0; x < grid.length; x++) {
				System.out.print(grid[x][y] + "\t");
				if (x == grid.length - 1) {
					System.out.println();
				}
			}
		}
		System.out.println();
	}

	private int getClosestCoordinateID(int x, int y) {

		int min = Integer.MAX_VALUE;
		int coordinateID = -1;

		for (Coordinate coordinate : inputList) {
			if (coordinate.getX() == x && coordinate.getY() == y) {
				return coordinate.getID() * 100;
			}
			int dist = getDistToCoordinate(coordinate, x, y);
			if (dist < min) {
				min = dist;
				coordinateID = coordinate.getID();
			} else if (dist == min) {
				coordinateID = -1;
			}

		}
		return coordinateID;
	}

	private int getDistToCoordinate(Coordinate c, int x, int y) {
		return Math.abs(c.getX() - x) + Math.abs(c.getY() - y);
	}

	@Override
	public void part2() {
		// TODO Auto-generated method stub

	}

	private void readFile() {
		String pathString = getPathStringToInput("testinput.txt");

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
