package course.springdata.gamestore.init;

import course.springdata.gamestore.entities.User;
import course.springdata.gamestore.services.interfaces.GameService;
import course.springdata.gamestore.services.interfaces.OrderService;
import course.springdata.gamestore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Scanner;

public class ControllerApp {
    Scanner scanner = new Scanner(System.in);

    private User currentUser = new User();
    private GameService gameService;
    private UserService userService;
    private OrderService orderService;


    public ControllerApp(GameService gameService, UserService userService, OrderService orderService) {
        this.gameService = gameService;
        this.userService = userService;
        this.orderService = orderService;
    }

    public void listen(String command){
        while(!command.equals("Abort")){

            try{

                String[] commandArr = command.split("\\|");

                switch (commandArr[0]){
                    case "RegisterUser":
                        userService
                                .registerUser(commandArr[1],commandArr[2],commandArr[3],commandArr[4]);
                        break;
                    case "LoginUser":
                        currentUser = userService.loginUser(currentUser,commandArr[1],commandArr[2]);
                        break;
                    case "Logout":
                        currentUser = userService.logout(currentUser);
                        break;
                    case "AddGame":
                        gameService.addGame(currentUser,commandArr[1],commandArr[2],commandArr[3],
                                commandArr[4],commandArr[5],commandArr[6],commandArr[7]);
                        break;
                    case "EditGame":
                        String[] temp = new String[commandArr.length-2];
                        System.arraycopy(commandArr,2,temp,0,commandArr.length-2);
                        gameService.editGame(currentUser,commandArr[1],temp);
                        break;
                    case "DeleteGame":
                        gameService.deleteGame(currentUser,commandArr[1]);
                        break;
                    case "AllGames":
                        gameService.showAllGames();
                        break;
                    case "DetailsGame":
                        gameService.showDetailsGame(commandArr[1]);
                        break;
                    case "OwnedGames":
                        gameService.showOwnedGames(currentUser);
                        break;
                    case "AddItem":
                        currentUser = userService.addItem(currentUser,commandArr[1]);
                        break;
                    case "RemoveItem":
                        currentUser = userService.removeItem(currentUser,commandArr[1]);
                        break;
                    case "BuyItem":
                        currentUser = userService.buyGame(currentUser,commandArr[1]);
                        break;
                    case "BuyAllItems":
                        currentUser = userService.buyAllItems(currentUser);
                        break;
                    default:
                        System.out.println("Invalid command.");
                }

            }catch(Exception e){
                System.out.println(e.getMessage());
            }

            System.out.println("Enter new command or abort controller.");
            command = scanner.nextLine();

        }
    }


}
