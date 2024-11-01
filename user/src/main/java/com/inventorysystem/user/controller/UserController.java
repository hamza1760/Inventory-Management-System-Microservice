package com.inventorysystem.user.controller;

import static com.inventorysystem.common.utilities.ApiEndPointConstants.API;
import static com.inventorysystem.common.utilities.ApiEndPointConstants.USER;

import com.inventorysystem.user.dto.UserDTO;
import com.inventorysystem.user.services.IUserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * USER controller
 */
@RestController
@CrossOrigin("*")
@RequestMapping(API + USER)
public class UserController {

    @Autowired
    IUserService iUserService;

    @PostMapping()
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO userDTO) {

        UserDTO userDto = iUserService.add(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }


}
