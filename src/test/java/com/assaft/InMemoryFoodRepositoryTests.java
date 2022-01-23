package com.assaft;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryFoodRepositoryTests {

    @BeforeEach
    public void setupAll () {
        sut = new InMemoryFoodRepository();
    }

    @Test
    public void givenEmptyRepo_whenNotAddedFood_shouldReturnZeroItems() {

        // Not calling sut.save, so no items added

        Assertions.assertThat(sut.findAll()).hasSize(0);

        for (Food.Type type : Food.Type.values()) {
            Assertions.assertThat(sut.findByType(type)).hasSize(0);
        }
    }

    @Test
    public void givenEmptyRepo_whenAddedOneFood_shouldContainOneFood() {

        sut.save(FoodTestObjects.DESSERT_1);

        Assertions.assertThat(sut.findAll()).hasSize(1);
        Assertions.assertThat(sut.findByType(Food.Type.DESSERT)).hasSize(1);
    }

    @Test
    public void whenSavingOneFood_shouldReturnSameFood() {


        Food savedFood = sut.save(FoodTestObjects.DESSERT_1);

        Assertions.assertThat(savedFood.getName()).isEqualTo(FoodTestObjects.DESSERT_1.getName());
        Assertions.assertThat(savedFood.getType()).isEqualTo(FoodTestObjects.DESSERT_1.getType());
        Assertions.assertThat(savedFood.getRating()).isEqualTo(FoodTestObjects.DESSERT_1.getRating());
    }

    // TODO: Add more tests to cover full repo functionality

    private InMemoryFoodRepository sut; // System Under Test
}
