package com.assaft;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface JpaFoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByType(Food.Type type);

    @Modifying
    @Query("UPDATE Food f SET f.rating = ?2 WHERE f.id = ?1")
    void updateRatingById(Long id, Integer newRating);
}
