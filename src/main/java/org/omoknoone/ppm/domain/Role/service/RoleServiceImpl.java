package org.omoknoone.ppm.domain.Role.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.omoknoone.ppm.domain.Role.aggregate.Role;
import org.omoknoone.ppm.domain.Role.dto.ViewRoleListResponseDTO;
import org.omoknoone.ppm.domain.Role.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ViewRoleListResponseDTO> viewRoleList() {

        List<Role> role = roleRepository.findAll();

        return modelMapper.map(role, new TypeToken<List<ViewRoleListResponseDTO>>() {}.getType());
    }
}
