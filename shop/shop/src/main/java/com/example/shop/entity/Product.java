package com.example.shop.entity;

import com.example.shop.Exception.NotEnoughStockException;
import com.example.shop.dto.ProductDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Product" )
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @Column(length = 20, nullable = false)
    @NotNull
    private String boardwriter;

    private String productname;

    private String uuid = UUID.randomUUID().toString();

    @Builder
    public Product(String name, int price, int stockQuantity, String boardwriter, String productname) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.boardwriter = boardwriter;
        this.productname = productname;
    }

    public static Product toDTO(ProductDTO productDTO){
        return Product.builder()
                .boardwriter(productDTO.getBoardwriter())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .stockQuantity(productDTO.getStockQuantity())
                .productname(productDTO.getProductname())
                .build();
    }

//    public static Product toSaveEntity(ProductDTO productDTO){
//        Product product = new Product();
//        product.setName(productDTO.getName());
//        product.setPrice(productDTO.getPrice());
//        product.setStockQuantity(productDTO.getStock());
//        product.setBoardwriter(productDTO.getBoardwriter());
//        product.setProductname(productDTO.getProductname());
//        return product;
//    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stockQuantity +
                ", boardWriter =" + boardwriter +
                ", productname =" + productname +
                '}';
    }

    //재고 증가
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}



    //    @Column(length = 1000)
//    private String boardContents;

//    @Column
//    private String boardTitle;

//    @Column
//    private int fileAttached;



//    private String uuid;

//    @OneToMany(mappedBy = "ItemEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
//    private List<ItemFile> boardFileEntityList = new ArrayList<>();
//
//    public static Item toSaveEntity(ItemDTO itemDTO){
//        Item item = new Item();
//        item.setBoardWriter(itemDTO.getBoardWriter());
//        item.setBoardContents(itemDTO.getBoardContents());
//        item.setBoardTitle(itemDTO.getBoardTitle());
//        item.setFileAttached(0);
//        return item;
//    }
//
//    public static Item toSaveFileEntity(ItemDTO itemDTO){
//        Item item = new Item();
//        item.setBoardWriter(itemDTO.getBoardWriter());
//        item.setBoardContents(itemDTO.getBoardContents());
//        item.setBoardTitle(itemDTO.getBoardTitle());
//        item.setFileAttached(1);
//        return item;
//    }

