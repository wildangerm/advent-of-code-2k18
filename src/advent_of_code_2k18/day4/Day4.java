package advent_of_code_2k18.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import advent_of_code_2k18.BaseDay;

public class Day4 extends BaseDay {

	private List<Record> inputList = new ArrayList<>();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	private Map<Integer, Guard> guards;

	public Day4() {
		readFile();
		Collections.sort(inputList);
		guards = createGuards();
	}

	@Override
	public void part1() {
		Optional<Guard> guard = guards.values().stream().sorted((g1, g2) -> {
			return g2.getMinutesAsleep().compareTo(g1.getMinutesAsleep());
		}).findFirst();

		int id = guard.get().id;
		int mostAsleepMinute = guard.get().getMostAsleepMinute();

		System.out.println("result: " + id + "*" + mostAsleepMinute + " = " + id * mostAsleepMinute);
	}

	@Override
	public void part2() {
		Optional<Guard> optGuard = guards.values().stream().sorted((g1, g2) -> {
			return g2.getMostAsleepMinuteCount().compareTo(g1.getMostAsleepMinuteCount());
		}).findFirst();

		Guard guard = optGuard.get();
		int id = guard.id;
		int mostAsleepMinute = guard.getMostAsleepMinute();
		System.out.println("result: " + id + "*" + mostAsleepMinute + " = " + id * mostAsleepMinute);

	}

	private Record createRecordFromRegex(String line) {

		String dateString = line.substring(1, 17);
		String thing = line.substring(19);

		return new Record(LocalDateTime.from(dtf.parse(dateString)), thing);
	}

	private Map<Integer, Guard> createGuards() {
		Map<Integer, Guard> guards = new LinkedHashMap<>();

		int activeGuardId = -1;

		for (Record record : inputList) {
			if (record.description.contains("Guard")) {

				String[] parts = record.description.split(" ");
				activeGuardId = Integer.parseInt(parts[1].substring(1));
				Guard g = guards.get(activeGuardId);
				if (g == null) {
					guards.put(activeGuardId, new Guard(activeGuardId, record.date, Activity.BEGIN));
				} else {
					g.activities.put(record.date, Activity.BEGIN);
				}

			} else if (record.description.contains("asleep")) {
				guards.get(activeGuardId).activities.put(record.date, Activity.ASLEEP);
			} else if (record.description.contains("wakes")) {
				guards.get(activeGuardId).activities.put(record.date, Activity.WAKE_UP);
			}
		}

		return guards;
	}

	private void readFile() {
		String pathString = getPathStringToInput("input.txt");

		try (Stream<String> stream = Files.lines(Paths.get(pathString))) {
			inputList = stream.map(line -> createRecordFromRegex(line)).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
