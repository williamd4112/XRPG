package rpg.equipment;

import rpg.component.Component;
import rpg.gameobject.GameObject;

public class Equipment implements Component {
	
	//Equipment Type Declaration
	public static enum EquipmentType{
		WEAPON,
		ARMOR,
		ASSISTANT,
	}

	private String name;
	private String desc;
	private String icon;
	private EquipmentType type;
	
	//@param : Name - pass to find the corresponding data in .xml
	public Equipment(String name)
	{
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
		
	};
	
	
}
