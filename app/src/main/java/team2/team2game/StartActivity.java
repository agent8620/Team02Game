package team2.team2game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        this.setContentView(R.layout.activity1);
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
