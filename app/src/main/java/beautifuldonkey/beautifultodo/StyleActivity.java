package beautifuldonkey.beautifultodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StyleActivity extends AppCompatActivity {

  int textSize;

  TextView tvTextSize;
  TextView tvTextSizeLabel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_style);

    TypedValue typedValue = new TypedValue();
    this.getResources().getValue(R.integer.text_size,typedValue,true);
    textSize = typedValue.data;

    tvTextSizeLabel = (TextView) findViewById(R.id.text_size_label);
    tvTextSize = (TextView) findViewById(R.id.text_size);
    if(tvTextSize != null && tvTextSizeLabel != null){
      tvTextSize.setText(String.valueOf(textSize));
      tvTextSize.setTextSize(textSize);
      tvTextSizeLabel.setTextSize(textSize);
    }

    Button btnAddTextSize = (Button) findViewById(R.id.btn_add);
    if(btnAddTextSize!=null){
      btnAddTextSize.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          textSize += 1;
          updateStyle();
        }
      });
    }

    Button btnSubTextSize = (Button) findViewById(R.id.btn_subtract);
    if(btnSubTextSize != null){
      btnSubTextSize.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          textSize -= 1;
          updateStyle();
        }
      });
    }
  }

  public void updateStyle(){
    tvTextSize.setText(String.valueOf(textSize));
    tvTextSize.setTextSize(textSize);
    tvTextSizeLabel.setTextSize(textSize);
  }
}
