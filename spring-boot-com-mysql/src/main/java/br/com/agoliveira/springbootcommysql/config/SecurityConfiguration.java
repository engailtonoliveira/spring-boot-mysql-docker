package br.com.agoliveira.springbootcommysql.config;

import br.com.agoliveira.springbootcommysql.security.CustomUserDetailsService;
import br.com.agoliveira.springbootcommysql.security.JwtAuthenticationEntryPoint;
import br.com.agoliveira.springbootcommysql.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String PATTERN_EMAIL = "/api/user/checkEmailAvailability";

    private static final String PATTERN_USERNAME = "/api/user/checkUsernameAvailability";

    private static final String PATTERN_USERS = "/api/users/**";

    private static final String PATTERN_AUTH = "/api/auth/**";

    private static final String ALL_PATTERN = "/";

    private static final String PATTERN_JS = "/**/*.js";

    private static final String PATTERN_CSS = "/**/*.css";

    private static final String PATTERN_HTML = "/**/*.html";

    private static final String PATTERN_JPG = "/**/*.jpg";

    private static final String PATTERN_SVG = "/**/*.svg";

    private static final String PATTERN_GIF = "/**/*.gif";

    private static final String PATTERN_PNG = "/**/*.png";

    private static final String PATTERN_FAVICON_ICO = "/favicon.ico";

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
  }

    @Override
    public void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN","USER")
                .antMatchers("/pessoa/*").hasAnyRole("ADMIN","USER")
                .antMatchers("/").permitAll()
                .and().formLogin();*/

        http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers(ALL_PATTERN, PATTERN_FAVICON_ICO, PATTERN_PNG, PATTERN_GIF, PATTERN_SVG, PATTERN_JPG, PATTERN_HTML, PATTERN_CSS, PATTERN_JS)
                .permitAll().antMatchers(PATTERN_AUTH)
                .permitAll().antMatchers(PATTERN_USERNAME, PATTERN_EMAIL)
                .permitAll().antMatchers(HttpMethod.GET, PATTERN_USERS)
                .permitAll().anyRequest()
                .authenticated();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
