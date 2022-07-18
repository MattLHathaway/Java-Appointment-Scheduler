package main;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
     * This function takes the current time zone and converts it to EST.
     * @param dateTime
     * @return
     */
    public static String convertToEST(String dateTime) {
        Timestamp currentTimeStamp = Timestamp.valueOf(String.valueOf(dateTime));
        LocalDateTime LocalDT = currentTimeStamp.toLocalDateTime();
        ZonedDateTime zoneDT = LocalDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime estDT = zoneDT.withZoneSameInstant(ZoneId.of("UTC-4"));
        LocalDateTime localOUT = estDT.toLocalDateTime();
        String estOUT = localOUT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return estOUT;
    }

    public static Double getTimeOffset() {
        ZonedDateTime zdt = ZonedDateTime.now();
        ZoneOffset offset = zdt.getOffset();
        int offsetMinutes = offset.getTotalSeconds() / 60;
        double offsetHours = ((double) offsetMinutes) / 60;

        return offsetHours;
    }
}