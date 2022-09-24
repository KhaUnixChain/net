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
@Table(name = "Comments")
public class Comment implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

    @Column(name = "feedback", columnDefinition = "nvarchar(250)")
    String feedback;

    @ManyToOne
	@JoinColumn(name = "Productid")
    Product product;

    @ManyToOne
    @JoinColumn(name = "Username")
    Account account;
}
