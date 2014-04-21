package rpg.core;

import rpg.component.PlayerControlPanel;
import rpg.gameobject.Character;

public class GameSystem {
	//only one instance
	private static GameSystem instance;
	
	//GameState enum
	public static enum GameState{
		NORMAL, //in normal gamescene
		PAUSE,  //in pause menu
		STOP    //in dialog
	};
	
	//member variable
	//Player : let all object can access present player
	//controller : current controlpanel
	//itembox : store player item
	private GameState state = GameState.NORMAL;
	private Character player;
	
	//Accessor
	//getPlayer : get current player
	public Character getPlayer()
	{
		return player;
	}
	//getState :
	public GameState getState()
	{
		return this.state;
	}

	//setters
	//setPlayer : set current player
   	public void setPlayer(Character player)
   	{
   		this.player = player;
   	}
   	//Stop : stop all object's automatically update (graphic excluded
   	public void stop()
   	{
   		this.state = GameState.STOP;
   	}
   	//Resume : recover all object's automatically update
   	public void resume()
   	{
   		this.state = GameState.NORMAL;
   	}
   	
	//External can access current system info by this
	public static GameSystem getInstance()
	{
		if(instance == null)
		{
			instance = new GameSystem();
			
			//create a new player
			instance.player = new Character("chr", 0, 1 , 64 , 64);
			instance.player.addComponent(new PlayerControlPanel());
	
			return instance;
		}
		else
			return instance;
	}
	

	

}
