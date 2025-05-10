package com.minh.shopee.domain;

import java.util.List;

import com.minh.shopee.domain.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@Table(name = "products")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private double price;
    private int stock;

    @OneToMany(mappedBy = "product")
    List<ProductImage> images;

    @ManyToOne
    private Category category;

}
