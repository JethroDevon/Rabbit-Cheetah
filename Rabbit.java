import java.awt.Graphics;

class Rabbit extends Sprite{

	//this is the tile the rabbit is presently on
	Tile present;

	boolean newTile = false;

	public Rabbit() throws Exception{

		//super class initialise extended sprite class with images from data folder, rabbit has 15 images across and 11 down, this is not a square amount
		//and care has to be given not to use blank frames in animations
		super("rabbit", "data/rabbit.png", 11, 15);

		//sprite might be too big make bit smaller
		setWidth(getWidth()/2);
		setHeight(getHeight()/2);

		//overides default state to not include blank sprite frames
		addState("default", 0, 152, getHeight(), getWidth(), 0, 0, 0, 0);

		//the animation is of a stationary rabbit that is sitting down and rotating, will do for start
		addState("START", 0, 9, getHeight(), getWidth(), 0, 0, 0, 0);

		//states for running in each of eight directions
		addState("RIGHT", 89, 97, getHeight(), getWidth(), 8, 0, 2, 180);
		addState("UP", 98, 106, getHeight(), getWidth(), 8, 0, 2, 90);
		addState("UP-RIGHT", 107, 115, getHeight(), getWidth(), 8, 0, 2, 135);
		addState("UP-LEFT", 116, 124, getHeight(), getWidth(), 8, 0, 2, 225);
		addState("LEFT", 146, 152, getHeight(), getWidth(), 8, 0, 2, 0);
		addState("DOWN", 119, 127, getHeight(), getWidth(), 8, 0, 0, 270);
		addState("DOWN-RIGHT", 128, 136, getHeight(), getWidth(), 7, 0, 2, 45);
		addState("DOWN-LEFT", 137, 145, getHeight(), getWidth(), 8, 0, 2, 135);

		//set the default state to start
		activateState("DOWN-RIGHT");

	}

		//tells the sprite to go in any of the eight directions that its surrounding nodes are in
	public boolean visitNeighbour(Tile _neighbour, Tile _present){

		if(checkCollision(_neighbour)){

			switch(_present.number - _neighbour.number){

			case 11 :

				activateState("UP-LEFT");
				break;

			case 10:

				activateState("UP");
				break;

			case 9:

				activateState("UP-RIGHT");
				break;

			case 1:

				activateState("LEFT");
				break;

			case -1:

				activateState("RIGHT");
				break;

			case -9:

				activateState("DOWN-LEFT");
				break;

			case -10:

				activateState("DOWN");
				break;

			case -11:

				activateState("DOWN-RIGHT");
				break;

			default:

				break;
		}
			return true;
		}else{

			return false;
		}
	}

	public void nextMove(Tile _rabbitTile, Tile _cheetahTile){

		if(visitNeighbour( present =_rabbitTile.neighbours.get((int)Math.random() * 9), _rabbitTile) && newTile == true){

			newTile = false;
		}else{

			newTile = visitNeighbour( present, _rabbitTile);
		}

		moveSprite();
	}
}