package com.mju.management.domain.project.dto.response;

import com.mju.management.domain.project.infrastructure.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProjectResponseDto {

    private Long projectIndex;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate finishDate;
    private boolean isChecked;


    public static GetProjectResponseDto from(Project project){
        return GetProjectResponseDto.builder()
                .projectIndex(project.getProjectIndex())
                .name(project.getName())
                .startDate(project.getStartDate())
                .finishDate(project.getFinishDate())
                .description(project.getDescription())
                .isChecked(project.isChecked())
                .build();
    }
}
