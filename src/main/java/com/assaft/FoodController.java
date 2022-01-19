package com.assaft;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(FoodController.BASE_PATH)
public class FoodController {

    public static final String BASE_PATH = "/api/v1/food";

    @GetMapping()
    public List<Food> getAll() {
        return new ArrayList<Food>();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getSingle(@PathVariable Long id) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/filter")
    public List<Food> getByType(@RequestParam(name = "type") Food.Type type) {
        return new ArrayList<Food>();
    }

    @PostMapping()
    public ResponseEntity<Food> addFood(@RequestBody Food food) throws URISyntaxException {

        // Mock adding to DB:
        Long newFoodId = 1L;
        food.setId(newFoodId);
        // end mock

        return ResponseEntity.created(new URI(
                String.format("%s/%d", BASE_PATH, newFoodId))).body(food);
    }

    @PutMapping()
    public ResponseEntity<Food> updateFood(@RequestBody FoodUpdate foodUpdate) {

        // Mock update:
        if (foodUpdate.getId() == 1L) {
            Food food = new Food(foodUpdate.getId(), "name", Food.Type.APPETIZER, foodUpdate.getRating());
        // end mock

            return ResponseEntity.ok(food);
        }

        return ResponseEntity.notFound().build();
    }
}
