package com.inventorysystem.auth.controller;


import static com.inventorysystem.common.utilities.ApiEndPointConstants.API;
import static com.inventorysystem.common.utilities.ApiEndPointConstants.USER;

import com.inventorysystem.auth.dto.UserDTO;
import com.inventorysystem.auth.service.UserService;
import com.inventorysystem.common.utilities.ApiEndPointConstants;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User controller
 */
@RestController
@CrossOrigin("*")
@RequestMapping(API + USER)
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Register the user.
     *
     * @param userDTO Object of UserDTO class.
     * @return Registered user.
     */
    @PostMapping()
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.addUser(userDTO));
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUser() {
        return ResponseEntity.ok(userService.getUser());
    }

}
