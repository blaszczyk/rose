// ROSE : Relational-Object-tranSlator-rosE
package sqltojava;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Rose {
	
	public static void main(String[] args)
	{
		if(args.length == 0)
		{
			System.out.println("Use:java -jar ROSE.jar filename");
			return;
		}
		try
		{
			List<Entity> entities = new ArrayList<>();
			MetaData metadata = new MetaData();
			RoseParser.parse(args[0],entities,metadata);
			CreateSQL.createDeleteTables(entities, metadata);
			CreateSQL.createCreateTables(entities, metadata);
			for(Entity entity : entities)
				CreateJavaModel.createModel(entity, metadata);
		}
		catch (FileNotFoundException | ParseException e)
		{
			e.printStackTrace();
		}
	}
}
