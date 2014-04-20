package rpg.core;

import rpg.gameobject.GameObject;

import com.badlogic.gdx.utils.Array;


//Usage : This class will process all operation about adding object , removing object , adding collider , etc
public class SceneObjectManger {
	//Object ID
	private long ObjectID = 1; // ZERO is player
	
	//GameObject list
	private Array<GameObject> gameobjectlist;
	
	public Array<GameObject> getObjectlist()
	{
		if(gameobjectlist == null)
			gameobjectlist = new Array<GameObject>();
		return gameobjectlist;
	}
	//Scene obtain a method for the others to add object to it
	public void addObject(GameObject obj)
	{
		if(gameobjectlist == null)
			gameobjectlist = new Array<GameObject>();
		
		this.gameobjectlist.add(obj);
		this.ObjectID++;
	}
	
	
}
