package com.example.demo.database.multiple

import com.example.demo.database.data.repository.central.CitiesDirectoryRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [MultipleApplication::class])
class CentralTest {

  @Autowired
  lateinit var citiesDirectoryRepository: CitiesDirectoryRepository

  @Test
  fun readCentral() {
    assertThat(citiesDirectoryRepository.findAll())
      .isNotNull
      .isNotEmpty
      .hasSize(15)
  }

}
