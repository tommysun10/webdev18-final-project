package com.example.myapp.services;

import java.util.List;
import java.util.Optional;

import com.example.myapp.repositories.RecipeRepository;
import com.example.myapp.repositories.CuisineRepository;
import com.example.myapp.models.Cuisine;
import com.example.myapp.models.Recipe;

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


@RestController
public class RecipeService {
    @Autowired
	RecipeRepository recipeRepository;

    @Autowired
	CuisineRepository cuisineRepository;

	@PostMapping("/api/cuisine/{cuisineId}/recipe")
	public Recipe create(HttpServletResponse response, @PathVariable("cuisineId") int cuisineId, @RequestBody Recipe recipe) {
        Optional<Cuisine> cuisineFound = cuisineRepository.findById(cuisineId); 
    		if (!cuisineFound.isPresent()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			    return null; 
        }
        
        Cuisine cuisine = cuisineFound.get(); 
        recipe.setCuisine(cuisine); 
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
}