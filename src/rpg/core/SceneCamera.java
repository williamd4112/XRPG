package rpg.core;

import com.badlogic.gdx.graphics.OrthographicCamera;

import rpg.gameobject.GameObject;


public class SceneCamera extends OrthographicCamera  {
	
	private GameObject target;
	
	public SceneCamera()
	{	
		super();
	}

	public void bind(GameObject target)
	{
		this.target = target;
	}
	
	public void follow()
	{
		if(target != null){
			this.position.set(target.getX(), target.getY(), 0);
			this.update();
		}
	}
}
