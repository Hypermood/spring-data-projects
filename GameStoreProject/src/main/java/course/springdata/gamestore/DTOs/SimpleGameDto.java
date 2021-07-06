package course.springdata.gamestore.DTOs;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleGameDto {

    private String title;
    private double price;


    @Override
    public String toString() {
        return String.format("%s %s",title,price);
    }
}
