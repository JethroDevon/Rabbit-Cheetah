import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics;


class Tile extends Sprite{

	//this value is for how much this tile would slow a cheetah or rabbit down, default is zero, number is the unique identity
	//of each tile based on its position on the grid
	public int tTorp = 0, number;

	//this array stores nearby tiles
	public ArrayList<Tile> neighbours = new ArrayList<Tile>();

	//MD stands for manhattan distance, for each tile further away from the rabbit or cheetah an extra value of ten is added, the name manhatten comes from manhatten
	//where an address could be ten blocks down and 3 blocks along, these two variables will be updated with a setMD function and set relative to rabit and cheetah
	//blocks diagonaly away may have a value of 14, or at least in some experiments that may be included in the experiments folder.
	public int rabbitMD, cheetahMD;

	//draws a line from the center of itself to the center of each neighbouring tile, for debuging abd possible demonstrations
	//takes a graphics object as an argument
	public void drawNeighbours(Graphics g2){

		for(Tile n : neighbours){

			g2.drawLine(getPosX() + getWidth()/2, getPosY() + getHeight()/2, n.getPosX() + n.getWidth()/2, n.getPosY() + n.getHeight()/2);
		}
	}

	//constructor must throw exception as sprite does so error can be passed up to being handled
	public Tile(String _name, BufferedImage _img) throws Exception{

		super(_name, _img);
	} 

	//get MD value
	public int getRabbitMD(){

		return rabbitMD;
	}

	//returns cheetahs MD value
	public int getCheetahMD(){

		return cheetahMD;
	}
}