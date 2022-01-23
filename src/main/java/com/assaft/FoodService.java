package com.assaft;

import java.util.List;

public interface FoodService {
    List<Food> findAll();
    Food findById(Long id);
    List<Food> findByType(Food.Type type);
    Food add(Food food);
    Food updateRatingById(FoodUpdate foodUpdate);
}
