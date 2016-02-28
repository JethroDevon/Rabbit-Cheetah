import java.awt.Graphics;

class Cheetah extends Sprite{


	private Tile targetNode, originNode, nextNode;

	private Stack path;

	public Cheetah() throws Exception{

		//super class initialise extended sprite class with images from data folder, rabbit has 15 images across and 11 down, this is not a square amount
		//and care has to be given not to use blank frames in animations
		super("cheetah", "data/cheetah.png", 6, 11);

		//lion is a bit big, make smaller
		setWidth(getWidth()/2);
		setHeight(getHeight()/2);

		//overides default state to not include blank sprite frames
		addState("default", 0, 63, getHeight(), getWidth(), 0, 0, 0, 0);

		//adding a condition, if cheetah at angle between first two args, then loop
		//between frames in second two args
		addAngleCondition( 0, 45, 0, 7);
		addAngleCondition( 45, 90, 40, 47);
		addAngleCondition( 90, 135, 32, 39);
		addAngleCondition( 135, 180, 48, 55);
		addAngleCondition( 180, 225, 56, 63);
		addAngleCondition( 225, 270, 24, 31);
		addAngleCondition( 270, 315, 8, 15);
		addAngleCondition( 315, 360, 16, 23);

		//set a starting angle
		pollConditions("ANGLE");
		setVelocity(1);
		setmaxVelocity(10);
		setAcceleration(2);


		//initialises path for use, must be glbally accessed for repeated use. 
		path = new Stack();
	}

	//if nodes between cheetah and rabbit have been initialised with tiles in args in 'nextMove()'
	//then push each element on linked list to stack structure for use in path finding
	public void initPath(){

		if(targetNode != null && originNode != null){

			while(targetNode.number != originNode.number){

				path.push(targetNode);
				targetNode = targetNode.parent;	
			}
		}
	}

	//draws all tiles connected to each neighbour, this is soley to check if all is working accordingly
	public void drawPath(Graphics gr2){

		try{

			Stack path2 = path;

			//have to pass an image even fot a temp tile, passes a cheetah image 
			Tile present = (Tile) path2.pop();

			while(!path2.stackEmpty()){

				Tile _temp = (Tile) path2.pop();

				gr2.drawLine( present.getPosX() +30, present.getPosY() +30, _temp.getPosX() +30, _temp.getPosY()+30);

				present = _temp;
			}
		}catch(Exception e){

			System.out.println("Error drawring cheetah path");
		}
	}


	public void nextMove(Tile _rabbitTile, Tile _cheetahTile){

		//while there is still a stack with different nodes on it to visit
		if( !path.stackEmpty()){

			if( circularCollision( nextNode, 70)){

				nextNode = (Tile) path.pop();

			}else{			
	
				//sets cheetahs angle to point to next node
				pointTo(nextNode);	

				//moves cheetah one unit vector
				moveSprite();
			}
		
		}else{

			//initialise args for initPath
			targetNode = _rabbitTile;
			originNode = _cheetahTile;

			//loads adt stack with linked list passed over frem getting the cheetah tile
			initPath();		

			//sets the first target node for hte cheetah to travel to
			nextNode = (Tile) path.pop();
		}
	}

	
}