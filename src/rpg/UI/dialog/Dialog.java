package rpg.UI.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import rpg.UI.UISkin;
import rpg.action.Action;
import rpg.controller.ControlPanel;
import rpg.core.GameSystem;
import rpg.core.Scene;
import rpg.texturefactory.TextureFactory;

public class Dialog implements InputProcessor {
	
	//Previous Control Panel ( maybe a dialog , too
	protected InputProcessor previousControl;
	//Scene which dialog in
	protected Scene scene;
	//usually use default 
	protected Texture background;
	//store the dialog page in string (Scene will process this string , analyze \n)
	protected Array<String> context;
	//head picture (optional)
	protected Texture head;
	//body picture (optional)
	protected Texture body;
	//context index
	protected int index = 0;
	//render will char by char write out the context , this is speed
	protected long writeInterval = 5;
	//fade in , fade out speed;
	protected long fadeInterval = 5;
	//dialog left bottom corner (usually default)
	protected int x = 20;
	protected int y = 20;
	//width , height will depend on screen width and height
	protected int width;
	protected int height;
	
	public long getWriteInterval()
	{
		return writeInterval;
	}
	
	public Dialog(Scene scene)
	{
		this.context = new Array<String>();
		//this.background = TextureFactory.getInstance().genSkin("01-Dialog");
		this.scene = scene;
		
	}
	//reset page
	public void reset()
	{
		index = 0;
	}
	//post a dialog to the scene
	public void postDialog()
	{
		this.scene.getUIManager().addDialog(this);
	}
	
	//request controll from system
	public void requestControl()
	{
		//Store previous
		this.previousControl = Gdx.input.getInputProcessor();
		//and then add self
		Gdx.input.setInputProcessor(this);
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
			closeDialog();
		}
	}
	
	//Close dialog
	public void closeDialog()
	{
		System.out.println("back");
		Gdx.input.setInputProcessor(previousControl);
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
