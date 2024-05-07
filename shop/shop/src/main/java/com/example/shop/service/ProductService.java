package com.example.shop.service;

import com.example.shop.dto.ProductDTO;

import com.example.shop.entity.Product;
import com.example.shop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public void create(ProductDTO productDTO) throws IOException {
        Product product = Product.toDTO(productDTO);

        log.info("product id = {}", product.getId());
        log.info("product price = {}", product.getPrice());
        log.info("product stock = {}", product.getStockQuantity());
        log.info("product boardWriter = {}", product.getBoardwriter());
        log.info("product productname = {}", product.getProductname());
        productRepository.save(product);
    }

    public List<Product>findItems(){
        return productRepository.findAll();
    }

    public Product findItemByUuid(String uuid) {
        return productRepository.findByUuid(uuid);
    }

    public void update(String uuid,ProductDTO productDTO){
        Optional<Product>optionalItem = Optional.ofNullable(productRepository.findByUuid(uuid));

        if(optionalItem.isPresent()){
            Product updateProduct = optionalItem.get();
            productRepository.updateItemByUUID(uuid, productDTO.getName(),
                    productDTO.getPrice(), productDTO.getStockQuantity(),
                    productDTO.getBoardwriter(),
                    productDTO.getProductname());
        }
    }
    @Transactional
    public void delete(String uuid){
        Optional<Product> optionalItem = Optional.ofNullable(productRepository.findByUuid(uuid));

        if(optionalItem.isPresent()){
            Product product = optionalItem.get();
            productRepository.delete(product);
        }
    }



}

//    public String updateItem()throws IOException{
//        return ;
//    }


//        if(itemDTO.getItemFile().isEmpty()){
//
//            Item item = Item.toSaveEntity(itemDTO);
//            itemRepository.save(item);
//        }else{
//            MultipartFile ItemFiled = itemDTO.getItemFile();
//            String originalFilename = ItemFiled.getOriginalFilename();
//            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
//            String savePath = "C:/springboot_img/" + storedFileName;
//            ItemFiled.transferTo(new File(savePath));
//            Item item = Item.toSaveFileEntity(itemDTO);
//            Long savedId = itemRepository.save(item).getId();
//            Item itemUuid = itemRepository.findById(savedId).get();
//
//            ItemFile itemFileEntity = ItemFile.toFileEntity(itemUuid,originalFilename,storedFileName);
//            itemFileRepository.save(itemFileEntity);
//        }

//    public List<ItemDTO> findAll(){
//        List<Item> itemEntityList = itemRepository.findAll();
//        List<ItemDTO> itemDTOList =new ArrayList<>();
//        for(Item item : itemEntityList){
//            itemDTOList.add(ItemDTO.toFileDTO(item));
//        }
//        return itemDTOList;
//    }


//


//    public Page<ItemDTO> paging(Pageable pageable){
//        int page = pageable.getPageNumber() - 1;
//        int pageLimit = 3;
//        Page<Item> itemEntities =
//                itemRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.ASC, "id")));
//
//        Page<ItemDTO> ItemDTOS = itemEntities.map(board -> new ItemDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle()));
//        return ItemDTOS;
//    }
