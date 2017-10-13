package ourproject.nildlumbini;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FullDetailView extends AppCompatActivity {

    TextView fullTitle, fullArticle, fullName, fullOption, fullDate;
    ImageView fullImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_detail_view);

        fullTitle = (TextView)findViewById(R.id.title2);
        fullArticle = (TextView)findViewById(R.id.article2);
        fullName = (TextView)findViewById(R.id.name2);
        fullOption = (TextView)findViewById(R.id.option2);
        fullDate = (TextView)findViewById(R.id.date2);
        fullImage = (ImageView)findViewById(R.id.image2);

        Bundle bundle = getIntent().getExtras();
        String[] object = bundle.getStringArray("message");

        //TODO retrieve1.title,retrieve1.article,retrieve1.name,retrieve1.option,retrieve1.date,retrieve1.imgUrl
        fullTitle.setText(object[0]);
        fullArticle.setText(object[1]);
        fullName.setText(object[2]);
        fullOption.setText(object[3]);
        fullDate.setText(object[4]);

        try {
            Picasso.with(FullDetailView.this).load(object[5]).resize(WindowManager.LayoutParams.FLAG_FULLSCREEN,500).into(fullImage);
        }catch (Exception e){
            e.printStackTrace();
        }

        //fullImage.setImageResource(R.drawable.house);

    }
}
