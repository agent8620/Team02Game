package team2.team2game;

/**
 * Created by android on 15/10/2018.
 */

class DroppableDefinitions {
     static final DroppableAsset Assets[] = new DroppableAsset[]{
            new DroppableAsset("Apple",40, 5, R.drawable.apple, 68, 60),
            new DroppableAsset("Bonus",40, 1, R.drawable.bonus, 34, 39),
            new DroppableAsset("Brick",40, -10, R.drawable.brick, 113, 147),
            new DroppableAsset("Cherry",40, 1, R.drawable.cherry, 63, 58),
            new DroppableAsset("Dagger",40, -31, R.drawable.dagger, 163, 105),
            new DroppableAsset("Death potion",40, -38, R.drawable.death_potion, 179, 97),
            new DroppableAsset("Health potion",40, 25, R.drawable.health_potion, 68, 55),
            new DroppableAsset("Pie",40, 16, R.drawable.pie, 144, 134),
            new DroppableAsset("Rainbow sword",40, -49, R.drawable.rainbow, 50, 50),
            new DroppableAsset("Rock",40, -99, R.drawable.stone, 620, 651),
            new DroppableAsset("Sword",40, -40, R.drawable.sword, 244, 95),
            new DroppableAsset("Chest",40, -60, R.drawable.trap, 142, 205),
            new DroppableAsset("Trophy",40, -71, R.drawable.trophy, 116, 118)};

     static int GetCount (){
        return Assets.length;
    }
}
