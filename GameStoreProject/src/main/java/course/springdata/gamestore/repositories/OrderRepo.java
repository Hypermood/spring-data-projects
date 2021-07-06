package course.springdata.gamestore.repositories;

import course.springdata.gamestore.entities.Game;
import course.springdata.gamestore.entities.Order;
import course.springdata.gamestore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
}
