package bn.blaszczyk.rose.creators;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import bn.blaszczyk.rose.MetaData;
import bn.blaszczyk.rose.model.*;
import bn.blaszczyk.roseapp.model.RelationType;

public class SQLCreator {
	
	public static void create(List<Entity> entities, MetaData metadata)
	{
		DBType dbType = DBType.getType(metadata.getDbtype());
		String fullpath = metadata.getSqlpath() + "createtables.sql";
		File file = new File(fullpath);
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		try(FileWriter writer = new FileWriter(file))
		{
			writer.write("--createtables.sql\n"
					+ "--generated by rose\n\n");
			
//			if(metadata.getDbname() != null)
//				writer.write("use " + metadata.getDbname() +"\ngo\n\n" );
			for(int i = entities.size() - 1; i >= 0; i-- )
			{
				switch(dbType)
				{
				case MYSQL:
//					writer.write("if exists (select * from information_schema.tables where table_schema = '" 
//								+ metadata.getDbname() + "' and table_name = '" + entities.get(i).getClassname() + "')\n");
					break;
				}
				writer.write("drop table " + entities.get(i).getSimpleClassName() + ";\n");
			}
			for(Entity entity : entities)
			{
				createTable(entity, metadata, dbType, writer);
				for(EntityField entityField : entity.getEntityFields() )
					createManyToManyTable(entityField, writer);
			}
			System.out.println( "File created: " + fullpath);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static String getManyToManyTableName( EntityField field  )
	{
		String format = "%s_%s";
		if( field.getName().compareTo(field.getCounterName()) < 0)
			return String.format(format, field.getCapitalName(), field.getCounterCapitalName());
		else
			return String.format(format, field.getCounterCapitalName(), field.getCapitalName());
	}
	
	
	private static void createManyToManyTable( EntityField field, Writer writer ) throws IOException
	{
		if( field.getType() == RelationType.MANYTOMANY && ( field.getName().compareTo(field.getCounterName()) < 0 ) )
			writer.write("create table " + getManyToManyTableName(field) + "\n"
					+ "(\n"
					+ "\t" + field.getName() + "_id int,\n"
					+ "\t" + field.getCounterName() + "_id int\n"
					+ ");\n\n"   );
	}
	
	private static void createTable(Entity entity, MetaData metadata, DBType dbType, Writer writer) throws IOException
	{
		// create table
		writer.write( "\ncreate table " + entity.getSimpleClassName() + "\n"
				+ "(\n" );
		
		// primary column
		writer.write("\t" + entity.getObjectName() + "_id int");
		switch(dbType)
		{
		case MYSQL:
			writer.write( " auto_increment,\n");
			break;
		}
		// primitive and enum columns
		for(Field field : entity.getFields())
			writer.write( "\t" + field.getName() + " " + field.getSqlType() + ",\n");
		
		// relational columns
		for(EntityField entityField : entity.getEntityFields())
			if(!entityField.getType().isSecondMany())
				writer.write( "\t" + entityField.getName() + "_id int,\n" );
		
		// primary key
		writer.write( "\tconstraint pk_" + entity.getSimpleClassName().toLowerCase() + " primary key ( " + entity.getObjectName() + "_id )");
		
		//foreign keys
		if(metadata.isUsingForeignKeys())
			for(EntityField entityField : entity.getEntityFields())
				if(entityField.getType() == RelationType.MANYTOONE)
					writer.write( ",\n\tconstraint fk_" + entity.getSimpleClassName().toLowerCase() + "_" + entityField.getEntity().getSimpleClassName().toLowerCase()
								+ " foreign key ( " + entityField.getName() + "_id ) references "
								+ entityField.getEntity().getSimpleClassName() + "( " + entityField.getEntity().getObjectName() + "_id )");
		//fin
		writer.write( "\n"
				+ ");\n" );
	}
}


