package course.springdata.gamestore.services;

import course.springdata.gamestore.DTOs.DetailedGameDto;
import course.springdata.gamestore.DTOs.OwnedGameDto;
import course.springdata.gamestore.DTOs.SimpleGameDto;
import course.springdata.gamestore.entities.Game;
import course.springdata.gamestore.entities.Order;
import course.springdata.gamestore.entities.User;
import course.springdata.gamestore.repositories.GameRepo;
import course.springdata.gamestore.repositories.OrderRepo;
import course.springdata.gamestore.repositories.UserRepo;
import course.springdata.gamestore.services.interfaces.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.NoSuchElementException;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final GameRepo gameRepo;
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public GameServiceImpl(GameRepo gameRepo, OrderRepo orderRepo, UserRepo userRepo) {
        this.gameRepo = gameRepo;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }


    public User persistUser(User user) {

        try{
            userRepo.save(user);
            return user;
        }
        catch(Exception err){
            throw new IllegalStateException("User info doesn't meet the validation requirements.");
        }

    }


    @Override
    public Game persistGame(Game game) {

        try{
            gameRepo.save(game);
            return game;
        }
        catch(Exception err){
            throw new IllegalStateException("Game info doesn't meet the validation requirements.");
        }

    }

    public Order persistOrder(Order order) {

        try{
            orderRepo.save(order);
            return order;
        }
        catch(Exception err){
            throw new IllegalStateException("Order info doesn't meet the validation requirements.");
        }

    }

    @Override
    public void addGame(User currentUser, String title, String price, String size, String trailer,
                        String thumbNail, String description, String releaseDate) {


        try {

            if(!currentUser.isLogged()){
                throw new IllegalStateException("You are not logged in..");
            }
            if(!currentUser.isAdministrator()){
                throw new IllegalStateException("You dont' have admin rights.");
            }


            double refPrice = Double.parseDouble(price);
            BigDecimal refSize = new BigDecimal(size);
            LocalDate refReleaseDate = LocalDate.parse(releaseDate);

            Game game = new Game(title,trailer,thumbNail,refSize,refPrice,description,refReleaseDate);
            persistGame(game);
            System.out.printf("%s was added to the games collection.%n",game.getTitle());


        }
        catch(Exception err){
            if(err.getClass().getSimpleName().equals("NumberFormatException") ||
                    err.getClass().getSimpleName().equals("ConstraintViolationException")){
                System.err.println("Invalid input.");
            }
            else{
                System.err.println(err.getMessage());
            }

        }


    }

    @Override
    public void editGame(User currentUser, String gameId, String[] values) {

        try {

            if(!currentUser.isLogged()){
                throw new IllegalStateException("You are not logged in.");
            }
            if(!currentUser.isAdministrator()){
                throw new IllegalStateException("You don't have admin rights.");
            }

            Game currentGame = gameRepo.findAll().stream()
                    .filter(g->g.getId()==Long.parseLong(gameId)).findFirst()
                    .orElseThrow(()->new NoSuchElementException("Such game doesn't exist."));


            Arrays.stream(values).forEach(comm->applyChanges(currentGame,comm));

            persistGame(currentGame);
            System.out.printf("%s was successfully updated.%n",currentGame.getTitle());


        }
        catch(Exception err){
            if(err.getClass().getSimpleName().equals("NumberFormatException") ||
                    err.getClass().getSimpleName().equals("ConstraintViolationException")){
                System.err.println("Invalid input.");
            }
            else{
                System.err.println(err.getMessage());
            }

        }

    }

    @Override
    public void deleteGame(User currentUser, String gameId) {

        try {

            if(!currentUser.isLogged()){
                throw new IllegalStateException("You are not logged in.");
            }
            if(!currentUser.isAdministrator()){
                throw new IllegalStateException("You don't have admin rights.");
            }

            Game currentGame = gameRepo.findAll().stream()
                    .filter(g->g.getId()==Long.parseLong(gameId)).findFirst()
                    .orElseThrow(()->new NoSuchElementException("Such game doesn't exist."));


            persistGame(currentGame);
            System.out.printf("%s was successfully deleted.%n",currentGame.getTitle());


        }
        catch(Exception err){
            System.err.println(err.getMessage());
        }

    }

    @Override
    public void showAllGames() {

        gameRepo.findAll().stream()
                .map(g->modelMapper.map(g, SimpleGameDto.class))
                .forEach(System.out::println);

    }

    @Override
    public void showDetailsGame(String title) {

        try {

            Game game = gameRepo.findAll().stream().filter(g -> g.getTitle().equals(title))
                    .findFirst().orElseThrow(() -> new NoSuchElementException("Such game doesn't exist."));

            System.out.println(modelMapper.map(game, DetailedGameDto.class));

        }
        catch(Exception err){
            System.err.println(err.getMessage());
        }

    }

    @Override
    public void showOwnedGames(User user) {
        try {

            if(!user.isLogged()){
                throw new IllegalStateException("You are not logged in.");
            }

            user.getGames().stream().map(g->modelMapper.map(g,OwnedGameDto.class)).forEach(System.out::println);

        }
        catch(Exception err){
            System.err.println(err.getMessage());
        }

    }

    private void applyChanges(Game game,String command) throws NumberFormatException {
        String[] arr = command.split("=");

        switch(arr[0]){
            case "size":
                game.setSize(new BigDecimal(arr[1]));
                return;
            case "price":
                game.setPrice(Double.parseDouble(arr[1]));
                return;
            case "description":
                game.setDescription(arr[1]);
                return;
            case "trailer":
                game.setTrailer(arr[1]);
                return;
            case "releaseDate":
                game.setReleaseDate(LocalDate.parse(arr[1]));
                return;
            case "title":
                game.setTitle(arr[1]);
                return;
            case "thumbnail":
                game.setImgThumbnail(arr[1]);

            default:
                throw new IllegalArgumentException("Invalid input");
        }

    }


}
