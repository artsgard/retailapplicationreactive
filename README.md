# retailapplicationreactive (work in progress)

This small application collaborates with the retailapplication and the retailapplicationkafka of this repo.

In order to git-clone, mvn clean install, and mvn spring-boot:run this application one has to de the following:

1) Create the db PRODUCT_PURCHASE_DB at Postgres db
2) change the profile into prod at application.properties of the retailapplication
3) adjust the user and password at DBConfig of the retailapplication at: https://github.com/artsgard/retailapplication/blob/master/src/main/java/com/artsgard/retailapplication/DBConfig.java
4) Use the scripts at resources: https://github.com/artsgard/retailapplication/tree/master/src/main/resources
5) or create the DB plus data by changing the config (after creating change  spring.jpa.generate-ddl=false spring.jpa.hibernate.ddl-auto=none spring.datasource.initialization-mode=never at: https://github.com/artsgard/retailapplication/blob/master/src/main/resources/application-prod.properties
