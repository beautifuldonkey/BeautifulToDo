package beautifuldonkey.beautifultodo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * extends sql lite database helper for application
 * Created by jaw_m on 7/28/2016.
 */
public class TodoDatabaseHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "ActivityTracker";
  private static final int DATABASE_VERSION = 1;

  private static final String TABLE_TODO = "TodoLists";

  private static final String COLUMN_LIST_NAME = "TodoListName";
  private static final String COLUMN_LIST_ITEMS = "TodoItems";

  private static final String TYPE_TEXT = " TEXT";

  public TodoDatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String CREATE_TODO_TABLE = "CREATE TABLE "+TABLE_TODO+"("+COLUMN_LIST_NAME+
        TYPE_TEXT+" PRIMARY KEY,"+COLUMN_LIST_ITEMS+TYPE_TEXT+")";
    db.execSQL(CREATE_TODO_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS "+TABLE_TODO);
    onCreate(db);
  }

  public void addTodoList(NoteList todoList){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(COLUMN_LIST_NAME,todoList.getName());
    db.insert(TABLE_TODO,null,contentValues);
    db.close();
  }

  public void deleteTodoList(String name){}

  public List<NoteList> getAllTodoLists(){
    List<NoteList> todoLists = new ArrayList<>();

    String query = "SELECT * FROM "+TABLE_TODO;
    SQLiteDatabase adventureDb = this.getWritableDatabase();
    Cursor cursor = adventureDb.rawQuery(query, null);

    if(cursor.moveToFirst()){
      do{
        NoteList todoList = new NoteList();
        todoList.setName(cursor.getString(0));

        List<Note> notes = new ArrayList<>();
        if(cursor.getString(1)!=null){

          String[] notesString = cursor.getString(1).split("\\|");
          for(int i=0; i < notesString.length; i++){
            Note note = new Note();
            String[] noteDetails = notesString[i].split("~");
            String noteName = "";
            String noteComments = "";
            if(noteDetails.length>0){
              noteName = noteDetails[0];
              if(noteDetails.length>1){
                noteComments = (!"".equals(noteDetails[1]) ? noteDetails[1]: "");
              }
            }
            note.setName(noteName);
            note.setComments(noteComments);
            notes.add(note);
          }
        }

        todoList.setNotes(notes);
        todoLists.add(todoList);
      }while (cursor.moveToNext());
    }
    cursor.close();

    return todoLists;
  }

  public int getTodoListCount() {
    String query = "SELECT * FROM "+TABLE_TODO;
    SQLiteDatabase adventureDb = this.getReadableDatabase();
    Cursor cursor = adventureDb.rawQuery(query, null);
    int count = cursor.getCount();
    cursor.close();
    return count;
  }

  public int updateTodoList(NoteList todoList){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();

    List<Note> notes = todoList.getNotes();
    String stringNotes = "";

    for(int i = 0; i < notes.size(); i++){
      stringNotes += notes.get(i).getName()+"~"+(notes.get(i).getComments()!=null ? notes.get(i).getComments() : "" )+"|";
    }

    contentValues.put(COLUMN_LIST_ITEMS, stringNotes);

    return db.update(TABLE_TODO, contentValues, COLUMN_LIST_NAME+"= ?",
        new String[]{todoList.getName()});
  }
}