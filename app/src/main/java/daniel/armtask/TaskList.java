package daniel.armtask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskList extends Activity {

    private ListView taskListView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> tasks = new ArrayList<String>();
    private File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"tasks.txt");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tasks);

        taskListView = (ListView) findViewById(R.id.taskList);
        taskListView.setAdapter(arrayAdapter);


    }

    public void addTaskButton(View v) {
        Button button = (Button) v; //Create a reference to v, cast as a button object

        EditText editText = (EditText) findViewById(R.id.newTaskInputBox);
        arrayAdapter.add(editText.getText().toString());
        editText.setText("");

    }

    public void loadTasks(View v) {
        if(file.exists()) {
            Scanner scan = null;

            try {
                scan = new Scanner(file);
            } catch (Exception e) {
                e.printStackTrace();
            }

            while(scan.hasNextLine()) {
                arrayAdapter.add(scan.nextLine());
            }
        }
    }

    public void saveButton(View v) {
        PrintStream printStream = null;
        try {
            printStream = new PrintStream(file);
        } catch(Exception e) {
            e.printStackTrace();
        }

        if(printStream != null) {
            for(String task : tasks) {
                printStream.println(task);
            }
            Toast.makeText(TaskList.this, "Saved", Toast.LENGTH_SHORT).show();
        }

    }

    public void clearButton(View v) {
        arrayAdapter.clear();
        arrayAdapter.notifyDataSetChanged();
        Toast.makeText(TaskList.this, "All tasks cleared", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
