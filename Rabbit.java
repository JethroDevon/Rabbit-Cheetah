class Rabbit extends Sprite{


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
		addState("RIGHT", 89, 97, getHeight(), getWidth(), 20, 0, 2, 0);
		addState("UP", 98, 105, getHeight(), getWidth(), 20, 0, 2, 90);
		addState("UP-RIGHT", 106, 113, getHeight(), getWidth(), 20, 0, 2, 45);
		addState("UP-LEFT", 114, 121, getHeight(), getWidth(), 20, 0, 2, 135);
		addState("LEFT", 122, 129, getHeight(), getWidth(), 20, 0, 2, 180);
		addState("DOWN", 130, 137, getHeight(), getWidth(), 0, 0, 0, 270);
		addState("DOWN-RIGHT", 138, 145, getHeight(), getWidth(), 20, 0, 2, 315);
		addState("DOWN-LEFT", 146, 153, getHeight(), getWidth(), 20, 0, 2, 225);

		//set the default state to start
		activateState("START");

	}
}