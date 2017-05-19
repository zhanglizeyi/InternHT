# InternHT

<h1>Postgres COMMAND LINE:</h1>
	createdb “database name”
	psql “database”
	select version() -> info of postgres

	\ (backslash) is commands 

	\h -> all the type
	\q -> quit
	\i -> reads in commands from the specified file
	

<h2>brew services start/stop/restart postgresql</h2>

Definition: (different systax of a URL statement)

<h2>1. public static Connection GetConnection(String url) throws SQLException</h2>
	Parameters: 
		url - a database url of the form
		jdbc:subprotocol:subname
		ex: (jdbc:postgresql://localhost/testdb)
	Returns:
		a connection to the data source
	Throws:
		SQLException - if a database access error occurs or the url is null
		SQLTimeException - when the driver has determined that the timeout value
					that the timeout value specified by the 
					setLoginTimeout method has been exceeded and has
					least tried to cancel the current database connection 
					attempt

<h2>2. public static Connection GetConnection(String username, String password) throws SQLException</h2>
	Paramenters:
		username: the database user on whose behalf the connection is being made		
		password: the user’s password
	Returns: 
		a connection to the data source
	Throws:
		SQLException - if a database access error occurs or the url is null
		SQLTimeException - when the driver has determined that the timeout value
					that the timeout value specified by the 
					setLoginTimeout method has been exceeded and has
					least tried to cancel the current database connection 
					attempt
		
<h2>3. public static Connection GetConnection(String url, String username, String password) throws SQLException</h2>

	Parameters:
		username - database username
		password - user’s password
		url - a database url of the form
			jdbc:subprotocol:subname
	Return:
		a connection to the URL
	Throws:
		SQLException - if a database access error occurs or the url is null
		SQLTimeException - when the driver has determined that the timeout value
					that the timeout value specified by the 
					setLoginTimeout method has been exceeded and has
					least tried to cancel the current database connection 
					attempt


<h1>FInd files directory from command:</h1>

	find / -type d -name “search name”

Insertion: 
	
	con = DriverManager.getConnection(url, user, password)
	String stm = “INSERT INTO db VALUES(‘z’, ‘123456’)”
	pst = con.prepareStatement(stm)
	pst.executeUpdate()

Update in database:

	ExecuteUpdate() throws SQLException:
		Def:
			executes the SQL statement in this preparedStatement obj,
		must be an SQL Data manipulation language statement, such as INSERT,
		UPDATE, or DELETE; or an SQL statement that returns nothing, such as a DDL
		statement.

		Returns:
			1.			
		Throws:
			SQLException: the row count for SQL access error occurs, this is called 
			on a closed preparedStatement or the SQL statement returns a ResultSet 	
  			obj.
 			SQLTimeoutException: timeout value that was specified b the 
						setQueryTimeout method has been exceeded and 
						has at least attempted to cancel the currently 
					 	running statement.


<h2>Retrieving data</h2>
	1. We get all authors from authors and print them to the console:
		pst = con.prepareStatement("select * from db");
		rs = pst.executeQuery();

	We use the executeQuery method, the method executes the given SQL statement, which returns a single ResultSet obj.
	The ResultSet is the data table returned by the SQL query.

<h2>Properties</h2>







