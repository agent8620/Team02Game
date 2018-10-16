package team2.team2game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class StartActivity extends SampleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_start);
    }

    public void Join(View v){
        startActivity(new Intent(StartActivity.this,MainActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }
    public void Story(View v){
        startActivity(new Intent(StartActivity.this,StoryActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }
    public void Settings(View v){
        startActivity(new Intent(StartActivity.this,SettingsActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }

    public void Exit(View v){
        finish();
        System.exit(0);
    }
}
