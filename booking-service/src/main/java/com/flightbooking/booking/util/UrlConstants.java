package com.flightbooking.booking.util;

public class UrlConstants {

	public static final String BOOKING = "/booking";

	public static final String CREATE_BOOKING = "/create";

	public static final String FLIGHT_SERVICE = "FLIGHT-SERVICE";

	public static final String FETCH_FLIGHT_SCHDEULE_INSTANCE_BY_ID = "/flight/flightScheduleInstance/{id}";

	public static final String ADMIN_SERVICE = "ADMIN-SERVICE";

	public static final String FETCH_SEAT_DETILS_SEATS = "/admin/seats/fetchDetails";

	public static final String RESERVE_SEATS = "/flight/flightSeatAvailability/reserve";
	
	public static final String RELEASE_SEATS = "/flight/flightSeatAvailability/release";
	
	public static final String  AUTH_SERVICE = "AUTH-SERVICE";
	
	public static final String GET_USER_BY_USERNAME = "/auth/user/{username}";
	
	public static final String FETCH_BOOKING_DETAILS_BY_BOOKING_REF_NO = "/fetchDetails";
}
