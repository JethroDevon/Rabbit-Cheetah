import java.awt.Graphics;

class Rabbit extends Sprite{

	Tile present, nextTile;

	public Rabbit() throws Exception{

		//super class initialise extended sprite class with images from data folder, rabbit has 15 images across and 11 down, this is not a square amount
		//and care has to be given not to use blank frames in animations
		super("rabbit", "data/rabbit.png", 11, 15);

		//sprite might be too big make bit smaller
		setWidth(getWidth()/2);
		setHeight(getHeight()/2);

		/*/overides default state to not include blank sprite frames
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
		*/


		addAngleCondition( 0, 45, 0, 7);
		addAngleCondition( 45, 90, 40, 47);
		addAngleCondition( 90, 135, 32, 39);
		addAngleCondition( 135, 180, 48, 55);
		addAngleCondition( 180, 225, 56, 63);
		addAngleCondition( 225, 270, 24, 31);
		addAngleCondition( 270, 315, 8, 15);
		addAngleCondition( 315, 360, 16, 23);
	}

	//rabbit needs present tile passed in on initialisation
	public void initRabbit( Tile _present){

		present = _present;
		visitNeighbour();
	}

	//tells the sprite to go in any of the eight directions that its surrounding nodes are in
	public void visitNeighbour(){

		int temp = (int) Math.random() * 9;
		nextTile = present.neighbours.get(1);
		System.out.println(temp + " " + nextTile.number);
	}

	//this function takes a tile as an argument, the rabbit will move to
	//its neighbour, the idea is to pass the presently colliding tile to 
	//next move
	public void nextMove(Tile _present){

		present = _present;
		visitNeighbour();

		if( circularCollision( nextTile, 50)){

			visitNeighbour();

			//set angle to be adjacant to tile in arguments
			pointTo(nextTile);

			//set a starting angle
			pollConditions("ANGLE");
		}else{
			

			setVelocity(1);//set angle to be adjacant to tile in arguments
			pointTo(nextTile);
			setmaxVelocity(6);
			setAcceleration(2);
		}



		moveSprite();
	}
}