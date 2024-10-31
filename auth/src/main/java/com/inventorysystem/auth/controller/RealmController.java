package com.inventorysystem.auth.controller;


import static com.inventorysystem.common.utilities.ApiEndPointConstants.API;
import static com.inventorysystem.common.utilities.ApiEndPointConstants.REALM;

import com.inventorysystem.auth.dto.AuthDetailDto;
import com.inventorysystem.auth.dto.UserDTO;
import com.inventorysystem.auth.service.IRealmService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Realm controller
 */
@RestController
@CrossOrigin("*")
@RequestMapping(API + REALM)
public class RealmController {

    @Autowired
    IRealmService realmService;

    /**
     * Register the user.
     *
     * @param userDTO Object of UserDTO class.
     * @return Registered user.
     */
    @PostMapping()
    public void addUser(@Valid @RequestBody UserDTO userDTO) {
        realmService.addUser(userDTO);
    }

    @GetMapping("/{realm}")
    public ResponseEntity<AuthDetailDto> addRealm(@PathVariable String realm) {
        return ResponseEntity.ok(realmService.addRealm(realm));
    }

}
