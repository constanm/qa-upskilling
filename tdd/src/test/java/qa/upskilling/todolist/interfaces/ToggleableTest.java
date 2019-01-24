package qa.upskilling.todolist.interfaces;

import qa.upskilling.todolist.implementations.TodoListItemImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ToggleableTest {

    Toggleable item;

    @BeforeEach
    public void setUp() {
        item = new TodoListItemImpl("Toggleable item");
    }

    @Test
    public void canToggleItem() {
        item.toggle();
        Assertions.assertTrue(item.isToggled());
    }

    @Test
    public void canUntoggleItem() {
        item.toggle();
        item.toggle();
        Assertions.assertFalse(item.isToggled());
    }
}
