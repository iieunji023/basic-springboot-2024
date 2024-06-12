# basic-springboot-2024
Java 빅데이터 개발자 과정 Spring Boot 학습 리포지토리

## 1일차
- Spring Boot 개요

- Spring Boot 개발환경 설정
  - Java JDK 확인 > 17버전 이상
    - https://jdk.java.net/archive/
    - 시스템 속성 > 고급 > 환경변수 중 JAVA_HOME 설정
  - Visual Studio 
    - VSCodeUserSetup-x64-1.90.0.exe 아님. 설치하지 말 것
    - VSCodeSetup-x64-1.90.0.exe로 설치할 것
    - Extensions > Korean 검색, 설치
    - Extensions > Java 검색, Extensions Pack for Java 설치
      - Debugger for Java 등 6개 확장팩이 같이 설치
    - Extensions > Spring 검색, Spring Boot Extension Pack 설치
      - Spring Initializr Java Support 등 3개 확장팩 같이 설치
    - Extensions > Gradle for Java 검색, 설치
  - Gradle build tool 설치 고려
    - https://gradle.org/releases/
  - Oracle latest version Docker - 보류

- Spring Boot 프로젝트 생성
  - 메뉴 보기 > 명령 팔레트 (Ctrl + Shift + P)
    - Spring Initializr: Create a Gradle Proejct ...
    - Specify Spring Boot version: 3.2.6
    - Specify project language: Java
    - Input Group Id(도메인 주소): com.eunji(개인적 변경할 것)
    - Input Artifact Id: spring01
    - Specify packaing type: Jar
    - Specify Java version: 17
    - Search for dependencies: Selected 0 dependencies
    - 폴더 선택 Diaglog 팝업: 원하는 폴더 선택 Generate ... 버튼 클릭
    - 오른쪽 하단 Open 팝업 Open 버튼 클릭
    - Git 설정 옵션, Language Support for JAVA(TM) by Red Hat 설정 항상 버튼 클릭

  - TroubleShooting
    1. 프로젝트 생성이 진행되다 Gradle Connection Error 뜨면,
      - Extensions > Gradle for Java를 제거
      - VSCode 재시작한 뒤 프로젝트 재생성
    2. Gradle 빌드시 버전 에러로 빌드가 실패하면
      - Gradle build tool 사이트에서 에러에 표시된 버전의 Gradle bt 다운로드
      - 개발 컴퓨터에 설치
    3. ':compileJava' execution failed...
      - JDK 17 ....... error 메시지
      - JAVA JDK 잘못된 설치 x86(32bit) x64비트 혼용 설치
      - eclipse adoptium jdk 17 새로 설치, 시스템 환경설정
      - build.gradle Spring Boot Framework 버전을 다운 3.3.0 -> 3.1.5
      - VSCode 재시작

  - 프로젝트 생성 후
    - /build.gradle 확인
    - src/main/resources/application.properties(또는 .yml) 확인
    - src/java/groupId/artifactId/ Java 소스 파일 위치, 작업
    - src/main/resources/ 프로젝트 설정 파일, 웹 리소스 파일(css, javascript, html, jsp ...)
    - Spring01Application.java Run | Debug 메뉴