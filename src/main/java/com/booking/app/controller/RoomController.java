package com.booking.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.SortedSet;
import java.util.TreeSet;

@Controller
@RequestMapping(value="/hotels")
public class RoomController {

    @Autowired
    HotelRepository hotels;

    @Autowired
    RoomTypeRepository roomTypes;

    @Autowired
    RoomRepository rooms;

    @Autowired
    BookingRepository bookings;

    // POST /hotels/{id}/rooms/ - creates a new room
    @RequestMapping(value="{id}/rooms", method=RequestMethod.POST)
    @AllowedForManageHotel
    public String saveRoom(@PathVariable("id") long id, @ModelAttribute Room room, Model model) {
        Hotel hotel = hotels.findOne(id);
        room.setHotel(hotel);
        rooms.save(room);
        return "redirect:/hotels/"+id+"/rooms";
    }

    // GET  /hotels/{id}/rooms/ - show the list of rooms of the hotel
    @RequestMapping(value="{id}/rooms", method= RequestMethod.GET)
    @AllowedForManageHotel
    public String showRooms(@PathVariable("id") long id, Model model) {
        Hotel hotel = hotels.findOne(id);
        Map<Long, Room> hotel_rooms = hotel.getRooms();
        Map<Integer, Room> rooms = new HashMap<Integer, Room>();

        for(Long entry : hotel_rooms.keySet()){
            Room r = hotel_rooms.get(entry);
            rooms.put(Integer.parseInt(r.getRoom_number()), r);
        }
        List<Room> orderedRooms = new ArrayList<Room>();
        SortedSet<Integer> orderedSet = new TreeSet<Integer>(rooms.keySet());
        for(Integer key : orderedSet)
            orderedRooms.add(rooms.get(key));

        model.addAttribute("hotel", hotel);
        model.addAttribute("orderedRooms",orderedRooms);
        return "rooms/hotel-rooms";
    }
    @RequestMapping(value="{id}/rooms/{id_room}/remove", method=RequestMethod.GET)
    @AllowedForManageHotel
    public String removeRoom(@PathVariable("id") long id, @PathVariable("id_room") long id_room, Model model)
    {
        Hotel hotel = hotels.findOne(id);

        for(Booking b : rooms.findOne(id_room).getBookings())
        {
            bookings.delete(b);
        }

        rooms.delete(id_room);
        model.addAttribute("hotel", hotel);
        return "redirect:/hotels/{id}/rooms";
    }
}
