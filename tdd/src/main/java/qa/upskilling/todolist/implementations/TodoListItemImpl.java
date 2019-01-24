package qa.upskilling.todolist.implementations;

import qa.upskilling.todolist.abstractions.TodoListItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TodoListItemImpl implements TodoListItem {

    final static Logger logger = LogManager.getLogger(TodoListItemImpl.class);
    private final String title;
    private boolean toggled;

    public TodoListItemImpl(String title) {
        if (title.isEmpty()) throw new IllegalArgumentException("Title cannot be empty");
        this.title = title;
        this.toggled = false;
    }

    public String getTitle() {
        return title;
    }

    public TodoListItemImpl setTitle(String title) {
        if (title.isEmpty()) throw new IllegalArgumentException("New title cannot be empty");
        return new TodoListItemImpl(title);
    }

    public boolean isToggled() {
        return toggled;
    }

    @Override
    public void toggle() {
        if (toggled) {
            toggled = false;
            return;
        }
        toggled = true;

    }

}
