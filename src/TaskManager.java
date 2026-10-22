import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TaskManager {
    
private static final String TASKS_FILE = "tasks.json";
private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
private Set<Integer> notifiedTasks = new HashSet<>();

    private List<Task> tasks = new ArrayList<>();
    private int nextId = 1;
    
    private void saveTasks() {
        try (FileWriter writer = new FileWriter(TASKS_FILE)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println("Assistant: Could not save tasks.");
        }
    }

    private void loadTasks() {
        try (FileReader reader = new FileReader(TASKS_FILE)) {
            Task[] savedTasks = gson.fromJson(reader, Task[].class);

            if (savedTasks != null) {
                tasks.clear();
                tasks.addAll(Arrays.asList(savedTasks));

                nextId = 1;

                for (Task task : tasks) {
                    if (task.getId() >= nextId) {
                        nextId = task.getId() + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Assistant: No saved tasks found. Starting fresh.");
        }
    }
    
    public TaskManager() {
        loadTasks();
    }



    public void addTask(String title) {
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Assistant: Task title cannot be empty.");
            return;
        }

        Task task = new Task(nextId, title.trim());
        tasks.add(task);
        saveTasks();

        System.out.println("Assistant: Task added successfully.");
        System.out.println("Task ID: " + nextId);
        System.out.println("Title: " + title.trim());

        nextId++;
    }
    
    public void showTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Assistant: You have no tasks yet.");
            return;
        }

        System.out.println("\nYour Tasks:");

        for (Task task : tasks) {
            String status;

            if (task.isCompleted()) {
                status = "Completed";
            } else {
                status = "Pending";
            }
            System.out.println(task.getId() + ". " + task.getTitle()
        + " [" + status + "]"
        + " [Priority: " + task.getPriority() + "]");
            if (task.getDeadline() != null) {
                System.out.println("   Deadline: " + task.getDeadline());
            }
            if (task.getReminderTime() != null) {
                System.out.println("   Reminder: " + task.getReminderTime());
            }
        }
    }

    public void completeTask(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                if (task.isCompleted()) {
                    System.out.println("Assistant: This task is already completed.");
                } else {
                    task.setCompleted(true);
                    saveTasks();
                    System.out.println("Assistant: Task completed successfully.");
                }
                return;
            }
        }

        System.out.println("Assistant: Task not found.");
    }

    public void deleteTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                saveTasks();
                System.out.println("Assistant: Task deleted successfully.");
                return;
            }
        }

        System.out.println("Assistant: Task not found.");
    }
    
    public void setDeadline(int id, String dateTime) {
        try {
            LocalDateTime deadline = LocalDateTime.parse(
                dateTime,
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            );

            for (Task task : tasks) {
                if (task.getId() == id) {
                    task.setDeadline(deadline.toString());
                    saveTasks();
                    System.out.println("Assistant: Deadline set successfully.");
                    return;
                }
            }

            System.out.println("Assistant: Task not found.");

        } catch (DateTimeParseException e) {
            System.out.println("Assistant: Use dd-MM-yyyy HH:mm format.");
        }
    }

    public void setReminder(int id, String dateTime) {
        try {
            LocalDateTime reminder = LocalDateTime.parse(
                dateTime,
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            );

            for (Task task : tasks) {
                if (task.getId() == id) {
                    task.setReminderTime(reminder.toString());
                    saveTasks();
                    System.out.println("Assistant: Reminder time saved successfully.");
                    return;
                }
            }

            System.out.println("Assistant: Task not found.");

        } catch (DateTimeParseException e) {
            System.out.println("Assistant: Use dd-MM-yyyy HH:mm format.");
        }
    }

    
    public void checkReminders() {
        LocalDateTime now = LocalDateTime.now();

        for (Task task : tasks) {
            if (task.isCompleted() || task.isReminderNotified()) {
                continue;
            }

            String reminderTime = task.getReminderTime();

            if (reminderTime == null) {
                continue;
            }

            try {
                LocalDateTime reminder = LocalDateTime.parse(reminderTime);

                if (!now.isBefore(reminder)) {
                    System.out.println("\nAssistant Reminder: " + task.getTitle());
                    System.out.println("Scheduled for: " + reminder);

                    task.setReminderNotified(true);
                    saveTasks();
                }
            } catch (DateTimeParseException e) {
                System.out.println("Assistant: Invalid reminder time for task "
                        + task.getId());
            }
        }
    }

    
    public void startReminderScheduler() {
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(
            this::checkReminders,
            0,
            1,
            TimeUnit.SECONDS
        );
    }
    
    public void setPriority(int id, String priority) {
        priority = priority.trim();
            if (!priority.equalsIgnoreCase("High")
                && !priority.equalsIgnoreCase("Medium")
                && !priority.equalsIgnoreCase("Low")) {
            System.out.println("Assistant: Choose High, Medium, or Low.");
            return;
            }
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setPriority(
                    priority.substring(0, 1).toUpperCase()
                    + priority.substring(1).toLowerCase()
                );
                saveTasks();
                System.out.println("Assistant: Priority updated to "
                        + task.getPriority() + ".");
                return;
            }
        }
        System.out.println("Assistant: Task not found.");
    }
    public Task findTaskByTitle(String title) {
        for (Task task : tasks) {
            if (task.getTitle().equalsIgnoreCase(title.trim())) {
                return task;
            }
        }

        return null;
    }

    public void showPendingTasks() {
        boolean found = false;

        for (Task task : tasks) {
            if (!task.isCompleted()) {
                if (!found) {
                    System.out.println("Assistant: Your pending tasks:");
                    found = true;
                }

                System.out.println(task.getId() + ". " + task.getTitle()
                        + " [Priority: " + task.getPriority() + "]");
            }
        }

        if (!found) {
            System.out.println("Assistant: You have no pending tasks.");
        }
    }
   
public void showHighPriorityTasks() {
    boolean found = false;

    for (Task task : tasks) {
        if (!task.isCompleted()
                && task.getPriority().equalsIgnoreCase("High")) {
            if (!found) {
                System.out.println("Assistant: Your high-priority tasks:");
                found = true;
            }

            System.out.println(task.getId() + ". " + task.getTitle()
                    + " [Priority: " + task.getPriority() + "]");
        }
    }

    if (!found) {
        System.out.println("Assistant: You have no pending high-priority tasks.");
    }
}


}
