package Player;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Timer;

import SpriteSheet.Movement.BufferImageLoader;
import SpriteSheet.Movement.SpriteSheet;

public class Player extends Rectangle implements ActionListener{
	
	BufferedImage player;
	BufferedImage spriteS;
	
	Timer delay;
	
	int imWidth = -64;
	int imHeight = 64;
	
	int speed = 0;
	
	boolean canThrough = true;
	
	Sword sword;
	public ArrayList<Sword> swords = new ArrayList<Sword>();
	
	public Player(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		grabImage(1,1, "/knight.png");
		
		delay = new Timer(250, this);
		
	}
	
	public void grabImage(int col, int row, String varation) {
		BufferImageLoader loader = new BufferImageLoader();
	
		
		
		
		
		try {
			spriteS = loader.loadImage(varation);
		}catch(IOException e) {
			
		}
		
		
		
		SpriteSheet ss = new SpriteSheet(spriteS);
		player = ss.grabImage(col, row, 32, 32, 32, 32);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			speed = -7;
		}
		else if(e.getKeyCode() == KeyEvent.VK_S) {
			speed = 7;
		}
	}
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			speed = 0;
		}
		else if(e.getKeyCode() == KeyEvent.VK_S) {
			speed = 0;
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1 && canThrough) {
			sword = new Sword(x+ 32, y+28, 20, 20);
			swords.add(sword);
			
			
			grabImage(2,1, "/knight.png");
			canThrough = false;
			delay.restart();
		}
	}
	
	public void move() {
		if((y > 25 || speed > 0) 
				&& (y < 455 || speed < 0)) {
			y += speed;
		}
		
		if(swords.size() > 0) {
			for(int p = 0; p < swords.size(); p++) {
				swords.get(p).move();
				
				if(swords.get(p).x > 1100) {
					swords.remove(p);
				}
			}
			
		}
		
	}
	
	public void draw(Graphics2D g2d) {
		
		if(swords.size() > 0) {
			for(int x = 0; x < swords.size(); x++) {
				swords.get(x).draw(g2d);
			}
			
		}
		
		g2d.drawImage(player, x+60, y, imWidth, imHeight, null);
		//g2d.drawRect(x, y, width, height);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == delay) {
			grabImage(1,1, "/knight.png");
			
			canThrough = true;
			delay.stop();
		}
		
	}
}
