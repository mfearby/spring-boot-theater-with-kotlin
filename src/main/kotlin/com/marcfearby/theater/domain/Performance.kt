package com.marcfearby.theater.domain

import javax.persistence.*

@Entity
data class Performance(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val title: String
) {
    @OneToMany(mappedBy = "performance")
    lateinit var bookings: List<Booking>

    override fun toString(): String = "Performance($id): $title"
}