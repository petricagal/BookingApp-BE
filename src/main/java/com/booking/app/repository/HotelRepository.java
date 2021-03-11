package com.booking.app.repository;

import com.booking.app.model.Hotel;
import org.springframework.data.repository.CrudRepository;

public interface HotelRepository extends CrudRepository<Hotel, Long> {

    Hotel findByName(String name);
}
