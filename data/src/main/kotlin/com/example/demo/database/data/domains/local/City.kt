package com.example.demo.database.data.domains.local

import com.example.demo.database.data.domains.Region
import javax.persistence.*

@Entity
@Table(name = "cities")
data class City(
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id: Int = -1,
  val city: String = "",
  @Enumerated(EnumType.STRING)
  val region: Region = Region.UNKNOWN,
  val country: String = "",
  val area: Double = 0.0,
  val population: Long = 0
)