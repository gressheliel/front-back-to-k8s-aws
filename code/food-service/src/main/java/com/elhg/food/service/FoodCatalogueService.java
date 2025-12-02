package com.elhg.food.service;

import com.elhg.food.dto.FoodCataloguePage;
import com.elhg.food.dto.FoodItemDTO;
import com.elhg.food.dto.Restaurant;
import com.elhg.food.entity.FoodItem;
import com.elhg.food.mapper.FoodItemMapper;
import com.elhg.food.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodCatalogueService {


    @Value("${restaurant.service.url}")
    private String restaurantBaseUrl;


    private final FoodItemRepository foodItemRepository;
    private final WebClient.Builder webClient;
    private final RestTemplate restTemplate;

    public FoodItemDTO addFoodItem(FoodItemDTO foodItemDTO) {
        FoodItem foodItemSavedInDB = foodItemRepository.save(FoodItemMapper.INSTANCE.mapFoodItemDTOToFoodItem(foodItemDTO));
        return FoodItemMapper.INSTANCE.mapFoodItemToFoodItemDto(foodItemSavedInDB);
    }

    public FoodCataloguePage fetchFoodCataloguePageDetails(Integer restaurantId) {
        List<FoodItem> foodItemList =  fetchFoodItemList(restaurantId);
        Restaurant restaurant = fetchRestaurantDetailsFromRestaurantMS(restaurantId);
        return createFoodCataloguePage(foodItemList, restaurant);
    }

    private FoodCataloguePage createFoodCataloguePage(List<FoodItem> foodItemList, Restaurant restaurant) {
        FoodCataloguePage foodCataloguePage = new FoodCataloguePage();
        foodCataloguePage.setFoodItemsList(foodItemList);
        foodCataloguePage.setRestaurant(restaurant);
        return foodCataloguePage;
    }


    /*private Restaurant fetchRestaurantDetailsFromRestaurantMS(Integer restaurantId) {
        return restTemplate.getForObject(restaurantBaseUrl+restaurantId, Restaurant.class);
    }*/


    private Restaurant fetchRestaurantDetailsFromRestaurantMS(Integer restaurantId) {
         WebClient client = webClient
                 .baseUrl(restaurantBaseUrl)
                 .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                 .defaultUriVariables(Collections.singletonMap("url", restaurantBaseUrl))
                 .build();
         return client.method(HttpMethod.GET)
                 .uri("/" + restaurantId)
                 .retrieve()
                 .bodyToMono(Restaurant.class)
                 .block();
    }


    private List<FoodItem> fetchFoodItemList(Integer restaurantId) {
        return foodItemRepository.findByRestaurantId(restaurantId);
    }
}
