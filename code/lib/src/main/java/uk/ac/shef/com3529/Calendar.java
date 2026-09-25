package uk.ac.shef.com3529;

public class Calendar {

    public static int daysBetweenTwoDates(int year1, int month1, int day1,
                                          int year2, int month2, int day2) {
        int days = 0;

        // sanitize month inputs
        if (month1 < 1) month1 = 1;
        if (month2 < 1) month2 = 1;
        if (month1 > 12) month1 = 12;
        if (month2 > 12) month2 = 12;

        // sanitize day inputs
        if (day1 < 1) day1 = 1;
        if (day2 < 1) day2 = 1;
        if (day1 > daysInMonth(month1, year1))
            day1 = daysInMonth(month1, year1);
        if (day2 > daysInMonth(month2, year2))
            day2 = daysInMonth(month2, year2);

        // swap dates if year2, month2, day2 is before year1, month1, day1
        if ((year2 < year1) ||
                (year2 == year1 && month2 < month1) ||
                (year2 == year1 && month2 == month1 && day2 < day1)) {
            int t = month2;
            month2 = month1;
            month1 = t;
            t = day2;
            day2 = day1;
            day1 = t;
            t = year2;
            year2 = year1;
            year1 = t;
        }

        // calculate days
        if (month1 == month2 && year1 == year2) {
            days = day2 - day1;
        } else {
            days += daysInMonth(month1, year1) - day1;
            days += day2;
            if (year1 == year2) {
                int month = month1 + 1;
                while (month < month2) {
                    days += daysInMonth(month, year1);
                    month++;
                }
            } else {
                int year;
                int month = month1 + 1;
                while (month <= 12) {
                    days += daysInMonth(month, year1);
                    month++;
                }
                month = 1;
                while (month < month2) {
                    days += daysInMonth(month, year2);
                    month++;
                }
                year = year1 + 1;
                while (year < year2) {
                    days += 365;
                    if (isLeapYear(year)) days++;
                    year++;
                }
            }
        }
        return days;
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public static int daysInMonth(int month, int year) {
        int daysInMonthNonLeapYear[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return month == 2 && isLeapYear(year) ? 29 : daysInMonthNonLeapYear[month - 1];
    }
}
