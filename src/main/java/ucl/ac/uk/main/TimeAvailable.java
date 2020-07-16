package ucl.ac.uk.main;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class TimeAvailable {
    private Calendar startTime;
    private Calendar finishTime;

    public TimeAvailable() {
        startTime = Calendar.getInstance();
        finishTime = Calendar.getInstance();
    }

    public TimeAvailable(int startMonth, int startDate, int startHour, int startMinute, int finishMonth, int finishDate, int finishHour, int finishMinute) {
        startTime = Calendar.getInstance();
        startTime.set(startTime.get(Calendar.YEAR), startMonth - 1, startDate, startHour, startMinute);
        finishTime = Calendar.getInstance();
        finishTime.set(finishTime.get(Calendar.YEAR), finishMonth - 1, finishDate, finishHour, finishMinute);
    }

    public Calendar getCalendarStart(){
        return startTime;
    }

    public void setStartTime(int startMonth, int startDate, int startHour, int startMinute) {
        startTime.set(startTime.get(Calendar.YEAR), startMonth - 1, startDate, startHour, startMinute);
    }

    public void setFinishTime(int finishMonth, int finishDate, int finishHour, int finishMinute) {
        finishTime.set(finishTime.get(Calendar.YEAR), finishMonth, finishDate, finishHour, finishMinute);
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(startTime.get(Calendar.MONTH) + 1).append(";");
        string.append(startTime.get(Calendar.DATE)).append(";");
        string.append(startTime.get(Calendar.HOUR)).append(";");
        string.append(startTime.get(Calendar.MINUTE)).append(";");
        string.append(finishTime.get(Calendar.MONTH) + 1).append(";");
        string.append(finishTime.get(Calendar.DATE)).append(";");
        string.append(finishTime.get(Calendar.HOUR)).append(";");
        string.append(finishTime.get(Calendar.MINUTE));
        return string.toString();
    }

    private String getTimeString(Calendar finishTime) {
        if (finishTime.get(Calendar.HOUR_OF_DAY) >= 10 && finishTime.get(Calendar.MINUTE) >= 10) {
            return finishTime.get(Calendar.HOUR_OF_DAY) + ":" + finishTime.get(Calendar.MINUTE);
        } else if (finishTime.get(Calendar.HOUR_OF_DAY) >= 10 && finishTime.get(Calendar.MINUTE) < 10) {
            return finishTime.get(Calendar.HOUR_OF_DAY) + ":0" + finishTime.get(Calendar.MINUTE);
        } else if (finishTime.get(Calendar.HOUR_OF_DAY) < 10 && finishTime.get(Calendar.MINUTE) >= 10) {
            return "0" + finishTime.get(Calendar.HOUR_OF_DAY) + ":" + finishTime.get(Calendar.MINUTE);
        } else {
            return "0" + finishTime.get(Calendar.HOUR_OF_DAY) + ":0" + finishTime.get(Calendar.MINUTE);
        }
    }

    public String getStartTime() {
        return getTimeString(startTime);
    }

    public String getFinishTime() {
        return getTimeString(finishTime);
    }

    private String getDateString(Calendar time) {
        if (time.get(Calendar.MONTH) + 1 >= 10 && time.get(Calendar.DAY_OF_MONTH) >= 10) {
            return time.get(Calendar.YEAR) + "-" + (time.get(Calendar.MONTH) + 1) + "-" + time.get(Calendar.DAY_OF_MONTH);
        } else if (time.get(Calendar.MONTH) + 1 >= 10 && time.get(Calendar.DAY_OF_MONTH) < 10) {
            return time.get(Calendar.YEAR) + "-" + (time.get(Calendar.MONTH) + 1) + "-0" + time.get(Calendar.DAY_OF_MONTH);
        } else if (time.get(Calendar.MONTH) + 1 < 10 && time.get(Calendar.DAY_OF_MONTH) >= 10) {
            return time.get(Calendar.YEAR) + "-0" + (time.get(Calendar.MONTH) + 1) + "-" + time.get(Calendar.DAY_OF_MONTH);
        } else {
            return time.get(Calendar.YEAR) + "-0" + (time.get(Calendar.MONTH) + 1) + "-0" + time.get(Calendar.DAY_OF_MONTH);
        }
    }

    public String getStartDate() {
        return getDateString(startTime);
    }

    public String getFinishDate() {
        return getDateString(finishTime);
    }

    private String getDateStringArea(Calendar time) {
        if (time.get(Calendar.MONTH) + 1 >= 10 && time.get(Calendar.DAY_OF_MONTH) >= 10) {
            return time.get(Calendar.DAY_OF_MONTH) + "/" + (time.get(Calendar.MONTH) + 1) + "/" + time.get(Calendar.YEAR);
        } else if (time.get(Calendar.MONTH) + 1 >= 10 && time.get(Calendar.DAY_OF_MONTH) < 10) {
            return "0" + time.get(Calendar.DAY_OF_MONTH) + "/" + (time.get(Calendar.MONTH) + 1) + "/" + time.get(Calendar.YEAR);
        } else if (time.get(Calendar.MONTH) + 1 < 10 && time.get(Calendar.DAY_OF_MONTH) >= 10) {
            return time.get(Calendar.DAY_OF_MONTH) + "/0" + (time.get(Calendar.MONTH) + 1) + "/" + time.get(Calendar.YEAR);
        } else {
            return "0" + time.get(Calendar.DAY_OF_MONTH) + "/0" + (time.get(Calendar.MONTH) + 1) + "/" + time.get(Calendar.YEAR);
        }
    }

    public String getStartDateArea() {
        return getDateStringArea(startTime);
    }

    public String getFinishDateArea() {
        return getDateStringArea(finishTime);
    }

    private void setDateTime(String time, String date, Calendar calendar) {
        String[] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        int min = Integer.parseInt(times[1]);
        String[] dates = date.split("-");
        int year = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[2]);
        calendar.set(year, month - 1, day, hour, min);
    }

    public void setStart(String time, String date) {
        setDateTime(time, date, startTime);
    }

    public void setFinish(String time, String date) {
        setDateTime(time, date, finishTime);
    }

    public double minutesAvailable() {
        return ChronoUnit.MINUTES.between(startTime.toInstant(), finishTime.toInstant());
    }

    public int getStartDayOfWeek() {
        return startTime.get(Calendar.DAY_OF_WEEK);
    }

    public int getFinishDayOfWeek() {
        return finishTime.get(Calendar.DAY_OF_WEEK);
    }
}
