package team2.team2game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class InventoryActivity extends SampleActivity {

    private RelativeLayout relativeLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

    }

    public void onReturnTonMenu(View v){
        finish();
    }

    public void onDiaryReadClick(View v){
        relativeLayout = findViewById(R.id.layoutSubLayout);
        relativeLayout.setVisibility(View.VISIBLE);
        relativeLayout.setEnabled(true);
        EnabledButtons(false);
    }

    public void onCloseClick(View v){
        relativeLayout = findViewById(R.id.layoutSubLayout);
        relativeLayout.setVisibility(View.GONE);
        relativeLayout.setEnabled(false);
        EnabledButtons(true);
    }

    public void EnabledButtons(boolean state){
        Button button = findViewById(R.id.buttonDream);
        button.setEnabled(state);
        button = findViewById(R.id.buttonAchievements);
        button.setEnabled(state);
        button = findViewById(R.id.buttonCustomize);
        button.setEnabled(state);
        button = findViewById(R.id.buttonReturn);
        button.setEnabled(state);
        button = findViewById(R.id.buttonStory);
        button.setEnabled(state);
        button = relativeLayout.findViewById(R.id.CloseButton);
        button.setEnabled(!state);
    }
}
