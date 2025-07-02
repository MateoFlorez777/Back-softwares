package com.softwares.service.impl;

import com.softwares.domain.AccountStatus;
import com.softwares.domain.USER_ROLE;
import com.softwares.exceptions.SellerException;
import com.softwares.models.Address;
import com.softwares.models.Seller;
import com.softwares.repository.AddressRepository;
import com.softwares.repository.OrderItemRepository;
import com.softwares.repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SellerServiceImplTest {

    @InjectMocks
    private SellerServiceImpl sellerService;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private OrderItemRepository orderItemRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSellerByEmail_Success() throws SellerException {
        Seller mockSeller = new Seller();
        mockSeller.setEmail("test@example.com");

        when(sellerRepository.findByEmail("test@example.com")).thenReturn(mockSeller);

        Seller result = sellerService.getSellerByEmail("test@example.com");

        assertEquals(mockSeller, result);
    }

    @Test
    void testGetSellerByEmail_NotFound() {
        when(sellerRepository.findByEmail("missing@example.com")).thenReturn(null);

        SellerException exception = assertThrows(SellerException.class, () -> {
            sellerService.getSellerByEmail("missing@example.com");
        });

        assertEquals("Vendedor no encontrado.", exception.getMessage());
    }

    @Test
    void testCreateSeller_Success() throws SellerException {
        Seller newSeller = new Seller();
        newSeller.setEmail("new@example.com");
        newSeller.setPassword("pass");
        Address address = new Address();
        newSeller.setPickupAddress(address);

        when(sellerRepository.findByEmail("new@example.com")).thenReturn(null);
        when(addressRepository.save(address)).thenReturn(address);
        when(passwordEncoder.encode("pass")).thenReturn("encoded-pass");
        when(sellerRepository.save(any(Seller.class))).thenAnswer(i -> i.getArgument(0));

        Seller createdSeller = sellerService.createSeller(newSeller);

        assertEquals("new@example.com", createdSeller.getEmail());
        assertEquals("encoded-pass", createdSeller.getPassword());
        assertEquals(USER_ROLE.ROLE_SELLER, createdSeller.getRole());
        assertEquals(address, createdSeller.getPickupAddress());

        verify(addressRepository).save(address);
        verify(sellerRepository).save(any(Seller.class));
    }

    @Test
    void testCreateSeller_AlreadyExists() {
        Seller existingSeller = new Seller();
        existingSeller.setEmail("exists@example.com");

        when(sellerRepository.findByEmail("exists@example.com")).thenReturn(existingSeller);

        Seller newSeller = new Seller();
        newSeller.setEmail("exists@example.com");

        SellerException exception = assertThrows(SellerException.class, () -> {
            sellerService.createSeller(newSeller);
        });

        assertEquals("El vendedor ya existe, usa un correo electrónico diferente.", exception.getMessage());
    }

    @Test
    void testUpdateSeller_Success() throws SellerException {
        Long id = 1L;
        Seller existingSeller = new Seller();
        existingSeller.setSellerName("Old Name");
        existingSeller.setEmail("old@example.com");

        Seller updateData = new Seller();
        updateData.setSellerName("New Name");
        updateData.setEmail("new@example.com");

        when(sellerRepository.findById(id)).thenReturn(Optional.of(existingSeller));
        when(sellerRepository.save(any(Seller.class))).thenAnswer(i -> i.getArgument(0));

        Seller updatedSeller = sellerService.updateSeller(id, updateData);

        assertEquals("New Name", updatedSeller.getSellerName());
        assertEquals("new@example.com", updatedSeller.getEmail());

        verify(sellerRepository).save(existingSeller);
    }

    @Test
    void testUpdateSeller_NotFound() {
        Long id = 99L;
        Seller updateData = new Seller();

        when(sellerRepository.findById(id)).thenReturn(Optional.empty());

        SellerException exception = assertThrows(SellerException.class, () -> {
            sellerService.updateSeller(id, updateData);
        });

        assertEquals("Vendedor no encontrado con el ID " + id, exception.getMessage());
    }

    @Test
    void testDeleteSeller_Success() throws SellerException {
        Long id = 1L;

        when(sellerRepository.existsById(id)).thenReturn(true);
        doNothing().when(sellerRepository).deleteById(id);

        sellerService.deleteSeller(id);

        verify(sellerRepository).deleteById(id);
    }

    @Test
    void testDeleteSeller_NotFound() {
        Long id = 99L;

        when(sellerRepository.existsById(id)).thenReturn(false);

        SellerException exception = assertThrows(SellerException.class, () -> {
            sellerService.deleteSeller(id);
        });

        assertEquals("Vendedor no encontrado con el ID " + id, exception.getMessage());
    }
}
