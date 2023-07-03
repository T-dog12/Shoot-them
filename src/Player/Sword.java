package Player;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sword extends Rectangle{
	
	BufferedImage sword;
	
	int imHeight = 32;
	int imWidth = 32;
	int angle = 0;
	
	int count = 0;
	
	Sword(int x, int y, int width, int height){
		super(x, y, width, height);
		try {
			sword = ImageIO.read(getClass().getResource("/sword.PNG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	void move() {
		x += 10;
		count ++;
		
		if(count == 4) {
			angle += 1;
			
			count = 0;
		}
		
	}
	void draw(Graphics2D g2d) {
		
		AffineTransform oldAngle = g2d.getTransform();
		
		g2d.rotate(angle, x+16, y+16);
		g2d.drawImage(sword, x-5, y-5, imWidth, imHeight, null);
		//g2d.drawRect(x, y, width, height);
		
		g2d.setTransform(oldAngle);
	}

}
