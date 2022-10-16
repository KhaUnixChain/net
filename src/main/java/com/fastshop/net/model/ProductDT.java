package com.fastshop.net.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDT {
    String info;
    Integer productId;
    String categoryId;
}
