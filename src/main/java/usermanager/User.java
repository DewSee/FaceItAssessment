package faceitspring;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Entity
@Data
@AllArgsConstructor
public class User extends ResourceSupport {

    private String firstName;
    private String lastName;
    @Id
    private String nickName;
    private String password;
    private String email;
    private String country;

    protected User() {
    }

    @Override
    public String toString() {
        return "User{" + "firstName=" + firstName + ", lastName=" + lastName + ", nickName=" + nickName + ", password=" + password + ", email=" + email + ", country=" + country + '}';
    }

}
