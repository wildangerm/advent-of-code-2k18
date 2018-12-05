package advent_of_code_2k18.day4;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Guard {
	int id;
	Map<LocalDateTime, Activity> activities = new LinkedHashMap<>();

	public Guard(int id, LocalDateTime date, Activity activity) {
		this.id = id;
		activities.put(date, activity);
	}

	public void addActivity(LocalDateTime date, Activity activity) {
		activities.put(date, activity);
	}

	public Integer getMinutesAsleep() {

		Integer sum = 0;
		LocalDateTime previousAsleep = null;
		for (Entry<LocalDateTime, Activity> activity : activities.entrySet()) {
			if (activity.getValue().equals(Activity.ASLEEP)) {
				previousAsleep = activity.getKey();
			} else if (activity.getValue().equals(Activity.WAKE_UP)) {
				sum += getAsleepTime(previousAsleep, activity.getKey());
			}
		}

		return sum;
	}

	private int getAsleepTime(LocalDateTime fromAsleep, LocalDateTime toAwake) {
		return toAwake.getMinute() - fromAsleep.getMinute();
	}

	private Entry<Integer, Integer> calcMostAsleepMinuteEntry() {
		HashMap<Integer, Integer> minuteStatsMap = new HashMap<>();

		boolean stillSleeping = false;
		int asleepStartMinute = 0;
		for (Entry<LocalDateTime, Activity> activity : activities.entrySet()) {
			if (activity.getValue().equals(Activity.ASLEEP)) {
				asleepStartMinute = activity.getKey().getMinute();
				stillSleeping = true;

			} else if (activity.getValue().equals(Activity.WAKE_UP) && stillSleeping) {
				for (int i = asleepStartMinute; i < activity.getKey().getMinute(); i++) {
					if (minuteStatsMap.get(i) != null) {
						minuteStatsMap.put(i, minuteStatsMap.get(i) + 1);
					} else {
						minuteStatsMap.put(i, 1);
					}
				}
				stillSleeping = false;
			}
		}

		Entry<Integer, Integer> result = null;
		if (minuteStatsMap.isEmpty()) {
			result = new AbstractMap.SimpleEntry<Integer, Integer>(-1, -1);
		} else {
			result = minuteStatsMap.entrySet().stream().max((min1, min2) -> {
				return min1.getValue().compareTo(min2.getValue());
			}).get();

		}

		return result;

	}

	public Integer getMostAsleepMinute() {
		return calcMostAsleepMinuteEntry().getKey();
	}

	public Integer getMostAsleepMinuteCount() {
		return calcMostAsleepMinuteEntry().getValue();
	}

}
