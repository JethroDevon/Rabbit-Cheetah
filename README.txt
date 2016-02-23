
This program simulates the A* algorithm and will also own a little extra functionality to demonstrate some of the underlying algorithms.


on start of the program there will be a rabbit and a cheetah on a grassy plain, they may be randomly placed on the start of the program and they will 
wander about the plane untill the cheetah is close enough to the rabbit to 'see' it, at this point the cheeta will find the shortest path
to the rabbit and the rabbit will move in the opposite direction to get away, the rabbit is safe when it leaves the bounds of the screen and
 the rabbit is caught when the cheetah catches up with it.

Three java classes, Game, Main and Sprite are usefull classes belonging to a games library I was already developing for my software projects coursework.
Main in this case contains the main method to start the program and manages a few swing components set up to paint from a 'Game' objects paint method which
it also calls run thread on.

Main in this case does not contain the actual main method the Game class does, it was designed to paint Sprite objects efficiently using a double buffer blitting strategy,
 however the draw graphics function within it is called in a thread that paints each iteration of the program to screen to create an animated effect, this is a perfect 
environment for managing algorithms and entities and demonstrating the results.

			





a button will reset and randomise the background and position of the animals.


				      THE PLAIN (or grid):

The plain in which the cheetah and rabbit will be drawn in relation to will be a class called grid that manages a two dimensional array of
objects called tiles, tiles will essentially be nodes with a range of properties; their width and height,
 X and Y positions, torpor value (tTorp), an image, all baring tTorp allready belong to my sprite class and an array of the neighbouring tiles surrounding them.

Grid class will contain functions that cheetah will use to recieve a walk in the form of a stack of tile objects, cheetah class could instead contain
the member functions however it would need either a lot of calls to the grid class or an entire copy of it, it is more efficient in terms of computational
cylcles for the grid class to perform all search operations.

different terrain will slow or speed the animals up when they collide with it both in terms of actual sprite movement and the total cost of a path being
calculated, it would be interesting to see the relation between the cost of a tile that physically slows a sprite down and the time used up navigating 
around it.

Lines will be superimposed over the plain when a key is pressed to display all possible paths available or similar. <- review



					THE CHEETAH:


Cheeta will recieve a stack of tile objects, each will be used for its X and Y coordinates, 
as the cheetah collides with each one the the tile is popped of the stack and the next coordinate is found
this continues until the stack is empty and the cheetah has arrived at its destination.

as the rabbit moves the cheetah will update the new path every time it collides with another tile, this update could
occur less than once per move, this could simulate less agility from the cheetah.

a movement function in the cheetahs sprite class could limit the speed in which the cheetah can turn this could throw
the cheetah of its stack destination tiles and also simulate speed but poor agility, all variations of the program will be created or options to tweak these
options may be included.

The cheeta will contain a function to 'see' the rabbit, it will be a circular collision detector that will set the range of the cheetas vision by increasing 
or decreasing the diameter variable.

There will be a function to get the rabbits tileX and tileY position in relation to itself and recieve a stack of copies of tile objects that will represent each node
to the rabbits location.

Although cheetah needs a stack of tiles from the grid class so it can have access to the functions within it such as collisions, positional 
 it does not make sense the cheetah class owning an algorithm to find paths as this would be done with the grid class anyway
therefore

					THE RABBIT:

The rabbit will have functions that affect behaviour based on the proximity of the cheetah.

The Rabbit will know the angle the cheeta is in in relation to itself

collision with the cheetah will be the end of the rabbit.

The rabbit will suddenly change direction as the cheetah catches up to attempt to outmanouver the cheetah,
the rabbit will be slower but more agile and the cheeta is faster but less agile.


Sprites were under free liscence from http://www.reinerstilesets.de, liscence is http://www.reinerstilesets.de/lizenz/
