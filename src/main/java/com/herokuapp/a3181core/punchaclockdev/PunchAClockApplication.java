package com.herokuapp.a3181core.punchaclockdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PunchAClockApplication {

    /**
     * SpringBoot起動メソッド
     *
     * @param args : 起動時のパラメータ
     */
    public static void main(String[] args) {
        SpringApplication.run(PunchAClockApplication.class, args);
    }
}
