package com.fastshop.net.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Productdetails")
public class ProductDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "info", columnDefinition = "nvarchar(200)", nullable = false)
    String info;

    @ManyToOne
    @JoinColumn(name = "Categorydetailid")
    CategoryDetail categoryDetail;

    @ManyToOne
    @JoinColumn(name = "productId")
    Product product;
}
