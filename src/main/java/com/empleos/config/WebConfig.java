package com.empleos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*

   Por defecto en un proyecto de Spring Bootcon Thymeleaflos recursos estáticos
   (css, javascript, images) están configurados en el directorio:
   src/main/resources/static
    mapear recursos estáticos de otros directorios adicionales

    Mapping URL
    Si tenemos por ejemplo la imagen “logo.png” en el directorio c:/empleos/img-vacantes/ podríamos llamar la imagen con la siguiente URL:
    http://localhost:8080/logos/logo.png

 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    //ponemos la variable que esta en el properties
    //inyectamos con value y como parametro ponemos el nombre de la propiedad
    //@Value("${empleosapp.ruta.imagenes}")
    //private String rutaImagenes; //esta variable


    @Value("${empleosapp.ruta.imagenes}")
    private String rutaImagenes;

    @Value("${empleosapp.ruta.cv}")
    private String rutaCv;

    public void addResourceHandlers(ResourceHandlerRegistry registry) {


        // Configuración de los recursos estáticos (imagenes de las vacantes)
        //registry.addResourceHandler("/logos/**").addResourceLocations("file:c:/empleos/img-vacantes/"); // Windows
        //registry.addResourceHandler("/logos/**").addResourceLocations("file:/empleos/img-vacantes/"); // Linux
        registry.addResourceHandler("/logos/**").addResourceLocations("file:"+rutaImagenes);

        // Configuración de los recursos estáticos (archivos de los CV)
        //registry.addResourceHandler("/cv/**").addResourceLocations("file:c:/empleos/files-cv/"); // Windows
        //registry.addResourceHandler("/cv/**").addResourceLocations("file:/empleos/files-cv/");
        registry.addResourceHandler("/cv/**").addResourceLocations("file:" + rutaCv); // Linux








        // Configuración de los recursos estáticos (imagenes de las vacantes)
        // registry.addResourceHandler("/logos/**").addResourceLocations("file:/empleos/img-vacantes/"); // Linux
        // registry.addResourceHandler("/logos/**").addResourceLocations("file:c:/empleos/img-vacantes/"); // Windows

        //registry.addResourceHandler("/logos/**").addResourceLocations("file:" + rutaImagenes);


        // Configuración de los recursos estáticos (archivos de los CV)
        //registry.addResourceHandler("/cv/**").addResourceLocations("file:"+ rutaCv); // Linux
        //registry.addResourceHandler("/cv/**").addResourceLocations("file:c:/empleos/files-cv/"); // Windows
        //registry.addResourceHandler("/cv/**").addResourceLocations("file:/empleos/files-cv/");


    }
}
