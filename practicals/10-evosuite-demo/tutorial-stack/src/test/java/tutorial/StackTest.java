package tutorial;

import org.junit.Test;
import org.junit.Assert;

public class StackTest {

    @Test
    public void test() {
	Stack<Object> stack = new Stack<Object>();
	stack.push(new Object());
	Assert.assertFalse(stack.isEmpty());
    }
}
