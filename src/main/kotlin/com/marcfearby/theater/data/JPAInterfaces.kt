package com.marcfearby.theater.data

import com.marcfearby.theater.domain.Performance
import com.marcfearby.theater.domain.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository: JpaRepository<Seat, Long>

interface PerformanceRepository: JpaRepository<Performance, Long>