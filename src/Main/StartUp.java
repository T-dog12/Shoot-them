package Main;

import java.awt.Color;

import javax.swing.JFrame;

public class StartUp extends JFrame{
	
	StartUp(){
		this.add(new Panel());
		this.setTitle("Shoot The Slimes");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBackground(new Color(179, 100, 30));
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StartUp start = new StartUp();
	}

}
