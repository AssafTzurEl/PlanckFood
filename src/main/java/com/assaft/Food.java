package com.assaft;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {

    Long id;
    String name;
    Type type;
    Integer rating;

    public enum Type {
        APPETIZER,
        MAIN_COURSE,
        DESSERT,
        HOT_DRINK,
        COLD_DRINK,
        ALCOHOL
    }
}
