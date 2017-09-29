package de.springboot;

import de.springboot.DataBase.BaseControler;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@ComponentScan
@EnableAutoConfiguration
public class Application {



    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        BaseControler.initialization();


    }
}
