import java.awt.image.BufferedImage;

/**
 * Created by Ace on 20.5.2014 г..
 */
public class EnemyBullet {
        int  id, height, width, pointX, pointY, bulletSpeed, damage, shotSpeed;
        boolean collides;
        BufferedImage sprite;

        public void setBullet(int id, int x, int y){
            switch (id) {
                case 1:{
                    this.id = 1;
                    this.pointX = x;
                    this.pointY = y;
                    this.bulletSpeed = 2;
                    this.shotSpeed = 4;
                    this.damage = 1;
                    this.sprite = DisplayActions.getImage("EBullet2");
                    this.width = 5;
                    this.height = 20;
                    this.collides = true;
                    break;
                }
                case 2:{
                    this.id = 2;
                    this.pointX = x;
                    this.pointY = y;
                    this.bulletSpeed = 2;
                    this.damage = 1;
                    this.shotSpeed = 15;
                    this.sprite = DisplayActions.getImage("EBullet2");
                    this.width = this.sprite.getWidth();
                    this.height = this.sprite.getHeight();
                    break;
                }
                case 3:{

                    this.id = 3;
                    this.pointX = x;
                    this.pointY = y;
                    this.bulletSpeed = 7;
                    this.damage = 1;
                    this.shotSpeed = 384;
                    this.sprite = DisplayActions.getImage("EBullet3");
                    this.width = this.sprite.getWidth();
                    this.height = this.sprite.getHeight();
                    break;
                }
            }
        }

        public boolean isOutsideBounds(EnemyBullet b){
            if( b.pointY > 640 || b.pointX > 480 || b.pointY < 0 ){
                return true;
            }
            else return false;
        }
    }


