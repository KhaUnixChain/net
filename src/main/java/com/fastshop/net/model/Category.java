package com.fastshop.net.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "Categories")
public class Category implements Serializable{
	@Id
	String id;

	@NotBlank(message = "Please enter name category.")
	@Column(name = "name", columnDefinition = "nvarchar(25)", nullable = false)
	String name;

	Boolean status;

	@JsonIgnore
	@OneToMany(mappedBy = "category")
	List<Product> products;
}
