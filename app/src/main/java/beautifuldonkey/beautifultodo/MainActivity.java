package beautifuldonkey.beautifultodo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import beautifuldonkey.beautifultodo.data.NoteList;

public class MainActivity extends AppCompatActivity {

  Context context;
  List<NoteList> lists;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    context = getApplicationContext();

    lists = new ArrayList<>();

    ListView noteLists = (ListView) findViewById(R.id.note_list);

    final Button btnAddList = (Button) findViewById(R.id.btn_add_list);
    if(btnAddList != null){
      btnAddList.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

          final NoteList newList = new NoteList();


          final View newListView = inflater.inflate(R.layout.popup_new_list,null);
          final PopupWindow popupWindow = new PopupWindow(newListView,300,400,true);
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
