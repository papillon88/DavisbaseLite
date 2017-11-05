import java.io.RandomAccessFile;



public class MakeInformationSchema {
	public static void main(String[] args) {
		try {
			/* FIXME: Put all binary data files in a separate subdirectory (subdirectory tree?) */
			/* FIXME: Should there not be separate Class static variables for the file names? 
			 *        and just hard code them here?
			 */
			/* TODO: Should there be separate methods to checkfor and subsequently create each file 
			 *       granularly, instead of a big bang all or nothing? 
			 */
			RandomAccessFile schemataTableFile = new RandomAccessFile("information_schema.schemata.tbl", "rw");
			RandomAccessFile tablesTableFile = new RandomAccessFile("information_schema.table.tbl", "rw");
			RandomAccessFile columnsTableFile = new RandomAccessFile("information_schema.columns.tbl", "rw");

			/*
			 *  Create the SCHEMATA table file.
			 *  Initially it has only one entry:
			 *      information_schema
			 */
			// ROW 1: information_schema.schemata.tbl
			schemataTableFile.writeByte("information_schema".length());
			schemataTableFile.writeBytes("information_schema");

			/*
			 *  Create the TABLES table file.
			 *  Remember!!! Column names are not stored in the tables themselves
			 *              The column names (TABLE_SCHEMA, TABLE_NAME, TABLE_ROWS)
			 *              and their order (ORDINAL_POSITION) are encoded in the
			 *              COLUMNS table.
			 *  Initially it has three rows (each row may have a different length):
			 */
			// ROW 1: information_schema.tables.tbl
			tablesTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			tablesTableFile.writeBytes("information_schema");
			tablesTableFile.writeByte("SCHEMATA".length()); // TABLE_NAME
			tablesTableFile.writeBytes("SCHEMATA");
			tablesTableFile.writeLong(1); // TABLE_ROWS

			// ROW 2: information_schema.tables.tbl
			tablesTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			tablesTableFile.writeBytes("information_schema");
			tablesTableFile.writeByte("TABLES".length()); // TABLE_NAME
			tablesTableFile.writeBytes("TABLES");
			tablesTableFile.writeLong(3); // TABLE_ROWS

			// ROW 3: information_schema.tables.tbl
			tablesTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			tablesTableFile.writeBytes("information_schema");
			tablesTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			tablesTableFile.writeBytes("COLUMNS");
			tablesTableFile.writeLong(7); // TABLE_ROWS

			/*
			 *  Create the COLUMNS table file.
			 *  Initially it has 11 rows:
			 */
			// ROW 1: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("SCHEMATA".length()); // TABLE_NAME
			columnsTableFile.writeBytes("SCHEMATA");
			columnsTableFile.writeByte("SCHEMA_NAME".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("SCHEMA_NAME");
			columnsTableFile.writeInt(1); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 2: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("TABLES".length()); // TABLE_NAME
			columnsTableFile.writeBytes("TABLES");
			columnsTableFile.writeByte("TABLE_SCHEMA".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("TABLE_SCHEMA");
			columnsTableFile.writeInt(1); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 3: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("TABLES".length()); // TABLE_NAME
			columnsTableFile.writeBytes("TABLES");
			columnsTableFile.writeByte("TABLE_NAME".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("TABLE_NAME");
			columnsTableFile.writeInt(2); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 4: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("TABLES".length()); // TABLE_NAME
			columnsTableFile.writeBytes("TABLES");
			columnsTableFile.writeByte("TABLE_ROWS".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("TABLE_ROWS");
			columnsTableFile.writeInt(3); // ORDINAL_POSITION
			columnsTableFile.writeByte("long int".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("long int");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 5: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS");
			columnsTableFile.writeByte("TABLE_SCHEMA".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("TABLE_SCHEMA");
			columnsTableFile.writeInt(1); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 6: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS");
			columnsTableFile.writeByte("TABLE_NAME".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("TABLE_NAME");
			columnsTableFile.writeInt(2); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 7: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS");
			columnsTableFile.writeByte("COLUMN_NAME".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("COLUMN_NAME");
			columnsTableFile.writeInt(3); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 8: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS");
			columnsTableFile.writeByte("ORDINAL_POSITION".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("ORDINAL_POSITION");
			columnsTableFile.writeInt(4); // ORDINAL_POSITION
			columnsTableFile.writeByte("int".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("int");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 9: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS");
			columnsTableFile.writeByte("COLUMN_TYPE".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("COLUMN_TYPE");
			columnsTableFile.writeInt(5); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 10: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS");
			columnsTableFile.writeByte("IS_NULLABLE".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("IS_NULLABLE");
			columnsTableFile.writeInt(6); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(3)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(3)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 11: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS");
			columnsTableFile.writeByte("COLUMN_KEY".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("COLUMN_KEY");
			columnsTableFile.writeInt(7); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(3)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(3)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
}