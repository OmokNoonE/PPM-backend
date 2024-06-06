package org.omoknoone.ppm.domain.projectdashboard.dto;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class GraphDTO {

    private String id;

    private List<Map<String, Object>> series;

    private List<String> categories;

    private int[] expectedProgress;
    private String projectMemberId;

    private String projectId;

    private String type;

    @Builder
    public GraphDTO(String id, List<Map<String, Object>> series, List<String> categories, int[] expectedProgress,
        String projectMemberId, String projectId, String type) {
        this.id = id;
        this.series = series;
        this.categories = categories;
        this.expectedProgress = expectedProgress;
        this.projectMemberId = projectMemberId;
        this.projectId = projectId;
        this.type = type;
    }
}

