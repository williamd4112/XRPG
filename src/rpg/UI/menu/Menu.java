package rpg.UI.menu;

import com.badlogic.gdx.InputProcessor;

import rpg.core.Scene;

public class Menu {
	
	protected InputProcessor previousControl;
	protected Scene scene;
	protected int index = 0;
	protected String[] options;
	
	public Menu(Scene scene)
	{
		this.scene = scene;
	}
	
}
