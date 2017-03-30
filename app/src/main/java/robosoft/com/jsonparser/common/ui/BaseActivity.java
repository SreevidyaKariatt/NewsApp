package robosoft.com.jsonparser.common.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import robosoft.com.newsapp.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_layout);

    }

}
