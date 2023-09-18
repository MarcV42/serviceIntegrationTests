package com.example.productrepository.products;

import com.example.productrepository.products.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductIntegrationTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;


    @Test
    void testGetAllProducts() throws Exception {
        //Given
        productRepository.save(new Product("1", "test", 5));


// hier kann man statt get auch post eingeben um eine postmethode zu testen
//When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
[
]
"""
));


        //THen
    }
}
