# ecoFriend
___
## build.gradle
### Email  
    implementation 'org.springframework.boot:spring-boot-starter-mail'

### Swagger    
    implementation 'io.springfox:springfox-boot-starter:3.0.0'    
    implementation 'io.springfox:springfox-swagger-ui:3.0.0'

### JSON    
    implementation 'org.json:json:20200518'

### JWT    
    implementation 'io.jsonwebtoken:jjwt:0.9.1'
### STOMP
    implementation 'org.springframework.boot:spring-boot-starter-websocket'
___
## guide
### _com/kh/ecoFriend/api/email/MailConfig.java_
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.naver.com");

        // 발송할 이메일 입력 : String
        javaMailSender.setUsername("example@example.com"); 

        // 발급받은 앱 비밀번호 입력 : String
        javaMailSender.setPassword("AppPassword"); 

        // smtp port 입력 : int
        javaMailSender.setPort(xxx);

        javaMailSender.setJavaMailProperties(getMailProperties());
        return javaMailSender;
    }
### _com/kh/ecoFriend/util/Common.java_
    // 오라클 설정 정보 (JDBC 연결)
    private static String ORACLE_URL = "jdbc:oracle:thin:@localhost:1521/xe"; // 데이터베이스 url
    private static String ORACLE_ID = "scott"; // 오라클 계정
    private static String ORACLE_PW = "tiger"; // 비밀번호
    private static String ORACLE_DRV = "oracle.jdbc.driver.OracleDriver"; // jdbc 드라이버