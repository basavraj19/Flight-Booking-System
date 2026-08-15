package com.flightbooking.flight.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flightbooking.flight.client.AdminService;
import com.flightbooking.flight.dto.AirportResponseModel;
import com.flightbooking.flight.dto.AirlineResponseModel;
import com.flightbooking.flight.dto.FlightSeatPriceModel;
import com.flightbooking.flight.dto.SearchFlightRequestModel;
import com.flightbooking.flight.dto.SearchFlightResponseModel;
import com.flightbooking.flight.entity.Flight;
import com.flightbooking.flight.entity.FlightSchedule;
import com.flightbooking.flight.entity.FlightScheduleInstance;
import com.flightbooking.flight.entity.FlightSeatAvailability;
import com.flightbooking.flight.exception.InvalidInputException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightSearchService {

	private final AdminService adminService;

	private final FlightScheduleService flightScheduleService;

	private final FlightScheduleInstanceService flightScheduleInstanceService;

	private final FlightSeatAvailabilityService flightSeatAvailabilityService;

	private final FlightService flightService;

	@Transactional(readOnly = true)
	public List<SearchFlightResponseModel> searchFlights(final SearchFlightRequestModel model) {

		validateIncomingRequest(model);

		String sourceCityCode = model.getSourceCityCode().trim().toUpperCase();
		String destinationCityCode = model.getDestinationCityCode().trim().toUpperCase();

		List<AirportResponseModel> sourceAirports = adminService.getAirportDetailsByCityCode(sourceCityCode).getBody();

		List<AirportResponseModel> destinationAirports = adminService.getAirportDetailsByCityCode(destinationCityCode)
				.getBody();

		List<Long> sourceAirportIds = getAirportIds(sourceAirports);
		List<Long> destinationAirportIds = getAirportIds(destinationAirports);

		LocalDate startDate = model.getTravelDate();
		LocalDate endDate = startDate.plusDays(3);

		List<FlightSchedule> flightScheduleList = flightScheduleService.getFlightSchedules(sourceAirportIds,
				destinationAirportIds, startDate, endDate);

		if (flightScheduleList.isEmpty()) {
			return Collections.emptyList();
		}
		List<Long> flightScheduleIds = flightScheduleList.stream().map(items -> items.getId()).toList();

		List<FlightScheduleInstance> flightScheduleInstanceList = flightScheduleInstanceService
				.getFlightScheduleInstances(flightScheduleIds, startDate, endDate);

		if (flightScheduleInstanceList.isEmpty()) {
			return Collections.emptyList();
		}

		List<Long> flightScheduleInstanceIds = flightScheduleInstanceList.stream().map(items -> items.getId()).toList();

		List<FlightSeatAvailability> flightSeatAvailabilityDetails = flightSeatAvailabilityService
				.getSeatAvailabilityByInstanceIds(flightScheduleInstanceIds);

		List<SearchFlightResponseModel> flights = searchFlightsHelper(flightScheduleList, flightScheduleInstanceList,
				flightSeatAvailabilityDetails, sourceAirports, destinationAirports);

		return flights;
	}

	private List<SearchFlightResponseModel> searchFlightsHelper(final List<FlightSchedule> flightScheduleList,
			final List<FlightScheduleInstance> flightScheduleInstanceList,
			final List<FlightSeatAvailability> flightSeatAvailabilityDetails,
			final List<AirportResponseModel> sourceAirports, final List<AirportResponseModel> destinationAirports) {

		List<SearchFlightResponseModel> flights = new ArrayList<>();

		Map<Long, FlightSchedule> scheduleMap = flightScheduleList.stream()
				.collect(Collectors.toMap(FlightSchedule::getId, Function.identity()));

		Map<Long, List<FlightSeatAvailability>> availabilityMap = flightSeatAvailabilityDetails.stream()
				.collect(Collectors.groupingBy(FlightSeatAvailability::getFlightScheduleInstanceId));

		Map<Long, AirportResponseModel> airportMap = Stream
				.concat(sourceAirports.stream(), destinationAirports.stream())
				.collect(Collectors.toMap(AirportResponseModel::getRecordId, Function.identity()));

		List<Long> flightIds = flightScheduleList.stream().map(FlightSchedule::getFlightId).distinct().toList();

		List<Flight> flightDetails = flightService.getFlightById(flightIds);

		List<Long> airlineIds = flightDetails.stream().map(Flight::getAirlineId).distinct().toList();

		List<AirlineResponseModel> airlines = adminService.getAirlineById(airlineIds).getBody();

		Map<Long, Flight> flightMap = flightDetails.stream()
				.collect(Collectors.toMap(Flight::getId, Function.identity()));

		Map<Long, AirlineResponseModel> airlineMap = airlines.stream()
				.collect(Collectors.toMap(AirlineResponseModel::getRecordId, Function.identity()));

		for (FlightScheduleInstance instance : flightScheduleInstanceList) {

			SearchFlightResponseModel flight = new SearchFlightResponseModel();

			FlightSchedule schedule = scheduleMap.get(instance.getFlightScheduleId());

			Flight flightDetailsObject = flightMap.get(schedule.getFlightId());

			AirlineResponseModel airline = airlineMap.get(flightDetailsObject.getAirlineId());

			List<FlightSeatAvailability> availability = availabilityMap.getOrDefault(instance.getId(),
					Collections.emptyList());

			List<FlightSeatPriceModel> seatPrices = getSeatPrices(availability);

			String sourceAirport = airportMap.get(schedule.getSourceAirportId()).getAirportCode();
			String destinationAirport = airportMap.get(schedule.getDestinationAirportId()).getAirportCode();

			// Build SearchFlightResponseModel here
			flight.setFlightScheduleInstanceId(instance.getId());
			flight.setFlightNumber(flightDetailsObject.getFlightNumber());
			flight.setAirlineName(airline.getAirlineName());
			flight.setDepartureTime(schedule.getDepartureTime());
			flight.setArrivalTime(schedule.getArrivalTime());
			flight.setSourceAirportCode(sourceAirport);
			flight.setDestinationAirportCode(destinationAirport);
			flight.setTravelDate(instance.getTravelDate());
			flight.setArrivalDayOffset(schedule.getArrivalDayOffset());
			flight.setSeatPrices(seatPrices);

			flights.add(flight);
		}

		return flights;
	}

	private void validateIncomingRequest(final SearchFlightRequestModel request) {

		if (request == null) {
			throw new InvalidInputException("Invalid request object.");
		}

		if (!StringUtils.hasText(request.getSourceCityCode())) {
			throw new InvalidInputException("Source City Code is required.");
		}

		if (!StringUtils.hasText(request.getDestinationCityCode())) {
			throw new InvalidInputException("Destination City Code is required.");
		}

		String sourceCityCode = request.getSourceCityCode().trim().toUpperCase();
		String destinationCityCode = request.getDestinationCityCode().trim().toUpperCase();

		if (sourceCityCode.equals(destinationCityCode)) {
			throw new InvalidInputException("Source and destination cities cannot be the same.");
		}

		if (request.getTravelDate() == null || request.getTravelDate().isBefore(LocalDate.now())) {
			throw new InvalidInputException("Please select a valid date to proceed.");
		}
	}

	private List<Long> getAirportIds(List<AirportResponseModel> airport) {
		return airport.stream().map(items -> items.getRecordId()).toList();
	}

	private List<FlightSeatPriceModel> getSeatPrices(final List<FlightSeatAvailability> availability) {

		if (availability == null || availability.isEmpty()) {
			return new ArrayList<>();
		}

		return availability.stream()
				.map(item -> FlightSeatPriceModel.builder().seatClassId(item.getSeatClassId())
						.availableSeats(item.getTotalSeats() - item.getBookedSeats()).price(item.getPrice()).build())
				.toList();
	}
}
