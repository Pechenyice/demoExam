import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.org.test.Calculation;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;

public class CalculationTests {
    public final Calculation calculation = new Calculation();

    @Test
    @DisplayName("Легкий тест 1")
    public void Test1() {
        int duration = 30;
        LocalTime[] times = new LocalTime[] {
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(15, 0),
                LocalTime.of(15, 30),
                LocalTime.of(16, 50)
        };
        int[] periods = new int[] {60, 30, 10, 10, 40};
        String[] test = calculation.getPeriods(times, periods, duration, LocalTime.of(8, 0), LocalTime.of(18, 0));

        String[] answers = new String[] {
                "8:00-8:30",
                "8:30-9:00",
                "9:00-9:30",
                "9:30-10:00",
                "11:30-12:00",
                "12:00-12:30",
                "12:30-13:00",
                "13:00-13:30",
                "13:30-14:00",
                "14:00-14:30",
                "14:30-15:00",
                "15:40-16:10",
                "16:10-16:40",
                "17:30-18:00"
        };

        Assertions.assertEquals(
                Arrays.toString(calculation.getPeriods(times, periods, duration, LocalTime.of(8, 0), LocalTime.of(18, 0))),
                Arrays.toString(answers)
        );
    }
}
