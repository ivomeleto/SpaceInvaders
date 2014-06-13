import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

/**
 * Created by Ace on 17.5.2014 г..
 */

public class InputHandler implements KeyListener {
    public boolean left, right, shoot;


    public boolean getLeft(){
        return left;
    }
    public boolean getRight(){
        return right;
    }
    public boolean getShoot(){
        return shoot;
    }

    @Override
    public void keyTyped(KeyEvent e) {

        int KeyCode = e.getKeyCode();
        if (KeyCode == KeyEvent.VK_A || KeyCode == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (KeyCode == KeyEvent.VK_D || KeyCode == KeyEvent.VK_RIGHT) {
            right = true;
        }

        if (KeyCode == KeyEvent.VK_CONTROL || KeyCode == KeyEvent.VK_SPACE || KeyCode == KeyEvent.VK_W || KeyCode == KeyEvent.VK_UP) {
            shoot = true;
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int KeyCode = e.getKeyCode();
        if (KeyCode == KeyEvent.VK_A || KeyCode == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (KeyCode == KeyEvent.VK_D || KeyCode == KeyEvent.VK_RIGHT) {
            right = true;
        }

        if (KeyCode == KeyEvent.VK_CONTROL || KeyCode == KeyEvent.VK_SPACE || KeyCode == KeyEvent.VK_W || KeyCode == KeyEvent.VK_UP) {
            shoot = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int KeyCode = e.getKeyCode();
        if (KeyCode == KeyEvent.VK_A || KeyCode == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (KeyCode == KeyEvent.VK_D || KeyCode == KeyEvent.VK_RIGHT) {
            right = false;
        }

        if (KeyCode == KeyEvent.VK_CONTROL || KeyCode == KeyEvent.VK_SPACE || KeyCode == KeyEvent.VK_W || KeyCode == KeyEvent.VK_UP) {
            shoot = false;
        }

    }
}