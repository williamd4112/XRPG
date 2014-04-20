package rpg.component;

import rpg.gameobject.GameObject;

public interface Component {
	//A component must refer some info from binding object in install
	public void install(GameObject bind);
	
	//Binding object will call every component it has update along with itself
	public void update();
	
	//When disable from binding object
	public void unintall();


	
}
