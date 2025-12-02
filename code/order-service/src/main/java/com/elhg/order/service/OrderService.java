package com.elhg.order.service;

import com.elhg.order.dto.OrderDTO;
import com.elhg.order.dto.OrderDTOFromFE;
import com.elhg.order.dto.UserDTO;
import com.elhg.order.entity.Order;
import com.elhg.order.mapper.OrderMapper;
import com.elhg.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final SequenceGenerator sequenceGenerator;
    private final RestTemplate restTemplate;
    private final WebClient.Builder webClient;

    @Value("${user.service.url}")
    private String userBaseUrl;


    public OrderDTO saveOrderInDb(OrderDTOFromFE orderDetails) {
        Integer newOrderID = sequenceGenerator.generateNextOrderId();
        UserDTO userDTO = fetchUserDetailsFromUserId(orderDetails.getUserId());
        Order orderToBeSaved = new Order(newOrderID, orderDetails.getFoodItemsList(), orderDetails.getRestaurant(), userDTO );
        orderRepository.save(orderToBeSaved);
        return OrderMapper.INSTANCE.mapOrderToOrderDTO(orderToBeSaved);
    }

    /*private UserDTO fetchUserDetailsFromUserId(Integer userId) {
        return restTemplate.getForObject("http://USER-SERVICE/user/fetchUserById/" + userId, UserDTO.class);
    }*/

    private UserDTO fetchUserDetailsFromUserId(Integer userId) {
        WebClient client = webClient
                .baseUrl(userBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", userBaseUrl))
                .build();
        return client.method(HttpMethod.GET)
                .uri("/" + userId)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();
    }
}
