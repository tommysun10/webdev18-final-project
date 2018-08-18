package com.example.myapp.models;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.myapp.models.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private Role role;

	@OneToMany(mappedBy = "chef")
	private List<Recipe> recipesOwned; 

	@ManyToMany
	private List<Recipe> recipesLiked; 


	@ManyToMany(cascade={CascadeType.ALL})
	@JsonIgnore
	@JoinTable(name="UserRelationship",
		joinColumns={@JoinColumn(name="followedId")},
		inverseJoinColumns={@JoinColumn(name="followerId")})
	private Set<User> followers = new HashSet<User>();
	
	@ManyToMany(mappedBy="followers")
	private Set<User> followed = new HashSet<User>();


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public List<Recipe> getRecipesOwned() {
		return recipesOwned;
	}

	public void setRecipesOwned(List<Recipe> recipesOwned) {
		this.recipesOwned = recipesOwned;
	}


	public List<Recipe> getRecipesLiked() {
		return recipesLiked;
	}

	public void setRecipesLiked(List<Recipe> recipesLiked) {
		this.recipesLiked = recipesLiked;
	}
	
	
	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public Set<User> getFollowed() {
		return followed;
	}

	public void setFollowed(Set<User> followed) {
		this.followed = followed;
	}
	
	public void addToFollowed(User user) {
		if(!user.getFollowers().contains(this))
			user.addToFollowers(this);
		this.followed.add(user);
	}
	
	public void addToFollowers(User user) {
			
		this.followers.add(user);
	}
	
	

}
