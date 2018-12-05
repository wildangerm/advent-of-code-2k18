package advent_of_code_2k18.day4;

import java.time.LocalDateTime;

public class Record implements Comparable<Record> {

	LocalDateTime date;
	String description;

	public Record(LocalDateTime date, String description) {
		this.date = date;
		this.description = description;
	}

	public LocalDateTime getDate() {
		return date;
	}

	@Override
	public int compareTo(Record o) {
		return date.compareTo(o.getDate());
	}

}
