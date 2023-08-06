package com.formatting.jsonlocaldate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class ObjectEx {
    @JsonDeserialize()
    private LocalDate localDate;
    private LocalDateTime localDateTime;
}
