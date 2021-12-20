package com.bookamovie.be.mapper;

import com.bookamovie.be.entity.ShowTime;
import com.bookamovie.be.model.ShowTimeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShowTimeMapper {
    ShowTimeResponse showTimeResponse(ShowTime showTime);

}
