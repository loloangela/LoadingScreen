package example.loloangela.loadingscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView) findViewById(R.id.loadingTv);
        TextView textView1 = (TextView) findViewById(R.id.unamTv);
        Button button = (Button) findViewById(R.id.closeBtn);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Get username from MainActivity
        Intent intent = getIntent();
        String username = intent.getStringExtra("X_MESSAGE");
//        textView1.setText(username);

        // Call Resolve Task - pass in elements so it can be updated from task
        new ResolveTask(this, textView, button, progressBar).execute(username);
    }

    public void exitPg(View view){
        finish();
    }

}
