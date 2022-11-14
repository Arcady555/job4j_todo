package ru.job4j.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@ThreadSafe
public class TimeZoneService {
    private final List<TimeZone> timeZones = new ArrayList<>();

    public TimeZoneService() {
        for (String timeId : TimeZone.getAvailableIDs()) {
            timeZones.add(TimeZone.getTimeZone(timeId));
        }
    }

    public List<TimeZone> getAllTimeZones() {
        return timeZones;
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<>();
        for (TimeZone zone : timeZones) {
            list.add(zone.getID() + " : " + zone.getDisplayName());
        }
        return list.toString();
    }
}