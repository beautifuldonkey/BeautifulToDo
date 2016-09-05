package beautifuldonkey.beautifultodo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

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
  String existingNoteName;

  EditText textNewItemName;
  EditText textNewItemComments;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_todo);
    context = getApplicationContext();

    todoList = getIntent().getParcelableExtra(TodoConstants.INTENT_EXTRA_LIST);
    todoDatabaseHelper = new TodoDatabaseHelper(this);

    registerReceiver(receiverRefreshNotes, new IntentFilter(TodoConstants.INTENT_EXTRA_REFRESH_LIST));
    registerReceiver(receiverUpdateNote, new IntentFilter(TodoConstants.INTENT_EXTRA_UPDATE_LIST));

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
          TextView textComments = (TextView) view.findViewById(R.id.text_comments);
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
          openPopup(null);
        }
      });
    }
  }

  @Override
  protected void onStop() {
    super.onStop();
    unregisterReceiver(receiverRefreshNotes);
    unregisterReceiver(receiverUpdateNote);
  }

  @Override
  protected void onResume() {
    super.onResume();
    registerReceiver(receiverRefreshNotes, new IntentFilter(TodoConstants.INTENT_EXTRA_REFRESH_LIST));
    registerReceiver(receiverUpdateNote, new IntentFilter(TodoConstants.INTENT_EXTRA_UPDATE_LIST));
  }

  private void openPopup(@Nullable final Note existingNote){
    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    final View view = inflater.inflate(R.layout.popup_new_item,null);
    final PopupWindow popupWindow = new PopupWindow(view,400,800,true);
    popupWindow.setContentView(view);
    popupWindow.showAtLocation(btnAddItem, Gravity.CENTER,0,0);

    textNewItemComments = (EditText) view.findViewById(R.id.new_note_comments);
    textNewItemName = (EditText) view.findViewById(R.id.new_note_name);

    if(existingNote!=null){
      existingNoteName = existingNote.getName();
      textNewItemName.setText(existingNoteName);
      textNewItemComments.setText(existingNote.getComments());
    }

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

        Note note = new Note();
        note.setName(textNewItemName.getText().toString());

        if(!"".equals(textNewItemComments.getText().toString())){
          note.setComments(textNewItemComments.getText().toString());

          int keyLocation = todoList.getNotes().indexOf(existingNote);

          if(keyLocation == -1 || todoList.getNotes().remove(keyLocation) == null){
            Toast.makeText(context,"note not removed",Toast.LENGTH_SHORT).show();
          }
        }


        if(!updateTodoNotes(existingNote,note)){
          Toast.makeText(context,"note list note updated",Toast.LENGTH_SHORT).show();
        }

        adapterTodo.notifyDataSetChanged();
        popupWindow.dismiss();
      }
    });
  }

  private BroadcastReceiver receiverRefreshNotes = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      adapterTodo.notifyDataSetChanged();
      todoDatabaseHelper.updateTodoList(todoList);
    }
  };

  private BroadcastReceiver receiverUpdateNote = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      Note note = intent.getParcelableExtra("ExistingNote");
      openPopup(note);
    }
  };

  private Boolean updateTodoNotes(@Nullable Note existingNote, Note newNote){
    Boolean updateSuccessful = false;

    todoList.getNotes().add(newNote);
    todoDatabaseHelper.updateTodoList(todoList);

    return updateSuccessful;
  }

}