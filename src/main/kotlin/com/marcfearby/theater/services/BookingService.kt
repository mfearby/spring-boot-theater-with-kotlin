package com.marcfearby.theater.services

import com.marcfearby.theater.domain.Seat
import org.springframework.stereotype.Service

@Service
class BookingService() {

    fun isSeatFree(seat: Seat): Boolean {
        return true
    }

}