package idolagames.turkey;

import java.awt.Graphics;
import java.awt.Image;

public class Player extends Entity {

	Image sprite = Art.getImage("player");
	public static final int SPEED = 3;
	
	public Player() {
		
	}
	@Override
	public void onTick(Input input) {
		// Take input
		if (input.buttons[Input.UP]) {
			this.y -= SPEED;
		}
		if (input.buttons[Input.DOWN]) {
			this.y += SPEED;
		}
		if (input.buttons[Input.LEFT]) {
			this.x -= SPEED;
		}
		if (input.buttons[Input.RIGHT]) {
			this.x += SPEED;
		}
		
		if (x < 0 ) {
			x = Turkey.SCREEN_WIDTH;
		}
		if (x > Turkey.SCREEN_WIDTH ) {
			x = 0;
		}
		if (y < 0) {
			y = Turkey.SCREEN_HEIGHT;
		}
		if (y > Turkey.SCREEN_HEIGHT){
			y = 0;
		}
	}

	@Override
	public void onDraw(Graphics g) {
		g.drawImage(sprite, (int)Math.round(x-16), (int)Math.round(y-16), null);
	}

}
