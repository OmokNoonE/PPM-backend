package org.omoknoone.ppm.domain.projectDashboard.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.omoknoone.ppm.domain.projectDashboard.aggregate.Graph;
import org.omoknoone.ppm.domain.projectDashboard.dto.GraphDTO;
import org.omoknoone.ppm.domain.projectDashboard.repository.GraphRepository;
import org.omoknoone.ppm.domain.schedule.service.ScheduleService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class GraphServiceImpl implements GraphService {

    private final GraphRepository graphRepository;
    private final ScheduleService scheduleService;
    private final MongoTemplate mongoTemplate;
    private final ModelMapper modelMapper;

    // init
    // 프로젝트가 생성될 때 대시보드가 초기값으로 생성 되어야함
    public void initDashboard(String projectId) {
    }


    // 프로젝트 Id를 통해 대시보드(그래프) 조회
    public List<GraphDTO> viewProjectDashboardByProjectId(String projectId) {

        List<Graph> graphs = graphRepository.findAllByProjectId(projectId);
        return modelMapper.map(graphs, new TypeToken<List<Graph>>() {
        }.getType());
    }


    // 전체진행률 (게이지) 업데이트
    public void updateGauge(String projectId) {

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("projectId").is(projectId),
                Criteria.where("type").is("gauge"),
                Criteria.where("series.name").is("전체진행률")
        );

        Query query = new Query(criteria);

        Update update = new Update();
        update.set("series.$.data", 55);

        mongoTemplate.updateMulti(
                query,
                update,
                Graph.class
        );

    }

    // pie (준비, 진행, 완료)
    public void updatePie(String projectId, String type) {
        int[] datas = new int[]{10, 30, 50};

        Graph graph = graphRepository.findAllByProjectIdAndType(projectId, type);

        for (int i = 0; i < datas.length; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("name", graph.getSeries().get(i).get("name"));
            data.put("data", datas[i]);
            graph.getSeries().set(i, data);
        }

        graphRepository.save(graph);

    }

    // table (구성원별 진행상태)
    public void updateTable(String projectId, String type) {

        // example data
        // projectId = 1, type = table
        Graph graph = graphRepository.findAllByProjectIdAndType(projectId, type);
        List<Map<String, Object>> series = graph.getSeries();

        // update 할 data를 담고 있는 Map
        Map<String, Map<String, Integer>> updates = Map.of(
                "조예린", Map.of("준비", 55, "진행", 55, "완료", 55),
                "오목이", Map.of("준비", 3, "진행", 2, "완료", 1)
        );

        // 새로운 값으로 update
        for (Map<String, Object> data : series) {
            String memberName = (String) data.get("구성원명");
            if (updates.containsKey(memberName)) {
                Map<String, Integer> memberUpdates = updates.get(memberName);
                for (Map.Entry<String, Integer> entry : memberUpdates.entrySet()) {
                    data.put(entry.getKey(), entry.getValue());
                }
            }
        }

        graphRepository.save(graph);

    }

    // column
    public void updateColumn(String projectId, String type) {

        Graph graph = graphRepository.findAllByProjectIdAndType(projectId, type);
        List<String> categories = graph.getCategories();
        List<Map<String, Object>> series = graph.getSeries();

        // update할 categoreis (section명들)
        List<String> updateCategories = Arrays.asList("섹션명1", "섹션명2", "섹션명3");

        // update할 section별 진행상황
        Map<String, List<Integer>> updates = Map.of(
                "준비", List.of(1, 1, 1),
                "진행", List.of(2, 2, 2),
                "완료", List.of(3, 3, 3)
        );

        // categories를 업데이트
        graph.getCategories().clear(); // 기존 카테고리를 모두 지우고
        graph.getCategories().addAll(updateCategories); // 새로운 카테고리로 대체

        // 각 상태(준비, 진행, 완료)에 대한 값을 업데이트
        for (Map.Entry<String, List<Integer>> entry : updates.entrySet()) {
            String status = entry.getKey();
            List<Integer> values = entry.getValue();

            // 현재 상태에 대한 값을 업데이트
            for (Map<String, Object> seriesItem : series) {
                if (seriesItem.get("name").equals(status)) {
                    seriesItem.put("data", values);
                    break; // 해당 상태에 대한 값 업데이트 후 루프 종료
                }
            }
        }

        // 업데이트된 데이터를 저장
        graphRepository.save(graph);

    }

    // line
    public void updateLine(String projectId, String type) {

        Graph graph = graphRepository.findAllByProjectIdAndType(projectId, type);
        List<String> categories = graph.getCategories();
        List<Map<String, Object>> series = graph.getSeries();

        // update할 categoreis (날짜)
        List<String> updateCategories = Arrays.asList(
                "01/02/2023",
                "02/02/2023",
                "03/02/2023",
                "04/02/2023",
                "05/02/2023",
                "06/02/2023",
                "07/02/2023",
                "08/02/2023",
                "09/02/2023",
                "10/02/2023",
                "11/02/2023",
                "12/02/2023"
        );

        // update할 section별 진행상황
        Map<String, List<Integer>> updates = Map.of(
                "예상진행률", Arrays.asList(5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5),
                "실제진행률", Arrays.asList(4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4)
        );


        // categories update
        graph.getCategories().clear(); // 기존 카테고리를 모두 지우고
        graph.getCategories().addAll(updateCategories); // 새로운 카테고리로 대체

        // data update
        for (Map.Entry<String, List<Integer>> entry : updates.entrySet()) {
            String status = entry.getKey();
            List<Integer> values = entry.getValue();

            for (Map<String, Object> seriesItem : series) {
                if (seriesItem.get("name").equals(status)) {
                    seriesItem.put("data", values);
                    break; // 해당 상태에 대한 값 업데이트 후 루프 종료
                }
            }
        }

        graphRepository.save(graph);

    }


}
