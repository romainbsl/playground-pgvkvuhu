package com.example.demo.database.data.repository.central

import com.example.demo.database.data.domains.central.CitiesDirectory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CitiesDirectoryRepository : JpaRepository<CitiesDirectory, Int>