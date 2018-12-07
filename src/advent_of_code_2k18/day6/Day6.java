package advent_of_code_2k18.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Function;

import advent_of_code_2k18.BaseDay;

public class Day6 extends BaseDay {

	private final int SAFE_REGION_DISTANCE = 10000;
	private List<Coordinate> inputList = new ArrayList<>();
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
		
		List<Coordinate> candidates = excludeInfinite();
		
		List<Integer> candidateIDs = candidates.stream()
												.map(Coordinate::getID)
												.collect(Collectors.toList());

		List<Integer> flatFilteredGrid = Arrays.stream(grid)
												.flatMapToInt(x -> Arrays.stream(x))
												.filter(x -> candidateIDs.contains(x))
												.boxed()
												.collect(Collectors.toList());

		Long max = flatFilteredGrid.stream()
									.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
									.entrySet()
									.stream()
									.max(Comparator.comparing(Entry::getValue))
									.get()
									.getValue();

		System.out.println("max: " + max);

	}

	@Override
	public void part2() {
		int safeRegionSize = 0;

		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {

				int distanceSum = 0;

				for (Coordinate c : inputList) {
					distanceSum += getDistToCoordinate(c, x, y);
				}
				if (distanceSum < SAFE_REGION_DISTANCE) {
					safeRegionSize++;
				}
			}
		}
		System.out.println("size of safe region: " + safeRegionSize);

	}

	private List<Coordinate> excludeInfinite() {
		List<Coordinate> candidates = new ArrayList<>();

		Set<Integer> toExclude = getIDsToExclude();

		inputList.forEach(coord -> {
			if (!toExclude.contains(coord.getID())) {
				candidates.add(coord);
			}
		});

		return candidates;
	}

	private Set<Integer> getIDsToExclude() {
		Set<Integer> toExclude = new HashSet<>();

		// Walk the outer edges, and if we find an ID, it is to be excluded
		// Top
		for (int x = 0; x < grid.length; x++) {
			int gridElement = grid[x][0];
			if (gridElement != -1) {
				toExclude.add(gridElement);
			}
		}

		// Bottom
		for (int x = 0; x < grid.length; x++) {
			int gridElement = grid[x][grid[0].length - 1];
			if (gridElement != -1) {
				toExclude.add(gridElement);
			}
		}

		// Left
		for (int y = 0; y < grid[0].length; y++) {
			int gridElement = grid[0][y];
			if (gridElement != -1) {
				toExclude.add(gridElement);
			}
		}

		// Right
		for (int y = 0; y < grid[0].length; y++) {
			int gridElement = grid[grid.length - 1][y];
			if (gridElement != -1) {
				toExclude.add(gridElement);
			}
		}

		return toExclude;
	}

	private int getClosestCoordinateID(int x, int y) {

		int min = Integer.MAX_VALUE;
		int coordinateID = -1;

		for (Coordinate coordinate : inputList) {
			if (coordinate.getX() == x && coordinate.getY() == y) {
				return coordinate.getID();
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

	private void printGrid() {
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
