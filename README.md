TestTask application
=====================

Description
---------
The project creates a rest api for creating a user, authorizing him, adding messages to the database and getting the latest messages from the database.

ATTENTION!!!
------------
Before launching the project or building it with tests, you must start the container with the base, as described below.

Pre-Installation steps
----------------------
1. Check if docker and docker-compose are installed in the operating system.
2. If they are not installed, then follow the links and install them
    - https://docs.docker.com/engine/install/
    - https://docs.docker.com/compose/install/compose-plugin/#installing-compose-on-linux-systems

To run an application in console:
3. Check if maven is installed in the operating system.
4. If he is not installed, then follow the link and install him
    - https://maven.apache.org/install.html
5. Check if openjdk 17 is installed in the operating system.

If you want to install the app and use it right away
-------------------------------------
* Open console and run the commands below
* Сlone project from GitHub
```
git clone git@github.com:romirov/InsideTestTask.git
``` 
* Go to the project folder
```
cd InsideTestTask
```
* Downloading PostgreSQL docker image from Docker Hub
```
docker pull postgres
```
* Downloading TestTask docker image from Docker Hub
```
docker pull marukhan/inside_test_task
```
* Run the images by running the command
```
docker-compose up
```
* Go to the Usage section and use the commands described there

If you want to build and install the application from the beginning
---------------------
1. Open console and run the commands below
2. Downloading PostgreSQL docker image from Docker Hub
```
docker pull postgres
```
3. Run it with the command(command only for testing)
```
docker run -it --rm -e POSTGRES_PASSWORD=postgres --name postgres -p 5432:5432 postgres -d postgres
```
4. Сlone project from GitHub
```
git clone git@github.com:romirov/InsideTestTask.git
``` 
If the application is not planned to be launched in the console,
then it can be opened and built in the IDE, using the tools it offers.
To run an application in console:
1. Go to the project folder
```
cd InsideTestTask
```
2. In the application.properties file, comment out the line 
```
spring.datasource.url=jdbc:postgresql://postgres:5432/postgres
```
and uncomment
```
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
```
spring.datasource.url=jdbc:postgresql://postgres:5432/postgres - is need for work in docker image
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres - is need for connect to posgresql from IDE and progeny tests in IDE
3. Putting together a project
```
mvn clean install -U 
```
4. Launching the application
```
java -jar target/TestTask-0.0.1-SNAPSHOT.jar
```
5. Go to the Usage section and use the commands described there

#### Optional:

####!!!Don't forget to change the line in application.properties.
The application can also be packaged in a docker container by running the command in the project directory( PostgreSQL image must be running at this time )
```
mvn spring-boot:build-image -DskipTests
```
The image with the application will be called marukhan/inside_test_task

It can be viewed with the command
```
docker images 
```
Then stop the PostgeSQL container if it is running
```
docker stop IMAGE_ID 
```
And run both containers at the same time by executing the command in the application directory
```
docker-compose up
```
To upload the application image to your repository on Docker Hub, do the following:
```
docker images
docker tag IMAGE_ID accaunt_name_from_docker_hub/image_name:latest
docker login
docker push accaunt_name_from_docker_hub/image_name:latest
```
In other words
```
docker tag local-image:tagname new-repo:tagname
docker push new-repo:tagname
```

Usage
-----
Open console and run the commands below:
- adding a user
```
curl -X 'POST' \
  'http://localhost:7777/user' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "adm",
  "password": "adm"
}'
```
- authorization (getting a jwt token)
```
curl -X 'POST' \
  'http://localhost:7777/login' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "adm",
  "password": "adm"
}'
```
- adding a message
```
curl -X 'POST' \
  'http://localhost:7777/message' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJuYW1lOiAiOiIxMTEiLCJleHAiOjE2NTk2NDY4MDB9._AoN_eqbASxerBei67zg9nk3cJJvgUQqosAbnZf32J5q9dkKCLHPRDkJv1vZtIx0' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "adm",
  "message": "string"
}'
```
- receiving historical messages
```
curl -X 'GET' \
  'http://localhost:7777/message' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJuYW1lOiAiOiIxMTEiLCJleHAiOjE2NTk2NDY4MDB9._AoN_eqbASxerBei67zg9nk3cJJvgUQqosAbnZf32J5q9dkKCLHPRDkJv1vZtIx0' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "adm",
  "message": "history 10"
}'
```



