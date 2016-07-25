package beautifuldonkey.beautifultodo.data;

import java.util.List;

/**
 * list of notes
 * Created by jaw_m on 7/24/2016.
 */
public class NoteList {
  private String name;
  private List<Note> notes;

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
