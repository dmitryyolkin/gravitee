# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.1/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.1/gradle-plugin/packaging-oci-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.1/reference/web/servlet.html)
* [Consul Discovery](https://docs.spring.io/spring-cloud-consul/reference/discovery.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### Example Rest controller
* Controllers
  * localhost:8080/hello-world/
* health-check
  * GET localhost:8080/actuator/health
* Open-API
  * swagger schema -> http://localhost:8080/v3/api-docs
  * swagger UI -> http://localhost:8080/swagger-ui/index.html
  * Help -> https://www.baeldung.com/spring-rest-openapi-documentation

### Consul 
* Consul deployment
  * Integration doc https://cloud.spring.io/spring-cloud-consul/reference/html/
  * Prerequisites
    * Deploy consul on local machine -> https://developer.hashicorp.com/consul/tutorials/archive/docker-container-agents
      * Please use `docker pull hashicorp/consul:latest` because `consul:latest` is not available anymore
      * And use `hashicorp/consul` instead of `consul` in instructions above
      * Run consul server
      ```
      docker run \
      -d \
      -p 8500:8500 \
      -p 8600:8600/udp \
      --name=badger \
      hashicorp/consul agent -server -ui -node=server-1 -bootstrap-expect=1 -client=0.0.0.0
      ```
      * Go to Consul UI http://localhost:8500/ 

### Run from Docker
* Build docker image 
  * go to project root and execute `./gradlew clean build`
  * as a result `build/libs/gravitee-example.jar` will be built
* Run docker image based on Docker compose
  * If you run from docker-compose - no need to run Consul separately (based on instruction above) 
  * run -> `docker compose -f docker-compose.yml up --force-recreate`
    * Go with service -> http://localhost:8080/hello-world
    * Go to Consul UI -> http://localhost:8500/ui
      * `consul` hostname is set in Docker compose
    * Go to GragiteeIO Api_management -> `http://localhost:8084/`
  * stop -> `docker compose -f docker-compose.yml down`