package org.omoknoone.ppm.domain.role.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.omoknoone.ppm.domain.role.aggregate.Role;
import org.omoknoone.ppm.domain.role.dto.ViewRoleListResponseDTO;
import org.omoknoone.ppm.domain.role.repository.RoleRepository;
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
