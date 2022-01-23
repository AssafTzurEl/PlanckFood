package com.assaft;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(FoodController.BASE_PATH)
public class FoodController {

    public static final String BASE_PATH = "/api/v1/food";

    public FoodController(FoodService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Food> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Food getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/filter")
    public List<Food> getByType(@RequestParam(name = "type") Food.Type type) {
        return service.findByType(type);
    }

    @PostMapping()
    public ResponseEntity<Food> addFood(@RequestBody Food food) throws URISyntaxException {
        Food repoFood = service.add(food);

        return ResponseEntity.created(new URI(
                String.format("%s/%d", BASE_PATH, repoFood.getId()))).body(repoFood);
    }

    // Demonstrating CQRS, not necessarily the best way to update an object!
    @PatchMapping()
    public Food updateFood(@RequestBody FoodUpdate foodUpdate) {
        return service.updateRatingById(foodUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.removeById(id);
    }

    @DeleteMapping()
    public void deleteAll() {
        service.removeAll();
    }

    private final FoodService service;
}
