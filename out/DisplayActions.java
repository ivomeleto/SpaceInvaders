

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DisplayActions extends JComponent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int xShip, yShip, moveSpeed, BulletTimer, attackSpeed, maxEnemies, difficulty, level, pause, score;
    public static int speed = 5;
    public static int shotSpeed = 50;
    public static int bulletSize = 20;
    boolean left, right, shoot;
    List<Enemy> enemies = new CopyOnWriteArrayList<Enemy>();
    List<Bullet> bullets = new CopyOnWriteArrayList<Bullet>();
    static DisplayActions actions = new DisplayActions();

    BufferedImage ship = getImage("Ship3"); // Add images by putting them in /res/.
    BufferedImage bullet = getImage("PBullet");

    InputHandler input = new InputHandler();
    static JFrame window = new JFrame(); // Creates a window
    Random rnd = new Random();

    public static void main(String[] Args){

    actions.setUpDisplay(); // Creates the window and sets the parameters in setUP;
    actions.setUpVariables();
        while (true) {
            try {
                actions.movement();
                actions.shoot(speed);
                actions.repaint();
                actions.spawnEnemies();
                actions.moveEnemeies();
                actions.detectCollision();
                Thread.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawImage(ship, xShip, yShip, ship.getWidth(), ship.getHeight(), null);
        for(Bullet b : bullets){
            g.drawImage(bullet, b.pointX,b.pointY,b.width,b.height, null);
        }
        for (Enemy e : enemies){
            g.drawImage(e.sprite, e.locX, e.locY, e.sprite.getWidth(), e.sprite.getHeight(), null);
        }
    }
    public void spawnEnemies() {
        if (enemies.isEmpty()) {
            for (int i = 0; i < difficulty; i++) {
                    Enemy e = new Enemy();
                    e.setEnemy(1);
                    enemies.add(e);
                }
            pause = 500; // 4 seconds
            difficulty += 3;
            speed+=2;
            if(shotSpeed > 5){
              shotSpeed-= 7;
            }
            bulletSize+=5;
            level += 1;
            }
        }
    public void detectCollision(){
        for (Bullet b : bullets){
            for (Enemy e : enemies){
                if (b.pointX >= e.locX && b.pointX <= e.locX + e.sprite.getWidth() && b.pointY <= e.locY && b.pointY < e.locY + e.sprite.getHeight()){
                    e.health--;
                    bullets.remove(b);
                }
            }
        }
    }
    public void moveEnemeies() {
        for (Enemy e : enemies) {

            int temp = rnd.nextInt(100);
            switch (e.id){
                case 1 : {
                        e.locX += e.moveSpeedX * e.directionX;
                    e.locY += e.moveSpeedY * e.directionX;
                    if (e.directionSwitchX == 0) {
                        if (e.locX <= 5 || e.locX > getWidth() - e.sprite.getWidth() - 10) {
                            e.directionX = -e.directionX;
                            e.directionSwitchX = 10;
                        } else if (temp < 1) {
                            e.directionX = -e.directionX;
                            e.directionSwitchX += 5;
                        }

                    }else {e.directionSwitchX--;}

                    if (e.health < 1) {
                        enemies.remove(e);
                        e.directionSwitchX += 5;
                    }
                }
                case 2 :
                    if (e.locX > 0 && e.locX < (window.getWidth() - e.sprite.getWidth())) {
                        e.locX += e.moveSpeedX * e.directionX;
                        e.locY += e.moveSpeedY * e.directionX;
                    }

                    if (e.locX <= 5 || e.locX > getWidth() - e.sprite.getWidth() - 10) {
                        e.directionX = -e.directionX;
                    }
            }
        }
    }
    public void shoot(int projSpeed){

        if(shoot && BulletTimer == 0){
            Bullet b = new Bullet(); // Creates a new bullet;
            b.getBullet(6 + xShip + ship.getWidth() / 8, yShip - ship.getHeight() / 9, 5, bulletSize, projSpeed, 1); // Sets bullet's Parameters
            bullets.add(b); // adds to array
            BulletTimer = shotSpeed; // Time between bullets is 32 * 8 ( thread.sleep) miliseconds = 256ms
        }else if(BulletTimer >= 1){
            BulletTimer--;
        }
        for (Bullet b : bullets ){
            if (b.isOutsideBounds(b)){
                bullets.remove(b);
            }
            else {
                b.pointY -= b.bulletSpeed;
            }

        }
    }
    public void movement() {
        left = input.getLeft();
        right = input.getRight();
        shoot = input.getShoot();

        if ( (xShip < (window.getWidth() - ship.getWidth()/3 - 5))){
            if (right) {
                xShip += moveSpeed;
            }
        }
        if (xShip >= 0 ){
            if (left) {
                xShip -= moveSpeed;
            }
        }
    }
    public void setUpVariables(){
        xShip = (window.getWidth()/2) - (ship.getWidth()/6);
        yShip = 600-ship.getHeight()/3;
        attackSpeed = 48; //lower is faster;
        moveSpeed = 5;
        difficulty = 3;
    }
    public void setUpDisplay(){

        window.setSize(600, 800);
        window.setTitle("Gaem");
        window.addKeyListener(input);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(actions);
        window.setVisible(true);
    }
    public static BufferedImage getImage(String str){
        File source = new File("res/" + str + ".png");
        BufferedImage in = null;
        try {
            in = ImageIO.read(source);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

}
