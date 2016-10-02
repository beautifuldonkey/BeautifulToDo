package beautifuldonkey.beautifultodo.data;

import android.os.Parcel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for the Note class
 * Created by jaw_m on 10/1/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class NoteTest {

  @Mock
  Parcel parcel;

  public NoteTest(){
    super();
  }

  Note testNote = new Note();

  String testNoteName = "testName";
  String testNoteComments = "testComments";

  @Test
  public void testBuildNote(){
    testNote.setName(testNoteName);
    testNote.setComments(testNoteComments);
    assertThat(testNote.getName(),is(testNoteName));
    assertThat(testNote.getComments(),is(testNoteComments));
  }

  @Test
  public void testNoteParcelable(){
    testNote.writeToParcel(parcel,0);
    parcel.setDataPosition(0);
    Note newTestNote = Note.CREATOR.createFromParcel(parcel);
    assertNotNull(newTestNote);
  }

  @Test
  public void testNoteParcelableArray(){
    Note[] testNoteArray = Note.CREATOR.newArray(1);
    assertNotNull(testNoteArray);
  }

  @Test
  public void testNoteContents(){
    assertThat(testNote.describeContents(),is(0));
  }
}