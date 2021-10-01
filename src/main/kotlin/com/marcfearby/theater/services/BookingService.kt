package com.marcfearby.theater.services

import com.marcfearby.theater.data.BookingRepository
import com.marcfearby.theater.data.SeatRepository
import com.marcfearby.theater.domain.Booking
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

    fun isSeatFree(seat: Seat, performance: Performance) = findBooking(seat, performance) == null

    fun findSeatOriginal(seatNum: Int, seatRow: Char): Seat? {
        val allSeats = seatRepository.findAll()
        val foundSeat = allSeats.filter {
            it.num == seatNum && it.row == seatRow
        }
        return foundSeat.firstOrNull()
    }

    fun findSeatSimpler(seatNum: Int, seatRow: Char): Seat? =
        seatRepository.findAll()
            .filter {
                it.num == seatNum && it.row == seatRow
            }
            .firstOrNull()

    fun findSeat(seatNum: Int, seatRow: Char): Seat? =
        seatRepository.findAll().firstOrNull {
            it.num == seatNum && it.row == seatRow
        }


    fun reserveSeat(seat: Seat, performance: Performance, customerName: String): Booking {
        val newBooking = Booking(id = 0, customerName = customerName)
        newBooking.seat = seat
        newBooking.performance = performance
        bookingRepository.save(newBooking)
        return newBooking
    }

    fun findBooking(selectedSeat: Seat, selectedPerformance: Performance): Booking? =
        bookingRepository.findAll()
            .filter {
                it.seat == selectedSeat && it.performance == selectedPerformance
            }
            .firstOrNull()

}