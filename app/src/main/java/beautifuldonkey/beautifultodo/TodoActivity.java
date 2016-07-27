package beautifuldonkey.beautifultodo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import beautifuldonkey.beautifultodo.adapters.AdapterManager;
import beautifuldonkey.beautifultodo.data.Note;
import beautifuldonkey.beautifultodo.data.NoteList;
import beautifuldonkey.beautifultodo.data.TodoConstants;

public class TodoActivity extends AppCompatActivity {

  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_todo);
    context = getApplicationContext();

    NoteList todoList = getIntent().getParcelableExtra(TodoConstants.INTENT_EXTRA_LIST);

    TextView textListName = (TextView) findViewById(R.id.text_list_name);
    textListName.setText(todoList.getName());

    ListView listTodoItems = (ListView) findViewById(R.id.list_todo_items);
    ArrayAdapter<Note> adapterTodo = AdapterManager.getNoteAdapter(context,todoList.getNotes());
    listTodoItems.setAdapter(adapterTodo);

    Button btnAddItem = (Button) findViewById(R.id.btn_add_item);
  }
}
