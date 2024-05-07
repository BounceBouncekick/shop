package com.example.shop.dto;



import com.example.shop.entity.Product;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductDTO {

    private Long id;
    private String boardwriter;
    private String name;
    private int price;
    private int stockQuantity;
    private String productname;

    @Builder
    public ProductDTO(String boardwriter, String name, int price, int stockQuantity, String productname) {
        this.boardwriter = boardwriter;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.productname = productname;
    }

    public static ProductDTO toEntity(Product product){
        return ProductDTO.builder()
                .boardwriter(product.getBoardwriter())
                .name(product.getName())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .productname(product.getProductname())
                .build();
    }

//    public static ProductDTO toProductDTO(Product product){
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setBoardwriter(product.getBoardwriter());
//        productDTO.setName(product.getName());
//        productDTO.setPrice(product.getPrice());
//        productDTO.setStock(product.getStockQuantity());
//        productDTO.setProductname(product.getProductname());
//        return productDTO;
//    }
}
