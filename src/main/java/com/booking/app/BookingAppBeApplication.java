package com.booking.app;

import com.booking.app.model.Booking;
import com.booking.app.model.Room;
import com.booking.app.model.User;
import com.booking.app.repository.BookingRepository;
import com.booking.app.repository.RoomRepository;
import com.booking.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class BookingAppBeApplication implements CommandLineRunner {

	@Autowired
	UserRepository users;



	@Autowired
	BookingRepository bookings;

	@Autowired
	RoomRepository rooms;


	public static void main(String[] args) {
		SpringApplication.run(BookingAppBeApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		Iterator<User> it = users.findAll().iterator();

		while(it.hasNext()){
			User u1 = it.next();
			String pass = u1.getPassword();
			users.save(u1);
		}

		Iterator<Booking> books = bookings.findAll().iterator();

		while(books.hasNext()){
			Booking book = books.next();
			Date begin = book.getBegin_date();
			Date end = book.getEnd_date();
			List<Date> dates = new ArrayList<Date>();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(begin);

			while (calendar.getTime().getTime() <= end.getTime())
			{
				Date result = calendar.getTime();
				dates.add(result);
				calendar.add(Calendar.DATE, 1);
			}

			Map<Date, Long> tmpMap = new HashMap<Date, Long>();

			for(Date d : dates)
			{
				tmpMap.put(d, book.getId());
			}

			Set<Room> rt = book.getRooms();

			for(Room r : rt)
			{
				r.setDays_reserved(tmpMap);
				rooms.save(r);
			}

			bookings.save(book);
		}
	}

	public Date removeTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
}
