# OmniUpdate - SOFTWARE DEVELOPER SKILLS ASSESSMENT

Implementation of OmniUpdate skills assessment with Spring Boot and the following:
* Spring Boot / Data
* JPA / Hibernate
* H2 embeded database - implementation is rdms agnostic
* Liquibase based schema creation - See below or `src/main/resources/dbchangelog.xml`
* Test data with 500 - Test data loads with app and integration tests (`src/main/resources/us-500.csv`)
* Sample api calls with Postman - `OmniUpdate Assesment.postman_collection.json` 

## Schema Design

### contact table
```xml
        <createTable  tableName="contact">
            <column  name="id"  type="bigint" autoIncrement="true">
                <constraints  primaryKey="true"  nullable="false"/>
            </column>
            <column name="first_name" type="varchar(255)" />
            <column name="last_name" type="varchar(255)" />
            <column name="address" type="varchar(512)" />
            <column name="phone_number" type="varchar(64)" />
            <column name="email" type="varchar(320)" />
        </createTable>

        <!-- Speed up searches for last_name like 'xxx%' -->
        <createIndex tableName="contact" indexName="contact_last_name_idx">
            <column name="last_name" />
        </createIndex>
```

## Tests

```$mvn verfiy```

## Run App

```$mvn springboot:run```


