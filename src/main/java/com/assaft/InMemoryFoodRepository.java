package com.assaft;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFoodRepository implements FoodRepository {
    @Override
    public List<Food> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Optional<Food> getById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Food> findByType(Food.Type type) {
        return db.values().stream()
                .filter(food -> food.getType() == type)
                .collect(Collectors.toList());
    }

    @Override
    public Food save(Food food) {
        boolean isNewFood = food.getId() == null || !db.containsKey(food.getId());

        if (isNewFood) {
            Long id = db.size() + 1L;
            food.setId(id);
        }

        db.put(food.getId(), food);

        return food;
    }

    @Override
    public Optional<Food> updateRatingById(Long id, Integer rating) {
        Food food = db.get(id);

        if (food != null) {
            food.setRating(rating);
        }

        return Optional.ofNullable(food);
    }

    @Override
    public void deleteById(Long id) {
        db.remove(id);
    }

    @Override
    public void deleteAll() {
        db.clear();
    }

    private final Map<Long, Food> db = new HashMap<>();
}
