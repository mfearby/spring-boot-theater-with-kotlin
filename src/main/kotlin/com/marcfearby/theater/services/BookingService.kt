package com.marcfearby.theater.services

import com.marcfearby.theater.data.BookingRepository
import com.marcfearby.theater.data.SeatRepository
import com.marcfearby.theater.domain.Performance
import com.marcfearby.theater.domain.Seat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookingService() {

    @Autowired
    lateinit var bookingRepository: BookingRepository

    @Autowired
    lateinit var seatRepository: SeatRepository

    fun isSeatFree(seat: Seat, performance: Performance): Boolean {
        val bookings = bookingRepository.findAll()
        val matchedBookings = bookings.filter {
            it.seat == seat && it.performance == performance
        }
        return matchedBookings.isEmpty()
    }

    fun findSeat(seatNum: Int, seatRow: Char): Seat? {
        val allSeats = seatRepository.findAll()
        val foundSeat = allSeats.filter {
            it.num == seatNum && it.row == seatRow
        }
        return foundSeat.firstOrNull()
    }

}