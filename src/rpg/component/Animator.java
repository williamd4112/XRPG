package rpg.component;

import rpg.gameobject.GameObject;

//Usage : only handling to make a animation , not include control when stop when start
public class Animator implements Component {
	
	//Binding object
	private GameObject bind;
	
	//Animation info
	private int frame = 0;
	private int key = 0;
	private int frequency = 4;
	
	public Animator(int frequency)
	{
		//initialize animation info
		this.frequency = frequency; 
	}
	
	public Animator()
	{
		
	}
	
	public int getKey()
	{
		return key;
	}
	
	public void setFrequency(int freq)
	{
		this.frequency = freq;
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
	
	//Reset the animation key (ex : use at make attack , and some animation except movement
	public void reset()
	{
		frame = 0;
		key = 0;
	}

	@Override
	public void install(GameObject bind) {
		 this.bind = bind;
	}

	@Override
	public void update() 
	{
		//Check if bind has a rigid body
		//doAnimation();
	}

	@Override
	public void unintall() {
		// TODO Auto-generated method stub
		
	}
}
