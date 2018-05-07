package com.example.demo.database.multiple

import com.example.demo.database.data.repository.local.CityRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [MultipleApplication::class])
class LocalTest {

  @Autowired
  lateinit var cityRepository: CityRepository

  @Test
  fun readCentral() {
    assertThat(cityRepository.findAll())
      .isNotNull
      .isNotEmpty
      .hasSize(8)
  }
}
