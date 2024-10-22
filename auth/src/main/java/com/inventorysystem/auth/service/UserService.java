package com.inventorysystem.auth.service;



import com.inventorysystem.auth.dto.UserDTO;
import java.util.*;

public interface UserService {

    UserDTO addUser(UserDTO userDTO);

    List<UserDTO> getUser();
}
