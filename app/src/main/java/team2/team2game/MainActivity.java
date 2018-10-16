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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ObjectType types[] = new ObjectType[]{
        new ObjectType("Apple",10, 1, R.drawable.apple, 68, 60),
        new ObjectType("Bonus",10, 1, R.drawable.bonus, 34, 39),
        new ObjectType("Brick",10, 1, R.drawable.brick, 113, 147),
        new ObjectType("Cherry",10, 1, R.drawable.cherry, 63, 58),
        new ObjectType("Dagger",10, 1, R.drawable.dagger, 163, 105),
        new ObjectType("Death potion",10, 1, R.drawable.death_potion, 179, 97),
        new ObjectType("Health potion",10, 1, R.drawable.health_potion, 68, 55),
        new ObjectType("Pie",1, 10, R.drawable.pie, 144, 134),
        new ObjectType("Rainbow sword",10, 1, R.drawable.rainbow, 0, 0),
        new ObjectType("Rock",1, 10, R.drawable.stone, 620, 651),
        new ObjectType("Sword",1, 10, R.drawable.sword, 244, 95),
        new ObjectType("Chest",1, 10, R.drawable.trap, 142, 205),
        new ObjectType("Trophy",1, 10, R.drawable.trophy, 116, 118)};

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
    private Timer timer = null;

    private PlayerObject playerObject = null;
    private ArrayList<DroppableObject> droppableObjList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity2);
        constraintLayout = findViewById(R.id.mainLayout);
        imgView = findViewById(R.id.player);
        playerObject = new PlayerObject(100, 20, imgView);

        if(!debug){
            LayoutsetDefault();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() { @Override public void run() { Update(); } });
                    runOnUiThread(new Runnable() { @Override public void run() { }});
                }
        }, UPDATE_INTERVAL, UPDATE_INTERVAL);

        dropNewDroppableObject();
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
        handleDroppableMovements();
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

    private Point getDisplaySize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        return displaySize;
    }

    private void dropNewDroppableObject(){
        DroppableObject obj = _spawnObject();
        droppableObjList.add(obj);
    }

    private DroppableObject _spawnObject (){
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.apple);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)findViewById(R.id.item).getLayoutParams();
        params.setMarginStart(new Random().nextInt(getDisplaySize().x - params.width));
        params.topMargin = -100;
        imageView.setLayoutParams(params);
        constraintLayout.addView(imageView);

        return new DroppableObject("red apple",  types[0], imageView);
    }

    private void handleDroppableMovements (){
        for (DroppableObject obj: droppableObjList){
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)obj.ImgView.getLayoutParams();
            params.topMargin += obj.getSpeed();
            obj.ImgView.setLayoutParams(params);

            boolean intersected = isDroppableIntersectingPlayer(obj);
            Point displaySize = getDisplaySize();

            if (params.topMargin > displaySize.y){
                Toast.makeText(this, "Object fallen away", Toast.LENGTH_LONG).show();
                destroyDroppableObject(obj);
            }
            else if (intersected){
                playerObject.Health += obj.getHealth();
                Toast.makeText(this, "Object collected. New health: " + playerObject.Health, Toast.LENGTH_LONG).show();
                destroyDroppableObject(obj);
            }
        }
    }

    private void destroyDroppableObject (DroppableObject obj){
        droppableObjList.remove(obj);
        constraintLayout.removeView(obj.ImgView);

        dropNewDroppableObject();
    }

    private boolean isDroppableIntersectingPlayer (DroppableObject obj){
        Rectangle playerRect = playerObject.getRectangle();
        Rectangle dropppableRect = obj.getRectangle();
        //https://gamedev.stackexchange.com/questions/80834/making-a-collision-detection-for-imageviews
        return playerRect.IsIntersecting(dropppableRect);
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
