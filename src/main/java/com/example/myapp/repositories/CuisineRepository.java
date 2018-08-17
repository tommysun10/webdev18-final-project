package com.example.myapp.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.myapp.models.Cuisine;

public interface CuisineRepository extends CrudRepository<Cuisine, Integer> {
    @Query("SELECT c FROM Cuisine c WHERE c.title=:title")
    Iterable<Cuisine> findCuisineByName(@Param("title") String title);
}


