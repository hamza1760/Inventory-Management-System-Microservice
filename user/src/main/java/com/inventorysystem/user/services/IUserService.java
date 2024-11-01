package com.inventorysystem.user.services;

import com.inventorysystem.user.dto.AuthDetailDto;
import com.inventorysystem.user.dto.UserDTO;

public interface IUserService {

    UserDTO add(UserDTO userDto);
}
