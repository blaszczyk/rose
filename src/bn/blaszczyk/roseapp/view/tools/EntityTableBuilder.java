package bn.blaszczyk.roseapp.view.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import bn.blaszczyk.roseapp.model.Readable;

public class EntityTableBuilder
{
	private final List<Icon> icons = new ArrayList<>();
	private final List<EntityTable.EntityAction> actions = new ArrayList<>();
	private List<? extends Readable> entities; 
	private int height;
	private int width;
	
	public EntityTableBuilder heigth(int heigth)
	{
		this.height = heigth;
		return this;
	}
	
	public EntityTableBuilder width(int width)
	{
		this.width = width;
		return this;
	}
	
	public EntityTableBuilder entities( Collection<? extends Readable> entities )
	{
		if(entities instanceof List)
			this.entities = (List<? extends Readable>) entities;
		else
		{
			List<Readable> tEntities = new ArrayList<>();
			tEntities.addAll(entities);
			this.entities = tEntities;
		}
		return this;
	}
	
	public EntityTableBuilder addButtonColumn(String iconFile, EntityTable.EntityAction action)
	{
		try
		{
			icons.add( new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("bn/blaszczyk/roseapp/resources/" + iconFile))) );
			actions.add(action);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}	
		return this;
	}
	
	public EntityTable build()
	{
		EntityTableModel tableModel= new EntityTableModel(entities, actions.size());
		EntityTable table = new EntityTable(tableModel, width, height);
		for(int i = 0; i < actions.size(); i++)
			table.setButtonColumn(i, icons.get(i), actions.get(i));
		return table;		
	}
	
	public JScrollPane buildInScrollPane()
	{
		return new JScrollPane(build());
	}
}