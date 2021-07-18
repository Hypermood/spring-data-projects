package course.springdata.mvcproject.data.entities;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @NotNull
    private String name;

    @NonNull
    @NotNull
    @Column(length = 100)
    private String description;

    @NonNull
    @NotNull
    private boolean isFinished;

    @NonNull
    @NotNull
    private BigDecimal payment;

    private String startDate;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "company_id")
    private Company company;

    public Project(@NonNull @NotNull String name, @NonNull @NotNull String description,
                   @NonNull @NotNull boolean isFinished, @NonNull @NotNull BigDecimal payment, String startDate, Company company) {
        this.name = name;
        this.description = description;
        this.isFinished = isFinished;
        this.payment = payment;
        this.startDate = startDate;
        this.company = company;
    }

    public Project(@NonNull @NotNull String name, @NonNull @NotNull String description, @NonNull @NotNull boolean isFinished, @NonNull @NotNull BigDecimal payment, String startDate) {
        this.name = name;
        this.description = description;
        this.isFinished = isFinished;
        this.payment = payment;
        this.startDate = startDate;
    }
}
