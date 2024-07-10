package com.empleos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity {


    @Autowired
    private DataSource dataSource;

/*
    @Bean
    JdbcUserDetailsManager users(DataSource dataSource){
      JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
            return jdbcUserDetailsManager;
    }
*/

    /*
       se implementa para que spring boot reonosca nuestras tablas
       y no tome encuenta las que spring da por defecto
     */
    @Bean
    public UserDetailsManager authenticateUsers() {

        UserDetails user = User.withUsername("usuario").
                password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("password")).build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("select username, password, estatus from Usuarios where username=?");
        users.setAuthoritiesByUsernameQuery("select u.username, p.perfil from UsuarioPerfil up " +
                "inner join Usuarios u on u.id = up.idUsuario " +
                "inner join Perfiles p on p.id = up.idPerfil " +
                "where u.username = ?");
        //users.createUser(user);
        return users;
    }


    //implementa la autorizacion
    //los recursos estaticos no requieren autenticacion
    //las vistas publicas no requieren autenticacion
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/bootstrap/**", "/images/**", "/tinymce/**", "logos/**").permitAll()
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/login", "/signup", "/search", "/bcrypt", "/about","/vacantes/view/**").permitAll()

                         // Asignar permisos a URLs por ROLES
                        .requestMatchers("/solicitudes/create/**",
                                "/solicitudes/save/**").hasAuthority("USUARIO")

                        .requestMatchers("/solicitudes/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
                        .requestMatchers("/vacantes/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
                        .requestMatchers("/categorias/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
                        .requestMatchers("/usuarios/**").hasAnyAuthority("ADMINISTRADOR")
                         //todas ls demas URLs de la aplicacion requieren autenticacion
                        .anyRequest().authenticated()

                )
                .httpBasic(Customizer.withDefaults())
                //.formLogin(Customizer.withDefaults());
                .formLogin((form) -> form
                    .loginPage("/login")
                    .permitAll()

        );
        return http.build();
    }



    /**
     *  Implementación de Spring Security que encripta passwords con el algoritmo Bcrypt
     * @return
     */


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}

/*
/*

//error con este bean no muestra el login
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/bootstrap/**", "/images/**", "/tinymce/**", "logos/**").permitAll()
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/signup", "/search", "/vacantes/view/**").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()

                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }
 */






/*

    ejemplo
    @Bean
    public UserDetailsManager authenticateUsers() {

        UserDetails user = User.withUsername("devs").
                password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("password")).build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setAuthoritiesByUsernameQuery("select user_name,user_pwd,user_enabled from user where user_name=?");
        users.setUsersByUsernameQuery("select user_name,user_role from user where user_name=?");
        users.createUser(user);
        return users;
    }

 */

/*


@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource)
            .usersByUsernameQuery("select username, password, estatus from Usuarios where username=?")
            .authoritiesByUsernameQuery("select u.username, p.perfil from UsuarioPerfil up " +
                    "inner join Usuarios u on u.id = up.idUsuario " +
                    "inner join Perfiles p on p.id = up.idPerfil " +
                    "where u.username = ?");
}

*/