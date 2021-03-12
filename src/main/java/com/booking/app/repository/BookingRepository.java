package com.booking.app.repository;

import com.booking.app.model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {

    Booking findOne(long booking_id);
}
