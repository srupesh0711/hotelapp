package com.HMS.Repository;

import com.HMS.Entity.AppUser;
import com.HMS.Entity.Property;
import com.HMS.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //findByUserId
    List<Review> findByAppUser(AppUser user);

    //findbyuserandproperty
    boolean existsByAppUserAndProperty(AppUser user, Property property);

}