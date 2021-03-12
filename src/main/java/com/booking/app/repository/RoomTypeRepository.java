package com.booking.app.repository;

import com.booking.app.model.RoomType;
import org.springframework.data.repository.CrudRepository;

public interface RoomTypeRepository extends CrudRepository<RoomType, Long> {


    RoomType findOne(long roomType);
}
