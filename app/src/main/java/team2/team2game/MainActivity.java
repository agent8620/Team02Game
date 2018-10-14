package team2.team2game;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Locale;

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

    private int Walls[] = new int[]{
            R.drawable.wall1,
            R.drawable.wall2,
            R.drawable.wall3
    };

    private int wall=0;
    final private MainActivity main = this;
    private RelativeLayout layoutTop,layoutBottom;
    public boolean debug = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity2);
        LayoutsetDefault();
    }

    private void LayoutsetDefault(){
        layoutBottom = findViewById(R.id.backgroundBottom);
        layoutTop = findViewById(R.id.backgroundTop);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.00f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);

        animator.addListener(new ValueAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}

            @Override
            public void onAnimationEnd(Animator animator) {}

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {
                if(Walls.length-1 == wall){
                    wall  = -1;
                }
                layoutBottom.setBackground(layoutTop.getBackground());
                layoutTop.setBackground( ContextCompat.getDrawable(main,Walls[++wall]));
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float height = layoutBottom.getHeight();
                final float translationY = height * progress;
                layoutBottom.setTranslationY(translationY);
                layoutTop.setTranslationY(translationY - height);

                if(debug){
                    Button but = findViewById(R.id.debugWall);
                    but.setVisibility(View.VISIBLE);
                    but.setText(String.format(Locale.getDefault(),"%.5f",progress));
                }
            }
        });
        animator.start();
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
