package Slimes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import SpriteSheet.Movement.BufferImageLoader;
import SpriteSheet.Movement.SpriteSheet;

public class Slimes extends Rectangle{
	
	BufferedImage spriteS;
	BufferedImage slime;
	
	Random ran;
	
	int imWidth = -64;
	int imHeight = 64;
	int ySpeed;
	int xSpeed;
	
	int count = 0;
	int possition = 1;
	
	public Slimes(int x, int y, int width, int height, int speed){
		super(x, y, width, height);
		
		xSpeed = speed;
		
		ran = new Random();
		int num1 = 1+ ran.nextInt(100);
		int num2 = 1+ ran.nextInt(100);
		
		//  p(true) = 0.25
		if(num1 % 2 == 0 && num2 % 2 == 0) {
			ySpeed = 0;
		}
		// p(num1 > num2) = 0.3712
		else if(num1 > num2) {
			ySpeed = 2;
			
		}
		// p(num1 <= num2) = 0.378
		else {
			ySpeed = -2;
			
		}
		
		
		grabImage(1,1, "/sSlime p5.PNG");
	}
	
	public void grabImage(int col, int row, String varation) {
		BufferImageLoader loader = new BufferImageLoader();
	
		try {
			spriteS = loader.loadImage(varation);
		}catch(IOException e) {
			
		}
		
		SpriteSheet ss = new SpriteSheet(spriteS);
		slime = ss.grabImage(col, row, 64, 64, 64, 64);
	}
	
	public void move() {
		x -= xSpeed;
		y -= ySpeed;
		
		if((y < 25 || ySpeed <= 0) 
				&& (y > 455 || ySpeed >= 0)) {
			ySpeed *= -1;
		}	
		
		count ++;
		if(count == 10) {
			count = 0;
			possition ++;
			
			if(possition == 5) {
				possition= 1;
			
			}
			grabImage(possition,1, "/sSlime p5.PNG");
		}
	}
	
	public void draw(Graphics2D g2d) {
		
		
		g2d.drawImage(slime, x+54, y-20, imWidth, imHeight, null);
		
		
	}
}
