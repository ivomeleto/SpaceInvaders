import java.awt.image.BufferedImage;


public class TakeDMG {

	int dmgX, dmgY;
	BufferedImage sprite;
	
	public void setCoords (int x, int y){
		this.dmgX = x;
		this.dmgY = y;
		this.sprite = DisplayActions.getImage("explosion3");
	}
}
