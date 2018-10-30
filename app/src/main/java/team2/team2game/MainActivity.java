package team2.team2game;

import android.content.Intent;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.view.Display;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends SampleActivity {

    public static int debug = 0;
    private ConstraintLayout constraintLayout = null;
    ImageView imgView = null;

    private boolean isPressedLeftSide = false;
    private boolean isPressedRightSide = false;

    public static final long UPDATE_INTERVAL = 50; // = 20 FPS
    Timer updateTimer = null;
    Timer secondsTimer = null;

    private TextView healthTextView = null;
    private TextView timerTextView = null;

    private PlayerObject playerObject = null;
    private DroppableSpawner droppableSpawner = null;

    private Point displaySize = null;
    int displayDPI = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = this.findViewById(R.id.mainLayout);
        imgView = this.findViewById(R.id.player);

        healthTextView = this.findViewById(R.id.healthTextView);
        timerTextView = this.findViewById(R.id.timerTextView);

        playerObject = new PlayerObject(100, 20, imgView);
        droppableSpawner = new DroppableSpawner(this);

        this.healthTextView.setText(String.format(Locale.getDefault(),"HP: %d", playerObject.Health));
        debug = new PreferencesFunctions(this.getPreferences(MODE_PRIVATE)).loadInt("DEBUG");

        this.updateDisplaySizeInternal();
        ScoreDefinition.zeroing();
        Animations.Cancel = false;
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
        ScoreDefinition.incrementSurvivedSeconds();
        timerTextView.setText(String.format(Locale.getDefault(), "Alive: %d",ScoreDefinition.getSurvivedSeconds()));
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

    private void updateDisplaySizeInternal(){
        Display display = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        this.displaySize = displaySize;
        this.displayDPI = getResources().getDisplayMetrics().densityDpi;
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

    public void OnDroppableObjectPassed (DroppableAsset asset){
        if (asset.getHealth() < 0) {
            ScoreDefinition.incrementDangerousObjectsPassed();
        }
    }
    public void OnDroppableObjectCatch (DroppableAsset asset) {
        this.playerObject.Health += asset.getHealth();
        if (this.playerObject.Health >= 0.0){
            this.healthTextView.setText(String.format(Locale.getDefault(), "HP: %d", this.playerObject.Health));
        }
        else {
            ScoreDefinition.setLastAssetName(asset.getName());
            this.healthTextView.setText(getString(R.string.Dead));
            SaveScore();
            finish();
            startActivity(new Intent(this, DeathActivity.class));
            overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        }
    }

    public void openMenu(View v){
        SaveScore();
        finish();
    }

    void SaveScore(){
        PreferencesFunctions preferencesFunctions = new PreferencesFunctions(this.getSharedPreferences("Team2GameScore",MODE_PRIVATE));
        ScoreDefinition.calculateScore();
        int score_previous = preferencesFunctions.loadInt("SCORE");
        preferencesFunctions.updateScore(score_previous);
        Animations.Cancel = true;
        updateTimer.cancel();
        secondsTimer.cancel();
    }

    private void startAnimations() {
        new Animations(getIntArrayOf("wall",10),this).WallFall();
        int[] arrayAnimations = getIntArrayOf("player_grey_",4);
        this.imgView.setImageResource(arrayAnimations[0]);
        new Animations(arrayAnimations,this).AnimatePlayer();
    }

    private int[] getIntArrayOf(String part, int lenght){
        int[] array = new int[lenght];
        for (int i = 0; i < lenght; i++)
        {
            try
            {
                Class res = R.drawable.class;
                Field field = res.getField(part+i);
                array[i] = field.getInt(null);
            }
            catch (Exception e)
            {
                if(lenght==10){
                    return new int[]{
                            R.drawable.wall0,
                            R.drawable.wall1,
                            R.drawable.wall2,
                            R.drawable.wall3,
                            R.drawable.wall4,
                            R.drawable.wall5,
                            R.drawable.wall6,
                            R.drawable.wall7,
                            R.drawable.wall8,
                            R.drawable.wall9

                    };
                }else{
                    return new int[]{
                            R.drawable.player_grey_0,
                            R.drawable.player_grey_1,
                            R.drawable.player_grey_2,
                            R.drawable.player_grey_3
                    };
                }
            }
        }
        return array;
    }

}
