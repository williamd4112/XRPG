package rpg.factory;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.badlogic.gdx.Gdx;

import rpg.exception.DefinitionError;
import rpg.item.Item;
import sun.org.mozilla.javascript.internal.ast.ThrowStatement;

public class ItemFactory {
	
	private static ItemFactory instance;
		
	public static ItemFactory getInstance()
	{
			if(instance == null)
				instance = new ItemFactory();
			return instance;
	}
	
	//search from file to find corresponding date for item 
	public Item createItem(String name) throws DefinitionError
	{
		String itemName = null;
		String itemIconPath = null;
		String itemDesc = null;
		
		try {
			//Build document
			SAXBuilder builder = new SAXBuilder();
			Document doc = new Document();
			
			doc = builder.build("bin/System/Item.xml");
			
			//Get root element
			Element root = doc.getRootElement();
			
			for(Element item : root.getChildren("Item"))
			{
				itemName = item.getAttributeValue("Name");
				if(itemName.equals(name)){
					itemIconPath = item.getAttributeValue("Icon");
					itemDesc = item.getAttributeValue("Desc");
					break;
				}
			}
				
						
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(itemName != null && itemIconPath != null && itemDesc != null){
			return new Item(itemName , itemIconPath , itemDesc);
		}
		else
			throw new DefinitionError(name);
	}
}
