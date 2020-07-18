package com.puhov.SpringBootHibernateProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity config) throws Exception {
        config
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/blog", "/blog/about", "/blog/add", "/blog/{id}", "/blog/{id}/edit", "/blog/{id}/remove").hasRole("EDITOR")
                .and()
                .formLogin().loginPage("/blog/login").defaultSuccessUrl("/blog").permitAll()
                .and()
                .logout().logoutUrl("/blog/logout").permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication()
                .withUser("user").password("password").roles("EDITOR");
    }
}
