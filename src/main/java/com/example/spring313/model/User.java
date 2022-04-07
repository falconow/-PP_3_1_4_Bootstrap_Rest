package com.example.spring313.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "email", columnList = "email", unique = true)
        }
)

public class User implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "FirsName")
   private String FirstName;

   @Column(name = "LastName")
   private String LastName;

   @Column(name = "Age")
   private String Age;

   @Column(name = "Email")
   private String Email;

   @Column(name = "Password")
   private String Password;

   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinTable(name = "users_role",
           joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
   )
   private List<Role> roles = new ArrayList<>();


   public User() {}


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFirstName() {
      return FirstName;
   }

   public void setFirstName(String firstName) {
      FirstName = firstName;
   }

   public String getLastName() {
      return LastName;
   }

   public void setLastName(String lastName) {
      LastName = lastName;
   }

   public String getAge() {
      return Age;
   }

   public void setAge(String age) {
      Age = age;
   }

   public String getEmail() {
      return Email;
   }

   public void setEmail(String email) {
      Email = email;
   }

   public void setPassword(String password) {
      Password = password;
   }

   public List<Role> getRoles() {
      return roles;
   }

   public void setRoles(List<Role> roles) {
      this.roles = roles;
   }

   public void setCollectionsRoles(List<Role> collectionsRoles) {
      roles = collectionsRoles;
   }

   public List<Role> getCollectionsRoles() {
      return roles;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
   }

   @Override
   public String getUsername() {
      return Email;
   }

   @Override
   public String getPassword() {
      return Password;
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
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", FirstName='" + FirstName + '\'' +
              ", LastName='" + LastName + '\'' +
              ", Age='" + Age + '\'' +
              ", Email='" + Email + '\'' +
              ", Password='" + Password + '\'';
   }
}
