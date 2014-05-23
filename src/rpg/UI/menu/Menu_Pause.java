package rpg.UI.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import rpg.action.ActionLibrary;
import rpg.core.GameSystem;
import rpg.core.Scene;
import rpg.factory.FontFactory;
import rpg.factory.TextureFactory;
import rpg.factory.FontFactory.FontType;

public class Menu_Pause extends Menu implements InputProcessor {
	
	private Texture texture;
	
	public Menu_Pause()
	{
		super();
		String[] options = {"Skill" , "Equipment" , "Item" , "Map" , "Save" , "Load"};
		
		this.background = "00-Pause";
		this.options = options;
		this.index = 2;
	}

	/*************Override Method************/
	@Override
	public void post()
	{
		super.post();
	}
	@Override
	//Tell renderer how to render background
	public void render(SpriteBatch batch , int x , int y , int w , int h)
	{
		//get font from factory
		BitmapFont text = FontFactory.getInstance().genFont(FontType.TITLE, h);
		
		int startY = (int) (y + h + (int)text.getLineHeight()*4*2 - (options.length - index - 2)*text.getLineHeight()*4);
		int focusY = y + h - (int)text.getLineHeight()*4*2;
		int curY = startY;
		int len = (int)text.getLineHeight()*4*options.length;
		
		for(int i = 0 ; i < options.length ; i++){
			if(i != index)
				text.draw(batch, options[i] , x + 64 , curY + len);
			curY -= (int)text.getLineHeight()*4;
		}
		curY = startY;
		for(int i = 0 ; i < options.length ; i++){
			if(i != index)
				text.draw(batch, options[i] , x + 64 , curY);
			curY -= (int)text.getLineHeight()*4;
		}
		for(int i = 0 ; i < options.length ; i++){
			if(i != index)
				text.draw(batch, options[i] , x + 64 , curY);
			curY -= (int)text.getLineHeight()*4;
		}
		texture = TextureFactory.getInstance().genSkin(background);
		
		batch.draw(texture, x, y, w, h);
		text.draw(batch, options[index], x + 96, focusY);
		
	}
	
	@Override
	public void close()
	{
		super.close();
		TextureFactory.getInstance().disposeTexture(background);
	}
	
	public void next() {
		if(index > 0){
			index--;
		}
		else
			index = options.length - 1;
	}
	
	public void back() {
		if(index < options.length - 1){
			index++;
		}
		else
			index = 0;
	}
	
	public void select() {
		switch(index){
		case 0:
		case 1:
		case 2:
			ActionLibrary.showMenu(new Object[]{"Item"});
			break;
		case 3:
		case 4:
		case 5:
		}
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		switch(keycode){
		case Input.Keys.ENTER:
			select();
			break;
		case Input.Keys.DOWN:
			next();
			break;
		case Input.Keys.UP:
			back();
			break;
		case Input.Keys.ESCAPE:
			close();
			break;
		}
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
