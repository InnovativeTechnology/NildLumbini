package ourproject.nildlumbini;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class DiaLog_Add extends AppCompatActivity {
    EditText title,article;
    Button add;
    Spinner option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        option= (Spinner) findViewById(R.id.option);
        String[] opt = this.getResources().getStringArray(R.array.option);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (DiaLog_Add.this, android.R.layout.simple_dropdown_item_1line,opt);
        option.setAdapter(adapter);


try {
    title = (EditText) findViewById(R.id.title1);
}catch (Exception e)
{
    e.printStackTrace();
}
        article= (EditText) findViewById(R.id.article);
        add= (Button) findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Intent intent = new Intent()

              //  DiaLog_Add.super.onBackPressed();
            }
        });
    }
}
