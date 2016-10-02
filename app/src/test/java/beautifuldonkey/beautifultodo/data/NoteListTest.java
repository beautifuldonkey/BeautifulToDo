package beautifuldonkey.beautifultodo.data;

import android.os.Parcel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
/**
 * Unit test for the NoteList class
 * Created by jaw_m on 10/2/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class NoteListTest {

  @Mock
  Parcel parcel;

  public NoteListTest(){
    super();
  }

  NoteList testNoteList = new NoteList();

  String testNoteListName = "testName";
  List<Note> testNoteListNotes = new ArrayList<>();

  @Test
  public void testNoteListCreate(){
    testNoteList.setName(testNoteListName);
    testNoteList.setNotes(testNoteListNotes);
    assertThat(testNoteList.getName(),is(testNoteListName));
    assertThat(testNoteList.getNotes(),is(testNoteListNotes));
  }

  @Test
  public void testNoteListParcelable(){
    testNoteList.writeToParcel(parcel,0);
    parcel.setDataPosition(0);
    NoteList newTestNoteList = NoteList.CREATOR.createFromParcel(parcel);
    assertNotNull(newTestNoteList);
  }

  @Test
  public void testNoteListParcelableArray(){
    NoteList[] testNoteListArray = NoteList.CREATOR.newArray(1);
    assertNotNull(testNoteListArray);
  }

  @Test
  public void testNoteListContents(){
    assertThat(testNoteList.describeContents(),is(0));
  }
}