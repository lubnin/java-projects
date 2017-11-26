package ru.rti.holidays.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import ru.rti.holidays.utility.GlobalConstants;

import javax.annotation.Resource;

/**
 * Main Spring Security configuration class
 */
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
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(GlobalConstants.URL_PATH_LOGIN))
                .accessDeniedPage("/accessDenied")
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/VAADIN/**",
                        "/PUSH/**",
                        "/UIDL/**",
                        GlobalConstants.URL_PATH_LOGIN,
                        GlobalConstants.URL_PATH_LOGIN + "/**",
                        "/error/**",
                        "/accessDenied/**",
                        "/vaadinServlet/**").permitAll()
                .antMatchers(
                        "/authorized",
                        GlobalConstants.URL_PATH_MAIN_PAGE, GlobalConstants.URL_PATH_MAIN_PAGE + "/**")
                .fullyAuthenticated()
                //.antMatchers("/admin", "/admin/**")
                //.hasAuthority("ADMIN")
                .and()
                .logout()
                .logoutUrl(GlobalConstants.URL_PATH_LOGOUT)
                .logoutSuccessUrl(GlobalConstants.URL_PATH_LOGIN)
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
