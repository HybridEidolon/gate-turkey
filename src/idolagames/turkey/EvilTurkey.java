package idolagames.turkey;

import java.awt.Graphics;
import java.awt.Image;

public class EvilTurkey extends Entity {

	public int directionX = 2;
	public int directionY = 2;
	
	public Image sprite = Art.getImage("turkey");
	
	public EvilTurkey(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void onTick(Input input) {
		x += directionX;
		y += directionY;
		
		if (x > Turkey.SCREEN_WIDTH || x < 0) {
			directionX += -(2*directionX);
		}
		if (y > Turkey.SCREEN_HEIGHT || y < 0) {
			directionY += -(2*directionY);
		}
	}

	@Override
	public void onDraw(Graphics g) {
		g.drawImage(sprite, (int)Math.round(x-16), (int)Math.round(y-16), null);
	}

}
