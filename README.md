# Running project
If you have docker:  
Compile first:
```shell
mvn clean package
```
Then run:
```shell
docker-compose up
```
If you don't have docker, first get database then:
```shell
mvn spring-boot:run
```
Remember about inputing proper db_url, username and password inside application.yml file.