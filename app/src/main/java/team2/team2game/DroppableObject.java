package team2.team2game;

import android.graphics.Point;
import android.widget.ImageView;

// Describes falling objects
public class DroppableObject
{
    public String Name;
    private ObjectType Type;

    public ImageView ImgView;

    public DroppableObject (String name, ObjectType type,double speed, ImageView imgView){
        this.Name = name;
        this.Type = type;
        this.Type.setMultiplier(speed);
        this.ImgView = imgView;
    }

    public DroppableObject (String name, ObjectType type, ImageView imgView){
        this.Name = name;
        this.Type = type;
        this.ImgView = imgView;
    }

    public ObjectType getObjectType(){
        return this.Type;
    }

    public double getSpeed(){
        return this.Type.getMultiplier();
    }

    public int getHealth(){
        return this.Type.getHealth();
    }

    public Rectangle getRectangle (){
        if(ImgView == null) return null;
        Rectangle rect = new Rectangle();
        rect.LeftUpperPoint = new Point(ImgView.getLeft(),ImgView.getTop());
        rect.RightLowerPoint = new Point(ImgView.getRight(),ImgView.getBottom());
        return rect;
    }

    @Override
    public void finalize(){

    }
}