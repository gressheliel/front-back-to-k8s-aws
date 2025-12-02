package com.elhg.order.service;

import com.elhg.order.dto.OrderDTO;
import com.elhg.order.dto.OrderDTOFromFE;
import com.elhg.order.dto.UserDTO;
import com.elhg.order.entity.Order;
import com.elhg.order.mapper.OrderMapper;
import com.elhg.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SequenceGenerator sequenceGenerator;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOrderInDb_shouldSaveOrderAndReturnOrderDTO() {
        // Arrange
        OrderDTOFromFE orderDetails = new OrderDTOFromFE();
        Integer newOrderId = 1;
        UserDTO userDTO = new UserDTO();
        Order orderToBeSaved = new Order(newOrderId, orderDetails.getFoodItemsList(), orderDetails.getRestaurant(), userDTO);
        OrderDTO orderDTOExpected = OrderMapper.INSTANCE.mapOrderToOrderDTO(orderToBeSaved);

        when(sequenceGenerator.generateNextOrderId()).thenReturn(newOrderId);
        when(restTemplate.getForObject(anyString(), eq(UserDTO.class))).thenReturn(userDTO);
        when(orderRepository.save(orderToBeSaved)).thenReturn(orderToBeSaved);

        // Act
        OrderDTO orderDTOActual = orderService.saveOrderInDb(orderDetails);

        // Assert
        verify(sequenceGenerator, times(1)).generateNextOrderId();
        assertDoesNotThrow(() -> orderService.saveOrderInDb(orderDetails));
    }
}
