package course.springdata.gamestore.services;

import course.springdata.gamestore.entities.Game;
import course.springdata.gamestore.entities.Order;
import course.springdata.gamestore.entities.User;
import course.springdata.gamestore.repositories.GameRepo;
import course.springdata.gamestore.repositories.OrderRepo;
import course.springdata.gamestore.repositories.UserRepo;
import course.springdata.gamestore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final GameRepo gameRepo;
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(GameRepo gameRepo, OrderRepo orderRepo, UserRepo userRepo) {
        this.gameRepo = gameRepo;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }


    @Override
    public User persistUser(User user) {

        try{
            userRepo.save(user);
            return user;
        }
        catch(Exception err){
            throw new IllegalStateException("User info doesn't meet the validation requirements.");
        }

    }

    
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
    public void registerUser(String email, String password, String repeatPass, String fullName) {

        try{
            if(!password.equals(repeatPass)){
                throw new IllegalArgumentException("Password and repeated password don't match.");
            }

            User user = new User(email,password,fullName);
            adminRights(user);
            persistUser(user);

            System.out.printf("%s successfully registered.%n",user.getFullName());

        }
        catch(Exception err){
            System.err.println(err.getMessage());
        }

    }

    @Override
    public User loginUser(User currentUser,String email, String password) {

        try{

            if(currentUser.isLogged()){
                throw new IllegalStateException("There is already logged-in user. Logout first,then try again.");
            }

            User user = userRepo.findAll()
                    .stream().filter(u -> u.getEmail().equals(email)
                            && u.getPassword().equals(password)).findFirst()
                    .orElseThrow(()->new NoSuchElementException("Such user doesn't exist."));


            user.setLogged(true);
            System.out.printf("%s successfully logged in.%n",user.getFullName());
            return persistUser(user);

        }
        catch(Exception err){
            System.err.println(err.getMessage());

        }
        return currentUser;

    }

    @Override
    public User logout(User currentUser) {
        try{

            if(!currentUser.isLogged()){
                throw new IllegalStateException("There isn't logged-in user.");
            }

            currentUser.setLogged(false);
            System.out.printf("%s successfully logged out.%n",currentUser.getFullName());
            return persistUser(currentUser);

        }
        catch(Exception err){
            System.err.println(err.getMessage());
        }
        return currentUser;

    }

    @Override
    public User buyGame(User currentUser,String title) {

        try{

            if(!currentUser.isLogged()){
                throw new IllegalStateException("You are not logged in.");
            }

            Game game = gameRepo.findAll().stream().filter(g -> g.getTitle().equals(title))
                    .findFirst().orElseThrow(() -> new NoSuchElementException("Such game doesn't exist."));

            if(!currentUser.getShoppingCart().contains(game)){
                throw new IllegalStateException("This game is not in your shopping cart, first add it there.");
            }

            currentUser.getGames().add(game);
            currentUser.getShoppingCart().remove(game);
            Order order = new Order(currentUser, List.of(game));


            persistGame(game);
            persistOrder(order);

            System.out.printf("You successfully bought the following game - %s.%n",game.getTitle());
            return persistUser(currentUser);

        }
        catch(Exception err){
            System.err.println(err.getMessage());
        }
        return currentUser;

    }

    @Override
    public User addItem(User currentUser, String title) {

        try{

            if(!currentUser.isLogged()){
                throw new IllegalStateException("You are not logged in.");
            }

            Game game = gameRepo.findAll().stream().filter(g -> g.getTitle().equals(title))
                    .findFirst().orElseThrow(() -> new NoSuchElementException("Such game doesn't exist."));

            if(currentUser.getGames().contains(game)){
                throw new IllegalStateException("You already own this game.");
            }

            currentUser.getShoppingCart().add(game);


            persistGame(game);

            System.out.printf("You successfully added the following game - %s into your shopping cart.%n",game.getTitle());
            return persistUser(currentUser);

        }
        catch(Exception err){
            System.err.println(err.getMessage());
        }

        return currentUser;
    }

    @Override
    public User removeItem(User currentUser, String title) {

        try{

            if(!currentUser.isLogged()){
                throw new IllegalStateException("You are not logged in.");
            }

            Game game = gameRepo.findAll().stream().filter(g -> g.getTitle().equals(title))
                    .findFirst().orElseThrow(() -> new NoSuchElementException("Such game doesn't exist."));

            if(!currentUser.getShoppingCart().contains(game)){
                throw new IllegalStateException("You haven't put the game into your shopping cart.");
            }

            currentUser.getShoppingCart().remove(game);
            persistGame(game);
            System.out.printf("You successfully removed the following game - %s from your shopping cart.%n",game.getTitle());
            return persistUser(currentUser);

        }
        catch(Exception err){
            System.err.println(err.getMessage());
        }
        return currentUser;

    }

    @Override
    public User buyAllItems(User currentUser) {

        try{
            StringBuilder bd = new StringBuilder("You successfully bought the following games:");

            if(!currentUser.isLogged()){
                throw new IllegalStateException("You are not logged in.");
            }
            if(currentUser.getShoppingCart().size() == 0){
                throw new IllegalStateException("Your shopping cart is empty.");
            }

            for (int i = 0; i < currentUser.getShoppingCart().size(); i++) {

                Game temp = currentUser.getShoppingCart().get(i);
                currentUser.getGames().add(temp);
                bd.append("\n\t").append(temp.getTitle());
                persistGame(temp);

            }

            Order order = new Order(currentUser, currentUser.getShoppingCart());
            currentUser.getShoppingCart().clear();


            persistOrder(order);
            System.out.println(bd.toString());
            return persistUser(currentUser);

        }
        catch(Exception err){
            System.err.println(err.getMessage());
        }
        return currentUser;

    }


    private void adminRights(User user){
        if(this.userRepo.findAll().size()==0){
            user.setAdministrator(true);
        }
    }

}
