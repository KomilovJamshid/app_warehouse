package uz.jamshid.app_warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.app_warehouse.entity.User;
import uz.jamshid.app_warehouse.entity.Warehouse;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.payload.UserDto;
import uz.jamshid.app_warehouse.repository.UserRepository;
import uz.jamshid.app_warehouse.repository.WarehouseRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    public Result add(UserDto userDto) {
        boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
        if (existsByPhoneNumber)
            return new Result("Phone number already exists", true);

        Set<Warehouse> warehouses = (Set<Warehouse>) warehouseRepository.findAllById(userDto.getWarehouseId());
        User user = new User();
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setCode(generateCode());
        user.setPassword(userDto.getPassword());
        user.setWarehouses(warehouses);
        return new Result("User added", true);
    }

    public Result get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new Result("Users sent successfully", true, userRepository.findAll(pageable));
    }

    public Result getById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return new Result("User by id sent", true, optionalUser.orElseGet(User::new));
    }

    public Result deleteUser(Integer id) {
        try {
            userRepository.deleteById(id);
            return new Result("User deleted", true);
        } catch (Exception exception) {
            return new Result("User nor found", false);
        }
    }

    public Result editUser(Integer id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new Result("User not found", true);
        User user = optionalUser.get();

        boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
        if (existsByPhoneNumber)
            return new Result("Phone number already exists", true);

        Set<Warehouse> warehouses = (Set<Warehouse>) warehouseRepository.findAllById(userDto.getWarehouseId());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setPassword(userDto.getPassword());
        user.setWarehouses(warehouses);
        userRepository.save(user);
        return new Result("User edited", true);
    }

    public String generateCode() {
        return String.valueOf(userRepository.count() + 1);
    }
}
