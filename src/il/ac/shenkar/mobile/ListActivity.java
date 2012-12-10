
package il.ac.shenkar.mobile;

import il.ac.shenkar.mobile.TaskModel.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends Activity {

    class TaskAdapter extends BaseAdapter {
        Context context;
        LayoutInflater inflater;

        public TaskAdapter(Context context, ArrayList<Object> data) {
            super();
            this.context = context;
            this.inflater = ((Activity) context).getLayoutInflater();
        }

        @Override
        public View getView(final int pos, View convertView, ViewGroup parent) {
            if (convertView == null) {
                final Task t = TaskModel.getInstance().tasks.get(pos);

                View item = inflater.inflate(R.layout.list_view_item, parent, false);

                ((Button) item.findViewById(R.id.DoneBtn))
                        .setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {                                
                                TasksOpenHelper db_helper = new TasksOpenHelper(v.getContext());
                                SQLiteDatabase db = db_helper.getWritableDatabase();
                                db.delete(TasksOpenHelper.TABLE_NAME, "id = ?", new String[] {
                                    String.valueOf(t.id)
                                });
                                db.close();
                                TaskModel.getInstance().tasks.remove(pos);
                                notifyDataSetChanged();
                                Toast.makeText(getBaseContext(), "Task Removed", Toast.LENGTH_SHORT).show();
                            }
                        });

                ((TextView) item.findViewById(R.id.textView1)).setText(t.text);
                ((TextView) item.findViewById(R.id.textView2))
                        .setText(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss",
                                Locale.getDefault()).format(t.timestamp));
                item.setTag(t);
                return item;
            } else {
                Task t = TaskModel.getInstance().tasks.get(pos);
                ((TextView) convertView.findViewById(R.id.textView1))
                        .setText(t.text);
                ((TextView) convertView.findViewById(R.id.textView2))
                        .setText(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss",
                                Locale.getDefault()).format(t.timestamp));
                convertView.setTag(t);
                return convertView;
            }
        }

        @Override
        public int getCount() {
            return TaskModel.getInstance().tasks.size();
        }

        @Override
        public Object getItem(int pos) {
            return TaskModel.getInstance().tasks.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

    }

    private ListView listView1;
    private TaskAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        TasksOpenHelper db_helper = new TasksOpenHelper(this);
        SQLiteDatabase db = db_helper.getReadableDatabase();
        // Cursor cur = db.rawQuery("SELECT * FROM " +
        // TasksOpenHelper.TABLE_NAME, null);
        Cursor cur = db.query(false, TasksOpenHelper.TABLE_NAME, null, null, null, null, null,
                null, null);
        if (cur != null && cur.moveToFirst())
            do {
                Task t = new Task();
                t.id = cur.getInt(0);
                t.text = cur.getString(1);
                t.timestamp = cur.getLong(2);
                TaskModel.getInstance().tasks.add(t);
            } while (cur.moveToNext());

        adapter = new TaskAdapter(this, null);
        // if (adapter.isEmpty())
        // for (int i = 0; i < 3; i++) {
        // Task t = new Task();
        // t.text = "Item" + i;
        // TaskModel.getInstance().tasks.add(t);
        // }

        listView1 = (ListView) findViewById(R.id.listView1);
        listView1.addHeaderView(getLayoutInflater().inflate(
                R.layout.list_view_header, listView1, false));
        listView1.setAdapter(adapter);

        findViewById(R.id.AddTaskBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent taskIntent = new Intent(v.getContext(),
                        TaskActivity.class);
                startActivityForResult(taskIntent, 0);
            }
        });

    }

    @Override
    protected void onDestroy() {
        TaskModel.getInstance().tasks.clear();
        super.onDestroy();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list, menu);
        return true;
    }
}
