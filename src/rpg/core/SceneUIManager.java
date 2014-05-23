package rpg.core;

import java.util.Arrays;
import java.util.Stack;

import rpg.UI.UI;
import rpg.UI.dialog.Dialog;
import rpg.UI.menu.Menu;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

//Renderer will render all UI in stack , but this will only process top of stack
public class SceneUIManager {
	//UI Container
	protected Stack<UI> UIStack;
	protected int index = 0;
	
	//Word displaying 
	protected char[] buffer;
	protected String prePage;
	protected long lastTime;
	protected int key = 0 ;
	
	public char[] getBuffer()
	{
		return buffer;
	}
	
	public Stack<UI> getStack()
	{
		return UIStack;
	}
	
	public boolean isEmptyStack()
	{
		if(UIStack != null)
			return UIStack.isEmpty();
		else
			return true;
	}
	
	public SceneUIManager()
	{
		lastTime = TimeUtils.nanoTime();
		buffer = new char[4096];
	}
	
	public void processUI()
	{
		if(UIStack != null){
			if(!UIStack.isEmpty()){
				//If there is UI in stack , stop the game ( but in the future will add new class DynamicUI 
				GameSystem.getInstance().stop();
				if(UIStack.peek().getClass().getName().equals("rpg.UI.dialog.Dialog")){
					processDialog(UIStack.peek());
				}
				else if(UIStack.peek().getClass().getName().equals("rpg.UI.menu.Menu_Pause")){
					processMenu(UIStack.peek());
				}
			}
			else{
				GameSystem.getInstance().resume();
			}
		}
	}
	
	public void processMenu(UI menu)
	{
		
	}
	
	//Process dialog and word buffer
	public void processDialog(UI ui)
	{		
		//dialogbox is empty or travered all
		if(UIStack == null)
			return;
		
		if(UIStack.isEmpty())
			return;
			
		Dialog dialog = (Dialog) ui;
		
		//All pages have been shown
		if(dialog.getCurrentPage() == null){
			UIStack.pop();
			return;
		}
		
		//if change page
		if(prePage != dialog.getCurrentPage()){
			//Reset buffer and key
			key = 0;
			Arrays.fill(buffer, '\0');
		}
		
		//Process string into buffer
		long currentTime = TimeUtils.nanoTime();
		long dt = currentTime - lastTime;
		long writeInterval = dialog.getWriteInterval();
		
		if(dt > writeInterval){
			if(key < dialog.getCurrentPage().length()){
				dialog.getCurrentPage().getChars(0, key, buffer, 0);
				key++;
			}
		}
		prePage =  dialog.getCurrentPage();

	}

	//Scene obtain a method for the others to add a dialog to it
	public void pushUI(UI ui)
	{
		if(UIStack == null)
			UIStack = new Stack<UI>();
		UIStack.push(ui);
	}
	public void popUI()
	{
		if(UIStack != null)
			UIStack.pop();
	}
}
