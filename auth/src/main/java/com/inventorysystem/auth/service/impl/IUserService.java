package com.inventorysystem.auth.service.impl;


import com.inventorysystem.auth.dto.RoleDTO;
import com.inventorysystem.auth.dto.UserDTO;
import com.inventorysystem.auth.entity.User;
import com.inventorysystem.auth.repository.RoleRepository;
import com.inventorysystem.auth.repository.UserRepository;
import com.inventorysystem.auth.service.UserService;
import com.inventorysystem.common.enums.RolesEnum;
import com.inventorysystem.common.exceptions.Exception;
import com.inventorysystem.common.utilities.ModelConverter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class IUserService implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDTO  addUser(UserDTO userDTO) {
        String email = userDTO.getEmail();
        User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new Exception("User already exists with email {}",user.getEmail());
        }
        userDTO.setRole(ModelConverter.entityToDto(roleRepository.findByRoleName(RolesEnum.Admin), RoleDTO.class));
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user = ModelConverter.dtoToEntity(userDTO,User.class);
        return ModelConverter.entityToDto(userRepository.save(user),UserDTO.class);
    }

    @Override
    public List<UserDTO> getUser() {
        List<User> users = userRepository.findAll();

        return ModelConverter.listEntityToDto(users,UserDTO.class);
    }
}
