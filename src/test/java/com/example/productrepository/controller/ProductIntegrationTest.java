package com.example.productrepository.controller;

import com.example.productrepository.products.ProductRepository;
import com.example.productrepository.products.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductIntegrationTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;


    @Test
    @DirtiesContext
    void testGetAllProducts() throws Exception {
        //Given
        productRepository.save(new Product("1", "test", 5));


        // hier kann man statt get auch post eingeben um eine postmethode zu testen
        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                        {
                        "id": "1",
                        "title": "test",
                        "price": 5
                        }
                        ]
                        """
                ));


        //THen
    }

    @Test
    @DirtiesContext
    void addProductTest() throws Exception {
        //Given

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "title": "test-title",
                                "price": 23
                                }
                                """)
                )

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""

                        {
                         "title": "test-title",
                                "price": 23
                        }
                        """))
                .andExpect(jsonPath("$.id").isNotEmpty());


    }

    @DirtiesContext
    @Test
    void testFindProductById() throws Exception {
        //GIVEN
        Product product = new Product("123", "test-title", 4);
        productRepository.save(product);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/" + product.id()))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "id": "123",
                        "title": "test-title",
                        "price": 4
                        }                   
                        """));

    }

    @DirtiesContext
    @Test
    void testUpdateProductById() throws Exception {
        //GIVEN
        Product oldProduct = new Product("123", "test-title", 4);
        productRepository.save(oldProduct);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/" + oldProduct.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "title": "new-title",
                                "price": 0
                                }
                                """))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "id": "123",
                        "title": "new-title",
                        "price": 0
                        }                   
                        """));

    }

    @DirtiesContext
    @Test
    void testDeleteProductById() throws Exception {
        //GIVEN
        Product product = new Product("123", "test-title", 4);
        productRepository.save(product);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/" + product.id()))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().string(""));

    }

}

