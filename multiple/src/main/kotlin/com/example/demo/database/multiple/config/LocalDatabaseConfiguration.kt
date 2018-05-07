package com.example.demo.database.multiple.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource


@Configuration
@EnableJpaRepositories(
  basePackages = ["com.example.demo.database.data.repository.local"],
  entityManagerFactoryRef = "localEntityManagerFactory",
  transactionManagerRef = "localTransactionManager"
)
@EnableTransactionManagement
class LocalDatabaseConfiguration {
  @Bean
  @ConfigurationProperties(prefix = "datasource.local")
  fun localDataSource(): DataSource {
    return DataSourceBuilder.create().build()
  }

  @Bean
  fun localEntityManagerFactory(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {
    return builder
      .dataSource(localDataSource())
      .packages("com.example.demo.database.data.domains.local")
      .persistenceUnit("localPersistenceUnit")
      .build()
  }

  @Bean
  fun localTransactionManager(@Qualifier("localEntityManagerFactory") factory: EntityManagerFactory): JpaTransactionManager {
    return JpaTransactionManager(factory)
  }

}