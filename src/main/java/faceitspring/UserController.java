package faceitspring;

import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    List<User> allUsers() {
        return repository.findAll();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{nickName}")
    User oneUser(@PathVariable String nickName) {

        return repository.findById(nickName)
                .orElseThrow(() -> new UserNotFoundException(nickName));
    }

    @PutMapping("/users/{nickName}")
    User replaceUser(@RequestBody User newUser, @PathVariable String nickName) {

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
    List<User> findByCountry(@PathVariable String country) {
        return repository.findAll().stream().filter(u -> u.getCountry().equals(country)).collect(toList());

    }
}
