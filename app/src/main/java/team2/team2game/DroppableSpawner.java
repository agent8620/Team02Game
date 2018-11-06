package team2.team2game;

import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by android on 15/10/2018.
 */

class DroppableSpawner {

    private boolean IsEnabled = false;

    private MainActivity gameActivity;
    private ArrayList<DroppableObject> droppableObjList = new ArrayList<>();

    private ConstraintLayout.LayoutParams templateLayoutParams;

    DroppableSpawner(MainActivity gameActivity){
        this.gameActivity = gameActivity;

        templateLayoutParams = (ConstraintLayout.LayoutParams)gameActivity.findViewById(R.id.droppablePatternImg).getLayoutParams();
    }

    void Enable (){
        IsEnabled = true;
    }

    void Update () {
        if (!IsEnabled) return;

        if (droppableObjList.size() == 0) {
            DropRandomObject();
        }
        HandleDroppableMovements();
    }

    private void DropRandomObject () {
        int randAssetIdx = new Random().nextInt(DroppableDefinitions.GetCount());
        DroppableAsset asset = DroppableDefinitions.Assets[randAssetIdx];

        DroppableObject droppableObj = _spawnObject(asset);
        droppableObjList.add(droppableObj);
    }

    private DroppableObject _spawnObject (DroppableAsset asset){
        ImageView imageView = new ImageView(gameActivity);
        imageView.setImageResource(asset.getTexture());
        ConstraintLayout.LayoutParams params = templateLayoutParams;
        params.setMarginStart(new Random().nextInt(gameActivity.getDisplaySize().x - asset.getWidth()));
        params.topMargin = -100;
        imageView.setLayoutParams(params);
        gameActivity.getConstraintLayout().addView(imageView);

        return new DroppableObject(asset, imageView);
    }

    private void HandleDroppableMovements (){
        for (DroppableObject obj: droppableObjList){
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)obj.ImgView.getLayoutParams();
            params.topMargin += obj.getSpeed();
            obj.ImgView.setLayoutParams(params);

            boolean intersected = gameActivity.isDroppableIntersectingPlayer(obj);
            Point displaySize = gameActivity.getDisplaySize();

            if (params.topMargin > displaySize.y){
                //Toast.makeText(this, "Object fallen away", Toast.LENGTH_LONG).show();
                destroyDroppableObject(obj);
                gameActivity.OnDroppableObjectPassed(obj.getObjectType());
            }
            else if (intersected){
                //gameActivity.playerObject.Health += obj.getHealth();
                //Toast.makeText(this, "Object collected. New health: " + playerObject.Health, Toast.LENGTH_LONG).show();
                destroyDroppableObject(obj);
                gameActivity.OnDroppableObjectCatch(obj.getObjectType());
            }
        }
    }

    private void destroyDroppableObject (DroppableObject obj){
        droppableObjList.remove(obj);
        gameActivity.getConstraintLayout().removeView(obj.ImgView);

        //dropNewDroppableObject();
    }
}
