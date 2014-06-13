import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Ace on 18.5.2014 г..
 */
public class Enemy {
    int value, health, shotFreqency, moveSpeedX, moveSpeedY, locX, locY, directionSwitchX, directionSwitchY, maxY, minY, directionX, directionY, id, cd;
    boolean switchedLeft, switchedRight;
    BufferedImage sprite;
    Random rng = new Random();


    public void setEnemy(int id){
        switch (id){
            case 1:{
                this.sprite = DisplayActions.getImage("EShip1");
                this.id = 1;
                this.health = 3;
                this.value = 1;
                this.moveSpeedX = 1;
                this.moveSpeedY = 0;
                this.shotFreqency = 64; //Same as before X * Thread.sleep(8)
                this.locX = rng.nextInt(300) + this.sprite.getWidth();
                this.locY = rng.nextInt(200);
                this.directionX = 1;
                this.directionY = 0;
                this.directionSwitchX = rng.nextInt(40) + 10;
                break;
            }
            case 2:{

                this.sprite = DisplayActions.getImage("EShip2");
                this.id = 2;
                this.health = 7;
                this.value = 3;
                this.moveSpeedX = 2;
                this.moveSpeedY = 1;
                this.shotFreqency = 16; //Same as before X * Thread.sleep(8)
                this.locX = rng.nextInt(300) + this.sprite.getWidth();
                this.locY = + rng.nextInt(200) + 20 ;
                this.directionX = 1;
                this.directionY = 1;
                this.maxY = locY + 100;
                this.minY = locY - 100;
                break;

            }
            case 3:{

                this.sprite = DisplayActions.getImage("EShip3");
                this.id = 3;
                this.health = 20;
                this.value = 7;
                this.directionSwitchX = rng.nextInt(50);
                this.moveSpeedX = 3;
                this.moveSpeedY = 1;
                this.shotFreqency = 280;
                this.directionX = 1;
                this.locX = rng.nextInt(300) + this.sprite.getWidth();
                this.locY = rng.nextInt(80);
                break;
            }
        }

    }
}
