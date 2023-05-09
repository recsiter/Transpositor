package com.transpositor.application;

import com.transpositor.logic.FileHandler;
import com.transpositor.logic.Transposition;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan(basePackages = {"com.transpositor.controller"})
@EnableAutoConfiguration
public class TranspositorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TranspositorApplication.class, args);
    }

}
