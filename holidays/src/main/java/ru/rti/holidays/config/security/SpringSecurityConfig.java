package ru.rti.holidays.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.annotation.Resource;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource(name = "authService")
    private UserDetailsService userDetailsService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {

/*        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/#!LoginPage").permitAll()
                .antMatchers("/#!AdminMain").hasAnyRole("ADMIN")
                .antMatchers("/#!EmployeeHolidays").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/#!LoginPage")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);*/
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                .accessDeniedPage("/accessDenied")
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/VAADIN/**",
                        "/PUSH/**",
                        "/UIDL/**",
                        "/login",
                        "/login/**",
                        "/error/**",
                        "/accessDenied/**",
                        "/vaadinServlet/**").permitAll()
                .antMatchers(
                        "/authorized",
                        "/main", "/main/**")
                .fullyAuthenticated()
                //.antMatchers("/admin", "/admin/**")
                //.hasAuthority("ADMIN")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true);
    }

    @Bean
    public DaoAuthenticationProvider createDaoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
