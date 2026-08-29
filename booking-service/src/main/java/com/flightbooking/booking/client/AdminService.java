package com.flightbooking.booking.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.flightbooking.booking.dto.SeatClassResponseModel;
import com.flightbooking.booking.util.JsonResponseEntity;
import com.flightbooking.booking.util.UrlConstants;

@FeignClient(name = UrlConstants.ADMIN_SERVICE)
public interface AdminService {

	@GetMapping(UrlConstants.FETCH_SEAT_DETILS_SEATS)
	public ResponseEntity<JsonResponseEntity<List<SeatClassResponseModel>>> getSeatDetails();
}
