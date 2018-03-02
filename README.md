# SkyWars
A university project implemented in 2016 to demonstrate design patterns.

Skywars in a turn based game and the goal is to destroy all the enemies ships.
The movement is controlled by the Move button and it is random.
You can undo and redo all actions using the respective buttons. 

The following paterns were used:

* Factory is used to create a variable number of the three different types of enemies.

* Command pattern is used to execute the three different events that the master ship can cause: 
Movement, Creation of new enemies, Removal of dead enemies.The CommandManager class is used to hold all the command with the order they are executed using a Stack as a container where commands are inserted and removed according to the 
last-in first-out principle.

* Observer is used by the MasterSpaceShip class which is the observable object and by the EnemyShip 
class which is the observer. 

* Strategy is used to change the behaviour(Offensive or Defensive) of master ship at runtime.


A [runnable JAR file](https://raw.githubusercontent.com/dfc747/SkyWars/master/skywars.jar) is included for demonstration.


![SkyWars](https://raw.githubusercontent.com/dfc747/SkyWars/master/skywars.png)
