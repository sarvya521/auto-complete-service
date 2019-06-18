# Configurations

### Swagger

For Configuration - Refer `com.ac.config.Swagger2Config` 

For API Documentation - Refer `com.ac.web.controller.AutoCompleteApiController`

Below are the required dependencies

```sh
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger2</artifactId>
  <version>2.9.2</version>
</dependency>
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger-ui</artifactId>
  <version>2.9.2</version>
</dependency>
```

### Log4j2
For Configuration - Refer `log4j2.yml`. 

Below are the required dependencies

```sh
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter</artifactId>
	<exclusions>
		<exclusion>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-logging</artifactId>
		</exclusion>
	</exclusions>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
<!-- for log4j2.yml, need jackson-databind and jackson-dataformat-yaml -->
<!-- spring-boot-starter-web -> spring-boot-starter-json -> jackson-databind -->
<!-- included by spring boot
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId> 
	<artifactId>jackson-databind</artifactId>
</dependency> -->
<dependency>
	<groupId>com.fasterxml.jackson.dataformat</groupId>
	<artifactId>jackson-dataformat-yaml</artifactId>
</dependency>
```

### LoggerUtilities
Below are the required dependencies

```sh
<dependency>
  <groupId>org.apache.commons</groupId>
  <artifactId>commons-collections4</artifactId>
  <version>4.3</version>
</dependency>
<dependency>
  <groupId>org.owasp.encoder</groupId>
  <artifactId>encoder</artifactId>
  <version>1.2.2</version>
</dependency>
```

### Lombok
Below is the required dependency

```sh
<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok</artifactId>
  <optional>true</optional>
</dependency>
```
To install lombok eclipse plug-in, Refer [Lombok Plugin Setup Doc](./lombok-plugin.md)

### Junit5
Below are the required dependencies

```sh
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-test</artifactId>
  <scope>test</scope>
  <exclusions>
    <exclusion>
      <groupId>org.junit.vintage</groupId>
      <artifactId>junit-vintage-engine</artifactId>
    </exclusion>
    <exclusion>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
    </exclusion>
  </exclusions>
</dependency>
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter-api</artifactId>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter-engine</artifactId>
  <scope>test</scope>
</dependency>
```

### Spring Cloud Config
For Configuration - Refer `bootstrap.yml`

Below are the required dependencies

```sh
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

### Circuit Breaker
Below are the required dependencies

```sh
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>
```

### Spring boot maven plugin
Add below plugin configuration in pom.xml

```sh
<plugin>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-maven-plugin</artifactId>
  <executions>
	<execution>
	  <goals>
	    <goal>build-info</goal>
	  </goals>
	</execution>
  </executions>
</plugin>
```

### Editor Config maven plugin
Add below plugin configuration in pom.xml

```sh
<plugin>
  <groupId>org.ec4j.maven</groupId>
  <artifactId>editorconfig-maven-plugin</artifactId>
  <version>0.0.10</version>
  <executions>
    <execution>
      <id>check</id>
      <phase>verify</phase>
      <goals>
        <goal>check</goal>
      </goals>
    </execution>
  </executions>
  <configuration>
    <excludes>
      <!-- Note that maven submodule directories and many non-source 
        file patterns are excluded by default -->
      <!-- see https://github.com/ec4j/editorconfig-maven-plugin/blob/master/ec4j-lint-api/src/main/java/org/ec4j/maven/lint/api/Constants.java#L37 -->
      <!-- You can exclude further files from processing: -->
      <exclude>src/main/**/*.html</exclude>
      <exclude>docs/</exclude>
    </excludes>
    <!-- All files are included by default: <includes> <include>**</include> </includes> -->
  </configuration>
</plugin>
```
Add eclipse plug-in, Refer [EditorConfig](./java-code-format.md)

### JavaDocs maven plugin
Add below plugin configuration in pom.xml

```sh
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-javadoc-plugin</artifactId>
	<configuration>
		<destDir>docs/javadocs</destDir>
	</configuration>
	<executions>
		<execution>
			<id>attach-javadocs</id>
			<goals>
				<goal>jar</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```
