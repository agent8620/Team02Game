package team2.team2game;

import android.graphics.Point;
import android.widget.ImageView;

// Describes falling objects
public class DroppableObject
{
    private String Name;
    private DroppableAsset Type;

    ImageView ImgView;

    public DroppableObject (String name, DroppableAsset type,double speed, ImageView imgView){
        this.Name = name;
        this.Type = type;
        this.Type.setMultiplier(speed);
        this.ImgView = imgView;
    }

    public DroppableObject (String name, DroppableAsset type, ImageView imgView){
        this.Name = name;
        this.Type = type;
        this.ImgView = imgView;
    }

    DroppableObject (DroppableAsset type, ImageView imgView){
        this.Name = type.getName();
        this.Type = type;
        this.ImgView = imgView;
    }

    DroppableAsset getObjectType(){
        return this.Type;
    }

    double getSpeed(){
        return this.Type.getMultiplier();
    }

    public int getHealth(){
        return this.Type.getHealth();
    }

    Rectangle getRectangle (){
        if(ImgView == null) return null;
        Rectangle rect = new Rectangle();
        rect.LeftUpperPoint = new Point(ImgView.getLeft(),ImgView.getTop());
        rect.RightLowerPoint = new Point(ImgView.getRight(),ImgView.getBottom());
        return rect;
    }

    public String getName() {
        return this.Name;
    }

    @Override
    public void finalize(){

    }
}