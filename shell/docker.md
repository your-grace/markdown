#### Dockerfile

```text
FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

- `FROM openjdk:8-jdk-alpine`：表示使用 JDK8 为基础镜像。
- `ARG JAR_FILE=target/*.jar`：表示定义一个参数名为 JAR_FILE，值为 `target/*.jar` 的构建参数
- `COPY ${JAR_FILE} app.jar`：表示把 target 目录下的 jar 文件复制一份新的到镜像内的 app.jar 文件
- `ENTRYPOINT ["java","-jar","/app.jar"]`：表示通过 `java -jar` 的形式运行 `app.jar`，ENTRYPOINT 用来配置容器启动时的运行命令，第一个参数是运行命令，后面是一个一个参数。
#### Docker插件添加

在 pom.xml 文件中添加 Maven 的 Docker 插件。

```tex
<plugin>
    <groupId>com.spotify</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <version>1.2.2</version>
    <executions>
        <execution>
            <id>build-image</id>
            <phase>package</phase>
            <goals>
                <goal>build</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <dockerHost>http://ip:2375</dockerHost>
        <imageName>itwanger/${project.artifactId}</imageName>
        <imageTags>
            <imageTag>${project.version}</imageTag>
        </imageTags>
        <forceTags>true</forceTags>
        <dockerDirectory>${project.basedir}</dockerDirectory>
        <resources>
            <resource>
                <targetPath>/</targetPath>
                <directory>${project.build.directory}</directory>
                <include>${project.build.finalName}.jar</include>
            </resource>
        </resources>
    </configuration>
</plugin>
```
配置搞定后，接下来就是对项目进行打包，可以直接点击 Maven 面板中的 package 进行打包
- 在 `executions` 节点下添加 `docker:build`，表示在执行 `mvn:package` 打包的时候顺带构建一下 Docker 镜像。
- 在 `configuration` 节点下添加 Docker 主机的地址、镜像的名字、镜像的版本、镜像文件的目录、以及 resources 节点下配置的 jar 包位置和名称等。
#### docker命令
```bash
#查看镜像
docker images
#启动镜像容器
docker run -d --name itwanger -p 8080:8080 itwanger/springboot-docker:0.0.1-SNAPSHOT
```

- `-d`: 后台运行容器，并返回容器ID；
- `--name`: 为容器指定一个名称 itwanger；
- `-p`: 指定端口映射，格式为：主机(宿主)端口:容器端口
