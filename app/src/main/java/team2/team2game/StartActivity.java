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

    public void onJoinClick(View v){
        startActivity(new Intent(StartActivity.this,MainActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }

    public void onInventoryClick(View v){
        startActivity(new Intent(StartActivity.this,InventoryActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }

    public void onExitClick(View v){
        finish();
        System.exit(0);
    }
}
