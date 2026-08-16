package com.flightbooking.flight.util;

public class UrlConstants {

	public static final String FLIGHT = "/flight";

	public static final String CREATE_NEW_FLIGHT_ENTRY = "/create";

	public static final String FETCH_FLIGHT_BY_NUMBER = "/flight/{flightNumber}";

	public static final String FTECH_FLIGHTS_BY_AIRLINE = "/fetchFlightsByAirline/{airlineId}";

	public static final String FLIGHT_SCHEDULE = "/flightSchedule";

	public static final String CREATE_NEW_FLIGHT_SCHEDULE_ENTRY = "/createflightScheduleEntry";

	public static final String UPDATE_FLIGHT_SCHEDULE_DETAILS = "/updateflightSchedule";

	public static final String ADMIN_SERVICE = "ADMIN-SERVICE";

	public static final String GET_AIRPORT_BY_ID = "/admin/airport/fetchAirportById/{airportId}";

	public static final String CREATE = "/create";

	public static final String FLIGHT_SCHEDULE_INSTANCE = "/flightScheduleInstance";

	public static final String CREATE_FLIGHT_SCHEDULE_INSTANCE = "/create";

	public static final String UPDATE_FLIGHT_SCHEDULE_INSTANCE = "/updateInstance/{id}";

	public static final String UPDATE_FLIGHT_SCHEDULE_INSTANCE_STATUS = "/updateStatus/{id}";

	public static final String FLIGHT_SEAT_CONFIGURATION = "/seatConfig";
	
	public static final String FETCH_SEAT_DETILS_SEATS = "/admin/seats/fetchDetails";

	public static final String CREATE_FLIGHT_SEAT_CONFIGURATION = "/create";

	public static final String GET_FLIGHT_SEAT_CONFIGURATION = "/fetch/{flightId}";

	public static final String UPDATE_FLIGHT_SEAT_CONFIGURATION = "/update/{id}";
	
	public static final String UPDATE_FLIGHT_SCHEDULE_INSTANCE_PRICE = "/updateFlightPrice";

	public static final String SEARCH_FLIGHTS = "/searchFlights";
	
	public static final String FETCH_AIRPORT_BY_CITY = "/admin/city/fetchAirportByCity/{cityCode}";
	
	public static final String FETCH_AIRLINE_BY_ID = "/admin/airline/{airlineId}";
}
