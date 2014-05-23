package rpg.core;

import rpg.component.ItemBox;
import rpg.component.PlayerControlPanel;
import rpg.exception.DefinitionError;
import rpg.factory.ItemFactory;
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
	
	//member variables
	//Player : let all object can access present player
	private GameState state = GameState.NORMAL;
	private Character player;
	private Scene currentScene;
	
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
	//getScene :
	public Scene getScene()
	{
		return this.currentScene;
	}

	//setters
	//setPlayer : set current player
   	public void setPlayer(Character player)
   	{
   		this.player = player;
   	}
   	//
   	public void setScene(Scene scene)
   	{
   		this.currentScene = scene;
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
			instance.player = new Character("Hero", 0, 2 , 64 , 64);
			instance.player.addComponent(new PlayerControlPanel());
			instance.player.addComponent(new ItemBox());
			
			instance.player.getItemBox().addItem("Herb");
			instance.player.getItemBox().addItem("Herb");
			instance.player.getItemBox().addItem("Herb");
			instance.player.getItemBox().addItem("Red Potion");
			instance.player.getItemBox().showAll();
	
	
			return instance;
		}
		else
			return instance;
	}
	

	

}
