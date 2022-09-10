package com.hamidspecial.SpringBatchProcessing.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Business_TBL")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Survey {
    @Id
    private long id;
    @Column(name = "series_reference")
    private String Series_reference;
    @Column(name = "period")
    private String Period;
    @Column(name = "data_value")
    private String Data_value;
    @Column(name = "suppressed")
    private String Suppressed;
    @Column(name = "status")
    private String STATUS;
    @Column(name = "units")
    private String UNITS;
    @Column(name = "magnitude")
    private String Magnitude;
    @Column(name = "subject")
    private String Subject;
    @Column(name = "groups")
    private String Group;
    @Column(name = "series_title_1")
    private String Series_title_1;
    @Column(name = "series_title_2")
    private String Series_title_2;
    @Column(name = "series_title_3")
    private String Series_title_3;
    @Column(name = "series_title_4")
    private String Series_title_4;
    @Column(name = "series_title_5")
    private String Series_title_5;
}
