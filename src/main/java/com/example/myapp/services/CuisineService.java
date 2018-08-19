package com.example.myapp.services;

import com.example.myapp.models.Cuisine;
import java.util.List;
import java.util.Optional;

import com.example.myapp.repositories.CuisineRepository;

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
@CrossOrigin(origins = "https://webdev-final-project-angular.herokuapp.com",  allowCredentials = "true")
public class CuisineService {
	@Autowired
	CuisineRepository cuisineRepository;

	@PostMapping("/api/cuisine")
	public Cuisine create(@RequestBody Cuisine cuisine) {
		List<Cuisine> cuisineFound = (List<Cuisine>) cuisineRepository.findCuisineByName(cuisine.getTitle());
		if (!cuisineFound.isEmpty()) {
			return null;
		}
		return cuisineRepository.save(cuisine);
	}

	@GetMapping("/api/cuisines")
	public List<Cuisine> findAllCuisines() {
		return (List<Cuisine>) cuisineRepository.findAll();
	}

	@DeleteMapping("/api/cuisine/{cuisineId}")
	public void deleteCuisine( @PathVariable("cuisineId") int cuisineId) {
		this.cuisineRepository.deleteById(cuisineId);
	}

	@PutMapping("/api/cuisine/{cuisineId}")
	public Cuisine updateCuisine(HttpServletResponse response, @PathVariable("cuisineId") int cuisineId, @RequestBody Cuisine newCuisine) {
		Optional<Cuisine> cuisineFound = cuisineRepository.findById(cuisineId);
        if (!cuisineFound.isPresent()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

		Cuisine cuisine = cuisineFound.get();
		cuisine.setTitle(newCuisine.getTitle());
        return cuisineRepository.save(cuisine);
	}

}