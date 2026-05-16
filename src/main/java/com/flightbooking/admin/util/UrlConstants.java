package com.flightbooking.admin.util;

public class UrlConstants {

	public static final String COUNTRY = "/country";

	public static final String CREATE_NEW_COUNTRY = "/create";

	public static final String FETCH_COUNTRY_DETAILS = "/fetchCountryDetails";

	public static final String FETCH_ALL_COUNTRY_DETAILS = "/fetchAllCountryDetails";

	public static final String UPDATE_COUNTRY_DETAILS = "/updateCountryDetails";

	public static final String CITY = "/city";

	public static final String CREATE_NEW_CITY = "/create";

	public static final String FETCH_CITY_DETAILS = "/fetchCityDetails";

	public static final String FETCH_CITIES_BY_COUNTRY = "/fetchCitiesByCountry";

	public static final String UPDATE_CITY_DETAILS = "/updateCityDetails";

	public static final String AIRLINE = "/airline";

	public static final String CREATE_NEW_AIRLINE = "/create";

	public static final String FETCH_AIRLINE = "/getAllAirlines";

	public static final String AIRPORT = "/airport";

	public static final String CREATE_NEW_AIRPORT = "/create";

	public static final String FETCH_AIRPORT_BY_CITY = "/fetchAirportByCity/{cityCode}";

	public static final String FLIGHT = "/flight";

	public static final String CREATE_NEW_FLIGHT_ENTRY = "/create";

	public static final String FETCH_FLIGHT_BY_NUMBER = "/flight/{flightNumber}";

	public static final String FTECH_FLIGHTS_BY_AIRLINE = "/fetchFlightsByAirline/{airlineId}";
}
