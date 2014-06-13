import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This is the class intended for bullets fired by the Player.
 * Created by Ace on 18.5.2014 г..
 */
public class Bullet {
    int  id, height, width, pointX, pointY, bulletSpeed, damage, direction, shotSpeed;
    boolean collides;
    BufferedImage sprite;

    public void setBullet(int id, int x, int y, int bulletSpeed, int height, int width){
                this.id = 1;
                this.pointX = x;
                this.pointY = y;
                this.bulletSpeed = bulletSpeed;
                this.shotSpeed = 24;
                this.damage = 1;
                this.direction = -1; // player
                this.sprite = DisplayActions.getImage("PBullet1");
                this.width = width;
                this.height = height;
                this.collides = true;
    }

    public boolean isOutsideBounds(Bullet b){
        if( b.pointY > 640 || b.pointX > 480 || b.pointY < 0 ){
            return true;
        }
        else return false;
    }
}
