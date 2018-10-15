package team2.team2game;

import android.graphics.Point;
import android.graphics.Rect;
import android.widget.ImageView;

public class PlayerObject
{
    public int Health;
    public double Speed;
    public ImageView ImgView;

    public PlayerObject (int health, double speed, ImageView imgView)
    {
        this.Health = health;
        this.Speed = speed;
        this.ImgView = imgView;
    }

    public Rectangle getRectangle (){
        if(ImgView == null) return null;
        Rectangle rect = new Rectangle();
        rect.LeftUpperPoint = new Point(ImgView.getLeft(),ImgView.getTop());
        rect.RightLowerPoint = new Point(ImgView.getRight(),ImgView.getBottom());
        return rect;
    }
}
