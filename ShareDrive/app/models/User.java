package models;

import play.db.jpa.*;
import play.data.validation.*;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User extends Model {
    
    @Required
    @MaxSize(15)
    @MinSize(4)
    @Match(value="^\\w*$", message="Not a valid username")
    public String username;
    
    @Required
    @MaxSize(15)
    @MinSize(5)
    public String password;
    
    @Required
    @MaxSize(100)
    public String fname; //user first name
    
    @Required
    @MaxSize(100)
    public String lname; //user last name
    
    @Required
    @Email
    public String email; //user email
    
    public boolean isActivated;
    
    public String activationCode;
    
    public String fieldOfStudy;
    
    public String university;
    
    public String aboutMe;
    
    public String contactNumber;
   
    public User(String fname, String lname, String email, String password, String username) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.isActivated = true; // should be false
        this.activationCode = "";
    }

    public String toString()  {
        return "User(" + username + ")";
    }
    
    
    public static User UserExist(String username) {
        return find("byUsername", username).first();
    }
    
    public static User UserExistByEmail(String email) {
        return find("byEmail", email).first();
    } 
}
