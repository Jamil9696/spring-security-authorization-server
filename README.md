# Getting Started

### first steps

* execute mvn clean, install
* copy and paste environment string in run configuration:
  DATASOURCE_URL=jdbc:postgresql://localhost:5432/resource_user;DATASOURCE_PASSWORD=superSecretPassword;DATASOURCE_USER=admin;FLYWAY_USER=admin;FLYWAY_PASSWORD=superSecretPassword;ISSUER_URI=http://127.0.0.1:9000;CLIENT=client;CLIENT_SECRET=secret;REDIRECT_URL=http://127.0.0.1:3000/authorized;ALLOWED_ORIGIN=http://127.0.0.1:3000;ALLOWED_HEADER=*;ALLOWED_METHOD=*
* run docker-compose-postgres.yml file
* build project
* open JShell console and generate code challenge and verifier (code in code-challenge-and-verifier.txt)
* in AuthorizationServerConfig Class, replace code challenge and verifier value in predefined urls with your own generated code
* run application

### How to use it
* in authconfig package, there are different Config Files which will be loaded using Spring Profiles
* Each Config file instantiates required bean objects and put it into the spring context to make them globally accessible (singleton pattern) 
* The number of required bean objects depends on the current chosen spring profile (see in application.yml file)
* This authorization server will provide different ways of authentication for documentation purpose
* Ways to authenticate are: via jwt (globally and resource role mapping), ldap authentication, 
and in a hybrid way (for example using UserDetailsService and ldap simultaneously, not yet implemented)

##### provided user 
* Bob@gmail.com
* Password: Hallo Bob

### Design architecture 
##### How to use the spring security framework


![img.png](img.png)


### for more information on this sketch:
* Book: Spring Security in action
* author: Laurentiu Spilca 
* chapter: Hello Spring Security -> 2.2 Which are the default configurations?
* link: https://learning.oreilly.com/library/view/spring-security-in/9781617297731/?ar=

### what is missing 

+ clients are currently provided at runtime by the authorization server but  
 must be stored in a database (finished)
* the resource server hasn't been finished yet
* unit and integration tests are still missing
* An user interface should be added
+ Tokens should associate roles with clients (finished)
* more provider for different authentications are needed
+ ldap authentication, embedded ldap server has been implemented, but real one is not used yet (partially finished)
+ Session Management 
+ more authentication providers

### 



                                    
