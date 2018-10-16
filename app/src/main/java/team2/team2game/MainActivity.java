package team2.team2game;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.Display;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int Walls[] = new int[]{
            R.drawable.wall1,
            R.drawable.wall2,
            R.drawable.wall3
    };

    private int wall=0;
    final private MainActivity main = this;
    private RelativeLayout layoutTop,layoutBottom;
    public boolean debug = true;
    private ConstraintLayout constraintLayout = null;
    private ImageView imgView = null;

    private boolean isPressedLeftSide = false;
    private boolean isPressedRightSide = false;

    public static final long UPDATE_INTERVAL = 50; // = 20 FPS
    private Timer updateTimer = null;
    private Timer secondsTimer = null;

    private TextView healthTextView = null;
    private TextView timerTextView = null;

    private int SurvivedSeconds = 0;

    private PlayerObject playerObject = null;
    private DroppableSpawner droppableSpawner = null;

    private Point displaySize = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.mainLayout);
        imgView = findViewById(R.id.player);

        healthTextView = findViewById(R.id.healthTextView);
        timerTextView = findViewById(R.id.timerTextView);

        playerObject = new PlayerObject(100, 20, imgView);
        droppableSpawner = new DroppableSpawner(this);

        displaySize = getDisplaySizeInternal();

        LayoutsetDefault();
        updateTimer = new Timer();
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() { @Override public void run() { Update();
                    droppableSpawner.Update();} });
            }
        }, UPDATE_INTERVAL, UPDATE_INTERVAL);
        secondsTimer = new Timer();
        secondsTimer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() { @Override public void run() { SecondsUpdate(); }});
            }
        }, 0, 1000);

        droppableSpawner.Enable();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventaction = event.getAction();
        Point displaySize = getDisplaySize();

        if (eventaction == MotionEvent.ACTION_DOWN){
            if (X < displaySize.x / 2) isPressedLeftSide = true;
            else isPressedRightSide = true;
        }
        else if (eventaction == MotionEvent.ACTION_UP) {
            isPressedLeftSide = isPressedRightSide = false;
        }
        return true;
    }

    private void Update (){
        handleCharacterMovement();
    }

    private void SecondsUpdate (){
        SurvivedSeconds++;
        timerTextView.setText(String.format(Locale.getDefault(), "Alive: %d",SurvivedSeconds));
    }

    private void handleCharacterMovement (){
        if (!isPressedLeftSide && !isPressedRightSide) return;

        double speed = playerObject.Speed;
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)imgView.getLayoutParams();
        int marginStart = params.getMarginStart();
        if (isPressedLeftSide) marginStart -= speed;
        else if (isPressedRightSide) marginStart += speed;

        int displayWidth = getDisplaySize().x;
        int maxX = displayWidth - params.width;
        marginStart = marginStart < 0 ? 0 : marginStart > maxX ? maxX : marginStart;
        params.setMarginStart(marginStart);
        imgView.setLayoutParams(params);
    }

    private Point getDisplaySizeInternal(){
        Display display = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        return displaySize;
    }

    public Point getDisplaySize (){
        return this.displaySize;
    }

    public ConstraintLayout getConstraintLayout(){
        return this.constraintLayout;
    }



    public boolean isDroppableIntersectingPlayer (DroppableObject obj){
        Rectangle playerRect = playerObject.getRectangle();
        Rectangle dropppableRect = obj.getRectangle();
        return playerRect.IsIntersecting(dropppableRect);
    }

    public void AddHealthToPlayer (int health){
        this.playerObject.Health += health;
        this.healthTextView.setText(String.format(Locale.getDefault(), "HP: %d", this.playerObject.Health));
    }

    public void openMenu(View v){
        startActivity(new Intent(this,StartActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }

    private void LayoutsetDefault(){
        layoutBottom = findViewById(R.id.backgroundBottom);
        layoutTop = findViewById(R.id.backgroundTop);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.00f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(100000L);

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
        new Handler().postDelayed(
            new Runnable(){
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            animator.start();
                        }
                    });
                }},1000);
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
