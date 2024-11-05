package com.soni.SpringAuth.user;

import java.util.Set;
import org.springframework.security.core.userdetails.UserDetails;
import com.soni.SpringAuth.common.CONSTANT;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.soni.SpringAuth.role.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails{
    
    @Id
    @GeneratedValue
    private Integer id;

    private String firstname;
    
    private String lastname;

    private String username;
    
    private String email;
    
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = CONSTANT.USER_ROLE_JUNCTION_TABLE_NAME, joinColumns = {
    @JoinColumn(name = "user_id") }, inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> authorities;


    @Override
    public Set<Role> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

	@Override
	public String getPassword() {
		return password;
	}

    @Override
	public String getUsername() {
		return username;
	}
}
