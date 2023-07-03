package Main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import Player.Player;
import Slimes.Slimes;


public class Panel extends JPanel implements Runnable, ActionListener{
	
	final static int GAME_WIDTH = 1000;
	final static int GAME_HEIGHT = 550;
	final static Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	
	final private int ns = 1_000_000_000/60;
	private static long lastFPSCheck = 0;
	private static long currentFPS = 0;
	private static long totalFrames =0;
	
	boolean dead = false;
	
	Thread gameThread;
	Graphics graphics;
	Image image;
	Random ran;
	
	Timer difficultyIncrease;
	int difficulty = 1;
	int lives = 3;
	
	Timer slimeTimer;
	Slimes slime;
	ArrayList<Slimes> slimes = new ArrayList<Slimes>();
	
	Player player;
	Font font = new Font("SERIF", Font.BOLD, 16);
	Font deadFont = new Font("SERIF", Font.BOLD, 60);
	
	public Panel() {
		ran = new Random();
		slimeTimer = new Timer(250,this);
		difficultyIncrease = new Timer(30000,this);
		
		newSlime();
		newPlayer();
		
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.addMouseListener(new ML());
		this.setPreferredSize(SCREEN_SIZE);
				
		// starts game thread
		gameThread = new Thread(this);
		gameThread.start();
		slimeTimer.start();
		difficultyIncrease.start();	
	}
	
	public void newPlayer() {
		player = new Player(10,10, 56,64);
	}
	public void newSlime() {
		slime = new Slimes(1100, 35 + ran.nextInt(416), 48, 42, difficulty);
		slimes.add(slime);
	}
	
	public void paint(Graphics g) {
		// puts everything on the screen all at once
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
	}
	void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		try {
			for (int x = 0; x<slimes.size(); x++) {
				slimes.get(x).draw(g2d);
			}
			
		}catch(Exception e) {
			
		}
		player.draw(g2d);
		
		g2d.setFont(font);
		g2d.drawString("Level "+difficulty+
				"                      Lives " + lives, 425, 20);
		
		if(lives <= 0) {
			dead = true;
			g2d.setFont(deadFont);
			g2d.drawString("YOU ARE DEAD!", 275, 250);
		}
	}
	
	void hitBoxes() {
		try {
			for(int x = 0; x < player.swords.size(); x++) {
				
				for (int p = 0; p< slimes.size(); p++) {
					if(player.swords.get(x).intersects(slimes.get(p))) {
						player.swords.remove(x);
						slimes.remove(p);
					}
				}
				
			}
		}catch (Exception e){
			
		}
	}
	
	void move() {
		if(!dead) {
			try {
				for (int p = 0; p< slimes.size(); p++) {
					//slimes.get(p).move();
					
					if(slimes.get(p).x < 30) {
						slimes.remove(p);
						lives --;
					}
				}
				
			}catch(Exception e) {
				
			}
			player.move();
		}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			
			
			//FPS
			totalFrames ++;
			
			
			if (System.nanoTime() > lastFPSCheck + ns) {
				lastFPSCheck = System.nanoTime();
				currentFPS = totalFrames/10000;
				totalFrames = 0;
				
				// updates the game's panel and movements
				repaint();
				hitBoxes();
				move();
			
			}
			
			
		}
	}
	
	public class AL implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			player.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			player.keyReleased(e);
		}
		
	}public class ML implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			player.mouseClicked(e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == slimeTimer) {
			newSlime();
			
			slimeTimer.setDelay(500+ran.nextInt(1500));
		}
		if(e.getSource() == difficultyIncrease) {
			if(difficulty < 5) {
				difficulty++;
			}else {
				difficultyIncrease.stop();
			}
		}
	}


	
}
