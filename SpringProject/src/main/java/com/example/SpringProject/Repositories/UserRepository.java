package com.example.SpringProject.Repositories;

import com.example.SpringProject.Models.BugUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<BugUser, Long> {
}
