# websocket-simple-server

This is a simple WebSocket server demo using Spring Boot. See the tutorial
[Creating a WebSocket Server with Spring Boot](https://www.nexmo.com/blog/2018/10/08/create-websocket-server-spring-boot-dr/)
for details.

This can run as a stand-alone server or compiled into a .war file and deployed
on a Tomcat server. *By default it is configured to run stand-alone*.

## Stand-alone server

Quickly run the server and test it.

### Configure

The server is set to run on port 8090 by configuring the property `server.port=8090`
in `src/main/resources/application.properties`. If the property is not set
it will default to port 8080.

### Run server
The simplest way to start the server is to run:

```
./gradlew bootRun
```

You can connect to it at [http://localhost:8090/socket](http://localhost:8090/socket).

## Run stand-alone server from a .jar file

You can also build a .jar file to run the stand-alone server.

### Configure

1. The server is set to run on port 8090 by configuring the property `server.port=8090`
in `src/main/resources/application.properties`. If the property is not set
it will default to port 8080.

2. (optional) Modify `build.gradle` and add a **bootJar** task to name the
generated .jar file `websocket-test.jar` rather than the default (which
would be websocket-simple-server-0.0.1-SNAPSHOT.jar):

    ```
    bootJar {
        archiveName 'websocket-test.jar'
    }
    ```

    Note: the bootJar task is still using the deprecated **archiveName** property
    as of version 2.1.6.RELEASE, rather than the newer archiveFileName.

### Build .jar file

Build a .jar file that will run the server.

```
./gradlew assemble

or

./gradlew bootJar

```

### Run server from .jar file

If you used the **bootJar** task to rename the .jar file to websocket-test.jar:

```
java -jar build/libs/websocket-test.jar
```

Otherwise, run it with the default name:

```
java -jar build/libs/websocket-simple-server-0.0.1-SNAPSHOT.jar
```

You can connect to it at [http://localhost:8090/socket](http://localhost:8090/socket).

## Run on Tomcat server

Build the application as a .war file and deploy it on a Tomcat server.

### Configure

The server property `server.port=8090` in `src/main/resources/application.properties`
is ignored. It will run on the port of the Tomcat server (default is 8080)
and application context path (the application name). For example, if the
application is named `websocket-test` the base URL will be
`ws://localhost:8080/websocket-test/`.

1. Modify the main entry point class, `SocketApplication` (the one annotated
with @SpringBootApplication) to extend SpringBootServletInitializer like this:

    ```
    @SpringBootApplication
    public class SocketApplication extends SpringBootServletInitializer {

        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
            return applicationBuilder.sources(SocketApplication.class);
        }

        public static void main(String[] args) {
            SpringApplication.run(SocketApplication.class, args);
        }
    }
    ```

    Note: the original configuration that runs stand-alone looks like this:

    ```
    @SpringBootApplication
    public class SocketApplication {

        public static void main(String[] args) {
            SpringApplication.run(SocketApplication.class, args);
        }

    }
    ```

2. Modify `build.gradle` and add the *war* plugin:

    ```
    plugins {
        id 'org.springframework.boot' version '2.1.6.RELEASE'
        id 'java'
        id 'war'
    }
    ```

3. (optional) Modify `build.gradle` and add a **bootWar** task to name the
generated .war file to `websocket-test.war` rather than the default (which
would be websocket-simple-server-0.0.1-SNAPSHOT.war):

    ```
    bootWar {
        archiveName 'websocket-test.war'
    }
    ```

    Note: the bootWar task is still using the deprecated **archiveName** property
    as of version 2.1.6.RELEASE, rather than the newer archiveFileName.

4. Modify `build.gradle` and add the `spring-boot-starter-tomcat` dependency
**providedCompile** like this:

    ```
    dependencies {
        implementation 'org.springframework.boot:spring-boot-starter-websocket'
        testImplementation 'org.springframework.boot:spring-boot-starter-test'
        providedCompile 'org.springframework.boot:spring-boot-starter-tomcat'
    }
    ```

Note: both **compileOnly** and **providedCompile** behave like the Maven
**provided** dependency but compileOnly requires the **java** plugin and
providedCompile requires the **war** plugin. Since we're using both plugins
either would work.

### Build the .war file

Build the war file in `build/libs/`.

```
./gradlew assemble

or

./gradlew bootWar

```

### Deploy .war file in a Tomcat server

Copy the .war file to the `webapps` directory under the base Tomcat
path of a running Tomcat server; for example to `/usr/local/tomcat/webapps`.
The application should deploy.

You should be able to connect to it at `ws://{server name}:{port}/websocket-test/socket`.
If running locally, it's ws://localhost:8080/websocket-test/socket.

Note: if it doesn't deploy, check the `webapps` directory to see if it unpacked
the .war file. Check that the attributes `unpackWars="true"` and
`autoDeploy="true"` are set in the <Host> element in the configuration
file `/usr/local/tomcat/conf/server.xml`.
