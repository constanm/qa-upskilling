package qa.upskilling.todolist.implementations;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static qa.upskilling.hamcrest.examples.IsEmptyTodoList.isAnEmptyTodoList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Tag("todolist")
public class TodoListImplTest {

    private static Logger logger;
    private TodoListImpl list;
    private TodoListItemImpl[] todos;

    @BeforeAll
    public static void setUpAll() {
        logger = LoggerFactory.getLogger(TodoListImplTest.class);
    }

    @AfterAll
    public static void tearDownAll() {
    }

    @BeforeEach
    public void setUp() {
        todos = new TodoListItemImpl[]{
                new TodoListItemImpl("Go for a walk with the dog"),
                new TodoListItemImpl("Buy milk and sugar"),
                new TodoListItemImpl("See favourite TV series")
        };
        list = new TodoListImpl();
    }

    @Test
    public void can_add_items_to_the_list() {
        logger.info("Add items to list test");
        list.addItem(todos[0]);
        list.addItem(todos[1]);
        Assertions.assertEquals(2, list.length());
    }

    @Test
    public void can_add_an_array_of_items_to_the_list() {
        logger.info("Add array of items in list test");
        list.addItems(todos);
        Assertions.assertEquals(3, list.length());
    }

    @Test
    public void can_add_item_with_title_to_the_list() {
        list.addItem("Go to training for trainers");
        assertThat(list.length(), equalTo(1));
    }

    @Test
    public void toggle_item_in_the_list() {
        list.addItem(todos[0]);
        assertThat(list.toggle("Go for a walk with the dog"), is(equalTo(true)));
    }

    @Test
    @DisplayName("Exception have been thrown when we toggle non-existing item")
    public void throw_exception_when_toggle_non_existing_item() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> list.toggle("Non-existing item in list")
        );
    }

    @Test
    public void can_not_add_two_items_with_the_same_titles() {
        list.addItem(todos[0]);
        list.addItem("Go for a walk with the dog");
        assertThat(list.length(), equalTo(1));
    }

    @Test
    public void can_delete_item_using_its_title() {
        String targetTitle = "Item for deletion";
        list.addItem("Item not for deletion");
        list.addItem(targetTitle);
        list.deleteItem(targetTitle);
        assertThat(list.length(), is(equalTo(1)));
    }

    @Test
    public void can_delete_item_using_its_index() {
        list.addItem(todos[0]);
        int targetItemIndex = list.addItem(todos[1]);
        list.deleteItem(targetItemIndex);
        assertThat(list.length(), is(equalTo(1)));
    }

    @Test
    public void throws_exception_when_deleting_item_with_wrong_index() {
        Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> list.deleteItem(1));
    }

    @Test
    public void toggle_all_untoggled_items() {
        String targetTitle = todos[0].getTitle();
        list.addItems(todos);
        list.toggle(targetTitle);
        list.toggleAll();
        Assertions.assertEquals(false, list.getItem(targetTitle).isToggled());
    }

    @Test
    public void get_item_with_its_title() {
        String targetTitle = todos[0].getTitle();
        list.addItems(todos);
        Assertions.assertEquals(targetTitle, list.getItem(targetTitle).getTitle());
    }

    @Test
    public void todo_list_is_empty_after_creation() {
        logger.info("test with our custom matcher for todo list class");
        assertThat(list, isAnEmptyTodoList());
    }

    @AfterEach
    public void tearDown() {

    }
}
