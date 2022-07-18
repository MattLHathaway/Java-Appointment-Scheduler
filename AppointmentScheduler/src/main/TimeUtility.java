package main;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

/**
 * This class' purpose is to convert time zone Strings into other time zone Strings.
 */
public class TimeUtility {

    /**
     * This function takes the current time zone and converts it to UTC.
     * @param dateTime
     * @return
     */
    public static String convertToUTC(String dateTime) {
        Timestamp currentTimeStamp = Timestamp.valueOf(String.valueOf(dateTime));
        LocalDateTime LocalDT = currentTimeStamp.toLocalDateTime();
        ZonedDateTime zoneDT = LocalDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utcDT = zoneDT.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime localOUT = utcDT.toLocalDateTime();
        String utcOUT = localOUT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return utcOUT;
    }

    /**
     * This function takes a String UTC time and converts it to local.
     * @param dateTime
     * @return
     */
    public static String convertUTCtoLocal(String dateTime) {
        Timestamp currentTimeStamp = Timestamp.valueOf(String.valueOf(dateTime));
        LocalDateTime LocalDT = currentTimeStamp.toLocalDateTime();
        ZonedDateTime zoneDT = LocalDT.atZone(ZoneId.of(ZoneId.of("UTC").toString()));
        ZonedDateTime utcDT = zoneDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime localOUT = utcDT.toLocalDateTime();
        String utcOUT = localOUT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return utcOUT;
    }

    /**
     * This function takes in an EST timeDate as a string and converts it to UTC.
     * @param dateTime
     * @return
     */
    public static String convertToUTCfromEST(String dateTime) {
        Timestamp currentTimeStamp = Timestamp.valueOf(String.valueOf(dateTime));
        LocalDateTime LocalDT = currentTimeStamp.toLocalDateTime();
        ZonedDateTime zoneDT = LocalDT.atZone(ZoneId.of(ZoneId.of("UTC-4").toString()));
        ZonedDateTime utcDT = zoneDT.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime localOUT = utcDT.toLocalDateTime();
        String utcOUT = localOUT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return utcOUT;
    }

    private static void printDurationBetweenTwoDates(ZonedDateTime estDateTime, ZonedDateTime utcDateTime) {
        Duration d = Duration.between(estDateTime, utcDateTime);
        long days = d.get(ChronoUnit.SECONDS);
        System.out.println("Time Difference between los angeles and dubai : " + days / (60 * 60) + " Hours " + (days % (60 * 60)) / 60 + " Minites");

    }

    /**
     * This function takes the current time zone and converts it to EST.
     * @param dateTime
     * @return
     */
    public static String convertToEST(String dateTime) {
        Timestamp currentTimeStamp = Timestamp.valueOf(String.valueOf(dateTime));
        LocalDateTime LocalDT = currentTimeStamp.toLocalDateTime();
        ZonedDateTime zoneDT = LocalDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utcDT = zoneDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalDateTime localOUT = utcDT.toLocalDateTime();
        String utcOUT = localOUT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return utcOUT;
    }

    public static Double getTimeOffset() {
        ZonedDateTime zdt = ZonedDateTime.now();
        ZoneOffset offset = zdt.getOffset();
        int offsetMinutes = offset.getTotalSeconds() / 60;
        double offsetHours = ((double) offsetMinutes) / 60;

        return offsetHours;
    }
}