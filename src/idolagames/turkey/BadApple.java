package idolagames.turkey;

import java.awt.Graphics;
import java.awt.Image;

public class BadApple extends Entity {

	Image sprite = Art.getImage("apple");
	public int timer = 0;
	public boolean isVisible = true;

	public BadApple(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void onTick(Input input) {
		timer++;
		if (timer == 15) {
			timer = 0;
			if (isVisible == true) isVisible = false; else isVisible = true;
		}
	}

	@Override
	public void onDraw(Graphics g) {
		if (isVisible) {
			g.drawImage(sprite, (int)Math.round(x-8), (int)Math.round(y-8), null);
		}
	}

}
