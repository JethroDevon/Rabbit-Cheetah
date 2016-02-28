import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;


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

	//this tile will be initialised as the parent tile of another, this is a pointer to another node and when the parent tile is passed to a cheetah
	//or rabbit object in the argumetns of a "getPath()" function, then the animal well essentially have a linked list each containing data as to the
	//location of each tile and thus a path to the other animal
	public Tile parent = null;

	//set to true if visited else false and not visited
	public boolean visited = false; 

	//constructor must throw exception as sprite does so error can be passed up to being handled
	public Tile(String _name, BufferedImage _img) throws Exception{

		//super takes the tile image as an argument
		super(_name, _img);
	} 

	//sets the deltaX and Y values for moving each tile, not real existing values, just in theory
	public void deltaMove( int _dx, int _dy){

		setXY( getPosX() - _dx, getPosY() - _dy);
	}

	//various search methods will be used to point tiles to other tiles, if a parent tile is returned
	//then it will contain a pointer to another tile that contains another pointer and so on, this is 
	//to give either rabbit or cheeta a linked list of all the nodes of the shortest path between them
	public Tile returnParent(){

		return parent;
	}

	//draws a line from the center of itself to the center of each neighbouring tile, for debuging abd possible demonstrations
	//takes a graphics object as an argument
	public void drawNeighbours( Graphics g2){

		for(Tile n : neighbours){

			g2.drawLine(getPosX() + getWidth()/2, getPosY() + getHeight()/2, n.getPosX() + n.getWidth()/2, n.getPosY() + n.getHeight()/2);
		}
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

