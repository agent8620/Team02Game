package team2.team2game;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import static android.content.Context.MODE_PRIVATE;

class PreferencesFunctions{
    private SharedPreferences sharedPreferences;
    private Editor editor;

    PreferencesFunctions(SharedPreferences sharedPreferences) { //getActivity().getPreferences(Context.MODE_PRIVATE)
        this.sharedPreferences = sharedPreferences;
        this.editor = this.sharedPreferences.edit();
    }

    void saveString(String key, String value){
        this.editor.putString(key,value);
        this.editor.commit();
    }

    String loadString(String key) {
        return sharedPreferences.getString(key,"");
    }

    void saveInt(String key, int value){
        editor.putInt(key,value);
        this.editor.commit();
    }

    int loadInt(String key) {
        return sharedPreferences.getInt(key,0);
    }

    void updateScore(int score_previous){
        if(ScoreDefinition.getScore()>score_previous){
            this.saveInt("DANGEROUS_OBJECTS_PASSED",ScoreDefinition.getDangerousObjectsPassed());
            this.saveInt("SURVIVED_SECONDS",ScoreDefinition.getSurvivedSeconds());
            this.saveInt("SCORE",ScoreDefinition.getScore());
        }
    }

}
