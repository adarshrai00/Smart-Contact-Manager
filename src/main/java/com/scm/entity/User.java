package com.scm.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@Builder
public class User  implements UserDetails {



    public User()
    {

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    @Column(unique=true , nullable = false)
    private String email;
    @Column(length=100000)
    private String about;
    private String role;
    private String profilePic;
    private boolean enabled=false;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;
    private String phoneNumber;


//    signup type
    @Enumerated(EnumType.STRING)
    private Providers provider=Providers.SELF;
    private  String providerUserId;

      @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
     private List<Contact> contacts=new ArrayList<>();


      @ElementCollection(fetch = FetchType.EAGER)
      private List<String> roleList=new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       Collection<SimpleGrantedAuthority> roles= roleList.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }




    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }
}

