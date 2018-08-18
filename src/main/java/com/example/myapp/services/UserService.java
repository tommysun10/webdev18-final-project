package com.example.myapp.services;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import com.example.myapp.models.Recipe;
import com.example.myapp.models.User;
import com.example.myapp.repositories.UserRepository;
import com.example.myapp.repositories.RecipeRepository;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class UserService {

    @Autowired
	UserRepository userRepository;

	@Autowired
	RecipeRepository recipeRepository;

	@PostMapping("/api/user")
	public User create(@RequestBody User user) {
		List<User> userFound = (List<User>) userRepository.findUserByUsername(user.getUsername());
		if (!userFound.isEmpty()) {
			return null; 
		}
		return userRepository.save(user);
	}

	@PostMapping("/api/login")
	public User login(@RequestBody User user, HttpSession session, HttpServletResponse response) {
		List<User> userFound = (List<User>) userRepository.findUserByUsernameAndPassword(user.getUsername(),
				user.getPassword());
		if (userFound.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null;
        }
		session.setAttribute("user", userFound.get(0));
		return userFound.get(0);
	}

	@GetMapping("/api/profile")
	public Optional<User> profile(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
		return userRepository.findById(currentUser.getId());
    }
    
	@PostMapping("/api/logout")
	public User logout(HttpSession session) {
		session.removeAttribute("user");
		return null;
	}

	@PutMapping("/api/user/{userId}")
	public User updateUser(@PathVariable("userId") int id, @RequestBody User newUser) {
		Optional<User> optional = userRepository.findById(id);
		List<User> userFound = (List<User>) userRepository.findUserByUsername(newUser.getUsername());

		if (!optional.isPresent()) {
			return null;
		}

		// checks if new username is taken
		if (!userFound.isEmpty()) {
			if (userFound.get(0).getId() != optional.get().getId()) {
				return null;
			}
		}

		User user = optional.get();
		user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		user.setPhone(newUser.getPhone());
		user.setEmail(newUser.getEmail());
		user.setRole(newUser.getRole());
		return userRepository.save(user);
	}

	@DeleteMapping("/api/user/{userId}")
	public void deleteUser(@PathVariable("userId") int id) {
		userRepository.deleteById(id);
	}

	@GetMapping("/api/user/{userId}")
	public Optional<User> findUserByUserId(@PathVariable("userId") String userId) {
		int id = Integer.parseInt(userId);
		return userRepository.findById(id);
	}

	@GetMapping("/api/users")
	public List<User> findAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@PostMapping("/api/register")
	public User register(@RequestBody User user, HttpSession session) {
		List<User> userFound = (List<User>) userRepository.findUserByUsername(user.getUsername());
		if (userFound.isEmpty()) {
			session.setAttribute("user", user);
			return userRepository.save(user);
		}
		return null;
	}
	
	@PostMapping("/api/user/recipe/{recipeId}/like")
	public User likeRecipe(@PathVariable("recipeId") int recipeId,HttpSession session, HttpServletResponse response) {
		Optional<Recipe> recipeFound = recipeRepository.findById(recipeId); 
		if (!recipeFound.isPresent()) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null; 
		}
	
		Recipe recipe = recipeFound.get(); 
		User currentUser = (User) session.getAttribute("user");
		if (currentUser == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		Optional<User> foundUser =  userRepository.findById(currentUser.getId()); 
		User user = foundUser.get();
		user.addRecipeLiked(recipe);
		recipe.addUserLiked(user); 
		recipeRepository.save(recipe);
		return userRepository.save(user);
	}
}