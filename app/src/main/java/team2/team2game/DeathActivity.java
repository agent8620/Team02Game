package team2.team2game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class DeathActivity extends SampleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death);
        printAndCalculateResult();
    }

    void printAndCalculateResult(){
        PreferencesFunctions preferencesFunctions = new PreferencesFunctions(this.getSharedPreferences("Team2GameScore",MODE_PRIVATE));
        int objectCount = preferencesFunctions.loadInt("DANGEROUS_OBJECTS_PASSED");
        int secondsCount = preferencesFunctions.loadInt("SURVIVED_SECONDS");
        int score_previous = preferencesFunctions.loadInt("SCORE");
        preferencesFunctions.updateScore(score_previous);
        TextView NameTextView = findViewById(R.id.textViewKiller);
        TextView scoreTextView = findViewById(R.id.textViewScore);
        NameTextView.setText(String.format(Locale.getDefault(),"by %s",ScoreDefinition.getLastAssetName()));
        scoreTextView.setText(String.format(Locale.getDefault(),"Result\nSurvived seconds:  %d|%d\nFallen object: %d|%d\nTotal Score: %d|%d",ScoreDefinition.getSurvivedSeconds(),secondsCount,ScoreDefinition.getDangerousObjectsPassed(),objectCount,ScoreDefinition.getScore(),score_previous));
    }

    public void onPlayAgainButtonClick (View v){
        finish();
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }

    public void onBackToMenuButtonClick (View v){
        finish();
        startActivity(new Intent(this, StartActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }
}


