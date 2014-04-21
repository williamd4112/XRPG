package rpg.core;

import java.util.Arrays;
import java.util.Stack;

import rpg.UI.dialog.Dialog;
import rpg.UI.menu.Menu;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class SceneUIManager {
	//Dialog container 
	protected Array<Dialog> dialogBox;
	protected Stack<Menu> menuBox;
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
	
	public Array<Dialog> getDialogBox()
	{
		return dialogBox;
	}
	
	public SceneUIManager()
	{
		lastTime = TimeUtils.nanoTime();
		buffer = new char[4096];
	}
	
	//Menu will use this method to add menu
	public void addMenu(Menu menu)
	{
		if(this.menuBox == null)
			this.menuBox = new Stack<Menu>();
		this.menuBox.add(menu);
	}
	
	public void removeMenu()
	{
		if(this.menuBox != null)
			this.menuBox.pop();
	}
	
	//Process dialog (if any dialog in the box , the game will stop
	public void processDialog()
	{
		//dialogbox not yet init
		if(dialogBox == null)
			return;
		
		//dialogbox is empty or travered all
		if(dialogBox.size < 1 || index >= dialogBox.size){
			dialogBox.clear();
			index = 0;
			GameSystem.getInstance().resume();
			return;
		}
			
		//if occur some error
		if(dialogBox.get(index) == null){
			index++;
			return;
		}
		else
			//Set up the game state
			GameSystem.getInstance().stop();
		
		if(dialogBox.get(index).getCurrentPage() == null){
			System.out.println("remove");
			//remove current dialog
			dialogBox.removeIndex(index);
			//increment index
			index++;
			//this dialog done , check if there is a next dialog in box
			System.out.println(dialogBox.size);
			return;
		}
		
		//if change page
		if(prePage != dialogBox.get(index).getCurrentPage()){
			//Reset buffer and key
			key = 0;
			Arrays.fill(buffer, '\0');
		}
		
		//Process string into buffer
		long currentTime = TimeUtils.nanoTime();
		long dt = currentTime - lastTime;
		long writeInterval = dialogBox.get(index).getWriteInterval();
		
		if(dt > writeInterval){
			if(key < dialogBox.get(index).getCurrentPage().length()){
				dialogBox.get(index).getCurrentPage().getChars(0, key, buffer, 0);
				key++;
			}
		}
		prePage = dialogBox.get(index).getCurrentPage();

	}

	//Scene obtain a method for the others to add a dialog to it
	public void addDialog(Dialog dialog)
	{
		if(dialogBox == null)
			dialogBox = new Array<Dialog>();
		
		this.dialogBox.add(dialog);
	}
	
	//Scene will use this method when dialog is done
	public void removeDialog(Dialog dialog)
	{
		this.dialogBox.removeValue(dialog, true);
	}
}
