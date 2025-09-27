package com.example.j3m4_backend.domain.schedule.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;   // 제목
    private String place;   // 장소
    private String description; // 설명
    private Long authorId; // 작성자 ID
    private LocalDateTime time; // 일정 시간

    @Builder
    public Schedule(String title, String place, String description, Long authorId, LocalDateTime time) {
        this.title = title;
        this.place = place;
        this.description = description;
        this.authorId = authorId;
        this.time = time;
    }

    public void updateSchedule(String title, String place, String description, LocalDateTime date) {
        this.title = title;
        this.place = place;
        this.description = description;
        this.time = date;
    }
}
