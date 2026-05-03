package com.btvn.ss16.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSearchDto {
    private String name;
    private Double minPrice;
    private Double maxPrice;
    private Long categoryId;
    private Integer minStock;
    private Integer maxStock;
    private String keyword;

    @Builder.Default
    private int page = 0;

    @Builder.Default
    private int size = 5;
}
