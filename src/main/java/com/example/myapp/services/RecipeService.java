package com.example.myapp.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.example.myapp.repositories.RecipeRepository;
import com.example.myapp.repositories.UserRepository;
import com.example.myapp.repositories.CuisineRepository;
import com.example.myapp.models.Cuisine;
import com.example.myapp.models.Recipe;
import com.example.myapp.models.User;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "https://webdev-final-project-angular.herokuapp.com")
public class RecipeService {
    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    CuisineRepository cuisineRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/cuisine/{cuisineId}/recipe")
    public Recipe create(HttpServletResponse response, HttpSession session, @PathVariable("cuisineId") int cuisineId,
            @RequestBody Recipe recipe) {
        Optional<Cuisine> cuisineFound = cuisineRepository.findById(cuisineId);
        if (!cuisineFound.isPresent()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        Cuisine cuisine = cuisineFound.get();
        User currentUser = (User) session.getAttribute("user");
		if (currentUser == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		Optional<User> foundUser =  userRepository.findById(currentUser.getId()); 
        User user = foundUser.get();
        
        recipe.setChef(user);
        recipe.setCuisine(cuisine);
        return recipeRepository.save(recipe);
    }

    @PutMapping("/api/recipe/{recipeId}")
    public Recipe updateRecipe(HttpServletResponse response, @PathVariable("recipeId") int recipeId,
            @RequestBody Recipe newRecipe) {
        Optional<Recipe> recipeFound = recipeRepository.findById(recipeId);
        if (!recipeFound.isPresent()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        Recipe recipe = recipeFound.get();
        recipe.setDescription(newRecipe.getDescription());
        recipe.setTitle(newRecipe.getTitle());
        recipe.setYoutubeUrl(newRecipe.getYoutubeUrl());
        recipe.setImageUrl(newRecipe.getImageUrl());
        recipe.setIngredients(newRecipe.getIngredients());
        return recipeRepository.save(recipe);
    }

    @GetMapping("/api/cuisine/{cuisineId}/recipes")
    public List<Recipe> findRecipesForCuisine(HttpServletResponse response, @PathVariable("cuisineId") int cuisineId) {
        Optional<Cuisine> cuisineFound = cuisineRepository.findById(cuisineId);
        if (!cuisineFound.isPresent()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        Cuisine cuisine = cuisineFound.get();
        return cuisine.getRecipes();
    }

    @GetMapping("/api/recipe/{recipeId}/like")
    public List<User> findLikesForRecipe(HttpServletResponse response, @PathVariable("recipeId") int recipeId) {
        Optional<Recipe> recipeFound = recipeRepository.findById(recipeId);
        if (!recipeFound.isPresent()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        Recipe recipe = recipeFound.get();
        return recipe.getLikes();
    }

    @GetMapping("/api/recipe/{recipeId}/chef")
    public User findChefForRecipe(HttpServletResponse response, @PathVariable("recipeId") int recipeId) {
        Optional<Recipe> recipeFound = recipeRepository.findById(recipeId);
        if (!recipeFound.isPresent()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        Recipe recipe = recipeFound.get();
        return recipe.getChef();
    }

    @GetMapping("/api/recipe/{recipeId}")
    public Recipe findRecipe(HttpServletResponse response, @PathVariable("recipeId") int recipeId) {
        Optional<Recipe> recipeFound = recipeRepository.findById(recipeId);
        if (!recipeFound.isPresent()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        return recipeFound.get();
    }

    @DeleteMapping("/api/recipe/{recipeId}")
	public void deleteRecipe( @PathVariable("recipeId") int recipeId) {
		this.recipeRepository.deleteById(recipeId);
	}
}