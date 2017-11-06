# DavisbaseLite

This is a functional command line based database engine similar to MySQL without the GUI and implemented using Java. All changes to database are saved in table data and index data files. So when database restarts, it bootstraps the most recent state from those files. **_Multi table queries not supported by this database._** 

**Following commands are supported :**

- SHOW SCHEMAS;
- USE schema_name;
- SHOW TABLES;
- CREATE SCHEMA schema_name;
- CREATE TABLE table_name (
column_name1 data_type(size) [primary key|not null],
column_name2 data_type(size) [primary key|not null],
column_name3 data_type(size) [primary key|not null],
...
);
- INSERT INTO TABLE table_name VALUES (value1,value2,value3,…);
- SELECT *
FROM table_name
WHERE column_name operator value;

**Steps to run the database engine :**

- DavisBaseLite.java has the main method. So just compile and run this file. 

**Examples :**

davisql> SELECT * FROM SCHEMATA;
SCHEMA_NAME |
---- |
information_schema |

davisql> SELECT * FROM TABLES;
+--------------------+--------------+------------+
| TABLE_SCHEMA       | TABLE_NAME   | TABLE_ROWS |
+--------------------+--------------+------------+
| information_schema | SCHEMATA     | 1          |
| information_schema | TABLES       | 3          |
| information_schema | COLUMNS      | 7          |
+--------------------+--------------+------------+

davisql> SELECT * FROM COLUMNS;
+--------------------+-------------+------------------+------------------+-------------+-------------+------------+
| TABLE_SCHEMA       | TABLE_NAME  | COLUMN NAME      | ORDINAL_POSITION | COLUMN_TYPE | IS_NULLABLE | COLUMN_KEY |
+--------------------+-------------+------------------+------------------+-------------+-------------+------------+
| information_schema | SCHEMATA    | SCHEMA_NAME      | 1                | varchar(64) | NO          |            |
| information_schema | TABLES      | TABLE_SCHEMA     | 1                | varchar(64) | NO          |            |
| information_schema | TABLES      | TABLE_NAME       | 2                | varchar(64) | NO          |            |
| information_schema | TABLES      | TABLE_ROWS       | 3                | long int    | NO          |            |
| information_schema | COLUMNS     | TABLE_SCHEMA     | 1                | varchar(64) | NO          |            |
| information_schema | COLUMNS     | TABLE_NAME       | 2                | varchar(64) | NO          |            |
| information_schema | COLUMNS     | COLUMN_NAME      | 3                | varchar(64) | NO          |            |
| information_schema | COLUMNS     | ORDINAL_POSITION | 4                | int         | NO          |            |
| information_schema | COLUMNS     | COLUMN_TYPE      | 5                | varchar(64) | NO          |            |
| information_schema | COLUMNS     | IS_NULLABLE      | 6                | varchar(3)  | NO          |            |
| information_schema | COLUMNS     | COLUMN_KEY       | 7                | varchar(3)  | NO          |            |
+--------------------+-------------+------------------+------------------+-------------+-------------+------------+


davisql> CREATE SCHEMA Zoo_schema;


davisql> CREATE TABLE Zoo (
Animal_ID INT PRIMARY KEY,
Name VARCHAR(20),
Sector SHORT INT
);

davisql> SELECT * FROM SCHEMATA;
+--------------------+
| SCHEMA_NAME        |
+--------------------+
| information_schema |
| Zoo_schema         |
+--------------------+

davisql> SELECT * FROM TABLES;
+--------------------+--------------+------------+
| TABLE_SCHEMA       | TABLE_NAME   | TABLE_ROWS |
+--------------------+--------------+------------+
| information_schema | SCHEMATA     | 1          |
| information_schema | TABLES       | 3          |
| information_schema | COLUMNS      | 7          |
| Zoo_schema         | Zoo          | 0          |
+--------------------+--------------+------------+


davisql> SELECT * FROM COLUMNS;
+--------------------+-------------+------------------+------------------+-------------+-------------+------------+
| TABLE_SCHEMA       | TABLE_NAME  | COLUMN NAME      | ORDINAL_POSITION | COLUMN_TYPE | IS_NULLABLE | COLUMN_KEY |
+--------------------+-------------+------------------+------------------+-------------+-------------+------------+
| information_schema | SCHEMATA    | SCHEMA_NAME      | 1                | varchar(64) | NO          |            |
| information_schema | TABLES      | TABLE_SCHEMA     | 1                | varchar(64) | NO          |            |
| information_schema | TABLES      | TABLE_NAME       | 2                | varchar(64) | NO          |            |
| information_schema | TABLES      | TABLE_ROWS       | 3                | long int    | NO          |            |
| information_schema | COLUMNS     | TABLE_SCHEMA     | 1                | varchar(64) | NO          |            |
| information_schema | COLUMNS     | TABLE_NAME       | 2                | varchar(64) | NO          |            |
| information_schema | COLUMNS     | COLUMN_NAME      | 3                | varchar(64) | NO          |            |
| information_schema | COLUMNS     | ORDINAL_POSITION | 4                | int         | NO          |            |
| information_schema | COLUMNS     | COLUMN_TYPE      | 5                | varchar(64) | NO          |            |
| information_schema | COLUMNS     | IS_NULLABLE      | 6                | varchar(3)  | NO          |            |
| information_schema | COLUMNS     | COLUMN_KEY       | 7                | varchar(3)  | NO          |            |
| Zoo_schema         | Zoo         | Animal_ID        | 1                | int         | NO          | PRI        |
| Zoo_schema         | Zoo         | Name             | 2                | varchar(20) | YES         |            |
| Zoo_schema         | Zoo         | Sector           | 3                | short       | YES         |            |
+--------------------+-------------+------------------+------------------+-------------+-------------+------------+

