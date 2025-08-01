package com.example.demo.helpers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.example.demo.dto.PageableResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.entities.Users;

public class Helper {

	public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {
	    List<U> entityList = page.getContent();

	    List<V> dtoList = entityList.stream()
	            .map(object -> new ModelMapper().map(object, type))
	            .collect(Collectors.toList());

	    PageableResponse<V> response = new PageableResponse<>();
	    response.setContent(dtoList);
	    response.setPageNumber(page.getNumber());
	    response.setPageSize(page.getSize());
	    response.setTotalElements(page.getTotalElements());
	    response.setTotalPages(page.getTotalPages());
	    response.setLastPage(page.isLast());

	    return response;
	}


}

//u is consider entity and v consider as usetdto