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


		/*	IGNORING STATE STRATEGY IN ORDER TO TEST CONDITION STRATEGY

		states for running in each of eight directions
		addState("RIGHT", 0, 7, getHeight(), getWidth(), 10, 0, 2, 0);
		addState("UP", 8, 15, getHeight(), getWidth(), 10, 0, 2, 270);
		addState("UP-RIGHT", 16, 23, getHeight(), getWidth(), 10, 0, 2, 315);
		addState("UP-LEFT", 24, 31, getHeight(), getWidth(), 10, 0, 2, 225);
		addState("LEFT", 56, 63, getHeight(), getWidth(), 10, 0, 2, 180);
		addState("DOWN", 32, 39, getHeight(), getWidth(), 10, 0, 2, 90);
		addState("DOWN-RIGHT", 40, 47, getHeight(), getWidth(), 10, 0, 2, 45);
		addState("DOWN-LEFT", 48, 55, getHeight(), getWidth(), 10, 0, 2, 135);

		//set the default state to start
		activateState("LEFT");

		*/

		addAngleCondition( 0, 45, 0, 7);
		addAngleCondition( 45, 90, 40, 47);
		addAngleCondition( 90, 135, 32, 39);
		addAngleCondition( 135, 180, 48, 55);
		addAngleCondition( 180, 225, 56, 63);
		addAngleCondition( 225, 270, 24, 31);
		addAngleCondition( 270, 315, 8, 15);
		addAngleCondition( 315, 360, 16, 23);

		path = new Stack();
	}


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

		Tile _temp = (Tile) path.pop();
		Tile present = _temp;

		while(!path.stackEmpty()){

			_temp = (Tile) path.pop();

			gr2.drawLine( present.getPosX() +30, present.getPosY() +30, _temp.getPosX() +30, _temp.getPosY()+30);

			present = _temp;
		}
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

		//while there is still a stack with different nodes on it to visit
		if(!path.stackEmpty()){

			if( !visitNeighbour( nextNode, _cheetahTile)){

				nextNode = (Tile) path.pop();
				//System.out.println(nextNode.number);
			}else{

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
			nextNode = (Tile) path.pop();
			//System.out.println("start node number is " + nextNode.number);
		}
	}

	
}