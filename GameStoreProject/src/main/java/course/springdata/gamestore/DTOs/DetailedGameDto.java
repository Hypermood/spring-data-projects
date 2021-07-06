package course.springdata.gamestore.DTOs;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class DetailedGameDto {

    private String title;
    private double price;
    private String description;
    private LocalDate releaseDate;


    @Override
    public String toString() {
        return String.format("Title: %s\nPrice: %s\nDescription: %s\nRelease Date: %s-%s-%s",
                title,price,description,releaseDate.getDayOfMonth(),releaseDate.getMonth(),releaseDate.getYear());
    }
}
