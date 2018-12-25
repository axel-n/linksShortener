package com.example.links_shortener.config;

import com.example.links_shortener.core.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class MultiHttpSecurityCustomConfig {

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private static AuthenticationEntryPoint restAuthenticationEntryPoint;

        // если изменить http коды, web форма тоже меняется
        // change http code for fail (to 401)
        /*@Autowired
        public static SimpleUrlAuthenticationFailureHandler restFailureHandler;

        // change http code for success (to 200)
        @Autowired
        public static MySavedRequestAwareAuthenticationSuccessHandler restSuccessHandler;

        @Bean
        public SimpleUrlAuthenticationFailureHandler restFailureHandler() {
            restFailureHandler = new SimpleUrlAuthenticationFailureHandler();
            return restFailureHandler;
        }

        @Bean
        public MySavedRequestAwareAuthenticationSuccessHandler restSuccessHandler() {
            restSuccessHandler = new MySavedRequestAwareAuthenticationSuccessHandler();
            return restSuccessHandler;
        }*/

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(restAuthenticationEntryPoint)
                    .and()
                .authorizeRequests()
                    .antMatchers("/api/link").permitAll()
                    .antMatchers("/api/user").hasRole("USER")
                    .and()
                .formLogin()
                    //.loginPage("/api/login")
                    //.successHandler(restSuccessHandler)
                    //.failureHandler(restFailureHandler)
                    .and()
                .logout();
        }
    }

    @Configuration
    @Order(1)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {


        @Override
        public void configure(final WebSecurity web) {
            web.ignoring().antMatchers("/h2-console/**");
        }

        protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/user/registration").permitAll()
                    .antMatchers("/user/dashboard").hasRole("USER")
                    .and()
                .formLogin()
                    .loginPage("/user/login")
                    .failureForwardUrl("/user/login?error")
                    .permitAll()
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .permitAll();
        }


    }
}