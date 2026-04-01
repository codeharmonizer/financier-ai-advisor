package com.neo.v1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@Table(name = "language")
@NoArgsConstructor
@AllArgsConstructor
public class LanguageEntity {

    @Id
    private Long id;

    private String code;

    private String name;

    @Column(name = "contentful_value")
    private String contentfulValue;
}
