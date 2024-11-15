package com.inventorysystem.backoffice.entity;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {

    private boolean isDeleted;

}
