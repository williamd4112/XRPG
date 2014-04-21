package rpg.gameobject;

import rpg.component.Animator;
import rpg.component.RigidBody;
import rpg.component.Sprite;

public class Character extends GameObject{	
			
	public Character(String Name, long ID, int cState, int x, int y){
		super(Name, ID, cState, x, y);
		
		//Initialize Components (Character's default components
		addComponent(new RigidBody(this , x , y));
		addComponent(new Sprite(Name , cState));
		addComponent(new Animator(4));
	}

	@Override
	public void update()
	{
		super.update();
	}
	
}
