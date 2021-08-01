package org.calling;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DateTimeInterval {

    //static method but parameter no need static
    private LocalDateTime from;
    private LocalDateTime to;

    private DateTimeInterval(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    //public static functions return Interval, using Private Constructor
    public static DateTimeInterval of(LocalDateTime from, LocalDateTime to) {
        return new DateTimeInterval(from, to);
    }

    // toMidnight so to : LocalTime.of(23:59:59:9999999), using Private Constructor..
    public static DateTimeInterval toMidnight(LocalDateTime from) {
        return new DateTimeInterval(from,
                LocalDateTime.of(from.toLocalDate(), LocalTime.of(23, 59,59, 999_999_999)));
    }

    // fromMidnight so from :LocalDateTime.of(to's LocalDate, 00:00:00
    public static DateTimeInterval fromMidnight(LocalDateTime to) {
        return new DateTimeInterval(LocalDateTime.of(to.toLocalDate(), LocalTime.of(0,0, 0)), to);
    }

    public static DateTimeInterval during(LocalDate date) {
        return new DateTimeInterval(
                LocalDateTime.of(date, LocalTime.of(0,0, 0)),
                LocalDateTime.of(date, LocalTime.of(23, 59, 59, 999_999_999)));
    }

    public Duration duration() {
        return Duration.between(from, to);
    }

    //getter

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public List<DateTimeInterval> splitByDay() {
        if (days() > 0) {
            return split(days());
        }
        return Arrays.asList(this); //days = 1 just List with single interval
    }

    private List<DateTimeInterval> split(long days) {
        List<DateTimeInterval> result = new ArrayList<>();
        addFirstDay(result);
        addMiddleDays(result, days);
        addLastDay(result);
        return result;
    }

    private void addLastDay(List<DateTimeInterval> result) {
        result.add(DateTimeInterval.fromMidnight(to)); //last day 00~to time
    }

    private void addMiddleDays(List<DateTimeInterval> result, long days) {
        for (int loop = 1; loop < days; loop++) { //from(00~2359) from+1(00~2359)..
            result.add(DateTimeInterval.during(from.toLocalDate().plusDays(loop)));
        }
    }

    private void addFirstDay(List<DateTimeInterval> result) {
        result.add(DateTimeInterval.toMidnight(from)); //첫날 from~midnight
    }

    private long days() {
        return Duration.between(from.toLocalDate().atStartOfDay(),
                                to.toLocalDate().atStartOfDay()).toDays();
    }

    public String toString() {
        return "[" + from + "-" + to + "]";
    }
}
