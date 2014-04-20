package rpg.component;

import rpg.gameobject.GameObject;

//Usage : only handling to make a animation , not include control when stop when start
public class Animator implements Component {
	//Binding object
	//if has a rigidbody , animator should do animation with movement
	private GameObject bind;
	private RigidBody body;
	//Animation info
	private int frame;
	private int key;
	private int frequency;
	
	public Animator(int frequency)
	{
		//initialize animation info
		this.frame = 0; 
		this.key = 0;
		this.frequency = frequency; 
	}
	
	public int getKey()
	{
		return key;
	}
		
	//Component owner will use this to update self key , so that renderer will be able to get the refreshed key
	public void doAnimation()
	{
		if(frame > frequency){
			frame = 0;
			if(key < 3)
				key++;
			else
				key = 0;
		}
		else{
			frame++;
		}
	}

	@Override
	public void install(GameObject bind) {
		 this.bind = bind;
		 this.body = bind.getRigidBody();
	}

	@Override
	public void update() {
		//Check if bind has a rigid body
		if(body != null){
			if(body.moving() || key != 0)
				doAnimation();
		}
		else
			doAnimation();
	}

	@Override
	public void unintall() {
		// TODO Auto-generated method stub
		
	}
}
