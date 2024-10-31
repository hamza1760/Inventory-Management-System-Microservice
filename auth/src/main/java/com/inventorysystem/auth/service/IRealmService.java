package com.inventorysystem.auth.service;



import com.inventorysystem.auth.dto.AuthDetailDto;
import com.inventorysystem.auth.dto.UserDTO;
import java.util.*;

public interface IRealmService {

    void addUser(UserDTO userDTO);
    AuthDetailDto addRealm(String realm);


}
