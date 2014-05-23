package rpg.UI.menu;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectMap.Values;

import rpg.alogrithm.Alogrithm;
import rpg.core.GameSystem;
import rpg.core.Scene;
import rpg.factory.FontFactory;
import rpg.factory.TextureFactory;
import rpg.factory.FontFactory.FontType;
import rpg.item.Item;

public class Menu_Item extends Menu implements InputProcessor{
	//Set when render , dipose when close
	private Texture texture;
	
	//Indicate which option is selected
	private int index = 0;
	
	public Menu_Item() {
		super();
		
		String[] options = {"Items"};
		this.options = options;
	}
	
	//Move cursor to next item
	public void next()
	{
		index++;
		
		if(index >= GameSystem.getInstance().getPlayer().getItemBox().getList().size)
			index = 0;
	}
	
	public void last()
	{
		index--;
		
		if(index < 0)
			index = GameSystem.getInstance().getPlayer().getItemBox().getList().size - 1;
	}
	
	@Override
	public void render(SpriteBatch batch , int x , int y , int w , int h)
	{
		texture = TextureFactory.getInstance().genSkin(background);
		BitmapFont text = FontFactory.getInstance().genFont(FontType.DIALOG ,h);
		
		int nx = x + w/3;
		int ny = y + 32;
		int nw = w - w/3 - 32;
		int nh = h - 64;
		
		alpha = Alogrithm.lerp(alpha , 0.6f, 0.1f);
		
		Color c = batch.getColor();	
		batch.setColor(c.r, c.g ,c.b , alpha);
			batch.draw(texture, x + w/3, y + 32  , w - w/3 - 32 , h - 64);
		batch.setColor(c.r, c.g ,c.b , alpha + 0.2f);
			int row = 0;
			int ix = x + w/3 + 10;
			int iy = (int) (ny + nh - 32 - 10 - row*(text.getLineHeight() + 10));
			int iw = nw - 20 ;
			int ih = (int) (text.getLineHeight() + 5);
			
			for(Values<Item> it = GameSystem.getInstance().getPlayer().getItemBox().getList().values() ; it.hasNext;){
				Item item = (Item) it.next();
				Texture icon = TextureFactory.getInstance().genIcon(item.getIconPath());
				
				iy = (int) (ny + nh - 32 - 10 - row*(text.getLineHeight() + 10));
				
				batch.draw(texture , ix , iy , iw , ih);
				batch.draw(icon , ix + 5 , iy , text.getLineHeight() , text.getLineHeight());
				
				if(index == row)
					text.setColor(Color.CYAN);
				else
					text.setColor(Color.WHITE);
				
				text.drawWrapped(batch, item.getName(), ix + text.getLineHeight() + 15 , iy + text.getLineHeight() , iw , BitmapFont.HAlignment.LEFT);
				text.drawWrapped(batch, String.valueOf(item.getCount()), ix + text.getLineHeight() + 15 , iy + text.getLineHeight()  ,iw - (text.getLineHeight() + 15)*2 , BitmapFont.HAlignment.RIGHT);
				row++;
			}
			
			int focus = (int) (ny + nh - 32 - 10 - index*(text.getLineHeight() + 10));
			batch.setColor(1.0f, 0.0f, 0.0f, Alogrithm.lerp(0.0f, 1.0f, 0.1f , 0.0f));
			batch.draw(texture , ix , focus , iw , ih);
			
		batch.setColor(c.r, c.g ,c.b, 1.0f);
	}
	
	@Override
	public void close()
	{
		super.close();
		
		//Call factory to clean non-often use resource
		TextureFactory.getInstance().disposeTexture(background);
	}
	
	@Override
	public boolean keyDown(int keycode)
	{
		switch(keycode){
		case Input.Keys.DOWN:
			next();
			break;
		case Input.Keys.UP:
			last();
			break;
		case Input.Keys.ESCAPE:
			close();
			break;
		}
		return false;
	}
	

}
