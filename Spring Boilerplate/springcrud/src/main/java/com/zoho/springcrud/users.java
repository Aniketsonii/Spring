package com.zoho.springcrud;



import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class users {
    
    @Id
    private Integer id;
    private String name;
    private String email;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
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
    @Override
    public String toString() {
        // return super.toString();
        StringBuilder sb = new StringBuilder()
            .append("User: ")
            .append("id = ")
            .append(this.getId())
            .append(" email = ")
            .append(this.getEmail())
            .append(" Name = ")
            .append(this.getName())
            .append(" Team = ")
            .append(this.getTeam())
            .append("\n");
            return sb.toString();
    }

}
