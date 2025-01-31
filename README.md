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

### Gravitee

* Gravitee API
  * Docs and models can be found at https://gravitee-io-labs.github.io/mapi-v2-docs-openapi-apis 

### Run from Docker
* Build docker image 
  * go to project root and execute `./gradlew clean build`
  * as a result `build/libs/gravitee-example.jar` will be built
* Run docker image based on Docker compose
  * If you run from docker-compose - no need to run Consul separately (based on instruction above) 
  * run -> `docker compose -f docker-compose.yml up --force-recreate`
    * Please keep attention: if you run docker compose 1st time then you need to do as follows
      * please comment `service-helloworld` service in `docker-compose.yml`
      * go to Gragitee Api Management -> `http://localhost:8084/
        * go to Organization -> Users tab
        * go to `admin` (or other user) user and click `Generate personal token`
        copy this token and insert it into `docker-compose.yml` in `GRAVITEE_API_KEY` arg
      * then uncomment `service-helloworld` service in `docker-compose.yml` and re-run everything again 
    * Go to Consul UI -> http://localhost:8500/ui
    * Go to Gragitee Api Management -> `http://localhost:8084/`
    * You can access to /hello-world controller 
      * via hello-service directly -> http://localhost:8080/hello-world
      * via Gravitee Api Gateway -> http://localhost:8082/hello-world
        * + Hello-world service is registered in Gragitee Api Management -> APIs tab
        + Monitor hello-world logs can be found with `docker logs -f --tail 20 gravitee-service-helloworld-1`
  * stop -> `docker compose -f docker-compose.yml down`