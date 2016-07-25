package beautifuldonkey.beautifultodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import beautifuldonkey.beautifultodo.data.NoteList;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    List<NoteList> lists = new ArrayList<>();

    ListView noteLists = (ListView) findViewById(R.id.note_list);

    Button btnAddList = (Button) findViewById(R.id.btn_add_list);

  }
}
