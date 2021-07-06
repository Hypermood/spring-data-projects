package course.springdata.gamestore.init;

import course.springdata.gamestore.constants.ConstructorTableVisualization;
import course.springdata.gamestore.services.interfaces.GameService;
import course.springdata.gamestore.services.interfaces.OrderService;
import course.springdata.gamestore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

import static course.springdata.gamestore.constants.ConstructorTableVisualization.TABLE_VISUALIZATION;

@Controller
public class Initializer implements CommandLineRunner {
    Scanner scanner = new Scanner(System.in);
    private GameService gameService;
    private UserService userService;
    private OrderService orderService;

    @Autowired
    public Initializer(GameService gameService, UserService userService, OrderService orderService) {
        this.gameService = gameService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {

            System.out.println("\n\n\n\n\n");
            System.out.println("Write 'Init' to start controller.");
            System.out.println(System.lineSeparator());
            if(scanner.nextLine().equals("Init")){
                System.out.println("  /\\_/\\\n ( o.o )\n  > ^ <\n");
                System.out.println(System.lineSeparator());
                System.out.println("Possible commands, some require authentication.");
                System.out.println(System.lineSeparator());
                System.out.println(TABLE_VISUALIZATION);
                System.out.println("Controller initializing...");
                System.out.println("Enter new command or abort controller.");
                ControllerApp controller = new ControllerApp(gameService,userService,orderService);
                controller.listen(scanner.nextLine());
                System.out.println("Controller stopping...");
                System.out.println("  /\\_/\\\n ( o.o )\n  > ^ <\n");
            }





    }
}
