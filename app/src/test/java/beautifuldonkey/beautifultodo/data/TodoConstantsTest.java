package beautifuldonkey.beautifultodo.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for application constants
 * Created by jaw_m on 10/2/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class TodoConstantsTest {

  public TodoConstantsTest(){
    super();
  }

  @Test
  public void testAppConstants(){
    TodoConstants todoConstants = new TodoConstants();
    assertNotNull(todoConstants);
    assertThat(TodoConstants.INTENT_OPEN_LIST,is(0));
    assertThat(TodoConstants.INTENT_EXTRA_LIST,is("NoteList"));
    assertThat(TodoConstants.INTENT_EXTRA_REFRESH_LIST,is("RefreshTodoLists"));
    assertThat(TodoConstants.INTENT_EXTRA_UPDATE_LIST,is("UpdateTodoLists"));
  }
}