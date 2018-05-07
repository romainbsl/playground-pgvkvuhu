package com.example.demo.database.data.repository.local

import com.example.demo.database.data.domains.local.City
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CityRepository : JpaRepository<City, Int>