package com.inventorysystem.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventorysystem.common.enums.RolesEnum;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private int roleId;
    @Enumerated(EnumType.STRING)
    private RolesEnum roleName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    @JsonIgnore
    private Set<User> users = new HashSet<>();


}
