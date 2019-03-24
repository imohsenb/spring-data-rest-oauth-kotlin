package com.imohsenb.spring.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.beans.PropertyVetoException
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = ["com.imohsenb.spring.repository"])
class DatasourceConfig {

    @Bean
    @Throws(PropertyVetoException::class)
    fun datasource(): DataSource {
        val builder = EmbeddedDatabaseBuilder()

        return builder
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/script/schema.sql")
                .addScript("db/script/data.sql")
                .build()
    }

    @Bean
    @Throws(PropertyVetoException::class)
    fun entityManagerFactory(@Qualifier("datasource") ds: DataSource): LocalContainerEntityManagerFactoryBean {
        val entityManagerFactory = LocalContainerEntityManagerFactoryBean()
        entityManagerFactory.dataSource = ds
        entityManagerFactory.setPackagesToScan(*arrayOf("com.imohsenb.spring.model"))
        val jpaVendorAdapter = HibernateJpaVendorAdapter()
        entityManagerFactory.jpaVendorAdapter = jpaVendorAdapter
        return entityManagerFactory
    }

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory
        return transactionManager
    }
}