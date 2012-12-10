
package il.ac.shenkar.mobile;

import java.util.ArrayList;
import java.util.Date;

public class TaskModel {
    public static class Task {
//        static private int nextID = 0;
//        int id = nextID++;
        int id = 0;
        long timestamp = new Date().getTime();
        String text = "";
    }

    private static TaskModel instance = null;

    ArrayList<Task> tasks = new ArrayList<Task>();

    private TaskModel() {
    }

    public static TaskModel getInstance() {
        if (null == instance)
            instance = new TaskModel();
        return instance;
    }

}
