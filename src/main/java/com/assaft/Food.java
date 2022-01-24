package com.assaft;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Food {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Type type;
    private Integer rating;

    public enum Type {
        APPETIZER,
        MAIN_COURSE,
        DESSERT,
        HOT_DRINK,
        COLD_DRINK,
        ALCOHOL
    }
}
