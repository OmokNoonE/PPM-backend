package org.omoknoone.ppm.domain.stakeholders.controller;


import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.common.HttpHeadersCreator;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.omoknoone.ppm.domain.stakeholders.dto.*;
import org.omoknoone.ppm.domain.stakeholders.service.StakeholdersService;
import org.omoknoone.ppm.domain.stakeholders.vo.ResponseStakeholders;
import org.omoknoone.ppm.domain.stakeholders.vo.ResponseViewStakeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stakeholders")
public class StakeholdersController {

    private final StakeholdersService stakeholdersService;

    private final ModelMapper modelMapper;

    @Autowired
    public StakeholdersController(StakeholdersService stakeholdersService, ModelMapper modelMapper) {
        this.stakeholdersService = stakeholdersService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createStakeholder(@RequestBody RequestCreateStakeholdersDTO requestCreateStakeholdersDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CreateStakeholdersDTO createStakeholdersDTO = modelMapper.map(requestCreateStakeholdersDTO, CreateStakeholdersDTO.class);

        Stakeholders newStakeholders = stakeholdersService.createStakeholder(createStakeholdersDTO);

        ResponseStakeholders responseStakeholders = modelMapper.map(newStakeholders, ResponseStakeholders.class);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("createStakeholder", responseStakeholders);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "이해관계자 등록 성공", responseMap));
    }

    @GetMapping("/view/{scheduleId}")
    public ResponseEntity<ResponseMessage> viewStakeholders(@PathVariable("scheduleId") Long scheduleId){

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<ViewStakeholdersDTO> stakeholdersDTOList = stakeholdersService.viewStakeholders(scheduleId);
        List<ResponseViewStakeholders> responseStakeholdersList = modelMapper.map(stakeholdersDTOList,
                new TypeToken<List<ViewStakeholdersDTO>>(){}.getType());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewStakeholders", responseStakeholdersList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "이해관계자 조회 성공", responseMap));
    }

    @PutMapping("/modify")
    public ResponseEntity<ResponseMessage> modifyStakeholder(@RequestBody RequestModifyStakeholdersDTO requestModifyStakeholdersDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ModifyStakeholdersDTO modifyStakeholdersDTO = modelMapper.map(requestModifyStakeholdersDTO, ModifyStakeholdersDTO.class);

        Long stakeholdersId = stakeholdersService.modifyStakeholder(modifyStakeholdersDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyStakeholder", stakeholdersId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "이해관계자 수정 성공", responseMap));
    }

    @DeleteMapping("/remove/{stakeholdersId}")
    public ResponseEntity<ResponseMessage> removeStakeholder(@PathVariable("stakeholdersId") Long stakeholdersId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        Long removedStakeholdersId = stakeholdersService.removeStakeholder(stakeholdersId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("removeStakeholder", removedStakeholdersId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(204, "이해관계자 삭제 성공", responseMap));
    }

}
