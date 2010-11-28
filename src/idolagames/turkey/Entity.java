package idolagames.turkey;

import java.awt.Graphics;

public abstract class Entity {
	public double x;
	public double y;
	public double width;
	public double height;
	
	public abstract void onTick(Input input);
	public abstract void onDraw(Graphics g);
	
	public Entity() {
		this.x = 0;
		this.y = 0;
	}
	
	public Entity(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
