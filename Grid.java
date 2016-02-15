import java.awt.Graphics;

class Grid extends Sprite{

	//this nested array list will store a grid of nodes, this class will perform algorithmic operations
	//such as returning shortest path walks between two points 
	Tile[][] grid;

	//cheetah and rabbit tile are the tiles that are presently colliding with either the cheetah or rabbit, they are initialised with
	//getAnimalLocation() function
	Tile cheetahTile, rabbitTile;

	//kernel allows me to ckeck surrounding elements in a two dimensional array by adding plus or minus values
	//to a central tile value, used in a loop each surrounding tile will be returned, this data will be used for
	//several operations including initialising each tiles surrounding tile array.
	int[][] kernel = {{-1, -1},{ 0, -1},{ 1, -1},{-1, 0},{ 0, 0},{ 1, 0},{-1, 1},{ 0, 1},{ 1, 1}};

	//Constructor 
	public Grid() throws Exception{

		super("tiles", "data/tileIMGs.png", 4, 3);

		try{

			//the grid will be ten by twenty tiles.
			grid = new Tile[9][9];

			//nested for loops initialise each tile object with an image and different X and Y values, so it can be
			//drawn onto the screen as a backdrop however the tiles also double up as nodes
			for(int x = 0; x < 9; x++){
				for(int y = 0; y < 9; y++){

					//initialise new tile at 2d tile grid array position xy
					grid[x][y] = new Tile("grass", getFrame(0));

					//sets x and y positions based on width and height in relation to other 
					grid[x][y].setXY(x * getWidth(), y * getHeight());

					//assigns an id number for each tile counting up
					grid[x][y].number = x + (9 * y );
				}		
			}

			//initialise each tile with an instance of a surrounding tile on its array list known as 'neighbours'
			setNeighbours();

		}catch(Exception e){

			System.out.println("grid failed to construct " + e.toString());
		}
	}	

	//draws the grid onto a graphics object passed into args
	public void drawGrid(Graphics gr2){

		for(int x = 0; x < grid.length; x++){
			for(int y = 0; y < grid[x].length; y++){

				//draws graphics to pased graphics variable, each tile is being drawn here
				gr2.drawImage( grid[x][y].getFrame(0), grid[x][y].getPosX(), grid[x][y].getPosY(), grid[x][y].getWidth()-1, grid[x][y].getHeight()-1, null);
			}
		}
	}

	//this function will initialise cheetahTile and rabbitTile member objects of type tile, this way the class will always have
	//access to the tile the rabbit or cheetah is on.
	public void getAnimalLocation(Cheetah _che, Rabbit _rab){

			//loops through tile array and returns tile that is colliding with cheetah
			//loops for each tile calling drawn name function
			for(int x = 0; x < grid.length; x++){
				for(int y = 0; y < grid[x].length; y++){

					if( _che.checkCollision(grid[x][y])){

						cheetahTile = grid[x][y];				
					}

					if( _rab.checkCollision(grid[x][y])){

						rabbitTile = grid[x][y];				
					}
				}
			}
	}

	//A tile in args is designated as the root node and using a breadth first search, this function uses a queue data structure to initialise parent
	//tiles in the grid along a frontier expanding out 
	public void BFS(Tile _tile){

		//creating queue structure
		Queue q = new Queue();

		//enqueueing the first tile passed in
		q.enqueue(_tile);

		while(!q.queueEmpty()){

			_tile = (Tile) q.dequeue();

			for(int t = 0; t < _tile.neighbours.size(); t++){

				if(_tile.neighbours.get(t).parent == null){
				
					_tile.neighbours.get(t).parent = _tile;
					q.enqueue(_tile.neighbours.get(t));
				}
			}
		}
	}

	//uses the above Breadth First search function to return a linked list node
	//to the cheetah
	public Tile BFStoRabbit(boolean _t, Cheetah _che, Rabbit _rab){

		if( _t){

			//updates rabbitTile and cheetahTile to get newest tiles colliding with rabbit and cheetah
			getAnimalLocation( _che, _rab);

			//connects all nodes to the tile the cheetah is colliding with
			BFS(cheetahTile);

			System.out.println("returning rabbit tile");

			//returns the rabbit tile
			return rabbitTile;
		}

		System.out.println("returning temp tile");
		//returns location of a temp tile set to null

		Tile t = cheetahTile;
		t.parent = null;
		return t;
	}

	//this function simply loops through all nodes resetting the parents to null, this is so
	//fresh searches are not interfered with, this only goes ahead if true is passed into args
	public void resetParents(boolean _t){

		if(_t){

			//resets last initialisation ready for new values
			for(int x = 0; x < grid.length; x++){
				for(int y = 0; y < grid[x].length; y++){

					grid[x][y].parent = null;
				}
			}
		}
	}

	//resets MD values for rabbit abd cheetah
	public void resetMD(){

		//resets last initialisation ready for new values
		for(int x = 0; x < grid.length; x++){
			for(int y = 0; y < grid[x].length; y++){

				grid[x][y].cheetahMD = 0;
				grid[x][y].rabbitMD = 0;
			}
		}
	}

	//set cheetah manhattan distance value, when this is called the tiles nearest to the cheetah have a lower value
	//and the tiles further away have a higher value, this is done using a breadth first algorithm to create a frontier of nodes
	//each layer of the new frontier is initialised with an incrementing value, the effect is lower values in the center of a grid
	public void setCheetahMD(){

		try{

			//initialising Tile in order to get a lower MD value than surounding without
			//it being initialised to ten as well as surrounding tiles
			cheetahTile.cheetahMD = 1;  

			//A depth first search from the tile colliding with cheetah will
			//increase the value of cheetah MD by ten each time, queue object
			//is initialised and a root tile is enqueued
			Queue q = new Queue();

			//enqueueing the tile that colliedes with cheetah as the root of a breadth first search
			q.enqueue(cheetahTile);

			int tempInt = 0;

			while(!q.queueEmpty()){

				cheetahTile = (Tile) q.dequeue();

				tempInt += 10;
				for(int t = 0; t < cheetahTile.neighbours.size(); t++){

					if(cheetahTile.neighbours.get(t).cheetahMD == 0){

						cheetahTile.neighbours.get(t).cheetahMD = tempInt;
						q.enqueue(cheetahTile.neighbours.get(t));
					}
				}
			}

		}catch(Exception e){

			System.out.println("Problem setting cheetahs MD values");
		}

	}

	//  - rabbit version of the above code, not yet refactored to operate on both rabbit and cheetah, this will be-
	//set Rabbit manhattan distance value, when this is called the tiles nearest to the cheetah have a lower value
	//and the tiles further away have a higher value, this is done using a breadth first algorithm to create a frontier of nodes
	//each layer of the new frontier is initialised with an incrementing value, the effect is lower values in the center of a grid
	public void setRabbitMD(){

		try{

			//initialising Tile in order to get a lower MD value than surounding without
			//it being initialised to ten as well as surrounding tiles
			rabbitTile.rabbitMD = 1;  

			//A depth first search from the tile colliding with rabbit will
			//increase the value of rabbit MD by ten each time, queue object
			//is initialised and a root tile is enqueued
			Queue q = new Queue();

			//enqueueing the tile that collides with rabbit as the root
			q.enqueue(rabbitTile);

			int tempInt = 0;

			while(!q.queueEmpty()){

				rabbitTile = (Tile) q.dequeue();

				tempInt += 10;
				for(int t = 0; t < rabbitTile.neighbours.size(); t++){

					if(rabbitTile.neighbours.get(t).rabbitMD == 0){

						rabbitTile.neighbours.get(t).rabbitMD = tempInt;
						q.enqueue(rabbitTile.neighbours.get(t));
					}
				}
			}

		}catch(Exception e){

			System.out.println("Problem setting rabbit MD value");
		}
	}



	//uses the kernel to initialise each tiles array of surrounding tiles, this way each tile will know who his neighbourse are !
	//this is meant to only be used internaly however it is not private as it may be used when reseting the program
	public void setNeighbours(){

		//for each tile in the grid
		for(int x = 0; x < grid.length; x++){
			for(int y = 0; y < grid[x].length; y++){

				//for each surrounding tile barring self
				for(int k = 0; k < 9; k++){

						//initialises each integer with an address for neighbour, this is to avoid leaving the range of the grid
						//and being that bit tidier, overhead is not so important here as this is being called once in startup
						int nX = x + kernel[k][0];
						int nY = y + kernel[k][1];
					
					if((nY > -1) && (nX > -1) && (nY < 9)  && (nX < 9)){

						//adds first value at position k to central tiles x address and second to central tiles y address
						grid[x][y].neighbours.add(grid[nX][nY]);			
					}
				}	
			}
		}
	}

	//adds a little patch of sand to the background, the two arguments represent the x and y values to the top
	//left tile, the location of the next eight tiles is based off that one address, aimed to use the kernel array to initialise
	//the tiles out but they could not be in the right order without lots of copying and pasting
	public void addSandPatch(int _x, int _y){

		try{

			grid[_x][_y].replaceFrame( getFrame(3), 0);
			grid[_x+1][_y].replaceFrame( getFrame(6), 0);
			grid[_x+2][_y].replaceFrame( getFrame(1), 0);
			grid[_x][_y+1].replaceFrame( getFrame(7), 0);
			grid[_x+1][_y+1].replaceFrame( getFrame(5), 0);
			grid[_x+2][_y+1].replaceFrame( getFrame(8), 0);
			grid[_x][_y+2].replaceFrame( getFrame(4), 0);
			grid[_x+1][_y+2].replaceFrame( getFrame(9), 0);
			grid[_x+2][_y+2].replaceFrame( getFrame(2), 0);

				//can use the kernel here however
				for(int k = 0; k < 9; k++){
					
					//adds first value at position k to central tiles x address and second to central tiles y address
					grid[_x + 1 + kernel[k][0]][_y + 1 + kernel[k][1]].setName("sand"); 
					grid[_x + 1 + kernel[k][0]][_y + 1 + kernel[k][1]].tTorp = 5; 
				}		
		
		}catch(Exception e){

			System.out.println("Error making sandPatch!");
		}
		
	}

	//shows all connected tiles with arrows
	public void pointToParents( Graphics gr2){

		//loops for each tile calling drawn name function
		for(int x = 0; x < grid.length; x++){
			for(int y = 0; y < grid[x].length; y++){

				grid[x][y].setArrow();
				grid[x][y].drawArrow(gr2);
			}
		}
	}

	//shows all tiles grid numbers 
	public void drawNumbers( Graphics gr2){

		//loops for each tile calling drawn name function
		for(int x = 0; x < grid.length; x++){
			for(int y = 0; y < grid[x].length; y++){

				grid[x][y].drawString( gr2, String.valueOf(grid[x][y].number),  2,   10 );
			}
		}
	}
	
	//shows all tiles type names
	public void drawNames(Graphics gr2){

		//loops for each tile calling drawn name function
		for(int x = 0; x < grid.length; x++){
			for(int y = 0; y < grid[x].length; y++){

				grid[x][y].drawString( gr2, grid[x][y].getName(), 5, grid[x][y].getHeight() /3 );
			}
		}
	}

	//shows all tiles Torp value ( how much it slows down a tile to)
	public void drawTorp(Graphics gr2){

		//loops for each tile calling drawn name function
		for(int x = 0; x < grid.length; x++){
			for(int y = 0; y < grid[x].length; y++){

				grid[x][y].drawString( gr2, String.valueOf(grid[x][y].tTorp), 45, grid[x][y].getHeight() /3 );
			}
		}
	}

	//draws all tiles connected to each neighbour, this is soley to check if all is working accordingly
	public void drawConnections(Graphics gr2){

		//loops for each tile calling drawn name function
		for(int x = 0; x < grid.length; x++){
			for(int y = 0; y < grid[x].length; y++){

				grid[x][y].drawNeighbours(gr2);
			}
		}
	}

	//shows rabbit and cheetahs cheetahs manhattan distances
	public void drawMD(Graphics gr2){

		//loops for each tile calling drawn name function
		for(int x = 0; x < grid.length; x++){
			for(int y = 0; y < grid[x].length; y++){

				grid[x][y].drawString( gr2, "cheetah:",  2, 14);
				grid[x][y].drawString( gr2, "rabbit:" ,  2, 34);
				grid[x][y].drawString( gr2, String.valueOf(grid[x][y].cheetahMD),  2, 24);
				grid[x][y].drawString( gr2, String.valueOf(grid[x][y].rabbitMD),  2, 44);
			}
		}
	}
}

//array list of arraylist help from this stack overflow url: http://stackoverflow.com/questions/25147799/java-arraylist-of-arraylist
//this was useful for calculating the A* algorithm http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html