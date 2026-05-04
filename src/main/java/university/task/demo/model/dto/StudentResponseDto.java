package university.task.demo.model.dto;

import java.time.LocalDate;

public record StudentResponseDto(Long id, String fullName, LocalDate acceptanceDate) {
    
};
