package com.example.demo.database.data.domains.central

import com.example.demo.database.data.domains.Region
import javax.persistence.*


@Entity
@Table(name = "cities_directory")
data class CitiesDirectory(
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id: Int = -1,
  val city: String = "",
  @Enumerated(EnumType.STRING)
  val region: Region = Region.UNKNOWN
)