import org.junit.Assert;
import org.junit.Test;

public class TestClassTest {

    @Test
    public void addTest1() {
        Assert.assertEquals(TestClass.add(5, 5), 10);
    }
}