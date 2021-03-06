package com.example.myapp.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.ElementCollection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Recipe {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String title;

  @ManyToOne
  @JsonIgnore
  private User chef; 

  @Lob 
  @Column(name="DESCRIPION", length=512)
  private String description; 
  
  private String youtubeUrl; 
  private String imageUrl;

  @ManyToMany
  @JsonIgnore
  @JoinTable(name = "recipe_user",
  joinColumns = { @JoinColumn(name = "fk_recipe") },
  inverseJoinColumns = { @JoinColumn(name = "fk_user") })
  private List<User> likes; 

  @ManyToOne
  @JsonIgnore
  private Cuisine cuisine;

  @ElementCollection(targetClass=String.class)
  private List<String> ingredients; 
  
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
	public String getYoutubeUrl() {
		return youtubeUrl;
	}
	public void setYoutubeUrl(String youtubeUrl) {
		this.youtubeUrl = youtubeUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<User> getLikes() {
		return likes;
	}
	public void setLikes(List<User> likes) {
		this.likes = likes;
	} 

	public void addUserLiked(User user) {
		this.likes.add(user);
	}

	public void removeUserLiked(User user) {
		this.likes.remove(user);
	}

	public Cuisine getCuisine() {
		return cuisine; 
	}

	public void setCuisine(Cuisine cuisine) {
		this.cuisine = cuisine; 
	}

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients (List<String> ingredients) {
		this.ingredients = ingredients;
	}


}
