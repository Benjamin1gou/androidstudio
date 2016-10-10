package local.hal.st32.android.mysqltester;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.text.SpannableStringBuilder;

public class MainActivity extends AppCompatActivity {

    MyAsyncTask task;
    EditText editText ;
    String text="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText =(EditText)findViewById(R.id.editText);

        SpannableStringBuilder sb = (SpannableStringBuilder)editText.getText();

        text=sb.toString();

    }

    public void submit(View view){
        SpannableStringBuilder sb = (SpannableStringBuilder)editText.getText();

        text=sb.toString();

        task= new MyAsyncTask(this);
        task.execute(text);
    }

}
