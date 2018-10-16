package team2.team2game;

import android.content.Intent;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.Display;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends SampleActivity {

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
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.mainLayout);
        imgView = findViewById(R.id.player);

        healthTextView = findViewById(R.id.healthTextView);
        timerTextView = findViewById(R.id.timerTextView);

        playerObject = new PlayerObject(100, 20, imgView);
        droppableSpawner = new DroppableSpawner(this);

        displaySize = getDisplaySizeInternal();

        startAnimations();

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
        finish();
    }

    private void startAnimations() {
        new Animations(new int[]{
                R.drawable.wall1,
                R.drawable.wall2,
                R.drawable.wall3
        },main).WallFall();
    }

}
