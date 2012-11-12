package il.ac.shenkar.mobile;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends Activity {


	class TaskAdapter extends BaseAdapter {
		Context context;
		ArrayList<Object> data = null;

		public TaskAdapter(Context context, ArrayList<Object> data) {
			super();
			this.context = context;
			this.data = data == null ? new ArrayList<Object>() : data;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			if (convertView == null) {
				View item = ((Activity) context).getLayoutInflater().inflate(
						R.layout.list_view_item, parent, false);
				TextView txt = (TextView) item.findViewById(R.id.textView1);
				txt.setText((String) data.get(pos));
				return item;
			} else {
				TextView txt = (TextView) convertView
						.findViewById(R.id.textView1);
				txt.setText((String) data.get(pos));
				return convertView;
			}
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int arg0) {
			return data.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

	}

	private ListView listView1;
	private TaskAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		adapter = new TaskAdapter(this, null);
		assert (adapter.data != null);
		for (int i = 0; i < 20; i++) {
			adapter.data.add("Item" + i);
		}

		listView1 = (ListView) findViewById(R.id.listView1);
		listView1.addHeaderView(getLayoutInflater().inflate(
				R.layout.list_view_header, listView1, false));
		listView1.setAdapter(adapter);
		
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                Intent taskIntent = new Intent(v.getContext(), TaskActivity.class);
                startActivityForResult(taskIntent, 0);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_list, menu);
		return true;
	}
}
