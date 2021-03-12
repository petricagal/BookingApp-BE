package com.booking.app.controller;

import com.booking.app.model.*;
import com.booking.app.repository.*;
import com.booking.app.util.HotelNotFoundException;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

@Controller
@RequestMapping(value = "/hotels")
public class HotelController {

    @Autowired
    HotelRepository hotels;

    @Autowired
    RoomTypeRepository roomTypes;

    @Autowired
    RoomRepository rooms;

    @Autowired
    UserRepository users;

    @Autowired
    ImageRepository images;

    @Autowired
    CommentRepository comments;

    @Autowired
    BookingRepository bookings;

    // GET  /hotels 			- the list of hotels
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("hotels", hotels.findAll());
        return "hotels/index";
    }

    // GET  /hotels.json 			- the list of hotels
    @RequestMapping(method = RequestMethod.GET, produces = {"text/plain", "application/json"})
    public @ResponseBody
    Iterable<Hotel> indexJSON(Model model) {
        return hotels.findAll();
    }

    // POST /hotels         	- creates a new hotel
    @RequestMapping(method = RequestMethod.POST)
    public String saveIt(@ModelAttribute Hotel hotel, Model model) {
        hotels.save(hotel);
        model.addAttribute("hotel", hotel);
        return "redirect:/hotels";
    }

    // GET  /hotels/{id} 		- the hotel with identifier {id}
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") long id, Model model) {
        Hotel hotel = hotels.find(id);
        if (hotel == null)
            throw new HotelNotFoundException();
        Iterable<com.booking.app.model.Comment> hotel_comments = comments.getComments(id);

        model.addAttribute("booking", new Booking());
        model.addAttribute("comments", hotel_comments);
        model.addAttribute("hotel", hotel);
        model.addAttribute("hotel" ,new Comment());
        model.addAttribute("users", users.findAll());
        model.addAttribute("roomTypes", roomTypes.findAll());

        Map<Long, Room> rmap = hotel.getRooms();
        Map<RoomType, Room> rttemp = new HashMap<RoomType, Room>();

        for (Room r : rmap.values()) {
            rttemp.put(r.getType(), r);
        }

        model.addAttribute("hotelRoomTypes", rttemp);
        return "hotels/show";
    }

    // GET  /hotels/{id}.json 		- the hotel with identifier {id}
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {"text/plain", "application/json"})
    public @ResponseBody
    Hotel showJSON(@PathVariable("id") long id, Model model) {
        Hotel hotel = hotels.findOne(id);
        if (hotel == null)
            throw new HotelNotFoundException();
        return hotel;
    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable("id") long id, Model model) {
        Hotel hotel = hotels.findOne(id);
        model.addAttribute("hotel", hotel);
        model.addAttribute("users", users.findAll());
        return "hotels/edit";
    }

    // POST /hotels/{id} 	 	- update the hotel with identifier {id}
    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public String editSave(@PathVariable("id") long id, @ModelAttribute("hotel") Hotel hotel) {
        hotel.setStatus(false);
        hotels.save(hotel);
        return "redirect:/hotels/{id}";
    }


    // GET  /hotels/{id}/remove 	- removes the hotel with identifier {id}
    @RequestMapping(value = "{id}/remove", method = RequestMethod.GET)
    public String remove(@PathVariable("id") long id, Model model) {
        for (Room r : hotels.findOne(id).getRooms().values()) {
            for (Booking b : r.getBookings()) {
                bookings.delete(b);
            }
        }

        hotels.delete(hotels.findOne(id));
        return "redirect:/hotels";
    }

    // ?????
    @RequestMapping(value = "{id}/approve", method = RequestMethod.GET)
    public String approve(@PathVariable("id") long id, Model model) {
        Hotel h = hotels.findOne(id);
        h.setStatus(true);
        hotels.save(h);
        return "redirect:/admin";
    }

    @RequestMapping(value = "{id}/upload", method = RequestMethod.POST)
    public String uploadImage(@PathVariable("id") long id, Model model, @RequestParam("files") MultipartFile files[]) {

        if (files.length > 0) {
            for (int i = 0; i < files.length; i++) {

                MultipartFile file = files[i];
                try {
                    byte[] bytes = file.getBytes();
                    String path = "src/main/resources/public/static/" + file.getOriginalFilename();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(new File(path)));
                    stream.write(bytes);
                    stream.close();

                    Image image = new Image();
                    image.setHotel(hotels.findOne(id));
                    image.setInsertion_date(new Date());
                    image.setPath(file.getOriginalFilename());
                    images.save(image);

                } catch (Exception e) {
                }
            }
            return "redirect:/hotels/{id}/upload";
        }
        return "";
    }

    @RequestMapping(value = "{id}/remove_image/{id_image}", method = RequestMethod.GET)
    public String deleteImage(@PathVariable("id") long id, @PathVariable("id_image") long id_image, Model model) {
        Image image = images.findOne(id_image);
        images.delete(image);
        return "redirect:/hotels/{id}/upload";
    }


    @RequestMapping(value="/search", method=RequestMethod.GET)
    public String searchHotel(Model model, @RequestParam("searchString") String searchString)
    {
        Iterator<Hotel> ithotels = hotels.findAll().iterator();
        List<Hotel> hotelsList = new ArrayList<Hotel>();

        while(ithotels.hasNext())
        {
            Hotel h = ithotels.next();
            if(h.getName().toLowerCase().contains(searchString.toLowerCase()))
                hotelsList.add(h);
        }

        model.addAttribute("hotels", hotelsList);
        return "hotels/index";
    }

}


