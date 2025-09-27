package com.example.j3m4_backend.domain.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CreateScheduleRequestDto {

    private String title;
    private String place;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime time;

    public CreateScheduleRequestDto(String title, String place, String description, LocalDateTime time) {
        this.title = title;
        this.place = place;
        this.description = description;
        this.time = time;
    }
}
