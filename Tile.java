class Tile extends Sprite{

	//this value is for how much this tile would slow a cheetah or rabbit down, default is zero, tType is the type of image to draw
	//for example one would be grass, two ould be beach, three would be an inside grase to beach curve etc
	int tTORP = 0, tType;

	//this array stores nearby tiles
	ArrayList<Tile> neighbours = new ArrayList()<Tiles>;

	//constructor must throw exception as sprite does so error can be passed up to being handled
	public Tile(String _name, BufferedImage _img) throws Exception{

		super(_name, _img);
	} 
}