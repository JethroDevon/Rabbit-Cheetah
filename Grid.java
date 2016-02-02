//array list of arraylist help from this stack overflow url: http://stackoverflow.com/questions/25147799/java-arraylist-of-arraylist

class Grid extends Sprite{

	//this nested array list will store a grid of nodes, this class will perform algorithmic operations
	//such as returning shortest path walks between two points 
	Tiles[][] grid;

	//Constructor 
	public Grid() throws Exception{

		try{

			//the grid will be ten by twenty tiles.
			grid = new Tiles[10][20];

			//nested for loops initialise each tile object with an image and different X and Y values, so it can be
			//drawn onto the screen as a backdrop however the tiles also double up as nodes
			for(int x = 0; x < 10; x++){
				for(int y = 0; y < 20; y++){

					//initialise new tile at 2d tile grid array position xy
					grid[x][y] = new Tile();
				}		
			}


		}catch(Exception e){

			System.out.println("grid failed to construct");
		}
	}	
}