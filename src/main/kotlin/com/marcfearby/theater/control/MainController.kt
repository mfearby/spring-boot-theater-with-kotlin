package com.marcfearby.theater.control

import com.marcfearby.theater.data.SeatRepository
import com.marcfearby.theater.services.BookingService
import com.marcfearby.theater.services.TheaterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

// Exercises for chapter 16 and beyond for the "Build Spring Boot Apps with
// the Kotlin Programming Language" course on Udemy by Matt Greencroft

@Controller
class MainController {

    @Autowired
    lateinit var theaterService: TheaterService

    @Autowired
    lateinit var bookingService: BookingService

    @Autowired
    lateinit var seatRepository: SeatRepository

    @RequestMapping("")
    fun homePage() : ModelAndView {
        return ModelAndView("seatBooking", "bean", CheckAvailabilityBackingBean())
    }

    @RequestMapping("checkAvailability", method=arrayOf(RequestMethod.POST))
    fun checkAvailability(bean: CheckAvailabilityBackingBean): ModelAndView {
        val selectedSeat = theaterService.find(bean.selectedSeatNum, bean.selectedSeatRow)
        val result = bookingService.isSeatFree(selectedSeat)
        bean.result = "Seat $selectedSeat is " + if (result) "available" else "booked"
        return ModelAndView("seatBooking", "bean", bean)
    }

    // Use only once to setup the initial table with data
//    @RequestMapping("bootstrap")
//    fun createInitialData(): ModelAndView {
//        val seats = theaterService.seats
//        seatRepository.saveAll(seats)
//        return homePage()
//    }

}


class CheckAvailabilityBackingBean() {
    val seatNums = 1..36
    val seatRows = 'A'..'O'
    var selectedSeatNum: Int = 1
    var selectedSeatRow: Char = 'A'
    var result: String = ""
}