import java.io.RandomAccessFile;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.SortedMap;



/**
 * @author Debaspreet Chowdhury
 * @version 1.0
 * <b>This is an example of how to read/write binary data files using RandomAccessFile class</b>
 *
 */
public class DavisBaseLite {
	// This can be changed to whatever you like
	static String prompt = "davisql> ";
	
	/*
	 *  This example does not dynamically load a table schema to be able to 
	 *  read/write any table -- there is exactly ONE hardcoded table schema.
	 *  These are the variables associated with that hardecoded table schema.
	 *  Your database engine will need to define these on-the-fly from
	 *  whatever table schema you use from your information_schema
	 */
	static String widgetTableFileName = "widgets.dat";
	static String tableIdIndexName = "widgets.id.ndx";
	static int id;
	static String name;
	static short quantity;
	static float probability;
	

    public static void main(String[] args) {
		/* Display the welcome splash screen */
		splashScreen();
		
		/* 
		 *  Manually create a binary data file for the single hardcoded 
		 *  table. It inserts 5 hardcoded records. The schema is inherent 
		 *  in the code, pre-defined, and static.
		 *  
		 *  An index file for the ID field is created at the same time.
		 */
		hardCodedCreateTableWithIndex();

		/* 
		 *  The Scanner class is used to collect user commands from the prompt
		 *  There are many ways to do this. This is just one.
		 *
		 *  Each time the semicolon (;) delimiter is entered, the userCommand String
		 *  is re-populated.
		 */
		Scanner scanner = new Scanner(System.in).useDelimiter(";");
		String userCommand; // Variable to collect user input from the prompt

		do {  // do-while !exit
			System.out.print(prompt);
			userCommand = scanner.next().trim();

			/*
			 *  This switch handles a very small list of commands of known syntax.
			 *  You will probably want to write a parse(userCommand) method to
			 *  to interpret more complex commands. 
			 */
			switch (userCommand) {
				case "display all":
					displayAllRecords();
					break;
				case "display":
					/* 
					 *  Your record retrieval must use the SELECT-FROM-WHERE syntax
					 *  This simple syntax allows retrieval of single records based 
					 *  only on the ID column.
					 */
					String recordID = scanner.next().trim();
					displayRecordID(Integer.parseInt(recordID));
					break;
				case "help":
					help();
					break;
				case "version":
					version();
					break;
				default:
					System.out.println("I didn't understand the command: \"" + userCommand + "\"");
			}
		} while(!userCommand.equals("exit"));
		System.out.println("Exiting...");
	    
    } /* End main() method */


//  ===========================================================================
//  STATIC METHOD DEFINTIONS BEGIN HERE
//  ===========================================================================


	/**
	 *  Help: Display supported commands
	 */
	public static void help() {
		System.out.println(line("*",80));
		System.out.println();
		System.out.println("\tdisplay all;   Display all records in the table.");
		System.out.println("\tget; <id>;     Display records whose ID is <id>.");
		System.out.println("\tversion;       Show the program version.");
		System.out.println("\thelp;          Show this help information");
		System.out.println("\texit;          Exit the program");
		System.out.println();
		System.out.println();
		System.out.println(line("*",80));
	}
	
	/**
	 *  Display the welcome "splash screen"
	 */
	public static void splashScreen() {
		System.out.println(line("*",80));
        System.out.println("Welcome to DavisBaseLite"); // Display the string.
		version();
		System.out.println("Type \"help;\" to display supported commands.");
		System.out.println(line("*",80));
	}

	/**
	 * @param s The String to be repeated
	 * @param num The number of time to repeat String s.
	 * @return String A String object, which is the String s appended to itself num times.
	 */
	public static String line(String s,int num) {
		String a = "";
		for(int i=0;i<num;i++) {
			a += s;
		}
		return a;
	}
	
	/**
	 * @param num The number of newlines to be displayed to <b>stdout</b>
	 */
	public static void newline(int num) {
		for(int i=0;i<num;i++) {
			System.out.println();
		}
	}
	
	public static void version() {
		System.out.println("DavisBaseLite v1.0\n");
	}


	/**
	 *  This method reads a binary table file using a hard-coded table schema.
	 *  Your query must be able to read a binary table file using a dynamically 
	 *  constructed table schema from the information_schema
	 */
	public static void displayAllRecords() {
		try {
			/* Open the widget table binary data file */
			RandomAccessFile widgetTableFile = new RandomAccessFile(widgetTableFileName, "rw");

			/*
			 *  Navigate throught the binary data file, displaying each widget record
			 *  in the order that it physically appears in the file. Convert binary data
			 *  to appropriate data types for each field.
			 */
			for(int record = 0;record < 5; record++) {
				System.out.print(widgetTableFile.readInt());
				System.out.print("\t");
				byte varcharLength = widgetTableFile.readByte();
				for(int i = 0; i < varcharLength; i++)
					System.out.print((char)widgetTableFile.readByte());
				System.out.print("\t");
				System.out.print(widgetTableFile.readShort());
				System.out.print("\t");
				System.out.println(widgetTableFile.readFloat());
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	public static void displayRecordID(int id) {
		try {
			int indexFileLocation = 0;
			long indexOfRecord = 0;
			boolean recordFound = false;

			RandomAccessFile widgetTableFile = new RandomAccessFile(widgetTableFileName, "rw");
			RandomAccessFile tableIdIndex = new RandomAccessFile(tableIdIndexName, "rw");

			/*
			 *  Use exhaustive brute force seach over the binary index file to locate
			 *  the requested ID values. Then use its assoicated address to seek the 
			 *  record in the widget table binary data file.
			 *
			 *  You may instead want to load the binary index file into a HashMap
			 *  or similar key:value data structure for efficient index-address lookup,
			 *  but this is not required.
			 */
			while(!recordFound) {
				tableIdIndex.seek(indexFileLocation);
				if(tableIdIndex.readInt() == id) {
					tableIdIndex.seek(indexFileLocation+4);
					indexOfRecord = tableIdIndex.readLong();
					recordFound = true;
				}
				/* 
				 *  Each index entry uses 12 bytes: ID=4-bytes + address=8-bytes
				 *  Move ahead 12 bytes in the index file for each while() loop
				 *  iteration to increment through index entries.
				 * 
				 */
				indexFileLocation += 12;
			}

			widgetTableFile.seek(indexOfRecord);
			System.out.print(widgetTableFile.readInt());
			System.out.print("\t");
			byte varcharLength = widgetTableFile.readByte();
			for(int i = 0; i < varcharLength; i++)
				System.out.print((char)widgetTableFile.readByte());
			System.out.print("\t");
			System.out.print(widgetTableFile.readShort());
			System.out.print("\t");
			System.out.println(widgetTableFile.readFloat());
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	/**
	 *  This method is hard-coded to create a binary table file with 5 records
	 *  It also creates an index file for the ID field
	 *  It is based on the following table schema:
	 *  
	 *  CREATE TABLE table (
	 *      id unsigned int primary key,
	 *      name varchar(25),
	 *      quantity unsigned short,
	 *      probability float
	 *  );
	 */
	public static void hardCodedCreateTableWithIndex() {
		long recordPointer;
		try {
			RandomAccessFile widgetTableFile = new RandomAccessFile(widgetTableFileName, "rw");
			RandomAccessFile tableIdIndex = new RandomAccessFile(tableIdIndexName, "rw");
			
			id = 1;
			name = "alpha";
			quantity = 847;
			probability = 0.341f;
			
			tableIdIndex.writeInt(id);
			tableIdIndex.writeLong(widgetTableFile.getFilePointer());
			widgetTableFile.writeInt(id);
			widgetTableFile.writeByte(name.length());
			widgetTableFile.writeBytes(name);
			widgetTableFile.writeShort(quantity);
			widgetTableFile.writeFloat(probability);
			
			id = 2;
			name = "beta";
			quantity = 1472;
			probability = 0.89f;
			
			tableIdIndex.writeInt(id);
			tableIdIndex.writeLong(widgetTableFile.getFilePointer());
			widgetTableFile.writeInt(id);
			widgetTableFile.writeByte(name.length());
			widgetTableFile.writeBytes(name);
			widgetTableFile.writeShort(quantity);
			widgetTableFile.writeFloat(probability);

			id = 3;
			name = "gamma";
			quantity = 41;
			probability = 0.5f;
			
			tableIdIndex.writeInt(id);
			tableIdIndex.writeLong(widgetTableFile.getFilePointer());
			widgetTableFile.writeInt(id);
			widgetTableFile.writeByte(name.length());
			widgetTableFile.writeBytes(name);
			widgetTableFile.writeShort(quantity);
			widgetTableFile.writeFloat(probability);

			id = 4;
			name = "delta";
			quantity = 4911;
			probability = 0.4142f;
			
			tableIdIndex.writeInt(id);
			tableIdIndex.writeLong(widgetTableFile.getFilePointer());
			widgetTableFile.writeInt(id);
			widgetTableFile.writeByte(name.length());
			widgetTableFile.writeBytes(name);
			widgetTableFile.writeShort(quantity);
			widgetTableFile.writeFloat(probability);

			id = 5;
			name = "epsilon";
			quantity = 6823;
			probability = 0.618f;
			
			tableIdIndex.writeInt(id);
			tableIdIndex.writeLong(widgetTableFile.getFilePointer());
			widgetTableFile.writeInt(id);
			widgetTableFile.writeByte(name.length());
			widgetTableFile.writeBytes(name);
			widgetTableFile.writeShort(quantity);
			widgetTableFile.writeFloat(probability);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}