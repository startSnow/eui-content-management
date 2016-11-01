# Synopsis
EUI cloud micro service expose REST APIs to be used by TV and Mobile devices to display list of applications.  Also provide ability to search and filter applications by LeEco custom category. 

Following software packages needs to be installed on development machine in order to build and run EUI cloud micro service module.
### Prerequisites : 
* JDK 1.8+ 
* Docker 1.12.x
* Maven  3.3.9+
* Spring Tool Suite(Optional)

### Follow below steps to build and run service on your local machine : 
 
* Checkout code from github  ( Please ask Bin Gong or Hardikkumar Patel for permission on euicloudservice repository in LE gitLab )
```
$ git clone -b develop git@git.letv.cn:bin.gong/euicloudservice.git
$ cd euicloudservice/      
```

* Build EUI cloud micro service code 
```
$ mvn package docker:build -P docker -Dmaven.test.skip=true
```

* View generated docker images
```
$ docker images
REPOSITORY                   TAG                 IMAGE ID            CREATED             SIZE
leeco/eui-cloud-service      0.0.1-SNAPSHOT      738a2bacf4d9        22 minutes ago      417.1 MB
frolvlad/alpine-oraclejdk8   slim                ea24082fc934        6 weeks ago         167.1 MB
```

* Run docker image 
``` 
$ docker run -p 8080:8080 leeco/eui-cloud-service:0.0.1-SNAPSHOT 
```

* Verify service status
```
$ docker ps
CONTAINER ID        IMAGE                                    COMMAND                  CREATED              STATUS              PORTS               NAMES
5636ee8659c6        leeco/eui-cloud-service:0.0.1-SNAPSHOT   "/docker-entrypoint.s"   About a minute ago   Up About a minute   3306/tcp          reverent
```

* SSH into Docker Running Container to view log and access database 
```
$ docker exec -it 5636ee8659c6 bash
bash-4.3#
```

* Save running docker image as tar file 
```
$ docker save leeco/eui-cloud-service:0.0.1-SNAPSHOT > eui-cloud-service-0.0.1-SNAPSHOT.tar
```
### Import docker image and run on other machine :  
 
* Download eui-cloud-service-0.0.1-SNAPSHOT from http://git.letv.cn/bin.gong/euicloudservice/blob/develop/eui-cloud-service-0.0.1-SNAPSHOT.tar
```
$ curl -O http://git.letv.cn/bin.gong/euicloudservice/blob/develop/eui-cloud-service-0.0.1-SNAPSHOT.tar
% Total       % Received % Xferd  Average Speed   Time    Time     Time  Current
                                  Dload  Upload   Total   Spent    Left  Speed
 100    98    0    98    0     0    262      0 --:--:-- --:--:-- --:--:--   262
$ ls
eui-cloud-service-0.0.1-SNAPSHOT.tar
```

* Load copied image to development machine 
```
$ docker load -i eui-cloud-service-0.0.1-SNAPSHOT.tar
```

* Run loaded image on other machin 
```
$ docker run -p 8080:8080 leeco/eui-cloud-service:0.0.1-SNAPSHOT
```

* Externalize Mysql Configuration 
```
$ docker run -p 8080:8080 -e MYSQL_HOST=10.79.5.87 -e MYSQL_PORT=3306 -e MYSQL_USER=euidev -e MYSQL_PASSWORD=euidev -e MYSQL_DATABASE=eui_service_dev  leeco/eui-cloud-service:0.0.1-SNAPSHOT
``` 

http://www.baeldung.com/spring-security-oauth-jwt

keytool -genkeypair -alias euistore -keyalg RSA -keypass euisecret -keystore eui.jks  -storepass euisecret

https://github.com/spring-projects/spring-security-oauth/blob/master/tests/annotation/jdbc/src/main/java/demo/Application.java


http://www.baeldung.com/rest-api-spring-oauth2-angularjs


curl -v  acme:acmesecret@localhost:9999/eui-auth-server/oauth/token -d grant_type=client_credentials

curl acme:acmesecret@localhost:9999/eui-auth-server/oauth/token -d grant_type=password -d username=euidev -d password=euidev


curl -H "Authorization:bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYWNtZSJdLCJzY29wZSI6WyJvcGVuaWQiXSwiZXhwIjoxNDc1MDM4NDkzLCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIl0sImp0aSI6ImE4MGQzYWJkLTk4NjMtNGRkNi04ODBjLWY2YjU0ZDdmMmNjZSIsImNsaWVudF9pZCI6ImFjbWUifQ.iDLBSbscgXTMcO_YFgQ8Cuvk7Dck2QophIrVzpXhd-OAld6W9VvokY-1OzojkM6sD1y6u2mwf2gkuJCHWYxK2qLVaO1D_eqMwmwj9QAs65BZLEe68mJsFFQDMxHupwwTrlrQlOKSmfC2hD6Pf7zoI-dflzqt20VfgQbGNgIWfAZZTzlgUH4Rb2JyN4M9ld9lrOJ4XP9UK8GshLe-6eulEs_tcYqZQWHiIM_-CK6vw3j6kHSNzs76MkkQPINkvoDZQ3stapzo04GXURU8yX5wwbvyQRZPUgHdLhaS7wHCfgP1JzYCwDlbEIrkryQRfj1ITZUewaqsCXY9ZHHfakr8zQ" -i http://localhost:9999/eui-auth-server/v1/user



eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibm9ybWFsLWFwcCJdLCJzY29wZSI6WyJvcGVuaWQiXSwiZXhwIjoxNDc1MDc4NjI1LCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIl0sImp0aSI6ImZmYWNjMzI4LTY2Y2QtNDhlMC04OTRkLTI5OTM3YzdlMDNkNyIsImNsaWVudF9pZCI6ImFjbWUifQ.DpvsSSQOqMmwcUweORNysNssTX9fg8G2dIZj9BFnIDjvEzkYCBCqSZCsw6YWOzDTeh9ZVfPsvzSXTonTnGzkIgXjbD6DG23NLxRiDEO-QoZbnPpGtY74e74wRLU-2nwKPCkGuJt2jjtRKISk9ajUuiURVzKXiHep3cXIlIZU3NNWFJMqseOCuNw1l5-Pe8CVyB2SYEr_35jM3i15v7tAPNRqRWI5O2zpI2YOjw9w2C-Nm8gX1u-eEm2kHKWvJFiWgEl0j1tfjzSEQyRtA-hYNjOoGoAMX1DPgeq66SHoyINyKh4_twi1driEGNzeqaUuJnjr7eQY6MNQEQgLcU6rmQ


http://stytex.de/blog/2016/02/01/spring-cloud-security-with-oauth2/


http://presos.dsyer.com/decks/oauth-rest.html#slide17
