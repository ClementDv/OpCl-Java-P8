package com.tourguide.gps;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class GpsApplication {



    public static void main(String[] args) {
        // Set default locale due to DoubleParse error in GpsUtil (Provided for the Project)
        Locale.setDefault(Locale.US);

        SpringApplication.run(GpsApplication.class, args);
    }
}
