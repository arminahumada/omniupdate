package com.omniupdate.addressbook;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@Slf4j
public class DataLoaderService {

    @Autowired
    private ContactRepo contactRepo;

    public enum HEADERS  {
        first_name,
        last_name,
        company_name,
        address,
        city,
        county,
        state,
        zip,
        phone1,
        phone2,
        email,
        web
    }


    /**
     * Load sample data on Application boot
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception {

        log.info("Loading contacts from us-500.csv");
        Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/us-500.csv"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader(HEADERS.class).withSkipHeaderRecord());

        csvParser.getRecords().stream()
                .map(record -> Contact.builder()
                        .firstName(record.get(HEADERS.first_name))
                        .lastName(record.get(HEADERS.last_name))
                        .address(record.get(HEADERS.address))
                        .phoneNumber(record.get(HEADERS.phone1))
                        .email(record.get(HEADERS.email))
                        .build())
                .forEach(contactRepo::save);

        log.info("Finished loading contacts");

    }
}
