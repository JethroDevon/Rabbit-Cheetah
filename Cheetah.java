class Cheetah extends Sprite{

	public Cheetah() throws Exception{

		//super class initialise extended sprite class with images from data folder, rabbit has 15 images across and 11 down, this is not a square amount
		//and care has to be given not to use blank frames in animations
		super("cheetah", "data/cheetah.png", 6, 11);

		//lion is a bit big, make smaller
		setWidth(getWidth()/2);
		setHeight(getHeight()/2);

		//overides default state to not include blank sprite frames
		addState("default", 0, 63, getHeight(), getWidth(), 0, 0, 0, 0);

		//states for running in each of eight directions
		addState("RIGHT", 0, 7, getHeight(), getWidth(), 10, 0, 2, 0);
		addState("UP", 8, 15, getHeight(), getWidth(), 10, 0, 2, 270);
		addState("UP-RIGHT", 16, 23, getHeight(), getWidth(), 10, 0, 2, 315);
		addState("UP-LEFT", 24, 31, getHeight(), getWidth(), 10, 0, 2, 225);
		addState("LEFT", 56, 63, getHeight(), getWidth(), 10, 0, 2, 180);
		addState("DOWN", 32, 39, getHeight(), getWidth(), 10, 0, 2, 90);
		addState("DOWN-RIGHT", 40, 47, getHeight(), getWidth(), 2, 0, 2, 45);
		addState("DOWN-LEFT", 48, 55, getHeight(), getWidth(), 10, 0, 2, 135);

		//set the default state to start
		activateState("DOWN-RIGHT");
	}

}