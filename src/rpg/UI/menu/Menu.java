package rpg.UI.menu;

import com.badlogic.gdx.InputProcessor;

import rpg.UI.UI;
import rpg.core.Scene;

public class Menu extends UI implements InputProcessor{

	//Menu default value (override if the specific menu need
	protected int index = 0;
	protected String background = "01-Dialog";
	protected String[] options;
	
	public Menu()
	{
		super();
	}
	
	@Override
	public void close()
	{
		super.close();
		this.scene.getUIManager().popUI();
	}
	
}
