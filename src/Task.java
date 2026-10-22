
public class Task {
    private int id;
    private String title;
    private boolean completed;
    private String deadline;
    private String reminderTime;
    private boolean reminderNotified;
    private String priority = "Medium";

    public Task(int id, String title) {
        this.id = id;
        this.title = title;
        this.completed = false;
        this.deadline = null;
        this.reminderTime = null;
    }

    public Task(int id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.deadline = null;
        this.reminderTime = null;
    }

    public Task(int id, String title, boolean completed,
                String deadline, String reminderTime) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.deadline = deadline;
        this.reminderTime = reminderTime;
        this.reminderNotified = false;
    }
    public Task(int id, String title, boolean completed,
            String deadline, String reminderTime, String priority) {
    this.id = id;
    this.title = title;
    this.completed = completed;
    this.deadline = deadline;
    this.reminderTime = reminderTime;
    this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }
    public boolean isReminderNotified() {
    return reminderNotified;
    }
    public void setReminderNotified(boolean reminderNotified) {
        this.reminderNotified = reminderNotified;
    }
    public String getPriority() {
    return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
}
