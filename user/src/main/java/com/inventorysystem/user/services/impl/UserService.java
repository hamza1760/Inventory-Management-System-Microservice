package com.inventorysystem.user.services.impl;

import static com.inventorysystem.common.utilities.Constants.USER_NOT_FOUND;

import com.inventorysystem.common.customexception.NotAuthorizedException;
import com.inventorysystem.common.customexception.NotFoundException;
import com.inventorysystem.common.enums.RolesEnum;
import com.inventorysystem.common.customexception.Exception;
import com.inventorysystem.common.utilities.Constants;
import com.inventorysystem.common.utilities.ModelConverter;
import com.inventorysystem.user.dto.AuthDetailDto;
import com.inventorysystem.user.dto.RoleDTO;
import com.inventorysystem.user.dto.UserDTO;
import com.inventorysystem.user.entity.User;
import com.inventorysystem.user.repository.RoleRepository;
import com.inventorysystem.user.repository.UserRepository;
import com.inventorysystem.user.services.IUserService;
import com.inventorysystem.user.utilities.AuthServiceUtils;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    AuthServiceUtils authServiceUtils;

    @Value("${kc.customerClientSecret}")
    private String customerClientSecret;
    @Value("${kc.customerPublicKey}")
    private String customerPublicKey;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDTO add(UserDTO userDto) {

        User user = userRepository.findByEmail(userDto.getEmail());
        if (user != null) {
            throw new Exception("User already exists with email {} ",user.getEmail());
        }

        userDto.setRole(ModelConverter.entityToDto(roleRepository.findByRoleName(RolesEnum.Admin), RoleDTO.class));
        sendUserToKeycloak(userDto);
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(ModelConverter.dtoToEntity(userDto, User.class));
        userDto.setPassword(null);
        return userDto;
    }

    private void sendUserToKeycloak(UserDTO userDto) {

        authServiceUtils.addRealmUserInKeycloak(userDto);
    }
}

