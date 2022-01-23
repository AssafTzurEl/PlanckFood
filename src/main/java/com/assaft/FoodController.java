package com.assaft;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
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
    public Food getById(@PathVariable Long id) {

        if (id < 0L) {
            throw new IllegalArgumentException("Invalid food ID: " + id);
        }

        Optional<Food> food = repository.getById(id);

        if (!food.isPresent()) {
            throw new NotFoundException(id);
        }

        return food.get();
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
    public Food updateFood(@RequestBody FoodUpdate foodUpdate) {

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
