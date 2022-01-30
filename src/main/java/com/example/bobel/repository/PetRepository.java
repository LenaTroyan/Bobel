package com.example.bobel.repository;

import com.example.bobel.entity.Pet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByBreed(String breed);
}
