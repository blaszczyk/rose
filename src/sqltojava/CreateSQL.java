package sqltojava;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CreateSQL {
	
	public static void createCreateTables(List<Entity> entities, MetaData metadata)
	{
		String fullpath = metadata.getSqlpath() + "createtables.sql";
		System.out.println(fullpath);
		File file = new File(fullpath);
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		try(FileWriter writer = new FileWriter(file))
		{
			writer.write("--createtables.sql\n--generated by rose\n\n");
			for(Entity entity : entities)
				createTable(entity, metadata, writer);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void createTable(Entity entity, MetaData metadata, Writer writer) throws IOException
	{
		writer.write( "create table " + entity.getSqlname() + "\n(\n" );
		for(Member member : entity)
		{
			writer.write( "\t" + member.getSqlname() + " " + member.getSqltype());
			if(member.isPrimary())
				writer.write( " auto_increment");
			else if(member.getDefvalue() != null)
				writer.write( " default " + member.getDefvalue() );
			writer.write(",\n");
		}
		writer.write( "\tconstraint pk_" + entity.getSqlname().toLowerCase() + " primary key ( " );
		boolean first = true;
		for(Member member : entity)
			if(member.isPrimary())
			{
				if(first)
					first = false;
				else
					writer.write(", ");
				writer.write( member.getSqlname() );
			}
		writer.write( " )\n);\n\n" );
	}

	public static void createDeleteTables(List<Entity> entities, MetaData metadata)
	{
		String fullpath = metadata.getSqlpath() + "deletetables.sql";
		System.out.println(fullpath);
		File file = new File(fullpath);
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		try(FileWriter writer = new FileWriter(file))
		{
			writer.write("--deletetables.sql\n--generated by rose\n\n");
			for(int i = entities.size() - 1; i >= 0; i-- )
				writer.write("delete table " + entities.get(i).getSqlname() + ";\n");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}


