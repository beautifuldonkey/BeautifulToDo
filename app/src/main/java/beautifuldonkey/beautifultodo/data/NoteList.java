package beautifuldonkey.beautifultodo.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * list of notes
 * Created by jaw_m on 7/24/2016.
 */
public class NoteList implements Parcelable {
  private String name;
  private List<Note> notes;

  public NoteList(){}

  public NoteList(Parcel parcel){
    name = parcel.readString();
    notes = new ArrayList<>();
    parcel.readList(notes,Note.class.getClassLoader());
  }

  public static Creator<NoteList> CREATOR = new Creator<NoteList>() {
    @Override
    public NoteList createFromParcel(Parcel source) {
      return new NoteList(source);
    }

    @Override
    public NoteList[] newArray(int size) {
      return new NoteList[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeList(notes);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Note> getNotes() {
    return notes;
  }

  public void setNotes(List<Note> notes) {
    this.notes = notes;
  }
}
