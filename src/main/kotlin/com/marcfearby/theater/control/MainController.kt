package com.marcfearby.theater.control

import com.marcfearby.theater.data.PerformanceRepository
import com.marcfearby.theater.data.SeatRepository
import com.marcfearby.theater.domain.Booking
import com.marcfearby.theater.domain.Performance
import com.marcfearby.theater.domain.Seat
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

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @RequestMapping("")
    fun homePage() : ModelAndView {
        val model = mapOf(
            "bean" to CheckAvailabilityBackingBean(),
            "performances" to performanceRepository.findAll(),
            "seatNums" to 1..36,
            "seatRows" to 'A'..'O'
        )
        return ModelAndView("seatBooking", model)
    }

    @RequestMapping("checkAvailability", method = [RequestMethod.POST])
    fun checkAvailability(bean: CheckAvailabilityBackingBean): ModelAndView {
        // was this: theaterService.find(bean.selectedSeatNum, bean.selectedSeatRow)
        val selectedSeat: Seat = bookingService.findSeat(bean.selectedSeatNum, bean.selectedSeatRow)!!
        val selectedPerformance = performanceRepository.findById(bean.selectedPerformance!!).get()

        bean.seat = selectedSeat
        bean.performance = selectedPerformance
        bean.available = bookingService.isSeatFree(selectedSeat, selectedPerformance)

        //if (!bean.available!!)
        bean.available?.takeIf { !it }.let {
            bean.booking = bookingService.findBooking(selectedSeat, selectedPerformance)
        }

//        bean.result = "Seat $selectedSeat is " + if (result) "available" else "booked"

        // return modelandview similar to homePage()

        val model = mapOf(
            "bean" to bean,
            "performances" to performanceRepository.findAll(),
            "seatNums" to 1..36,
            "seatRows" to 'A'..'O'
        )

        return ModelAndView("seatBooking", model)
    }

    @RequestMapping("booking", method = [RequestMethod.POST])
    fun bookASeat(bean: CheckAvailabilityBackingBean): ModelAndView {
        val booking = bookingService.reserveSeat(bean.seat!!, bean.performance!!, bean.customerName)
        return ModelAndView("bookingConfirmed", "booking", booking)
    }

//    // Use only once to setup the initial table with data
//    @RequestMapping("bootstrap")
//    fun createInitialData(): ModelAndView {
//        val seats = theaterService.seats
//        seatRepository.saveAll(seats)
//        return homePage()
//    }

}


class CheckAvailabilityBackingBean() {
    var selectedSeatNum: Int = 1
    var selectedSeatRow: Char = 'A'
    var selectedPerformance: Long? = null
    var customerName: String = ""
    var available: Boolean? = null

    var seat: Seat? = null
    var performance: Performance? = null
    var booking: Booking? = null
}