package com.example.shop.controller;

import com.example.shop.dto.ProductDTO;
import com.example.shop.entity.Product;
import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;


@ResponseBody
@Controller
@RequiredArgsConstructor
@RequestMapping("/shop-service")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/health-check")
    public String status() {
        return "It`s Working in SHOP Service";
    }

    //상품 등록
    @PostMapping("/create")
    public ResponseEntity<?> create(@ModelAttribute ProductDTO productDTO) throws IOException {
        productService.create(productDTO);
        return ResponseEntity.ok("등록되었습니다.");
    }

    //상품 목록 찾기
    @GetMapping("/List")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productService.findItems();
        return ResponseEntity.ok(products);
    }

    //uuid조회를 통한 상품찾기
    @GetMapping("/product/{uuid}")
    public ResponseEntity<Product> findItemByuuid(@PathVariable("uuid") String uuid) {
        Product product = productService.findItemByUuid(uuid);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //상품수정
    @PostMapping("/update/{uuid}")
    public ResponseEntity<String> UpdatebyUuid(@PathVariable("uuid")String uuid, @ModelAttribute ProductDTO productDTO){
        productService.update(uuid, productDTO);
        return ResponseEntity.ok("수정되었습니다.");
    }

    //상품삭제
    @GetMapping("/delete/{uuid}")
    public ResponseEntity<String> DeletebyUuid(@PathVariable("uuid") String uuid){
        productService.delete(uuid);
        return ResponseEntity.ok("삭제되었습니다.");
    }


}


//    @PostMapping("items/{name}/edit")
//    public ResponseEntity<String> updateItem(@PathVariable("name") String name,Model model){
//        itemService.updateItem(item);
//
//        return ResponseEntity.ok("제품이 성공적으로 수정되었습니다.");
//    }








//


//    @GetMapping("/paging")
//    public String paging(@PageableDefault(page =1 ,size = 3, sort = "id", direction = Sort.Direction.ASC) Pageable pageable, Model model){
//        Page<ItemDTO> ItemList = itemService.paging(pageable);
//        int blockLimit = 3;
//        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) -1) * blockLimit +1;
//        int endPage = ((startPage + blockLimit - 1) < ItemList.getTotalPages()) ? startPage + blockLimit - 1 : ItemList.getTotalPages();
//
//        model.addAttribute("ItemList", ItemList);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//        return "paging";
//    }


