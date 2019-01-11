package faceitspring;

import java.util.List;
import static java.util.stream.Collectors.toList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return repository.findAll();
    }

    @PostMapping("/users")
    User addNewUser(@RequestBody User newUser) {
        boolean isNewUser;

        isNewUser = repository.findAll().stream().noneMatch(u -> u.getNickName().equals(newUser.getNickName()));
        if (isNewUser) {
            repository.save(newUser);
        } else {
            throw new UserAlreadyExistsException(newUser.getNickName());
        }
        return newUser;
    }

    @GetMapping("/users/{nickName}")
    User getUserByNickName(@PathVariable String nickName) {

        return repository.findById(nickName)
                .orElseThrow(() -> new UserNotFoundException(nickName));
    }

    @PutMapping("/users/update/{nickName}")
    User updateUser(@RequestBody User newUser, @PathVariable String nickName) {

        return repository.findById(nickName)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setNickName(newUser.getLastName());
                    user.setNickName(newUser.getNickName());
                    user.setPassword(newUser.getPassword());
                    user.setEmail(newUser.getEmail());
                    user.setCountry(newUser.getCountry());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setNickName(nickName);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{nickName}")
    void deleteUser(@PathVariable String nickName) {
        repository.deleteById(nickName);
    }

    @GetMapping("/users/country/{country}")
    List<User> findUserByCountry(@PathVariable String country) {
        return repository.findAll().stream().filter(u -> u.getCountry().equals(country)).collect(toList());

    }

    @GetMapping("/users/name/{name}")
    List<User> findUserByName(@PathVariable String name) {
        return repository.findAll().stream().filter(u -> u.getFirstName().toLowerCase().contains(name) || u.getLastName().toLowerCase().contains(name.toLowerCase())).collect(toList());

    }
}
