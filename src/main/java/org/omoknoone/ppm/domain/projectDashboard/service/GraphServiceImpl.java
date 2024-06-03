package org.omoknoone.ppm.domain.projectDashboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.employee.service.EmployeeService;
import org.omoknoone.ppm.domain.project.aggregate.Project;
import org.omoknoone.ppm.domain.project.service.ProjectService;
import org.omoknoone.ppm.domain.projectDashboard.aggregate.Graph;
import org.omoknoone.ppm.domain.projectDashboard.dto.GraphDTO;
import org.omoknoone.ppm.domain.projectDashboard.repository.GraphRepository;
import org.omoknoone.ppm.domain.projectmember.dto.ProjectMemberDTO;
import org.omoknoone.ppm.domain.projectmember.service.ProjectMemberService;
import org.omoknoone.ppm.domain.schedule.service.ScheduleService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class GraphServiceImpl implements GraphService {

    private final GraphRepository graphRepository;
    private final ScheduleService scheduleService;
    private final MongoTemplate mongoTemplate;
    private final ModelMapper modelMapper;
    private final ProjectMemberService projectMemberService;
    private final ProjectService projectService;

    // init
    // 프로젝트가 생성될 때 대시보드가 초기값으로 생성 되어야함
    public void initGraph(String projectId) {

        // 게이지
        List<Map<String, Object>> gaugeSeries = List.of(
            Map.of(
                "name", "전체진행률",
                "data", new int[1]
            )
        );

        // 테이블
        // List<Map<String, Object>> tableSeries = new ArrayList<>();
        // List<viewProjectMembersByProjectResponseDTO> members = projectMemberService.viewProjectMembersByProject(Integer.valueOf(projectId));
        //
        // for (viewProjectMembersByProjectResponseDTO member : members) {
        //     String employeeName = employeeService.getEmployeeNameByProjectMemberId(member.getProjectMemberEmployeeId());
        //     Map<String, Object> row = new HashMap<>();
        //     row.put("구성원명", employeeName);
        //     row.put("준비", 0);
        //     row.put("진행", 0);
        //     row.put("완료", 0);
        //     tableSeries.add(row);
        // }


        // 파이
        List<Map<String, Object>> pieSeries = List.of(
            Map.of(
                "name", "준비",
                "data", 0
            ),
            Map.of(
                "name", "진행",
                "data", 0
            ),
            Map.of(
                "name", "완료",
                "data", 0
            )
        );

        // 라인
        List<Map<String, Object>> lineSeries = List.of(
            Map.of(
                "name", "예상진행률",
                "data", new int[10]
            ),
            Map.of(
                "name", "실제진행률",
                "data", new int[10]
            )
        );

        // 프로젝트 시작일, 종료일 저장

        LocalDate startDate = projectService.viewStartDate(Integer.valueOf(projectId));
        LocalDate endDate = projectService.viewEndDate(Integer.valueOf(projectId));

        List<LocalDate> dateCategories = projectService.divideWorkingDaysIntoTen(startDate, endDate);

        System.out.println("dateCategories = " + dateCategories);

        List<String> lineCategories = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            lineCategories.add("");
        }

        for (int i = 0; i < lineCategories.size(); i++) {
            lineCategories.set(i, String.valueOf(dateCategories.get(i)));
        }


        // 컬럼
        // List<Map<String, Object>> columnSeries = List.of(
        //     Map.of(
        //         "name", "준비",
        //         "data", new int[3]
        //     ),
        //     Map.of(
        //         "name", "진행",
        //         "data", new int[3]
        //     ),
        //     Map.of(
        //         "name", "완료",
        //         "data", new int[3]
        //     )
        // );


        // 컬럼 (섹션별 -> 구성원별)

        int count = 0; // 총 구성원 수를 담을 변수

        // 구성원 목록 (이름)


        List<ProjectMemberDTO> dtoList =
            projectMemberService.viewProjectMembersByProject(Integer.valueOf(projectId));

        // categories에 구성원 이름 담기
        for (ProjectMemberDTO dto : dtoList) {
            // String name = employeeService.getEmployeeNameByProjectMemberId(String.valueOf(dto.getProjectMemberId()));
            // columnCategories.add(name);
            count += 1;
        }

        List<String> columnCategories = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            columnCategories.add("");
        }

        // 구성원들의 준비, 진행, 완료 상태 갯수
        List<Map<String, Object>> columnSeries = List.of(
            Map.of(
                "name", "준비",
                "data", new int [count]
            ),
            Map.of(
                "name", "진행",
                "data", new int[count]
            ),
            Map.of(
                "name", "완료",
                "data", new int[count]
            )
        );

        // 그래프 생성
        Graph gaugeGraph = Graph.builder()
            .projectId(projectId)
            // .projectMemberId(projectMemberId)
            .type("gauge")
            .series(gaugeSeries)
            .build();

        // Graph tableGraph = Graph.builder()
        //     .projectId(projectId)
        //     .projectMemberId(projectMemberId)
        //     .type("table")
        //     .series(tableSeries)
        //     .build();

        Graph pieGraph = Graph.builder()
            .projectId(projectId)
            // .projectMemberId(projectMemberId)
            .type("pie")
            .series(pieSeries)
            .build();

        Graph lineGraph = Graph.builder()
            .projectId(projectId)
            // .projectMemberId(projectMemberId)
            .type("line")
            .series(lineSeries)
            .categories(lineCategories)
            .build();

        Graph columnGraph = Graph.builder()
            .projectId(projectId)
            // .projectMemberId(projectMemberId)
            .type("column")
            .series(columnSeries)
            .categories(columnCategories)
            .build();

        // 저장
        graphRepository.saveAll(List.of(gaugeGraph, /*tableGraph,*/ pieGraph, lineGraph, columnGraph));
    }



    // 프로젝트 Id를 통해 대시보드(그래프) 조회
    @Transactional(readOnly = true)
    public GraphDTO viewProjectDashboardByProjectId(String projectId, String type) {

        Graph graph = graphRepository.findAllByProjectIdAndType(projectId, type);

        return modelMapper.map(graph, GraphDTO.class);
    }


    // 전체진행률 (게이지) 업데이트
    /* 메모. 전체진행률이 [10] <- 이런 식으로 한 칸짜리 배열 안에 있어야 front에서 오류 안 남 */
    @Transactional
    public void updateGauge(String projectId, String type) {

        Graph graph = graphRepository.findAllByProjectIdAndType(projectId, type);

        if(graph != null) {

            int[] newdata = new int[1];
            newdata[0] = scheduleService.updateGauge(Long.valueOf(projectId));

            Map<String, Object> data = new HashMap<>();
            data.put("name", graph.getSeries().get(0).get("name"));
            data.put("data", newdata);
            graph.getSeries().set(0, data);

            graphRepository.save(graph);
        }
    }

    @Transactional
    public void updateGaugeAll(){

        List<Project> inProgressProject = projectService.viewInProgressProject();

        List<Integer> inProgressProjectIds = inProgressProject.stream()
            .map(Project::getProjectId)
            .toList();

        for (Integer projectId : inProgressProjectIds) {
            updateGauge(String.valueOf(projectId), "gauge");
        }

    }

    // pie (준비, 진행, 완료)
    @Transactional
    public void updatePie(String projectId, String type) {
        // int[] datas = new int[]{10, 30, 50};
        int[] datas = scheduleService.updatePie(Long.parseLong(projectId));

        Graph graph = graphRepository.findAllByProjectIdAndType(projectId, type);

        if(graph != null) {

        for (int i = 0; i < datas.length - 1; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("name", graph.getSeries().get(i).get("name"));
            data.put("data", datas[i]);
            graph.getSeries().set(i, data);
        }

        graphRepository.save(graph);
        }

    }

    @Transactional
    public void updatePieAll(){

        List<Project> inProgressProject = projectService.viewInProgressProject();

        List<Integer> inProgressProjectIds = inProgressProject.stream()
            .map(Project::getProjectId)
            .toList();

        for (Integer projectId : inProgressProjectIds) {
            updatePie(String.valueOf(projectId), "pie");
        }

    }

    // table (구성원별 진행상태)
    // public void updateTable(String projectId, String type) {
    //
    //     // example data
    //     // projectId = 1, type = table
    //     Graph graph = graphRepository.findAllByProjectIdAndType(projectId, type);
    //     List<Map<String, Object>> series = graph.getSeries();
    //
    //     // update 할 data를 담고 있는 Map
    //     // Map<String, Map<String, Integer>> updates = Map.of(
    //     //         "조예린", Map.of("준비", 55, "진행", 55, "완료", 55),
    //     //         "오목이", Map.of("준비", 3, "진행", 2, "완료", 1)
    //     // );
    //
    //     Map<String, Object> updates = scheduleService.updateTable(Long.parseLong(projectId));
    //
    //     System.out.println("updates = " + updates);
    //
    //     // 새로운 값으로 update
    //     for (Map<String, Object> data : series) {
    //         String memberName = (String) data.get("구성원명");
    //         if (updates.containsKey(memberName)) {
    //             Map<String, Integer> memberUpdates = updates.get(memberName);
    //             for (Map.Entry<String, Integer> entry : memberUpdates.entrySet()) {
    //                 data.put(entry.getKey(), entry.getValue());
    //             }
    //         }
    //     }
    //
    //     graphRepository.save(graph);
    //
    // }

    // column
    @Transactional
    public void updateColumn(String projectId, String type) {

        Graph graph = graphRepository.findAllByProjectIdAndType(projectId, type);

        if (graph != null) {

        Map<String, Object> updates = scheduleService.updateColumn(Long.parseLong(projectId));

        List<String> updateCategories = (List<String>) updates.get("categories");
        List<Map<String, Object>> updateSeries = (List<Map<String, Object>>) updates.get("series");

        System.out.println("updateCategories = " + updateCategories);
        System.out.println("updateSeries = " + updateSeries);

        if (graph != null && updateCategories != null) {
            graph.getCategories().clear();
            graph.getCategories().addAll(updateCategories);

            graph.getSeries().clear();
            graph.getSeries().addAll(updateSeries);

            graphRepository.save(graph);
        }
        }
    }
    @Transactional
    public void updateColumnAll(){

        List<Project> inProgressProject = projectService.viewInProgressProject();

        List<Integer> inProgressProjectIds = inProgressProject.stream()
            .map(Project::getProjectId)
            .toList();

        for (Integer projectId : inProgressProjectIds) {
            updateColumn(String.valueOf(projectId), "column");
        }

    }




    // line
    public void updateLine(String projectId, String type) {

        Graph graph = graphRepository.findAllByProjectIdAndType(projectId, type);

        if(graph != null) {

        List<String> categories = graph.getCategories();
        List<Map<String, Object>> series = graph.getSeries();

        /* 날짜 (시작 ~ 끝 10등분 한 것) 업데이트 */
        String pattern = "yyyy-MM-dd";

        LocalDate startDate = projectService.viewStartDate(Integer.valueOf(projectId));
        LocalDate endDate = projectService.viewEndDate(Integer.valueOf(projectId));

        List<LocalDate> dateCategories = projectService.divideWorkingDaysIntoTen(startDate, endDate);

        // LocalDate -> String
        List<String> stringCategories = dateCategories.stream()
            .map(date -> date.format(DateTimeFormatter.ofPattern(pattern)))
            .toList();

        // categories update
        graph.getCategories().clear(); // 기존 카테고리를 모두 지우고
        graph.getCategories().addAll(stringCategories); // 새로운 카테고리로 대체


        /* 예상 진행률 업데이트 */
        int[] expectProgress = new int[10];
        for (int i = 0; i < expectProgress.length; i++) {
            expectProgress[i] = i;
        }

        System.out.println("expectProgress = " + expectProgress);

        if (graph != null) {
            List<Map<String, Object>> seriesList = graph.getSeries();
            for (int i = 0; i < seriesList.size(); i++) {
                Map<String, Object> seriesEntry = seriesList.get(i);
                if ("예상진행률".equals(seriesEntry.get("name"))) {
                    List<Integer> data = (List<Integer>) seriesEntry.get("data");
                    for (int j = 0; j < expectProgress.length; j++) {
                        data.set(j, expectProgress[j]);
                    }
                    graphRepository.save(graph);
                    break;
                }
            }
        }


        /* 실제 진행률 업데이트 */
        int index = 0;

        // 현재 날짜가 각 날짜의 범위 내에 있는 지 확인하여 index 구하기
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < dateCategories.size(); i++) {
            LocalDate start = i == 0 ? LocalDate.MIN : dateCategories.get(i - 1).plusDays(1);
            LocalDate end = dateCategories.get(i);

            if (currentDate.isAfter(start) && currentDate.isBefore(end)) {
                index = i;                          // 해당하는 범위의 인덱스 반환
            }
        }

        System.out.println("index = " + index);

        // update할 section별 진행상황 (현재 진행률)
        int newdata = scheduleService.updateGauge(Long.valueOf(projectId));

        if (graph != null) {
            for (Map<String, Object> seriesEntry : series) {
                if ("실제진행률".equals(seriesEntry.get("name"))) {
                    List<Integer> data = (List<Integer>) seriesEntry.get("data");
                    if (index >= 0 && index < data.size()) {
                        data.set(index, newdata);
                        graphRepository.save(graph);
                    }
                    break;
                }
            }
        }

        graphRepository.save(graph);

        }
    }

    @Transactional
    public void updateLineAll(){

        List<Project> inProgressProject = projectService.viewInProgressProject();

        List<Integer> inProgressProjectIds = inProgressProject.stream()
            .map(Project::getProjectId)
            .toList();

        for (Integer projectId : inProgressProjectIds) {
            updateLine(String.valueOf(projectId), "line");
        }

    }

    // 프로젝트 id에 해당하는 그래프 데이터 삭제
    @Override
    public void deleteGraphByProjectId(String projectId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("projectId").is(projectId));
        mongoTemplate.remove(query, Graph.class);
    }

    // mongoDB 데이터 초기화용
    @Override
    public void deleteAllGraph(){
        mongoTemplate.remove(new Query(), Graph.class);
    }
}
