package com.BTA.SmartPrep.domain.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="Categories")
public class Category {
    @Id //This Annotation Says this is an ID
    @GeneratedValue(strategy = GenerationType.UUID) //Creates ID for us, using UUID
    @Column(name = "category_ID",updatable = false,nullable = false) //col name from DB, not Updatable, cannot be null
    private UUID categoryId;

    @Column(name = "name",nullable = false)
    private String name;
}
