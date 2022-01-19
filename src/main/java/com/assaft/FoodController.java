package com.assaft;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(FoodController.BASE_PATH)
public class FoodController {

    public static final String BASE_PATH = "/api/v1/food";

    public FoodController(FoodRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public List<Food> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getById(@PathVariable Long id) {

        Optional<Food> food = repository.getById(id);

        return food.isPresent() ? ResponseEntity.ok(food.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/filter")
    public List<Food> getByType(@RequestParam(name = "type") Food.Type type) {
        return repository.findByType(type);
    }

    @PostMapping()
    public ResponseEntity<Food> addFood(@RequestBody Food food) throws URISyntaxException {

        Food repoFood = repository.save(food);

        return ResponseEntity.created(new URI(
                String.format("%s/%d", BASE_PATH, repoFood.getId()))).body(repoFood);
    }

    // Demonstrating CQRS, not necessarily the best way to update an object!
    @PatchMapping()
    public ResponseEntity<Food> updateFood(@RequestBody FoodUpdate foodUpdate) {

        Optional<Food> updatedFood = repository.updateRatingById(foodUpdate.getId(), foodUpdate.getRating());

        return updatedFood.isPresent() ? ResponseEntity.ok(updatedFood.get()) : ResponseEntity.notFound().build();
    }

    private final FoodRepository repository;
}
