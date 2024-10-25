package com.example.NextU.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.NextU.models.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}
