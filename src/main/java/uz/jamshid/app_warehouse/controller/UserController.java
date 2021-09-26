package uz.jamshid.app_warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.payload.UserDto;
import uz.jamshid.app_warehouse.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public Result addUser(@RequestBody UserDto userDto) {
        return userService.add(userDto);
    }

    @GetMapping
    public Result getUsers(@RequestParam int page) {
        return userService.get(page);
    }

    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public Result editUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        return userService.editUser(id, userDto);
    }
}
