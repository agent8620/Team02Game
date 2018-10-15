package team2.team2game;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.util.Size;

/**
 * Created by android on 09/10/2018.
 */

public class Rectangle {
    public Point LeftUpperPoint;
    public Point RightLowerPoint;

    public Rectangle () { }

    public Size getSize (){
        return new Size(RightLowerPoint.x - LeftUpperPoint.x, RightLowerPoint.y - LeftUpperPoint.y);
    }

    public boolean IsIntersecting (Rectangle other){
        boolean intersects = false;
        // If one rectangle is on left side of other
        //if (this.LeftUpperPoint.x > other.RightLowerPoint.x || other.LeftUpperPoint.x > this.RightLowerPoint.x)
          if( (other.LeftUpperPoint.x-this.RightLowerPoint.x) * (other.RightLowerPoint.x-this.LeftUpperPoint.x) <= 0 &&
                  (other.LeftUpperPoint.y-this.RightLowerPoint.y) * (other.RightLowerPoint.y-this.LeftUpperPoint.y) <= 0)
            intersects = true;
        // If one rectangle is above other
        //if (this.LeftUpperPoint.y < other.RightLowerPoint.y || other.LeftUpperPoint.y < this.RightLowerPoint.y)
            //intersects = false;
        //intersects = true;

        //Log.d("myTag","this rect: " + this.toString() + ", other rect: " + other.toString() + ", intersects: " + intersects);
        return intersects;
    }

    @Override
    public String toString() {
        return "{LU-X: " + LeftUpperPoint.x + ",LU-Y: " + LeftUpperPoint.y + ", RL-X: " + RightLowerPoint.x + ", RL-Y: " + RightLowerPoint.y + "}";
    }
}
