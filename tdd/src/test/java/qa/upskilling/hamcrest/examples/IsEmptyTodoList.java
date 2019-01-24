package qa.upskilling.hamcrest.examples;

import qa.upskilling.todolist.abstractions.TodoList;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEmptyTodoList extends TypeSafeMatcher<TodoList> {
    @Factory
    public static Matcher<TodoList> isAnEmptyTodoList() {
        return new IsEmptyTodoList();
    }

    @Override
    protected boolean matchesSafely(TodoList todoList) {
        return todoList.length() == 0;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an empty todo list");
    }
}
