package team2.team2game;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.util.Size;

/**
 * Created by android on 09/10/2018.
 */

public class Rectangle {
    Point LeftUpperPoint;
    Point RightLowerPoint;

    Rectangle () { }

    Size getSize (){
        return new Size(RightLowerPoint.x - LeftUpperPoint.x, RightLowerPoint.y - LeftUpperPoint.y);
    }

    boolean IsIntersecting (Rectangle other){
        boolean intersects = false;
          if( (other.LeftUpperPoint.x-this.RightLowerPoint.x) * (other.RightLowerPoint.x-this.LeftUpperPoint.x) <= 0 &&
                  (other.LeftUpperPoint.y-this.RightLowerPoint.y) * (other.RightLowerPoint.y-this.LeftUpperPoint.y) <= 0)
            intersects = true;
        return intersects;
    }

    @Override
    public String toString() {
        return "{LU-X: " + LeftUpperPoint.x + ",LU-Y: " + LeftUpperPoint.y + ", RL-X: " + RightLowerPoint.x + ", RL-Y: " + RightLowerPoint.y + "}";
    }
}
