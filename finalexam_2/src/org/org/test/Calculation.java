package org.org.test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Calculation {
    public String[] getPeriods(LocalTime[] startTimes, int[] durations, int consultationTime, LocalTime beginWorkingTime, LocalTime endWorkingTime) {
        int offset = getMinute(beginWorkingTime);
        int lastMinute = getMinute(endWorkingTime);

        List<Integer> minutes = new ArrayList<>();
        for (int i = offset; i <= lastMinute; i++) {
            minutes.add(i);
        }

        for (int i = 0; i < startTimes.length; i++) {
            removeFromTimeFromList(minutes, startTimes[i], durations[i]);
        }

        int startTime = minutes.get(0);
        int counter = 0;
        boolean intercepted = false;

        List<String> periods = new ArrayList<>();

        for (int i = 0; i < minutes.size(); i++) {
            if (counter == 0) {
                startTime = minutes.get(i);
                if (intercepted) startTime--;
            }
            if (minutes.get(i) == -1) {
                counter = 0;
                intercepted = true;
                continue;
            }
            counter++;
            if (counter == consultationTime + (!intercepted ? 1 : 0)) {
                periods.add(minuteToString(startTime) + "-" + minuteToString(minutes.get(i)));
                startTime = minutes.get(i);
                intercepted = false;
                counter = 1;
            }
        }

        return periods.toArray(new String[0]);
    }

    public void removeFromTimeFromList(List<Integer> minutes, LocalTime time, int duration) {
        int periodStart = getMinute(time);

        for (int i = 0; i < minutes.size(); i++) {
            if (minutes.get(i) > periodStart && minutes.get(i) <= periodStart + duration) {
                minutes.set(i, -1);
            }
        }
    }

    public int getMinute(LocalTime time) {
        return time.getHour() * 60 + time.getMinute();
    }

    public String minuteToString(int minute) {
        return minute / 60 + ":" + String.format("%02d", minute % 60);
    }
}
