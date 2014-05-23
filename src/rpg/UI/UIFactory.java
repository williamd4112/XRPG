package rpg.UI;

import rpg.UI.dialog.Dialog;
import rpg.UI.menu.Menu;
import rpg.UI.menu.Menu_Item;
import rpg.UI.menu.Menu_Pause;
import rpg.core.Scene;
import rpg.exception.DefinitionError;

public class UIFactory {
	private static UIFactory instance;
	
	public static UIFactory getInstance()
	{
		if(instance == null)
			instance = new UIFactory();
		return instance;
	}
		
	//Create dialog only can use this method
	public Dialog createDialog(Scene location , String type , String[] context) throws DefinitionError
	{
		if(type.equals("Normal")){
			Dialog dlg = new Dialog();
			for(String page : context){
				dlg.addPage(page);
			}
			return dlg;
		}
		else{
			throw new DefinitionError(type);
		}
	}
	
	//Create menu only can use this method
	public Menu createMenu(String type) throws DefinitionError
	{
		if(type.equals("Pause")){
			return new Menu_Pause();
		}
		else if(type.equals("Item")){
			return new Menu_Item();
		}
		
		System.err.println("No such type of menu.");
		return null;
	}
	
}
