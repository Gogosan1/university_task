package university.task.demo.model.dto;

import java.time.LocalDate;

public record StudentCreateRequestDto(String fullName, Long groupId, LocalDate acceptanceDate) {
    
};
