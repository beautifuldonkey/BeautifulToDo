package beautifuldonkey.beautifultodo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import beautifuldonkey.beautifultodo.adapters.AdapterManager;
import beautifuldonkey.beautifultodo.data.Note;
import beautifuldonkey.beautifultodo.data.NoteList;
import beautifuldonkey.beautifultodo.data.TodoConstants;

public class MainActivity extends AppCompatActivity {

  Context context;
  List<NoteList> lists;

  Button btnAddList;
  NoteList newList;

  View newListView;
  PopupWindow popupWindow;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    context = getApplicationContext();

    lists = new ArrayList<>();

    //START TEST DATA
    Note testNote = new Note();
    testNote.setName("testName");

    List<Note> notes = new ArrayList<>();
    notes.add(testNote);

    NoteList testNoteList = new NoteList();
    NoteList testNoteList2 = new NoteList();

    testNoteList.setName("test note list name");
    testNoteList.setNotes(notes);

    testNoteList2.setName("name2");
    testNoteList2.setNotes(notes);

    lists.add(testNoteList);
    lists.add(testNoteList2);
    //END TEST DATA

    ListView noteLists = (ListView) findViewById(R.id.note_list);
    ArrayAdapter<NoteList> noteListAdapter = AdapterManager.getNoteListAdapter(context,lists);
    if(noteLists != null){
      noteLists.setAdapter(noteListAdapter);
      noteLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          Intent intent = new Intent(context,TodoActivity.class);
          startActivityForResult(intent,TodoConstants.INTENT_OPEN_LIST);
        }
      });
    }

    btnAddList = (Button) findViewById(R.id.btn_add_list);
    if(btnAddList != null){
      btnAddList.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

          newList = new NoteList();

          newListView = inflater.inflate(R.layout.popup_new_list,null);
          popupWindow = new PopupWindow(newListView,300,400,true);
          popupWindow.setContentView(newListView);
          popupWindow.showAtLocation(btnAddList, Gravity.CENTER,0,0);

          Button btnDoneListName = (Button) newListView.findViewById(R.id.btn_new_list_name);
          btnDoneListName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              TextView textListName = (TextView) newListView.findViewById(R.id.new_list_name);
              if(textListName.getText().length()>0){
                newList.setName(textListName.getText().toString());
                popupWindow.dismiss();
              }else{
                Toast.makeText(context,"Please enter a name for this list",Toast.LENGTH_SHORT).show();
              }
            }
          });
          lists.add(newList);
        }
      });
    }

  }
}
