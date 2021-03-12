package com.booking.app.repository;

import com.booking.app.model.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {

    Room findOne(long id_room);

    void delete(long id_room);
}
