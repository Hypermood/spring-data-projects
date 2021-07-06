package course.springdata.gamestore.entities;


import course.springdata.gamestore.validations.annotations.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "games")
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Pattern(regexp = "^[A-Z].+$")
    @javax.validation.constraints.Size(min = 3,max = 100)
    private String title;
    @NonNull
    @javax.validation.constraints.Size(min = 11,max = 11)
    private String trailer;
    @NonNull
    @Pattern(regexp = "^https?:\\/\\/.+$")
    private String imgThumbnail;
    @NonNull
    @Size
    private BigDecimal size;
    @NonNull
    @Min(value = 0)
    private double price;
    @NonNull
    @javax.validation.constraints.Size(min = 20)
    private String description;
    @NonNull
    private LocalDate releaseDate;

}
