package course.springdata.gamestore.services.interfaces;

import course.springdata.gamestore.entities.Game;
import course.springdata.gamestore.entities.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public interface GameService {
    Game persistGame(Game game);
    void addGame(User currentUser, String title, String price,
                 String size, String trailer, String thumbNail, String description, String releaseDate);
    void editGame(User currentUser,String gameId,String[] values);
    void deleteGame(User currentUser,String gameId);
    void showAllGames();
    void showDetailsGame(String name);
    void showOwnedGames(User user);
}
