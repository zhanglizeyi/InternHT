# InternHT

<h1>Spring Framework</h1>
<h3>Spring framework has layer construction with 7 modules</h3>
<li>Spring AOP: source-level, metadata, AOP infrastructure</li>
<p>AOP 模块为基于Spring的应用程序中的对象提供了事务管理服务 </p>
<li>Spring ORM: Hibernate support, Ibats support, JDO support</li>
<li>Spring Web: WebApplicationContext, Mutipart resolver, Web utilties</li>
<p>web 上细纹模块建立在应用程序上下文模块之上，为基于web的应用程序提供了上下文。所以，spring框架支持与Struts的集成。Web模块还简化了处理多部分请求以及将请求参数绑定到域对象的工作</p>
<li>Spring Context: Application context, UI support, Validation, JNDL EJB support and remodeling Mail</li>
<p>Spring 上下文是一个配置文件，向Spring框架提供上下文信息。spring上下文包括企业服务，例如 JNDI，EJB，电子邮件，国际化，消炎和调度功能</p>
<li>Spring Web MVC: Web MVC, Framework, Web Views, JSP/Velocity, PDF/Export</li>
<p>MVC 框架是一个全功能的构建web应用程序的MVC实现。 通过策略接口，MVC框架编程为高度可配置的，MVC容纳了大量视图技术，其中包括JSP，Velocity，Tiles， iText,和POI</p>
<li>Spring Core: Supporting utilties, Bean container</li>
<p>BeanFactory (IOC) 模式将应用程序的配置和依赖规范与实际的应用程序代码分开</p>


<h1>AOP 面向方面的编程</h1>
<p>是一种编程技术，AOP 的核心构造是方面，它将那些影响多个类的行为封装到可重用的模块中. 在典型的面向对象开发方式中，可能要将日志记录语句放在所有方法和 Java 类中才能实现日志功能。在 AOP 方式中，可以反过来将日志服务模块化，并以声明的方式将它们应用到需要日志的组件上
AOP 的功能完全集成到了 Spring 事务管理、日志和其他各种特性的上下文中</p>

<h1>IOC 容器</h1>
<p>Spring 设计的核心是 org.springframework.beans 包， 它的设计目标是与 JavaBean 组件一起使用。这个包通常不是由用户直接使用，而是由服务器将其用作其他多数功能的底层中介。下一个最高级抽象是 BeanFactory 接口，它是工厂设计模式的实现，允许通过名称创建和检索对象。BeanFactory 也可以管理对象之间的关系</p>
	
	<h3>BeanFactory支持两个对象模型</h3>
	<p>单态 模型提供了具有特定名称的对象的共享实例，可以在查询时对其进行检索。Singleton 是默认的也是最常用的对象模型。对于无状态服务对象很理想<p>

	<p>原型 模型确保每次检索都会创建单独的对象。在每个用户都需要自己的对象时，原型模型最适合</p>

	<h3>BeanFactory 接口</h3>

	<h2>XmlBeanFactory example</h2>
	<ol>
		<li>1. BeanFactory factory = new XMLBeanFactory(new FileInputSteam("mybean.xml"))</li>
		<p>因为org.springframework.beans.factory.BeanFactory 是一个简单接口所以可以针对各种低层的存储方法实现。最常用的BeanFactory定义是 XMLBeanFactory, 她根据XML 文件的定义装入 bean</p>
		<li>2. MyBean mybean = (MyBean) factory.getBean("mybean")</li>
		<p>在 XML 文件中定义的 Bean 是被消极加载的，这意味在需要 bean 之前，bean 本身不会被初始化。要从 BeanFactory 检索 bean，只需调用 getBean() 方法，传入将要检索的 bean 的名称即可</p>

		<li>每个 bean 的定义都可以是 POJO （用类名和 JavaBean 初始化属性定义） 或 FactoryBean。FactoryBean 接口为使用 Spring 框架构建的应用程序添加了一个间接的级别</li>

	</ol>

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







