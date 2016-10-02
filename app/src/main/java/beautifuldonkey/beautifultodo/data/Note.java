package beautifuldonkey.beautifultodo.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * defines the note class that can be added to a list
 * Created by jaw_m on 7/24/2016.
 */
public class Note implements Parcelable{
  private String name;
  private String comments;

  public Note(){}

  public Note(Parcel source) {
    name = source.readString();
    comments = source.readString();
  }

  public static Creator<Note> CREATOR = new Creator<Note>() {
    @Override
    public Note createFromParcel(Parcel source) {
      return new Note(source);
    }

    @Override
    public Note[] newArray(int size) {
      return new Note[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(comments);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }
}