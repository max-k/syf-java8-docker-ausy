rps-ear: Rock Paper and Scissor Game.
=========================================================
Author: Darlys Maldonado <cdcarlys@gmail.com>  
Technologies: Java, J2EE, Wicket, Bootstrap     

What is it?
-----------

This is an example of how to play Rock Paper and Scissor, pay attention if two player plays the same move, I assume that both lose.

Features used:

* Injection of a value from `web.xml` using `@Resource`
* Injection of a stateful session bean using `@EJB`

This is an EAR version, with the following structure:

        -`rps-ear` - parent module
            - `ejb`: Contains EJB beans. Creates a `.jar` file
            - `war`: Contains the Wicket web application, which uses the EJB beans. Creates a `.war` file
            - `ear`: Packages the EJB JAR and WAR into an EAR. Creates an `.ear` file
			- `ws` : Contains a WS to expose some functionalities of the game to be provided


System requirements
-------------------

All you need to build this project is Java 8 (Java SDK 1.8) or better, Maven 3.1 or better.
We have used for this game the following versions:
JDK version : 1.8.0_111
Maven : 3.3.9
WildFly : 10.1.0


The application this project produces is designed to be run on JBoss WildFly and Window.

 
Configure Maven
---------------

On the current folder 'tools' you can find a maven folder with the version 3.3.9.
Move this folder on your environment et set the environment variable MAVEN_HOME = your-path\apache-maven-3.3.9 and modifiy the variable PATH to add %MAVEN_HOME%\bin
Open a console a run the followin command : mvn --version. This should show the current maven version.

Install Java 8
--------------

Follow the instruction on the following page: https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html


Start JBoss WildFly with the Web Profile
-------------------------

1. On the current folder you can find the folder wildfly-10.1.0.Final.
2. Move this folder on you place of preference.
3. Create the environment variable WILDFLY_HOME = your-path\wildfly-10.1.0.Final.
4. Run the following command on %WILDFLY_HOME%\bin 

        For Windows: WILDFLY_HOME\bin\domain.bat
5. This command will start the server.


Build and Deploy the RPS Game
-------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line.

1. Make sure you have started the WildFly Server as described above.
2. Open a command line and navigate to the root directory of this rps-parent.
3. Type this command to build and deploy the archive:

        mvn clean install
        
4. The code must compile. Type the following command on the current folder.
		 
		 %WILDFLY_HOME%\bin\jboss-cli.bat --connect --file=deploy-domain.cli

4. This will deploy `target/rps-ear.ear` to the running instance of the server.


Access the application 
----------------------

The application will be running at the following URL: <http://localhost:8180/rps-war>.

 * You will see a page with a table listing the existing Contact entities. Initially, this table is empty.
 * Click on the _Insert a new Constact_ link to add a new contact.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        %WILDFLY_HOME%\bin\jboss-cli.bat --connect --file=undeploy-domain.cli

