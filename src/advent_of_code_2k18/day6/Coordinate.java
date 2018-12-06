package advent_of_code_2k18.day6;

public class Coordinate {

	private int x;
	private int y;
	private static int counter = 1;
	private int ID;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
		this.ID = counter++;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getID() {
		return ID;
	}
}
