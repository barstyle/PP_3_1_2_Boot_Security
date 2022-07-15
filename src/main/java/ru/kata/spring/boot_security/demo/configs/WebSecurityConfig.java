package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity // конф класс для спринг секьюрити
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserDetailsServiceImpl userDetailsService) {
        this.successUserHandler = successUserHandler;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers("/", "/index").permitAll() // полный доступ
                    .antMatchers("user/**").hasAnyRole("USER", "ADMIN") // USER or ADMIN
                    .antMatchers("admin/**").hasRole("ADMIN") // only ADMIN
                    .anyRequest().authenticated()
                .and()
                    .formLogin().successHandler(successUserHandler)
//                    .loginPage("/login")
//                    .usernameParameter("email")
//                    .passwordParameter("password")
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .permitAll();
    }

//     аутентификация inMemory

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        List<UserDetails> usersList = new ArrayList<>();
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{noop}user")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{noop}admin")
//                .roles("ADMIN")
//                .build();
//
//        usersList.add(user);
//        usersList.add(admin);
//
//        return new InMemoryUserDetailsManager(usersList);
//    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                    .passwordEncoder(passwordEncoder)
                    .withUser("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles("ADMIN")
                .and()
                    .withUser("user")
                    .password(passwordEncoder.encode("user"))
                    .roles("USER");

        auth.userDetailsService(userDetailsService);
    }


}