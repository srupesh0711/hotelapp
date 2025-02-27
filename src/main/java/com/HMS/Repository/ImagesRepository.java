package com.HMS.Repository;

import com.HMS.Entity.Images;
import com.HMS.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long> {
    List<Images>findByProperty(Property property);
}