package idolagames.turkey;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class Turkey extends Applet implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	private boolean running = false;
	
	public static final int SCREEN_WIDTH = 320;
	public static final int SCREEN_HEIGHT = 240;
	public static final int SCREEN_SCALE = 2;
	public int score;
	
	public Player player;
	public Apple apple;
	public BadApple badapple;
	public EvilTurkey turkey;
	public EvilTurkey turkey2;
	public boolean secondTurkeyAdded = false;
	public int timer = 0;
	
	public List<Entity> entities;
	public Input input;
	public Random random = new Random();
	
	public Turkey() {
		this.entities = new LinkedList<Entity>();
		this.setSize(SCREEN_WIDTH*SCREEN_SCALE, SCREEN_HEIGHT*SCREEN_SCALE);
		this.addKeyListener(this);
		this.input = new Input();
		this.player = new Player();
		this.entities.add(player);
		this.apple = new Apple(16+random.nextInt(SCREEN_WIDTH-16), 16+random.nextInt(SCREEN_HEIGHT-16));
		this.entities.add(apple);
		this.badapple = new BadApple(16+random.nextInt(SCREEN_WIDTH-16), 16+random.nextInt(SCREEN_HEIGHT-16));
		this.entities.add(badapple);
		this.turkey = new EvilTurkey(32+random.nextInt(SCREEN_WIDTH-32), 32+random.nextInt(SCREEN_HEIGHT-32));
		this.entities.add(turkey);
	}
	
	@Override
	public void start() {
		this.running = true;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		Image screenBuffer = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
		while (this.running) {
			// Iterate through entities
			Graphics g = screenBuffer.getGraphics();
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			for (Entity e : this.entities) {
				e.onTick(this.input);
				input.tick();
				e.onDraw(g);
			}
			
			// Collision with GOOD apple!
			if ((player.x > apple.x-8) && (player.x < apple.x+8)
					&& (player.y > apple.y-8) && (player.y < apple.y+8)) {
				resetPositions();
				this.score+=100;
			}
			
			// Collision with Bad Apple!
			if ((player.x > badapple.x-8) && (player.x < badapple.x+8)
					&& (player.y > badapple.y-8) && (player.y < badapple.y+8)) {
				resetPositions();
				this.score-=200;
			}
			
			// Collision with Evil Turkey!
			if ((player.x > turkey.x-16) && (player.x < turkey.x+16)
					&& (player.y > turkey.y-16) && (player.y < turkey.y+16)) {
				resetPositions();
				this.score-=50;
			}
			
			// Collision with Evil Turkey 2!
			if (secondTurkeyAdded) {
				if ((player.x > turkey2.x-16) && (player.x < turkey2.x+16)
						&& (player.y > turkey2.y-16) && (player.y < turkey2.y+16)) {
					resetPositions();
					this.score-=50;
				}
			}
			
			// difficulty increaser!
			if (score >= 4000 && secondTurkeyAdded == false) {
				secondTurkeyAdded = true;
				turkey2 = new EvilTurkey(random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_HEIGHT));
				turkey2.directionX = 4;
				turkey2.directionY = 4;
				this.entities.add(turkey2);
			}
			
			// Draw score
			g.setColor(Color.BLACK);
			g.drawString(score+"", 11, 221);
			g.setColor(Color.WHITE);
			g.drawString(score+"", 10, 220);
			
			// Tutorial text timer
			if (timer <= 180) {
				timer += 1;
				g.setColor(Color.WHITE);
				g.drawString("Collect the faster blinking apple!",15,12);
				g.drawString("Don't collect the slower one! Dodge the turkeys!",15,22);
				g.drawString("Get 10,000pts to win!", 15, 32);
				g.drawString("Don't go below 0pts!", 15, 42);
			}
			
			// Win screen!
			if (score >= 10000) {
				entities.clear();
				g.setColor(Color.WHITE);
				g.drawString("YOU WIN!", 120, 120);
			}
			
			// GG LOL YOU LOSE FAIL FACE
			if (score < 0) {
				System.exit(0);
			}
			
			// Draw buffer to screen
			g.dispose();
			Graphics screenG = this.getGraphics();
			try {
				screenG.drawImage(screenBuffer, 0, 0, SCREEN_WIDTH*SCREEN_SCALE, SCREEN_HEIGHT*SCREEN_SCALE, null);
			} catch (Exception e) {
				System.err.println("Unable to draw buffer");
				e.printStackTrace(System.err);
			}
			
			// Wait
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e1) {
				System.err.println("LOL INTERRUPTED");
				e1.printStackTrace(System.err);
			}
		}
	}

	public static void main(String[] args) {
		Turkey game = new Turkey();
		JFrame frame = new JFrame();
		frame.setLayout(new GridLayout());
		frame.add(game);
		frame.setPreferredSize(new Dimension(SCREEN_WIDTH*SCREEN_SCALE, SCREEN_HEIGHT*SCREEN_SCALE));
		frame.pack();
		frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        game.start();
	}

	public void keyPressed(KeyEvent ke) {
        input.set(ke.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent ke) {
        input.set(ke.getKeyCode(), false);
    }

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void resetPositions() {
		apple.x = 16+random.nextInt(SCREEN_WIDTH-16);
		apple.y = 16+random.nextInt(SCREEN_HEIGHT-16);
		badapple.x = 16+random.nextInt(SCREEN_WIDTH-16);
		badapple.y = 16+random.nextInt(SCREEN_HEIGHT-16);
		turkey.x = 32+random.nextInt(SCREEN_WIDTH-32);
		turkey.y = 32+random.nextInt(SCREEN_HEIGHT-32);
		if (secondTurkeyAdded) {
			turkey2.x = 32+random.nextInt(SCREEN_WIDTH-32);
			turkey2.y = 32+random.nextInt(SCREEN_HEIGHT-32);
		}
	}

}
