package advent_of_code_2k18;

import java.net.URL;

public abstract class BaseDay {

	public abstract void part1();

	public abstract void part2();

	protected String getPathStringToInput(String fileName) {
		URL url = getClass().getResource(fileName);
		String pathString = url.getPath().substring(1).replace("/", "\\\\").replaceAll("%20", " ");
		return pathString;
	}
}
