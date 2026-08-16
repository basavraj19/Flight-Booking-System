package com.flightbooking.admin.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.admin.dto.SeatClassResponseModel;
import com.flightbooking.admin.service.SeatClassService;
import com.flightbooking.admin.util.JsonResponseEntity;
import com.flightbooking.admin.util.StringConstants;
import com.flightbooking.admin.util.UrlConstants;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(UrlConstants.SEATS)
@RequiredArgsConstructor
public class SeatClassController {

	private final SeatClassService seatClassService;

	@GetMapping(UrlConstants.SEAT_DETAILS)
	public ResponseEntity<JsonResponseEntity<List<SeatClassResponseModel>>> getAllSeatTypes() {
		JsonResponseEntity<List<SeatClassResponseModel>> response = new JsonResponseEntity<>();

		final List<SeatClassResponseModel> seatTypes = seatClassService.getAllSeatTypes();

		response.setStatus(StringConstants.success);
		response.setMessage(StringConstants.recordFetchSuccessMessage);
		response.setResult(seatTypes);
		response.setException(null);
		response.setStatusCode(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
