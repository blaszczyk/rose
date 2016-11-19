/*
 * MetaDataParser.java
 * generated by rose
 */

package bn.blaszczyk.rose;


public class MetaDataParser
{
	public static void parseField( bn.blaszczyk.rose.MetaData metaData, String name, String value ) throws java.text.ParseException
	{
		switch( name.toLowerCase() )
		{
		case "srcpath":
			metaData.setSrcpath( value );
			break;
		case "sqlpath":
			metaData.setSqlpath( value );
			break;
		case "usingforeignkeys":
			metaData.setUsingForeignKeys( Boolean.parseBoolean( value ) );
			break;
		case "dbtype":
			metaData.setDbtype( value );
			break;
		case "dbuser":
			metaData.setDbuser( value );
			break;
		case "dbpassword":
			metaData.setDbpassword( value );
			break;
		case "dbserver":
			metaData.setDbserver( value );
			break;
		case "dbport":
			metaData.setDbport( value );
			break;
		case "dbname":
			metaData.setDbname( value );
			break;
		case "mainpackage":
			metaData.setMainpackage( value );
			break;
		case "mainname":
			metaData.setMainname( value );
			break;
		case "modelpackage":
			metaData.setModelpackage( value );
			break;
		case "usingannotations":
			metaData.setUsingAnnotations( Boolean.parseBoolean( value ) );
			break;
		case "parserpackage":
			metaData.setParserpackage( value );
			break;
		case "parserformat":
			metaData.setParserformat( value );
			break;
		case "controllerpackage":
			metaData.setControllerpackage( value );
			break;
		case "controllerformat":
			metaData.setControllerformat( value );
			break;
		case "controllerclass":
			metaData.setControllerclass( value );
			break;
		case "entitymodelpackage":
			metaData.setEntitymodelpackage( value );
			break;
		case "entitymodelformat":
			metaData.setEntitymodelformat( value );
			break;
		case "entitymodelfactoryclass":
			metaData.setEntitymodelfactoryclass( value );
			break;
		default:
			System.out.println( "Unknown Primitive Field: " + name + " in MetaData");
		}
	}

}
