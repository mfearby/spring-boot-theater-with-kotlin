package com.marcfearby.theater.control

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

// Exercises for chapter 16 and beyond for the "Build Spring Boot Apps with
// the Kotlin Programming Language" course on Udemy by Matt Greencroft

@Controller
class MainController {

    @RequestMapping("")
    fun homePage() : ModelAndView {
        return ModelAndView("seatBooking", "bean", CheckAvailabilityBackingBean())
    }

}


class CheckAvailabilityBackingBean() {
    val seatNums = 1..36
    val seatRows = 'A'..'O'
    var selectedSeatNum: Int = 1
    var selectedSeatRow: Char = 'A'
    var result: String = ""
}