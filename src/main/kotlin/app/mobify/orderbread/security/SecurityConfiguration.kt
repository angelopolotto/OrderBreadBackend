package app.mobify.orderbread.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder

// https://www.codementor.io/gtommee97/rest-authentication-with-spring-security-and-mongodb-j8wgh8kg7
// https://dzone.com/articles/using-session-puzzling-to-bypass-two-factor-authen
// https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt
// https://stackoverflow.com/questions/29606290/authentication-with-spring-security-spring-data-mongodb
// https://www.javabullets.com/securing-spring-data-rest-preauthorize/
// https://dzone.com/articles/securing-urls-using-springnbspsecurity
@EnableWebSecurity
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Autowired
    var userDetailsService: MongoUserDetailsService? = null
    @Autowired
    private val passwordEncoder: PasswordEncoder? = null

    @Bean
    fun authProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder)
        return authProvider
    }

    @Autowired
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.authenticationProvider(authProvider())
//        auth.inMemoryAuthentication()
//                .withUser("user").password("{noop}user").roles("USER").and()
//                .withUser("admin").password("{noop}admin").roles("USER", "ADMIN")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
//                .authorizeRequests().anyRequest().authenticated() //hasAnyRole("ADMIN", "USER")
                .authorizeRequests().antMatchers("/rest/**").authenticated()
                .and().httpBasic()
                .and().sessionManagement().disable()
    }

    /* To allow Pre-flight [OPTIONS] request from browser */
    @Throws(Exception::class)
    override fun configure(web: WebSecurity?) {
        web!!.ignoring().antMatchers(HttpMethod.OPTIONS, "/**")
    }
}