package team2.team2game;

import android.graphics.Point;
import android.graphics.Rect;
import android.widget.ImageView;

class PlayerObject
{
    int Health;
    double Speed;
    private ImageView ImgView;

    PlayerObject (int health, double speed, ImageView imgView)
    {
        this.Health = health;
        this.Speed = speed;
        this.ImgView = imgView;
    }

    Rectangle getRectangle (){
        if(ImgView == null) return null;
        Rectangle rect = new Rectangle();
        rect.LeftUpperPoint = new Point(ImgView.getLeft(),ImgView.getTop());
        rect.RightLowerPoint = new Point(ImgView.getRight(),ImgView.getBottom());
        return rect;
    }
}
