package beautifuldonkey.beautifultodo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import beautifuldonkey.beautifultodo.R;
import beautifuldonkey.beautifultodo.data.Note;
import beautifuldonkey.beautifultodo.data.NoteList;

/**
 * defines array adapter implementations used in project
 * Created by jaw_m on 7/25/2016.
 */
public class AdapterManager {

  public static ArrayAdapter<Note> getNoteAdapter(final Context context, final List<Note> notes){
    ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(context,R.layout.item_note,notes){
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        Note currentNote = notes.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_note,null);

        TextView textNoteName = (TextView) view.findViewById(R.id.text_note_name);
        textNoteName.setText(currentNote.getName());
        textNoteName.setTextColor(Color.BLACK);

        TextView textNoteComments = (TextView) view.findViewById(R.id.text_note_comments);
        textNoteComments.setText(currentNote.getComments());
        textNoteComments.setTextColor(Color.BLACK);

        return view;
      }
    };
    return adapter;
  }

  public static ArrayAdapter<NoteList> getNoteListAdapter(final Context context, final List<NoteList> noteLists){
    ArrayAdapter<NoteList> adapter = new ArrayAdapter<NoteList>(context, R.layout.item_note_list, noteLists){
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        NoteList currentList = noteLists.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_note_list,null);

        TextView textListName = (TextView) view.findViewById(R.id.list_name);
        textListName.setText(currentList.getName());
        textListName.setTextColor(Color.BLACK);

        return view;
      }
    };
    return adapter;
  }

}
