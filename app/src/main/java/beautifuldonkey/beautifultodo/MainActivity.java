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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import beautifuldonkey.beautifultodo.adapters.AdapterManager;
import beautifuldonkey.beautifultodo.data.NoteList;
import beautifuldonkey.beautifultodo.data.TodoConstants;
import beautifuldonkey.beautifultodo.data.TodoDatabaseHelper;

public class MainActivity extends AppCompatActivity {

  Context context;
  List<NoteList> lists;
  ArrayAdapter<NoteList> noteListAdapter;
  Button btnAddList;
  NoteList newList;
  TodoDatabaseHelper todoDatabaseHelper;
  View newListView;
  PopupWindow popupWindow;
  String existingName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    context = getApplicationContext();

    lists = new ArrayList<>();
    todoDatabaseHelper = new TodoDatabaseHelper(this);
    registerReceiver(receiverRefreshLists,new IntentFilter(TodoConstants.INTENT_EXTRA_REFRESH_LIST));
    registerReceiver(receiverUpdateTodoList,new IntentFilter(TodoConstants.INTENT_EXTRA_UPDATE_LIST));

    if(todoDatabaseHelper.getTodoListCount()>0){
      lists = todoDatabaseHelper.getAllTodoLists();
    }

    ListView noteLists = (ListView) findViewById(R.id.note_list);
    noteListAdapter = AdapterManager.getNoteListAdapter(context,lists);
    if(noteLists != null){
      noteLists.setAdapter(noteListAdapter);
      noteLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          NoteList selectedList = lists.get(position);
          Intent intent = new Intent(context,TodoActivity.class);
          intent.putExtra(TodoConstants.INTENT_EXTRA_LIST,selectedList);
          startActivityForResult(intent,TodoConstants.INTENT_OPEN_LIST);
          unregisterReceiver(receiverRefreshLists);
          unregisterReceiver(receiverUpdateTodoList);
        }
      });
    }

    btnAddList = (Button) findViewById(R.id.btn_add_list);
    if(btnAddList != null){
      btnAddList.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          openPopup(null);
        }
      });
    }

  }

  @Override
  protected void onResume() {
    super.onResume();
    registerReceiver(receiverRefreshLists,new IntentFilter(TodoConstants.INTENT_EXTRA_REFRESH_LIST));
    registerReceiver(receiverUpdateTodoList,new IntentFilter(TodoConstants.INTENT_EXTRA_UPDATE_LIST));
    updateTodoLists();
  }

  private void openPopup(@Nullable final NoteList listToUpdate){
    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    newList = new NoteList();

    existingName = "";
    if(listToUpdate!=null){
      newList = listToUpdate;
      existingName = listToUpdate.getName();
    }

    newListView = inflater.inflate(R.layout.popup_new_list,null);
    popupWindow = new PopupWindow(newListView,300,500,true);
    popupWindow.setContentView(newListView);
    popupWindow.showAtLocation(btnAddList, Gravity.CENTER,0,0);

    Button btnDoneListName = (Button) newListView.findViewById(R.id.btn_new_list_name);
    btnDoneListName.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        TextView textListName = (TextView) newListView.findViewById(R.id.new_list_name);
        if(textListName.getText().length()>0){
          newList.setName(textListName.getText().toString());
          if(listToUpdate!=null){
            todoDatabaseHelper.deleteTodoList(existingName);
          }
          todoDatabaseHelper.addTodoList(newList);
          updateTodoLists();
          popupWindow.dismiss();
        }else{
          Toast.makeText(context,"Please enter a name for this list",Toast.LENGTH_SHORT).show();
        }
      }
    });

    Button btnCancel = (Button) newListView.findViewById(R.id.btn_cancel);
    btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        popupWindow.dismiss();
      }
    });
  }

  private BroadcastReceiver receiverRefreshLists = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      updateTodoLists();
    }
  };

  private BroadcastReceiver receiverUpdateTodoList = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      NoteList listToUpdate = intent.getParcelableExtra("ExistingNote");
      openPopup(listToUpdate);
    }
  };

  private void updateTodoLists(){
    List<NoteList> todoLists = new ArrayList<>();
    if(todoDatabaseHelper.getTodoListCount()>0){
      todoLists = todoDatabaseHelper.getAllTodoLists();
    }
    lists.clear();
    lists.addAll(todoLists);
    noteListAdapter.notifyDataSetChanged();
  }
}