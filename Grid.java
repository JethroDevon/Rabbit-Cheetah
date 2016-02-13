import java.awt.Graphics;

class Grid extends Sprite{

	//this nested array list will store a grid of nodes, this class will perform algorithmic operations
	//such as returning shortest path walks between two points 
	Tile[][] grid;

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

	//set cheetah manhattan distance value, when this is called the tiles nearest to the cheetah have a lower value
	//and the tiles further away have a higher value
	public void setCheetahMD( Cheetah _che){

		Tile temp;

		//all sprite objects must be created in try catch blocks
		try{

			//temp tile for storing tile colliding with cheetah, initalised because it has to be
			//but if named error can be used for debugging
			temp = new Tile("error", getFrame(5));

			//loops through tile array and returns tile that is colliding with cheetah
			//loops for each tile calling drawn name function
			for(int x = 0; x < grid.length; x++){
				for(int y = 0; y < grid[x].length; y++){

					if( _che.checkCollision(grid[x][y])){

						temp = grid[x][y];				
					}

					//taking advantage of this loop to clear the cheetahMD value to clear old
					//data
					grid[x][y].cheetahMD = 0;
				}
			}

		//initialising temp Tile in order to get a lower MD value than surounding without
		//it being initialised to ten
		temp.cheetahMD = 1;  

		//A depth first search from the tile colliding with cheetah will
		//increase the value of cheetah MD by ten each time, queue object
		//is initialised and a root tile is enqueued
		Queue q = new Queue();
		q.enqueue(temp);

		int tempInt = 0;

		while(!q.queueEmpty()){

			temp = (Tile) q.dequeue();

			tempInt += 10;
			for(int t = 0; t < temp.neighbours.size(); t++){

				if(temp.neighbours.get(t).cheetahMD == 0){

					temp.neighbours.get(t).cheetahMD = tempInt;
					q.enqueue(temp.neighbours.get(t));
				}
			}
		}

		}catch(Exception e){

			System.out.println("Could not create cheetah distance heurestic algorithm");
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

	//shows all tiles grid numbers 
	public void drawNumbers(Graphics gr2){

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

	//shows cheetahs manhattan distance
	public void drawCheetahMD(Graphics gr2){

		//loops for each tile calling drawn name function
		for(int x = 0; x < grid.length; x++){
			for(int y = 0; y < grid[x].length; y++){

				grid[x][y].drawString( gr2, "ch-dist:\n" + String.valueOf(grid[x][y].cheetahMD),  2, 40);
			}
		}
	}
}

//array list of arraylist help from this stack overflow url: http://stackoverflow.com/questions/25147799/java-arraylist-of-arraylist