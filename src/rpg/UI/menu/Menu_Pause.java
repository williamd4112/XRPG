package rpg.UI.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import rpg.core.Scene;

public class Menu_Pause extends Menu implements MenuControlPanel , InputProcessor {
	
	public Menu_Pause(Scene scene)
	{
		super(scene);
		
		String[] options = {"Item" , "Equipment" , "Skill" , "Map" , "Save" , "Load"};
		
		this.options = options;
	}
	
	@Override
	public void postMenu()
	{
		//this.scene.getUIManager().addMenu(this);
	}

	@Override
	public void requestControl() {
		this.previousControl = Gdx.input.getInputProcessor();
		Gdx.input.setInputProcessor(this);
		
	}

	@Override
	public void next() {
		index = ( index < this.options.length - 1) ? index++ : 0; 
	}

	@Override
	public void select() {
		switch(index){
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		}
		
	}

	@Override
	public void check() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
