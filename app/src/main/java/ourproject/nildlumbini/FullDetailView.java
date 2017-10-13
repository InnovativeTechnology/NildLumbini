package ourproject.nildlumbini;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class FullDetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_detail_view);

        ImageView imageView = (ImageView)findViewById(R.id.image2);
        imageView.setImageResource(R.drawable.house);

    }
}
