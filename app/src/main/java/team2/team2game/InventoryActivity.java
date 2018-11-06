package team2.team2game;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Locale;

public class InventoryActivity extends SampleActivity {

    private RelativeLayout windowLayout;
    private ToggleButton catBaki,catBob,catGrey,catRambo,catRandom;
    TextView textRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        windowLayout = findViewById(R.id.layoutSubLayout);
        int cat_player = new PreferencesFunctions(this.getSharedPreferences("Team2GameScore",MODE_PRIVATE)).loadInt("PLAYER_SKIN",0);
        catBaki = findViewById(R.id.buttonBaki);
        catBob = findViewById(R.id.buttonBob);
        catGrey = findViewById(R.id.buttonGrey);
        catRambo = findViewById(R.id.buttonRambo);
        catRandom = findViewById(R.id.buttonRandom);
        textRandom = findViewById(R.id.textViewRandom);
        checkingButtons(cat_player);
    }

    public void checkingButtons(int cat_player) {
        switch (cat_player){
            case 1:
                catBaki.setChecked(false); catBaki.setEnabled(true);
                catBob.setChecked(true); catBob.setEnabled(false);
                catGrey.setChecked(false); catGrey.setEnabled(true);
                catRambo.setChecked(false); catRambo.setEnabled(true);
                catRandom.setChecked(false); catRandom.setEnabled(true);
                break;
            case 4:
                catBaki.setChecked(false); catBaki.setEnabled(true);
                catBob.setChecked(false); catBob.setEnabled(true);
                catGrey.setChecked(true); catGrey.setEnabled(false);
                catRambo.setChecked(false); catRambo.setEnabled(true);
                catRandom.setChecked(false); catRandom.setEnabled(true);
                break;
            case 7:
                catBaki.setChecked(false); catBaki.setEnabled(true);
                catBob.setChecked(false); catBob.setEnabled(true);
                catGrey.setChecked(false); catGrey.setEnabled(true);
                catRambo.setChecked(true); catRambo.setEnabled(false);
                catRandom.setChecked(false); catRandom.setEnabled(true);
                break;
            case -1:
                catBaki.setChecked(false); catBaki.setEnabled(true);
                catBob.setChecked(false); catBob.setEnabled(true);
                catGrey.setChecked(false); catGrey.setEnabled(true);
                catRambo.setChecked(false); catRambo.setEnabled(true);
                catRandom.setChecked(true); catRandom.setEnabled(false);
                break;
            default:
                catBaki.setChecked(true); catBaki.setEnabled(false);
                catBob.setChecked(false); catBob.setEnabled(true);
                catGrey.setChecked(false); catGrey.setEnabled(true);
                catRambo.setChecked(false); catRambo.setEnabled(true);
                catRandom.setChecked(false); catRandom.setEnabled(true);
                break;
        }
    }

    public void onReturnTonMenu(View v){
        finish();
    }

    public void onCatClick(View v){
        int cat_player;
        if(v == catBaki) {
            cat_player = 0;
        }else if(v == catBob) {
            cat_player = 1;
        }else if(v == catGrey) {
            cat_player = 4;
        }else if(v == catRambo) {
            cat_player = 7;
        }else {
            cat_player = -1;
        }
        checkingButtons(cat_player);
        new PreferencesFunctions(this.getSharedPreferences("Team2GameScore",MODE_PRIVATE)).saveInt("PLAYER_SKIN",cat_player);
    }

    public void onButtonClick(View v){
        windowLayout.setVisibility(View.VISIBLE);
        windowLayout.setEnabled(true);
        EnabledButtons(false);
        TextView textView = windowLayout.findViewById(R.id.textViewInventory);
        if(v == findViewById(R.id.buttonStory)) {
            textView.setText(R.string.story);
        }else
            if(v==findViewById(R.id.buttonAchievements)){
                int top = 128;
                TextView textViewTemplate = (TextView) LayoutInflater.from(windowLayout.getContext()).inflate(R.layout.template_assets,windowLayout,false);
                for (DroppableAsset asset : DroppableDefinitions.Assets) {
                    Drawable drawable = ContextCompat.getDrawable(windowLayout.getContext(),asset.getTexture());
                    ScaleDrawable scaledLeft = new ScaleDrawable(drawable, Gravity.CENTER, 24, 24);
                    textViewTemplate.setCompoundDrawablesWithIntrinsicBounds(null,null,scaledLeft,null);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.topMargin = top;
                    params.bottomMargin = 0;
                    textViewTemplate.setLayoutParams(params);
                    textViewTemplate.setId(View.generateViewId());
                    textViewTemplate.setText(String.format(Locale.getDefault(),"%s",textViewTemplate.getText() + asset.getName() + " is "
                            + ((asset.getHealth()>0)?"useful":"dangerous") + "\n"));
                }
                textViewTemplate.setText(String.format(Locale.getDefault(),"%s",textViewTemplate.getText() + "\n\n\n\n Soon! Statistics, Achievements, Achievement Icon, Goals and Objectives"));
                windowLayout.addView(textViewTemplate);
                windowLayout.invalidate();
            }else{
                if(v==findViewById(R.id.buttonCustomize)){
                    catBaki.setVisibility(View.VISIBLE);
                    catBob.setVisibility(View.VISIBLE);
                    catGrey.setVisibility(View.VISIBLE);
                    catRambo.setVisibility(View.VISIBLE);
                    catRandom.setVisibility(View.VISIBLE);
                    textRandom.setVisibility(View.VISIBLE);
                }
            }
    }

    public void onCloseClick(View v){
        windowLayout.setVisibility(View.INVISIBLE);
        catBaki.setVisibility(View.GONE);
        catBob.setVisibility(View.GONE);
        catGrey.setVisibility(View.GONE);
        catRambo.setVisibility(View.GONE);
        catRandom.setVisibility(View.GONE);
        textRandom.setVisibility(View.GONE);
        windowLayout.setEnabled(false);
        EnabledButtons(true);
        TextView textView = windowLayout.findViewById(R.id.textViewInventory);
        textView.setText("");
    }

    public void EnabledButtons(boolean state){
        Button button = findViewById(R.id.buttonDream);
        button.setEnabled(state);
        button.setFocusable(state);
        button = findViewById(R.id.buttonAchievements);
        button.setEnabled(state);
        button.setFocusable(state);
        button = findViewById(R.id.buttonCustomize);
        button.setEnabled(state);
        button.setFocusable(state);
        button.setFocusable(state);
        button = findViewById(R.id.buttonStory);
        button.setEnabled(state);
        button.setFocusable(state);
    }
}
