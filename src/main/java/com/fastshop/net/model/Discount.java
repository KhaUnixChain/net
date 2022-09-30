package com.fastshop.net.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "Discounts")
public class Discount implements Serializable{
    @Id
    @Column(name = "id", columnDefinition = "varchar(50)")
    String id;

    @Temporal(TemporalType.DATE)
    @Column(name = "DateFrom")
    Date dateFrom = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "DateEnd")
    Date dateEnd = new Date();

    @Column(name = "free", columnDefinition = "int")
    Integer free;

    @JsonIgnore
    @OneToMany(mappedBy = "discount")
    List<Product> products;	
}
