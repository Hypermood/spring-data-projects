package course.springdata.gamestore.entities;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @NonNull
    @ManyToMany
    @JoinTable(
            name = "orders_games",
            joinColumns = @JoinColumn(name = "order_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "game_id",referencedColumnName = "id")
    )
    private List<Game> products;

}
