package com.booking.app.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Booking")  // 404
public class BookingNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
}
