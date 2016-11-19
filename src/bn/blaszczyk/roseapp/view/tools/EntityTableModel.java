package bn.blaszczyk.roseapp.view.tools;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import bn.blaszczyk.roseapp.model.Readable;
import bn.blaszczyk.roseapp.view.ThemeConstants;
import bn.blaszczyk.roseapp.view.inputpanels.FileInputPanel;

public class EntityTableModel <T extends Readable> implements TableModel, ThemeConstants {
	
	private enum ColType {
		ICON,
		MEMBER,
		ENTITY;
	}
	
	private class ColContent {
		private ColType colType;
		private int index;
		private Icon icon;
		
		public ColContent(ColType colType, int index)
		{
			this.colType = colType;
			this.index = index;
		}
		
		public ColContent()
		{
			this.colType = ColType.ICON;
		}
		public ColType getColType()
		{
			return colType;
		}
		public int getIndex()
		{
			return index;
		}
		public Icon getIcon()
		{
			return icon;
		}
		public void setIcon(Icon icon)
		{
			this.icon = icon;
		}
	}
	
	private final List<T> entites;
	private boolean empty;
	private Readable first;
	private final int buttonCount;
	private final List<ColContent> colContents = new ArrayList<>();
	
	
	public EntityTableModel(List<T> entities, int buttonCount)
	{
		this.entites = entities;
		this.empty = entities.isEmpty();
		for( int i = 0; i < buttonCount; i++)
			colContents.add(new ColContent() );
		if(!empty)
		{
			first = entities.get(0);
			for( String col : first.getTableCols().replaceAll(" ", "").split(";") )
				if(col.substring(0, 1).equalsIgnoreCase("m") )
					colContents.add(new ColContent(ColType.MEMBER, Integer.parseInt(col.substring(1))));
				else 
					if(col.substring(0, 1).equalsIgnoreCase("e") )
						colContents.add(new ColContent(ColType.ENTITY, Integer.parseInt(col.substring(1))));
		}
		this.buttonCount = buttonCount > 0 ? buttonCount : 0;
	}

	public T getEntity(int row)
	{
		return entites.get(row);
	}
	
	public void setButtonIcon(int columnIndex, Icon icon)
	{
		colContents.get(columnIndex).setIcon(icon);
	}
	
	@Override
	public int getRowCount()
	{
		return entites.size();
	}
	
	@Override
	public int getColumnCount()
	{
		return colContents.size();
	}
	
	@Override
	public String getColumnName(int columnIndex)
	{
		switch (colContents.get(columnIndex).getColType())	
		{
		case MEMBER:
			return first.getFieldName(colContents.get(columnIndex).getIndex());
		case ENTITY:
			return first.getEntityName(colContents.get(columnIndex).getIndex());
		default:
			return "";
		}		
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
//		Object o = first.getEntityValue(colContents.get(columnIndex).getIndex());
//		System.err.println(String.valueOf(o));

		switch (colContents.get(columnIndex).getColType())
		{
		case ICON:
			return Icon.class;
		case MEMBER:
			return first.getFieldValue(colContents.get(columnIndex).getIndex()).getClass();
		case ENTITY:
			return first.getEntityClass(colContents.get(columnIndex).getIndex());
		default:
			return null;
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return colContents.get(columnIndex).getColType().equals(ColType.ICON);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		switch (colContents.get(columnIndex).getColType())
		{
		case ICON:
			return colContents.get(columnIndex).getIcon();
		case MEMBER:
			Object o =  entites.get(rowIndex).getFieldValue(colContents.get(columnIndex).getIndex());
			if( o instanceof String && FileInputPanel.isFileName(o.toString()))
				return o.toString().substring( o.toString().lastIndexOf("/")+1);
			return o;
		case ENTITY:
			return entites.get(rowIndex).getEntityValue(colContents.get(columnIndex).getIndex());
		default:
			return null;
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
	}
	
	@Override
	public void addTableModelListener(TableModelListener l)
	{
	}
	
	@Override
	public void removeTableModelListener(TableModelListener l)
	{
	}

	public int getButtonCount()
	{
		return buttonCount;
	}
	
	public int getColumnWidth( int columnIndex )
	{
		if( getColumnClass(columnIndex) == String.class )
			return 7 * first.getLength1(colContents.get(columnIndex).getIndex());
		else if( getColumnClass(columnIndex) == BigDecimal.class )
			return 15 * first.getLength1(colContents.get(columnIndex).getIndex());
		else if( getColumnClass(columnIndex) == Icon.class )
			return BUTTON_WIDTH;
		else 
			return CELL_WIDTH;
	}

	
}