package com.empleos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EmpleosApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpleosApplication.class, args);
        //System.out.println(new BCryptPasswordEncoder().encode("alex"));
    }

}
