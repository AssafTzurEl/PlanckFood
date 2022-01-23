package com.assaft;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FoodServiceImpl implements FoodService {

    public FoodServiceImpl(FoodRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Food> findAll() {
        return repository.findAll();
    }

    @Override
    public Food findById(Long id) {

        if (id < 0L) {
            throw new IllegalArgumentException("Invalid food ID: " + id);
        }

        Optional<Food> food = repository.getById(id);

        if (!food.isPresent()) {
            throw new NotFoundException(id);
        }

        return food.get();
    }

    @Override
    public List<Food> findByType(Food.Type type) {
        return repository.findByType(type);
    }

    @Override
    public Food add(Food food) {
        return repository.save(food);
    }

    @Override
    public Food updateRatingById(FoodUpdate foodUpdate) {
        if (foodUpdate.getId() < 0L) {
            throw new IllegalArgumentException("Invalid food ID: " + foodUpdate.getId());
        }

        Optional<Food> updatedFood = repository.updateRatingById(foodUpdate.getId(), foodUpdate.getRating());

        if (!updatedFood.isPresent()) {
            throw new NotFoundException(foodUpdate.getId());
        }
        return updatedFood.get();
    }

    private final FoodRepository repository;
}
