package com.flightbooking.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightbooking.admin.dto.SeatClassResponseModel;
import com.flightbooking.admin.entity.SeatClass;
import com.flightbooking.admin.exception.ResourceNotFoundException;
import com.flightbooking.admin.repository.SeatClassRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatClassService {

	private final SeatClassRepository seatClassRepository;

	@Transactional(readOnly = true)
	public List<SeatClassResponseModel> getAllSeatTypes() {
		List<SeatClass> seatTypes = seatClassRepository.findAll();

		if (seatTypes.isEmpty()) {
			throw new ResourceNotFoundException("No data found.");
		}

		List<SeatClassResponseModel> responseList = seatTypes.stream().map(this::mapToObj).toList();

		return responseList;
	}

	private SeatClassResponseModel mapToObj(final SeatClass seat) {

		SeatClassResponseModel response = SeatClassResponseModel.builder().seatCode(seat.getSeatCode())
				.name(seat.getName()).description(seat.getDescription()).displayOrder(seat.getDisplayOrder())
				.active(seat.isActive()).build();

		return response;
	}
}
