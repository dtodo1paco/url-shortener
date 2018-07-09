# URL Shortener

### A simple URL Shorten service 

![URLShortener](https://github.com/dtodo1paco/url-shortener/raw/master/images/demo.gif)

This is a sample project to test [Spring-boot](https://spring.io/projects/spring-boot) capabilities to serve REST operations and a SPA client made with [React-md](https://react-md.mlaursen.com/) to use them.
It is based on some popular URL shortener services such as [bitly](https://bitly.com/) or [goo.gl](https://goo.gl/).

![URLShortener](https://github.com/dtodo1paco/url-shortener/raw/master/images/image_1.png)

### Features

URL has many features including:
- A public operation to short a source URL
- The code (URL shortened) obtained is always the same given the same source URL
- When a code is requested, the server would redirect your browser to the corresponding source URL
- Every code request is saved to keep track of shorten URL use

#### ToDo
- User registration
- Every authenticated user has access to visits of its shortened URLs
- Admin desktop
- Unit/Functional testing for all the services

### Issues/Bugs/Suggestions
Feel free to report any bugs or make suggestions [here](https://github.com/dtodo1paco/url-shortener/issues)

### Summary
The system has 2 main blocks:
- Public: which offers operations to short a URL and to resolve (and navigate to) a shortened URL to its original form.
- Private: to manage, search and view all the statistics related to shortened URL use.

Every mapped URL is called Resource, and saves the original URL value and the user and datetime when it was mapped. When a request is made for the code created, a ResourceVisit is created saving datetime, user-agent, user (if present) and resource served.

The client is a React-md SPA that creates the corresponding POST requests to the server and show results to the UI user.
The REST services can be requested outside the client application, so if you don't like react-md or this particular client, you may delete the CLIENT part of the project.

### Setting up
#### Preinstalled software
Make sure you have installed the following tools:
- npm 3.5.2+
- java 8+
- maven 3+
- MongoDB

#### Server side (Java)
Go to REST directory and execute the following
```sh
mvn clean install && java -jar target/url-shortener-0.0.1-SNAPSHOT.jar 
```
You will see maven compiling sources and creating the JAR you are launching in next command. 
```sh
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 2.486 s
[INFO] Finished at: 2018-07-09T17:32:54+02:00
[INFO] Final Memory: 33M/301M
[INFO] ------------------------------------------------------------------------

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v1.5.3.RELEASE)

2018-07-09 17:32:55.202  INFO 21865 --- [           main] o.d.samples.urlshortener.MyApplication   : Starting MyApplication v0.0.1-SNAPSHOT on pac-Inspiron-5570 with PID 21865 (/BAR/WORKSPACE/SPRING/url-shortener/REST/target/url-shortener-0.0.1-SNAPSHOT.jar started by pac in /BAR/WORKSPACE/SPRING/url-shortener/REST)
```
SpringBoot will run then and from now, you can navigate to [http://localhost:8080](http://localhost:8080) to see the main page. 

#### Client side (React)
The client is precompiled in bundle.js, but if you want to make changes, go to CLIENT directory and type:
```sh
npm install
```
This will install all the dependencies related to the project. Now, you can run the server locally (for development purposes), with
```sh
npm run dev
```
And, when you're done, you can make
```sh
npm run prod
```
to generate a new build.js for the Server project. And you're done.
**Remember to restart the Spring-boot server for changes to take effect.**

### Some considerations
This project is just for test the technologies, a real project will need some improvements like:
- run after a web server (Apache, nginx,...) corectly configured to avoid hacker attacks (mod-security, remove any server information,...).
- If needed, a load balancer to manage load peaks. To make the service scalable, you can run multiple spring-boot servers on all the nodes you want (balanced by Apache or similar) and connect all of them to the same database (see [application.properties](https://github.com/dtodo1paco/url-shortener/REST/src/main/resources/application.properties)).
- There are no automated Test neither in the client or the server side. This is **unacceptable** for a production-ready webapp.

### Donations/Tips :coffee: :beer: :smile:
If you like this URL-shortener and wish to support futher development you can make a donation or just say it [here](https://dtodo1paco.github.io/cv/fjap.html)
