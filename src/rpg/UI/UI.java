package rpg.UI;

import rpg.core.GameSystem;
import rpg.core.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

//UserInterface is a kind of graphic object in the game , every graphic object should obtain a group of render method
public class UI implements InputProcessor {

	//Scene which UI in
	protected final Scene scene;
	//Previous Control Panel ( maybe a dialog , too
	protected InputProcessor previousControl;
	//fade effect
	protected long writeInterval = 5;
	protected long fadeInterval = 5;
	protected float alpha = 0;
	protected float fadeSpeed = 0.2f;
	
	public UI()
	{
		this.scene = GameSystem.getInstance().getScene();
	}
	
	//request controll from system
	public void requestControl()
	{
		//Store previous
		this.previousControl = Gdx.input.getInputProcessor();
		//and then add self
		Gdx.input.setInputProcessor(this);
	}
	
	//post a UI to the scene
	public void post()
	{
		requestControl();
		this.scene.getUIManager().pushUI(this);
	}
	//for renderer 's char by char output
	public long getWriteInterval()
	{
		return writeInterval;
	}
	public long getFadeInterval()
	{
		return fadeInterval;
	}
		
	//Tell renderer how to render background (Renderer will pass camera bound and width , height)
	public void render(SpriteBatch batch , int x , int y , int w , int h){}
	//Tell renderer how to render text
	public void render(SpriteBatch batch , char[] buffer , int x , int y , int w , int h){}
	
	//Close UI
	public void close()
	{
		Gdx.input.setInputProcessor(previousControl);
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
