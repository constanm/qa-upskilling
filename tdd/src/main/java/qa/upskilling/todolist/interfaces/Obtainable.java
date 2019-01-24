package qa.upskilling.todolist.interfaces;

import qa.upskilling.todolist.implementations.TodoListItemImpl;

public interface Obtainable {
    TodoListItemImpl getItem(String title);

    TodoListItemImpl getItem(int number);
}
