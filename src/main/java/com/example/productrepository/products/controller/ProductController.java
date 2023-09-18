package com.example.productrepository.products.controller;

import com.example.productrepository.products.models.NewProduct;
import com.example.productrepository.products.models.Product;
import com.example.productrepository.products.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        System.out.println("Du hast die testGetAllProducts() Methode über MockMvc aufgerufen ");
        return productService.findAllProducts();
    }

    @PostMapping
    public Product addProduct(@RequestBody NewProduct newProduct)
    {
        System.out.println("Du hast die addProductTest() Methode über MockMvc aufgerufen ");

        return productService.addProduct(newProduct);
    }

    @GetMapping("{id}")
    public Product getProductById(@PathVariable String id) {

        System.out.println("Du hast die testFindProductById() Methode über MockMvc aufgerufen ");

        return productService.findById(id);
    }

    @PutMapping("{id}")
    public Product putProduct(@PathVariable String id, @RequestBody NewProduct product) {

        System.out.println("Du hast die testUpdateProductById Methode über MockMvc aufgerufen ");

        return productService.updateProduct(id, product);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable String id) {

        System.out.println("Du hast die testDeleteProductById() Methode über MockMvc aufgerufen ");

        productService.deleteProduct(id);
    }
}
