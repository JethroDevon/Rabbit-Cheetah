import javax.swing.*;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

class Main extends JFrame{

	Game game;

	public Main(){

		
		//this.setUndecorated(true);
		game = new Game(800, 800);
		this.add(game);
		this.pack();
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		//calls runnable function
		game.run();

	}
	
	public static void main(String[] args) throws InterruptedException {
				
		new Main();
	}
}
