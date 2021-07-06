package course.springdata.gamestore.DTOs;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OwnedGameDto {

    private String title;

    @Override
    public String toString() {
        return String.format("%s",title);
    }
}
