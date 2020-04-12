package edu.eci.arsw.uniwheels.config;

import edu.eci.arsw.uniwheels.services.AuthServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.activation.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new AuthServices();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(encodePassword());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider()).userDetailsService(userDetailsService()).passwordEncoder(encodePassword());

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .logout()
                .logoutSuccessUrl("/webapp/Principal/index.html")
                .and()
                .csrf().disable()
                .cors().disable()
                .formLogin().loginPage("/webapp/Inicio/inicio.html").loginProcessingUrl("/login")
                .defaultSuccessUrl("/webapp/Menu/menu.html")
                .permitAll();

        http
                .authorizeRequests()
                .antMatchers("/uniwheels/**","/webapp/Carro/**","/webapp/Conductor/**",
                        "/webapp/Pasajero/**","/webapp/Menu/**","/webapp/Admin/**").authenticated().anyRequest().permitAll();

        //http.formLogin();
        //http.loginPage("/login").permitedAll();


    }


    @Bean
    public BCryptPasswordEncoder encodePassword(){
        return new BCryptPasswordEncoder();
    }
}
