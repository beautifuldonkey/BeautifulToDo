package beautifuldonkey.beautifultodo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import beautifuldonkey.beautifultodo.adapters.AdapterManager;
import beautifuldonkey.beautifultodo.data.Note;
import beautifuldonkey.beautifultodo.data.NoteList;
import beautifuldonkey.beautifultodo.data.TodoConstants;
import beautifuldonkey.beautifultodo.data.TodoDatabaseHelper;

public class TodoActivity extends AppCompatActivity {

  ArrayAdapter<Note> adapterTodo;
  Button btnAddItem;
  Context context;
  Integer initialItemHeight;
  ListView listTodoItems;
  NoteList todoList;
  TodoDatabaseHelper todoDatabaseHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_todo);
    context = getApplicationContext();

    todoList = getIntent().getParcelableExtra(TodoConstants.INTENT_EXTRA_LIST);
    todoDatabaseHelper = new TodoDatabaseHelper(this);

    TextView textListName = (TextView) findViewById(R.id.text_list_name);
    if(textListName!=null){
      textListName.setText(todoList.getName());
    }

    listTodoItems = (ListView) findViewById(R.id.list_todo_items);
    adapterTodo = AdapterManager.getNoteAdapter(context,todoList.getNotes());
    if(listTodoItems!=null){
      listTodoItems.setAdapter(adapterTodo);
      listTodoItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          if(initialItemHeight==null){
            initialItemHeight = view.getHeight();
          }
          TextView textComments = (TextView) view.findViewById(R.id.text_note_comments);
          if(textComments.getVisibility()==View.INVISIBLE){
            textComments.setVisibility(View.VISIBLE);
            view.setMinimumHeight(initialItemHeight+50);
          }else{
            textComments.setVisibility(View.INVISIBLE);
            view.setMinimumHeight(initialItemHeight);
          }
        }
      });
    }

    btnAddItem = (Button) findViewById(R.id.btn_add_item);
    if(btnAddItem!=null){
      btnAddItem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
          final View view = inflater.inflate(R.layout.popup_new_item,null);
          final PopupWindow popupWindow = new PopupWindow(view,300,500,true);
          popupWindow.setContentView(view);
          popupWindow.showAtLocation(btnAddItem, Gravity.CENTER,0,0);

          Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
          btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              popupWindow.dismiss();
            }
          });

          Button btnDone = (Button) view.findViewById(R.id.btn_done);
          btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              EditText textNewItemName = (EditText) view.findViewById(R.id.new_item_name);
              if(textNewItemName.getText()!= null){
                Note note = new Note();
                note.setName(textNewItemName.getText().toString());
                todoList.getNotes().add(note);
                adapterTodo.notifyDataSetChanged();
                popupWindow.dismiss();
                todoDatabaseHelper.updateTodoList(todoList);
              }
            }
          });

        }
      });
    }

  }
}