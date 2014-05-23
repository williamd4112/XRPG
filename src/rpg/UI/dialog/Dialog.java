package rpg.UI.dialog;

import rpg.UI.UI;
import rpg.core.Scene;
import rpg.factory.FontFactory;
import rpg.factory.TextureFactory;
import rpg.factory.FontFactory.FontType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

//Remember , all class except renderer only hold non-graphical data

public class Dialog extends UI implements InputProcessor {

	//store the dialog page in string (Scene will process this string , analyze \n)
	protected Array<String> context;
	//Background texture
	protected String background = "01-Dialog";
	//head picture (optional)
	protected String head;
	//body picture (optional)
	protected String body;
	//Font
	protected String font = "AGaramondPro-Regular.otf";
	//context index
	protected int index = 0;
	
	public Dialog()
	{
		super();
		this.context = new Array<String>();
		
	}
	
	/*************Override Method************/
	//post to scene
	@Override
	public void post()
	{
		super.post();
		reset();
	}
	@Override
	//Tell renderer how to render background
	public void render(SpriteBatch batch , int x , int y , int w , int h)
	{
		x = x + 32;
		y = y + 32;
		w = w - 64;
		h /= 3;
		Color c = batch.getColor();		
		batch.setColor(c.r, c.g ,c.b, 0.5f);
		batch.draw(TextureFactory.getInstance().genSkin(background) , x , y , w , h);
		batch.setColor(c.r, c.g ,c.b, 1.0f);
	}
	@Override
	//Tell renderer how to render text
	public void render(SpriteBatch batch , char[] buffer , int x , int y , int w , int h)
	{
		BitmapFont text = FontFactory.getInstance().genFont(FontType.DIALOG ,h);
		x = x + 32;
		y = y + 32;
		w = w - 64;
		h /= 3;
		batch.setColor(Color.WHITE);
		text.drawWrapped(batch, String.valueOf(buffer), x + 15 , y + h - 15, w);
	}
	
	/*************Self Method**************/
	//reset page
	public void reset()
	{
		index = 0;
	}
	//to add new context
	public void addPage(String text)
	{
		context.add(text);
	}
	
	//get current page
	public String getCurrentPage()
	{
		//System.out.println(context.get(index).indexOf("\n") + " ; " + context.get(index).length());
		if(index == context.size){
			return null;
		}
		else{
			return context.get(index);
		}
	}
	
	//increment index to go to next page
	public void nextPage()
	{
		index++;
		//Over the bound
		if(index == context.size){
			close();
		}
	}

	/***************Control Panel***************/
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		switch(keycode){
		case Input.Keys.ENTER:
			//System.out.println(index);
			getCurrentPage();
			nextPage();
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
