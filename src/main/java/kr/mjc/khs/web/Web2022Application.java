package kr.mjc.khs.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Web2022Application {

    public static void main(String[] args) {
        SpringApplication.run(Web2022Application.class, args);
    }

}
