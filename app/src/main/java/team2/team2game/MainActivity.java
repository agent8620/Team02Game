package team2.team2game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ObjectType types[] = new ObjectType[]{
        new ObjectType("Apple",1, 1, R.drawable.apple, 68, 60),
        new ObjectType("Bonus",1, 1, R.drawable.bonus, 34, 39),
        new ObjectType("Brick",1, 1, R.drawable.brick, 113, 147),
        new ObjectType("Cherry",1, 1, R.drawable.cherry, 63, 58),
        new ObjectType("Dagger",1, 1, R.drawable.dagger, 163, 105),
        new ObjectType("Death potion",1, 1, R.drawable.death_potion, 179, 97),
        new ObjectType("Health potion",1, 1, R.drawable.health_potion, 68, 55),
        new ObjectType("Pie",1, 1, R.drawable.pie, 144, 134),
        new ObjectType("Rainbow sword",1, 1, R.drawable.rainbow, 0, 0),
        new ObjectType("Rock",1, 1, R.drawable.stone, 620, 651),
        new ObjectType("Sword",1, 1, R.drawable.sword, 244, 95),
        new ObjectType("Chest",1, 1, R.drawable.trap, 142, 205),
        new ObjectType("Trophy",1, 1, R.drawable.trophy, 116, 118)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity2);
    }

    public void ClickText(View v1){
        final Button textView = (Button)findViewById(R.id.tx1);
        final View v = findViewById(R.id.q2);
        // this is the view on which you will listen for touch events

        textView.setText("Touch coordinates : " + v.getHeight() + " " + v.getWidth()
        );
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
