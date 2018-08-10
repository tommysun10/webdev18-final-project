package com.example.myapp.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Recipe {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String title;

  @ManyToOne
  @JsonIgnore
  private User chef; 

  private String description; 
  private String youtube; 

  @ManyToMany(mappedBy = "recipesLiked")
  private List<User> likes; 

  @ManyToOne
  @JsonIgnore
  private Cuisine cuisine;
  
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public User getChef() {
		return chef;
	}
	public void setChef(User chef) {
		this.chef = chef;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getYoutube() {
		return youtube;
	}
	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}
	public List<User> getLikes() {
		return likes;
	}
	public void setLikes(List<User> likes) {
		this.likes = likes;
	} 

	public Cuisine getCuisine() {
		return cuisine; 
	}

	public void setCusine(Cuisine cuisine) {
		this.cuisine = cuisine; 
	}


}
