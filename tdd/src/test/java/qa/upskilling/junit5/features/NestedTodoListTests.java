package qa.upskilling.junit5.features;

import qa.upskilling.todolist.implementations.TodoListImpl;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import qa.upskilling.hamcrest.examples.IsEmptyTodoList;

import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("A todo list")
public class NestedTodoListTests {

    TodoListImpl todoList;

    @Nested
    @DisplayName("when new")
    class WhenNewTodoList {

        @BeforeEach
        void setUpEmptyTodoList() {
            todoList = new TodoListImpl();
        }

        @Test
        @DisplayName("is empty")
        void newTodoListIsEmpty() {
            MatcherAssert.assertThat(todoList, IsEmptyTodoList.isAnEmptyTodoList());
        }

        @Test
        @DisplayName("throws exception when toggle all items in empty list")
        void throwsExceptionWhenToggleNonExistingItem() {
            Assertions.assertThrows(NullPointerException.class,
                    () -> todoList.toggleAll());
        }

    }
}
