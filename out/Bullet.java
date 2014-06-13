import java.awt.*;

/**
 * Created by Ace on 18.5.2014 г..
 */
public class Bullet {
    Rectangle rect = new Rectangle();
    int height, width, pointX, pointY, bulletSpeed, damage;

    public void getBullet( int pointX, int pointY, int width, int height, int bulletSpeed, int damage){
        this.bulletSpeed = bulletSpeed;
        this.damage = damage;
        this.width = width;
        this.height = height;
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public boolean isOutsideBounds(Bullet b){
        if( b.pointY > 640 || b.pointX > 480 || b.pointY < 0){
            return true;
        }
        else return false;
    }
}
