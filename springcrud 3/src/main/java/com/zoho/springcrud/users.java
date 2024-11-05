package com.zoho.springcrud;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;



@Entity
public class users {
    
    @Id
    private Integer id;

    @Pattern(regexp = "^[A-Za-z]{7,29}$",message = "Enter a valid name")
    @NotNull(message = "Name Cannot be Empty.")
    private String name;
    
    @Pattern(regexp = "\\A(?!\\s*\\Z).+",message = "Email Cannot be Empty")
    @NotNull(message = "Email Cannot be Empty")
    @Email(message = "Invalid Email Address")
    private String email;

    @Pattern(regexp = "^[A-Z]{2,10}$", message = "Team Cannot be Empty")
    @NotNull(message = "Team Cannot be Empty")
    private String team;


    public users() {
        
    }

    public users(String name, String email, String team) {
        this.name = name;
        this.email = email;
        this.team = team;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() throws ConstraintViolationException{
        try{
            return name;
        }
        catch(Exception e){
            throw new ConstraintViolationException("record alredy present");
        }
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() throws ConstraintViolationException{
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTeam() {
        return team;
    }
    public void setTeam(String team) {
        this.team = team;
    }
    // @Override
    // public String toString() {
    //     // return super.toString();
    //     StringBuilder sb = new StringBuilder()
    //         .append("User: ")
    //         .append("id = ")
    //         .append(this.getId())
    //         .append(" email = ")
    //         .append(this.getEmail())
    //         .append(" Name = ")
    //         .append(this.getName())
    //         .append(" Team = ")
    //         .append(this.getTeam())
    //         .append("\n");
    //         return sb.toString();
    // }

}
