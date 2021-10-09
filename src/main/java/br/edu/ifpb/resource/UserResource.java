package br.edu.ifpb.resource;

import br.edu.ifpb.domain.User;
import br.edu.ifpb.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class UserResource {

    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String addUser(@Valid User user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "add-user";
        }

        repository.save(user);
        return "redirect:/index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String showUserList(Model model) {

        model.addAttribute("users", repository.findAll());
        return "index";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Integer id, Model model) {

        User user = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id" + id));

        model.addAttribute("user", user);
        return "update-user";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updateUser(@PathVariable Integer id, @Valid User user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        repository.save(user);
        return "redirect:/index";
    }

}
