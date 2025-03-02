


package com.shoppingcart.user.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @NotEmpty
    private String id;
    @NotEmpty
    private String description;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "categoryEntity")
    private List<ProductEntity> products=new ArrayList<>();

    public CategoryEntity() {
    }

    public CategoryEntity(String id) {
        this.id = id;
    }
}
