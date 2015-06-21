package cmpe295.sjsu.edu.salesman;

/**
 * Created by Rucha on 6/20/15.
 */
public class User {
    public Name name;
    public String password;
    public String email;


    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
