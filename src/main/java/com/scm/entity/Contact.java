package com.scm.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String picture;
    private String description;
    private boolean favorite=false;
    private String linkedinLink;
    private String facebookLink;
    //    private List<String> socialLinks= new ArrayList<String>();
    private String cloudinaryImagePublicId;
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "contact",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private List<SocialLink> links=new ArrayList<>();
}
