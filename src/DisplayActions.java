

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

public class DisplayActions extends JComponent {

    int xShip, yShip, moveSpeed, BulletTimer, attackSpeed, difficulty, level, pause, sf, currentBackground, health;
    long invulnerabiltyTime = 500;
    Timer t = new Timer();
    boolean left, right, shoot, run = true, playerHit = false;
    static long score = 0;
    static int currentLvlScore = 0;
    static int projSpeed = 3       ;
    static int shotInterval = 70;
    static int bulletSize = 15;
    static int bulletWidth = 5;

    List<Enemy> enemies = new CopyOnWriteArrayList<Enemy>();
    List<Bullet> bullets = new CopyOnWriteArrayList<Bullet>();
    List<EnemyBullet> enemyBullets = new CopyOnWriteArrayList<EnemyBullet>();

    static DisplayActions actions = new DisplayActions();
    Timer tm = new Timer();

    BufferedImage ship = getImage("bcTest"); // Add images by putting them in /res/.
    BufferedImage backgroundImages[] = {getImage("space1"), getImage("space2"),
            getImage("space3") , getImage("space4"),
            getImage("space5")};
    InputHandler input = new InputHandler();
    static JFrame window = new JFrame(); // Creates a window
    Random rnd = new Random();

    public static void main(String[] Args) {

        actions.setUpDisplay(); // Creates the window and sets the parameters in setUP;
        actions.setUpVariables();
        while (true) {
            try {
                actions.movement();
                actions.shoot(projSpeed);
                actions.repaint();
                actions.spawnEnemies();
                actions.moveEnemeies();
                actions.enemyShoot();
                actions.detectCollision();

                Thread.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    protected void paintComponent(Graphics g) {


        // use all pictures from the array
        g.drawImage(backgroundImages[currentBackground % backgroundImages.length], 0, 0, null);
        g.setColor(Color.GREEN);
        g.drawString(String.format("Score: %d Level: %d", score, level), 30,30);
        g.setColor(Color.BLUE);
        g.drawImage(ship, xShip, yShip, ship.getWidth() / 3, ship.getHeight() / 3, null);
        for (Bullet b : bullets) {
            g.drawImage(b.sprite, b.pointX, b.pointY, b.width, b.height, null);
        }
        for (EnemyBullet eb : enemyBullets) {
            g.drawImage(eb.sprite, eb.pointX, eb.pointY, eb.sprite.getWidth(), eb.sprite.getHeight(), null);
        }
        for (Enemy e : enemies) {
            g.drawImage(e.sprite, e.locX, e.locY, e.sprite.getWidth(), e.sprite.getHeight(), null);
        }
    }
    public void spawnEnemies() {
        if (enemies.isEmpty()) {
            for (int i = 0; i < difficulty; ) {
                int temp = rnd.nextInt(200);
                if (temp < 110 - difficulty*3) {
                    temp = 1;
                } else if (temp < 180 - difficulty*3 && temp > 110 - difficulty*3) {
                    temp = 2;
                } else if (temp >= 180 - difficulty*3) {
                    temp = 3;
                }

                Enemy e = new Enemy();
                e.setEnemy(temp);
                if (difficulty - i >= e.value) {
                    enemies.add(e);
                    i += e.value;
                }
            }
            difficulty += 3;
            level += 1;
            currentLvlScore = 0;
            
            if(playerHit == false && level != 1){
            	projSpeed+= 6;
            	shotInterval-= 4;
            	bulletSize+= 7;
            }else if (level != 1){
            	projSpeed -= 5;
            	shotInterval += 5;
            	bulletSize -= 3;
            }
            if (level % 2 == 0){
                currentBackground++;
            }
        }
    }
    public void enemyShoot() {
        for (EnemyBullet eb : enemyBullets) {
            eb.pointY += eb.bulletSpeed;
            if (eb.isOutsideBounds(eb)) {
                enemyBullets.remove(eb);
            }
        }

        for (Enemy e : enemies) {

            if (e.cd == 0) {
                int temp = rnd.nextInt(100);
                switch (e.id) {
                    case 1: {
                        if (temp > 98) {
                            EnemyBullet eb = new EnemyBullet();
                            eb.setBullet(e.id, (e.locX + (e.sprite.getWidth() / 2)), e.locY);
                            enemyBullets.add(eb);
                            e.cd = e.shotFreqency;
                        }
                    }

                    break;

                    case 2: {
                        if (temp > 96) {
                            EnemyBullet eb = new EnemyBullet();
                            eb.setBullet(e.id, (e.locX + (e.sprite.getWidth() / 4)), e.locY + e.sprite.getHeight() / 4);
                            enemyBullets.add(eb);
                            e.cd = e.shotFreqency;
                        }
                    }

                    break;

                    case 3: {
                        if (temp > 98) {
                            for (int i = 0; i < rnd.nextInt(2) + 2; i++) {
                                EnemyBullet eb = null;
                                eb = new EnemyBullet();
                                eb.setBullet(e.id, (e.locX + (e.sprite.getWidth() / 2) + (rnd.nextInt(30) - 30)), e.locY + rnd.nextInt(30));
                                enemyBullets.add(eb);
                            }

                            e.cd = e.shotFreqency;
                        }
                    }
                    break;
                }

            } else {
                e.cd--;
            }
        }
    }
    public void detectCollision() {
        for (Bullet b : bullets) {
            for (Enemy e : enemies) {
                if (b.pointX >= e.locX && b.pointX <= e.locX + e.sprite.getWidth() && b.pointY <= e.locY && b.pointY < e.locY + e.sprite.getHeight()) {
                    e.health--;
                    bullets.remove(b);
                    score += 10;
                    currentLvlScore +=10;
                }
            }

        }
        for (EnemyBullet eb : enemyBullets) {
            if (eb.pointX > xShip + ship.getWidth() / 16 && eb.pointX < xShip + ship.getWidth() / 4 && eb.pointY >= yShip - 5 && eb.pointY < yShip + ship.getHeight() / 6) {
                if (!playerHit) {
                    actions.tm.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            playerHit = false;
                            ship = getImage("bcTest");
                        }
                    }, invulnerabiltyTime);
                    health -= eb.damage;
                    playerHit = true;
                    //System.out.println("Player Hit!");
                    ship = getImage("bcTestNeg");
                }
            }
        }
        if (health < 1) {
            window.dispose();
            actions.run = false;
        }
    }
    public void moveEnemeies() {
        for (Enemy e : enemies) {

            int temp = rnd.nextInt(100);
            switch (e.id) {
                case 1: {
                    e.locX += e.moveSpeedX * e.directionX;
                    e.locY += e.moveSpeedY * e.directionX;

                    if (e.directionSwitchX == 0) {
                        if (e.locX <= 1 || e.locX > getWidth() - e.sprite.getWidth() - 10) {
                            e.directionX = -e.directionX;
                            e.directionSwitchX = 10;
                        } else if (temp < 1) {
                            e.directionX = -e.directionX;
                            e.directionSwitchX += 5;
                        }

                    } else {
                        e.directionSwitchX--;
                    }
                    break;
                }
                case 2: {

                    temp = rnd.nextInt(200);
                    e.locX += e.moveSpeedX * e.directionX;
                    e.locY += e.moveSpeedY * e.directionY;


                    if (e.directionSwitchX == 0) {
                        if (e.locX <= 1 || e.locX > window.getWidth() - e.sprite.getWidth()) {
                            e.directionX = -e.directionX;
                            e.directionSwitchX += 60;
                        } else if (temp < 1) {
                            e.directionX = -e.directionX;
                            e.directionSwitchX += 5;
                        }
                    } else {
                        e.directionSwitchX--;
                    }
                    if (e.directionSwitchY == 0) {
                        if (e.locY > e.maxY || e.locY < e.minY || e.locY < 10) {
                            e.directionY = -e.directionY;
                            e.directionSwitchY = 15;
                        } else if (temp < 3) {
                            if (e.directionSwitchX == 0) {
                                e.directionX = -e.directionX;
                                e.directionSwitchX += 5;
                            }
                        } else if (temp > 198) {
                            e.directionY = -e.directionY;
                            e.directionSwitchY = 30;
                        }

                    } else {
                        e.directionSwitchY -= 1;
                    }
                    break;
                }
                case 3: {
                    e.locX += e.moveSpeedX * e.directionX;


                    if (e.locX < 1 || e.locX > window.getWidth() - e.sprite.getWidth()) {
                        e.directionX = -e.directionX;
                        if (e.locX > xShip + ship.getWidth() / 12 ) {

                            e.switchedLeft = false;
                        } else if ( e.locX < xShip + ship.getWidth() / 12) {

                            e.switchedRight = false;
                        }
                    }


                    if (!e.switchedRight) {
                        if (e.locX > (xShip + ship.getWidth() / 12) + e.directionSwitchX) {
                            e.directionX = -e.directionX;
                            e.switchedLeft = false;
                            e.switchedRight = true;
                        }
                    }

                    if (!e.switchedLeft) {
                        if (e.locX < (xShip - ship.getWidth() / 12) - e.directionSwitchX) {
                            e.directionX = -e.directionX;
                            e.switchedRight = false;
                            e.switchedLeft = true;
                        }
                    }


                }
            }
            if (e.health < 1) {
                enemies.remove(e);
                score += 100;
            }

        }

    }
    public void shoot(int shotSpeed){

        if(shoot && BulletTimer == 0){
            Bullet b = new Bullet(); // Creates a new bullet;
            Bullet b2 = new Bullet();
            Bullet b3 = new Bullet();
            
            b.setBullet(1,-20 + xShip + ship.getWidth() / 8, window.getHeight() - ship.getHeight() / 4, Math.abs(projSpeed), Math.abs(bulletSize), bulletWidth); // Sets bullet's Parameters
            b2.setBullet(1, 6 + xShip + ship.getWidth() / 8, window.getHeight() - ship.getHeight() / 4 - 8, Math.abs(projSpeed), Math.abs(bulletSize) + 5, bulletWidth + 4);
            b3.setBullet(1, 34 + xShip + ship.getWidth() / 8, window.getHeight() - ship.getHeight() / 4, Math.abs(projSpeed), Math.abs(bulletSize), bulletWidth);
//            bullets.add(b); // adds to array
//            if (score>=1000){
//            	bullets.add(b2);
//            }
//            bullets.add(b3);
            if(health == 3){
            	b2.setBullet(1, 6 + xShip + ship.getWidth() / 8, window.getHeight() - ship.getHeight() / 4 - 8, Math.abs(projSpeed), Math.abs(bulletSize) + 5, bulletWidth); 
            	bullets.add(b2);
              
            }else if(health == 2){
            	bullets.remove(b2);
            	bullets.add(b);
            	bullets.add(b3);
            }else if(health == 1){
            	b2.setBullet(1, 6 + xShip + ship.getWidth() / 8, window.getHeight() - ship.getHeight() / 4 - 8, Math.abs(projSpeed), Math.abs(bulletSize) + 5, bulletWidth + 4);
            	bullets.add(b2);
            	bullets.add(b);
            	bullets.add(b3);
            }
            BulletTimer = shotInterval;  // Time between bullets is 32 * 8 ( thread.sleep) miliseconds = 256ms
            
        }else if(BulletTimer >= 1){
            BulletTimer--;
        }
        for (Bullet b : bullets ){
            if (b.isOutsideBounds(b) && b.collides){
                bullets.remove(b);
            }
            else {
                b.pointY += b.bulletSpeed * b.direction;
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
        attackSpeed = 24; //lower is faster;
        moveSpeed = 3;
        difficulty = 3;
        health = 3;
        currentBackground = 0;
    }
    public void setUpDisplay(){

        window.setSize(480, 640);
        window.setTitle("Game");
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
