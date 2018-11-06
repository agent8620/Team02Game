package team2.team2game;

class ScoreDefinition {
    private static int DangerousObjectsPassed = 0;
    private static int SurvivedSeconds = 0;
    private static int Score = 0;
    private static String LastAssetName = "air";

    ScoreDefinition(int dangerousObjectsPassed, int secondsSurvived) {
        DangerousObjectsPassed = dangerousObjectsPassed;
        SurvivedSeconds = secondsSurvived;
    }

    ScoreDefinition() {

    }

    static void setLastAssetName(String name) {
        LastAssetName = name;
    }

    static String getLastAssetName() {
        return LastAssetName;
    }

    static void setDangerousObjectsPassed(int dangerousObjectsPassed) {
        DangerousObjectsPassed = dangerousObjectsPassed;
    }

    static void incrementDangerousObjectsPassed() {
        DangerousObjectsPassed++;
    }

    static int getDangerousObjectsPassed() {
        return DangerousObjectsPassed;
    }

    static void setSurvivedSeconds(int secondsSurvived) {
        SurvivedSeconds = secondsSurvived;
    }

    static void incrementSurvivedSeconds() {
        SurvivedSeconds++;
    }

    static int getSurvivedSeconds() {
        return SurvivedSeconds;
    }

    static int[] getScores() {
        return new int[]{DangerousObjectsPassed, SurvivedSeconds};
    }

    static int getScore() {
        return Score;
    }

    static void calculateScore() {
        Score = SurvivedSeconds * DangerousObjectsPassed * 10 / 3;
    }

    static int calculateAndGetScore() {
        calculateScore();
        return Score;
    }

    static void zeroing(){
        DangerousObjectsPassed = 0;
        SurvivedSeconds = 0;
        Score = 0;
        LastAssetName = "air";
    }
}
