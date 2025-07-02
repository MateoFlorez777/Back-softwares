package com.softwares.service.impl;

import com.softwares.exceptions.ProductException;
import com.softwares.models.Product;
import com.softwares.models.Seller;
import com.softwares.models.Category;
import com.softwares.repository.CategoryRepository;
import com.softwares.repository.ProductRepository;
import com.softwares.request.CreateProductRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        productService = new ProductServiceImpl(productRepository, categoryRepository);
    }

    @Test
    public void testCreateProduct_whenAllCategoriesExist() throws ProductException {
        // Arrange
        Seller seller = new Seller();
        seller.setId(1L);

        CreateProductRequest req = new CreateProductRequest();
        req.setTitle("Zapatos");
        req.setDescription("Zapatos deportivos");
        req.setMrpPrice(10000);
        req.setSellingPrice(80000);
        req.setColor("Negro");
        req.setSizes("L");
        req.setImages(List.of("img1.jpg", "img2.jpg"));
        req.setCategory("ropa");
        req.setCategory2("zapatos");
        req.setCategory3("deportivos");

        // Simulamos que las categorías ya existen
        Category cat1 = new Category(); cat1.setCategoryId("ropa");     cat1.setLevel(1);
        Category cat2 = new Category(); cat2.setCategoryId("zapatos");  cat2.setLevel(2);
        Category cat3 = new Category(); cat3.setCategoryId("deportivos"); cat3.setLevel(3);

        when(categoryRepository.findByCategoryId("ropa")).thenReturn(cat1);
        when(categoryRepository.findByCategoryId("zapatos")).thenReturn(cat2);
        when(categoryRepository.findByCategoryId("deportivos")).thenReturn(cat3);

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setTitle("Zapatos");

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        Product result = productService.createProduct(req, seller);

        // Assert
        assertNotNull(result);
        assertEquals("Zapatos", result.getTitle());
        verify(categoryRepository, times(1)).findByCategoryId("ropa");
        verify(productRepository, times(1)).save(any(Product.class));
    }



    @Test
    public void testFindProductById_whenExists() throws ProductException {
        // Arrange
        Product mockProduct = new Product();
        mockProduct.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        // Act
        Product result = productService.findProductById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindProductById_whenNotExists() {
        // Arrange
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        // Act + Assert
        ProductException exception = assertThrows(ProductException.class, () -> {
            productService.findProductById(2L);
        });

        assertEquals("Producto no encontrado.", exception.getMessage());
        verify(productRepository, times(1)).findById(2L);
    }


    @Test
    public void testDeleteProduct_success() throws ProductException {
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void testUpdateProduct_setsCorrectIdAndSaves() throws ProductException {
        Product newProductData = new Product();
        newProductData.setTitle("Actualizado");

        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product updated = productService.updateProduct(1L, newProductData);

        assertEquals(1L, updated.getId());
        assertEquals("Actualizado", updated.getTitle());
    }


    @Test
    public void testToggleProductStock() throws ProductException {
        Product product = new Product();
        product.setIn_stock(true);
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product updated = productService.updateProductStock(1L);

        assertFalse(updated.isIn_stock());
    }

    @Test
    public void testCalculateDiscountPercentage_valid() {
        int discount = ProductServiceImpl.calculateDiscountPercentage(200, 150);
        assertEquals(25, discount);
    }

    @Test
    public void testCalculateDiscountPercentage_zeroMrp_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProductServiceImpl.calculateDiscountPercentage(0, 150);
        });
    }



}
