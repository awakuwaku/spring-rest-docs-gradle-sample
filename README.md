# spring-rest-docs-gradle-sample
Spring REST Docs の Gradle Build Sample


# 前提
-   JDK
    - [OpenJDK (11.0.x)](http://openjdk.java.net/)
-   Build Tool
    - [Gradle (5.0)](https://gradle.org/)
-   Framework
    - [Spring Boot (2.1.1.RELEASE)](https://spring.io/projects/spring-boot)
    - [Spring REST Docs (2.0.2.RELEASE)](https://spring.io/projects/spring-restdocs)
-   IDE
    -   [Eclipse (4.9.x)](http://www.eclipse.org/home/index.php) + [Spring Tools](https://marketplace.eclipse.org/content/spring-tool-suite-sts-eclipse)


# 利用方法
Gradleにて `asciidoctor` の `task` を実行することで、 `build/asciidoc/html5` 配下にAPIドキュメントのhtmlファイルを生成する。
  ```shell
  ./gradlew clean asciidoctor
  ```

