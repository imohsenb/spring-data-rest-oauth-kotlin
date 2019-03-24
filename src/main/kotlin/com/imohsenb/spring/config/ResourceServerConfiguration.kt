package com.imohsenb.spring.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod


@Configuration
@EnableResourceServer
class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {

    @Autowired
    private lateinit var tokenServices: ResourceServerTokenServices

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
                .antMatchers("/users/**").hasRole("ADMIN")
                .antMatchers("/**").hasAnyRole("ADMIN", "STANDARD")
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
    }

}