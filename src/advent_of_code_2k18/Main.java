package advent_of_code_2k18;

import java.util.ArrayList;
import java.util.List;

import advent_of_code_2k18.day4.Day4;

public class Main {
	static List<BaseDay> days = new ArrayList<>();
	
	public static void main(String[] args) {
//		days.add(new Day1());
//		days.add(new Day2());
//		days.add(new Day3());
		days.add(new Day4());
		
		for (BaseDay baseDay : days) {
			baseDay.part1();
			baseDay.part2();
		}
	}
}
