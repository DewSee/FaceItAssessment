package faceitspring;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String nickName) {
        super("A user with this nickname already exists: " + nickName);
    }

}
