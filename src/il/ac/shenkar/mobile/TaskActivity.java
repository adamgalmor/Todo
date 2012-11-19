
package il.ac.shenkar.mobile;

import il.ac.shenkar.mobile.TaskModel.Task;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class TaskActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Task t = new Task();
                t.text = ((EditText) findViewById(R.id.editText1)).getText()
                        .toString();

                DatePicker datePicker = ((DatePicker) findViewById(R.id.datePicker1));
                TimePicker timePicker = ((TimePicker) findViewById(R.id.timePicker1));

                Calendar cal = Calendar.getInstance(Locale.getDefault());
                cal.set(datePicker.getYear(), datePicker.getMonth(), datePicker
                        .getDayOfMonth(), timePicker.getCurrentHour(), timePicker
                        .getCurrentMinute());

                Toast.makeText(getBaseContext(), "Task Created", Toast.LENGTH_SHORT).show();

                t.timestamp = cal.getTimeInMillis();
                TaskModel.getInstance().tasks.add(t);

                Collections.sort(TaskModel.getInstance().tasks,
                        new Comparator<Task>() {
                            @Override
                            public int compare(Task arg0, Task arg1) {
                                return (int) (arg0.timestamp - arg1.timestamp);
                            }
                        });

                Intent intent = new Intent();
                if (getParent() == null) {
                    setResult(Activity.RESULT_OK, intent);
                } else {
                    getParent().setResult(Activity.RESULT_OK, intent);
                }
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_task, menu);
        return true;
    }
}
