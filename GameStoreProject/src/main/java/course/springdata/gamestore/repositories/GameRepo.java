package course.springdata.gamestore.repositories;

import course.springdata.gamestore.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepo extends JpaRepository<Game,Long> {
}
