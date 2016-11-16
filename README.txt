===================
Database connection
===================
Data source bean, defined as:

<bean id="dataSource" class="org.springframework.jndi.JndiObjectFactoryBean"
		scope="singleton">
		<property name="jndiName" value="java:comp/env/jdbc/PawelVisit" />
		<property name="resourceRef" value="true" />
</bean>

in src/main/resources/jpaContext.xml
refers to:

<Resource name="jdbc/PawelVisit" auth="Container" type="javax.sql.DataSource"
maxActive="100" maxIdle="30" maxWait="10000"
username="db_username" password="db_password" driverClassName="com.mysql.jdbc.Driver"
url="jdbc:mysql://db_server_host:db_server_port/db_schema?autoReconnect=true"/>

defined in Tomcats CATALINA_HOME/conf/context.xml


