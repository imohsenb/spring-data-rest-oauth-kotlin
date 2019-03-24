package com.imohsenb.spring.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value


@Configuration
@EnableResourceServer
class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {

    @Autowired
    private val tokenServices: ResourceServerTokenServices? = null

    @Value("\${security.jwt.resource-ids}")
    private val resourceIds: String? = null

    @Throws(Exception::class)
    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId(resourceIds).tokenServices(tokenServices)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/**", "/api-docs/**").permitAll()
                .antMatchers("/springjwt/**").authenticated()
                .and()
                .headers()
                .frameOptions()
                .disable()
                .and().csrf().ignoringAntMatchers("/console/**")//don't apply CSRF protection to /h2-console
    }

//    @Throws(Exception::class)
//    override fun configure(http: HttpSecurity) {
////        http.requestMatchers()
////                .antMatchers(SECURED_PATTERN).and().authorizeRequests()
////                .antMatchers(HttpMethod.POST, SECURED_PATTERN).access(SECURED_WRITE_SCOPE)
////                .anyRequest().access(SECURED_READ_SCOPE)
//        http.requestMatchers().anyRequest().and().authorizeRequests()
//                .antMatchers("/**").permitAll()
//    }

    companion object {

        private val RESOURCE_ID = "resource-server-rest-api"
        private val SECURED_READ_SCOPE = "#oauth2.hasScope('read')"
        private val SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')"
        private val SECURED_PATTERN = "/secured/**"
    }
}