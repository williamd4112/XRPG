package rpg.component;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Values;

import rpg.exception.DefinitionError;
import rpg.factory.ItemFactory;
import rpg.gameobject.GameObject;
import rpg.item.Item;

public class ItemBox implements Component{

	//Item container
	private ObjectMap<String , Item> itemBox;
	
	public ItemBox()
	{
		itemBox = new ObjectMap<String , Item>();
	}
	
	public ObjectMap<String , Item> getList()
	{
		return itemBox;
	}
	
	public void showAll()
	{
		if(itemBox.size < 1)
			return;
		for(Values it = itemBox.values() ; it.hasNext;){
			Item item = (Item) it.next();
			System.out.println("Item Box : " + item.getName() + " ; Count : " + item.getCount());
			System.out.println(item.getDesc());
		}
		
	}
	
	public void addItem(String name)
	{
		if(itemBox.containsKey(name)){
			itemBox.get(name).addCount();
		}
		else{
			try {
				itemBox.put(name, ItemFactory.getInstance().createItem(name));
			} catch (DefinitionError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void install(GameObject bind) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unintall() {
		// TODO Auto-generated method stub
		
	}


}
