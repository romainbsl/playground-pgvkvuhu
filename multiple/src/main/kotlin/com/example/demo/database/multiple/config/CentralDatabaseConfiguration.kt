package com.example.demo.database.multiple.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource


@Configuration
@EnableJpaRepositories(
  basePackages = ["com.example.demo.database.data.repository.central"],
  entityManagerFactoryRef = "centralEntityManagerFactory",
  transactionManagerRef = "centralTransactionManager"
)
@EnableTransactionManagement
class CentralDatabaseConfiguration {
  @Bean
  @Primary
  @ConfigurationProperties(prefix = "datasource.central")
  fun centralDataSource(): DataSource {
    return DataSourceBuilder.create().build()
  }

  @Bean
  @Primary
  fun centralEntityManagerFactory(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {
    return builder
      .dataSource(centralDataSource())
      .packages("com.example.demo.database.data.domains.central")
      .persistenceUnit("centralPersistenceUnit")
      .build()
  }

  @Bean
  @Primary
  fun centralTransactionManager(@Qualifier("centralEntityManagerFactory") factory: EntityManagerFactory): JpaTransactionManager {
    return JpaTransactionManager(factory)
  }

}