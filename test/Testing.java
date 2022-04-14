import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Testing {
    @Test
    @DisplayName("Тест 1")
    public void test1() {
        Assertions.assertEquals(2, test.jokerMask.Test.calculate(1, 1));
    }

    @Test
    @DisplayName("Тест 2")
    public void test2() {
        Assertions.assertNotEquals(2, test.jokerMask.Test.calculate(1, 2));
    }
}
