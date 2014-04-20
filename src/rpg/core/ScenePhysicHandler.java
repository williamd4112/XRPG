package rpg.core;

import rpg.component.RigidBody;
import rpg.gameobject.GameObject;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

public class ScenePhysicHandler {
	//handling scene
	final Scene_Level scene;
	
	public ScenePhysicHandler(Scene_Level scene)
	{
		this.scene = scene;
	}
	
	public void collisionTest()
	{		
		Array<GameObject> objs = scene.objectManager.getObjectlist();
		TiledMap level = scene.getLevel();
		
		for(int i = 0 ; i < objs.size ; i++){
			RigidBody body = objs.get(i).getRigidBody();
			if(body != null){
				//collide with level (if collided will skip collide with the other gameobject)
				if(body.isPassible(level, body.getDestX(), body.getDestY())){
					//collide with characters
					for(int j = 0 ; j < objs.size ; j++ ){
						if(j != i){
							RigidBody testBody =  objs.get(j).getRigidBody();
							if(testBody != null){
								body.onCollision(testBody);
							}
						}
					}
				}

			}

		}
		
	}
}
