package com.assaft;

import java.util.List;
import java.util.Optional;

public interface FoodRepository {
    List<Food> findAll();
    Optional<Food> getById(Long id);
    List<Food> findByType(Food.Type type);
    Food save(Food food);
    Food updateRatingById(Long id, Integer rating);
}
