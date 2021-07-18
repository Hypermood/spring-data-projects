package course.springdata.mvcproject.web.controllers;

import course.springdata.mvcproject.service.services.UserService;
import course.springdata.mvcproject.web.models.UserRegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/register",method = RequestMethod.GET)
    public ModelAndView register(){
        return new ModelAndView("user/register");
    }

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public ModelAndView registerConfirm(UserRegisterModel model){
       if(!model.getPassword().equals(model.getConfirmPassword())){
           return super.redirect("/register");
       }
       else{
           userService.registerUser(model);
           return super.redirect("/login");
       }

    }

    @RequestMapping(path = "/login",method = RequestMethod.GET)
    public ModelAndView login(){
        return new ModelAndView("user/login");
    }
}
