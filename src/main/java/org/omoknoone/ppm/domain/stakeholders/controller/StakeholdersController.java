package org.omoknoone.ppm.domain.stakeholders.controller;


import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.omoknoone.ppm.domain.stakeholders.dto.*;
import org.omoknoone.ppm.domain.stakeholders.service.StakeholdersService;
import org.omoknoone.ppm.domain.stakeholders.vo.ResponseStakeholders;
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
    public ResponseEntity<ResponseStakeholders> createStakeholder(@RequestBody RequestCreateStakeholdersDTO requestCreateStakeholdersDTO) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CreateStakeholdersDTO createStakeholdersDTO = modelMapper.map(requestCreateStakeholdersDTO, CreateStakeholdersDTO.class);

        Stakeholders newStakeholders = stakeholdersService.createStakeholder(createStakeholdersDTO);

        ResponseStakeholders responseStakeholders = modelMapper.map(newStakeholders, ResponseStakeholders.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseStakeholders);
    }

    @GetMapping("/view/{scheduleId}")
    public ResponseEntity<List<ResponseStakeholders>> viewStakeholders(@PathVariable("scheduleId") Long scheduleId){

        List<StakeholdersDTO> stakeholdersDTOList = stakeholdersService.viewStakeholders(scheduleId);
        List<ResponseStakeholders> responseStakeholdersList = modelMapper.map(stakeholdersDTOList,
                new TypeToken<List<Stakeholders>>(){}.getType());

        return ResponseEntity.status(HttpStatus.OK).body(responseStakeholdersList);
    }

    @PutMapping("/modify")
    public ResponseEntity<ResponseMessage> modifyStakeholder(@RequestBody RequestModifyStakeholdersDTO requestModifyStakeholdersDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ModifyStakeholdersDTO modifyStakeholdersDTO = modelMapper.map(requestModifyStakeholdersDTO, ModifyStakeholdersDTO.class);

        Long stakeholdersId = stakeholdersService.modifyStakeholder(modifyStakeholdersDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", stakeholdersId);

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(new ResponseMessage(200, "이해관계자 수정 성공", responseMap));
    }

    @DeleteMapping("/remove/{stakeholdersId}")
    public ResponseEntity<ResponseMessage> removeStakeholder(@PathVariable("stakeholdersId") Long stakeholdersId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long removedStakeholdersId = stakeholdersService.removeStakeholder(stakeholdersId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", removedStakeholdersId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .headers(headers)
                .body(new ResponseMessage(204, "이해관계자 삭제 성공", responseMap));
    }

}
