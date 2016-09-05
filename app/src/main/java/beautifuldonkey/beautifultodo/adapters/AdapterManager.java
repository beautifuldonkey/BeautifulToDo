package beautifuldonkey.beautifultodo.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import beautifuldonkey.beautifultodo.R;
import beautifuldonkey.beautifultodo.data.Note;
import beautifuldonkey.beautifultodo.data.NoteList;
import beautifuldonkey.beautifultodo.data.TodoConstants;
import beautifuldonkey.beautifultodo.data.TodoDatabaseHelper;

/**
 * defines array adapter implementations used in project
 * Created by jaw_m on 7/25/2016.
 */
public class AdapterManager {

  public static ArrayAdapter<Note> getNoteAdapter(final Context context, final List<Note> notes){
    ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(context,R.layout.item_note,notes){
      @Override
      public View getView(final int position, View convertView, ViewGroup parent) {
        Note currentNote = notes.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_note,null);

        TextView textNoteName = (TextView) view.findViewById(R.id.text_name);
        textNoteName.setText(currentNote.getName());
        textNoteName.setTextColor(Color.BLACK);

        TextView textNoteComments = (TextView) view.findViewById(R.id.text_comments);
        textNoteComments.setText(currentNote.getComments());
        textNoteComments.setTextColor(Color.BLACK);

        Button btnDeleteNote = (Button) view.findViewById(R.id.btn_remove);
        btnDeleteNote.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            notes.remove(position);
            Intent intent = new Intent(TodoConstants.INTENT_EXTRA_REFRESH_LIST);
            context.sendBroadcast(intent);
          }
        });

        ImageButton btnUpdateNote = (ImageButton) view.findViewById(R.id.btn_edit);
        btnUpdateNote.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(TodoConstants.INTENT_EXTRA_UPDATE_LIST);
            intent.putExtra("ExistingNote", notes.get(position));
            intent.putExtra("ExistingNotePosition",String.valueOf(position));
            context.sendBroadcast(intent);
          }
        });

        return view;
      }
    };
    return adapter;
  }

  public static ArrayAdapter<NoteList> getNoteListAdapter(final Context context, final List<NoteList> noteLists){
    final TodoDatabaseHelper todoDatabaseHelper = new TodoDatabaseHelper(context);
    ArrayAdapter<NoteList> adapter = new ArrayAdapter<NoteList>(context, R.layout.item_note_list, noteLists){
      @Override
      public View getView(final int position, View convertView, ViewGroup parent) {
        NoteList currentList = noteLists.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_note_list,null);

        TextView textListName = (TextView) view.findViewById(R.id.text_name);
        textListName.setText(currentList.getName());
        textListName.setTextColor(Color.BLACK);

        Button btnDeleteList = (Button) view.findViewById(R.id.btn_remove);
        btnDeleteList.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            todoDatabaseHelper.deleteTodoList(noteLists.get(position).getName());
            Intent intent = new Intent(TodoConstants.INTENT_EXTRA_REFRESH_LIST);
            intent.putExtra("refreshLists",true);
            context.sendBroadcast(intent);
          }
        });

        ImageButton btnUpdateList = (ImageButton) view.findViewById(R.id.btn_edit);
        btnUpdateList.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(TodoConstants.INTENT_EXTRA_UPDATE_LIST);
            intent.putExtra("ExistingNote", noteLists.get(position));
            context.sendBroadcast(intent);
          }
        });

        return view;
      }
    };
    return adapter;
  }

}
