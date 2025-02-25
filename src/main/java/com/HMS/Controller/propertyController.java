package com.HMS.Controller;

import com.HMS.Entity.Property;
import com.HMS.Repository.PropertyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
// http://localhost:8080/api/v1/properties/search-hotels
public class propertyController {

    private PropertyRepository propertyRepository;

    public propertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/search-hotels")
    public List<Property> searchHotels(
          @RequestParam String name

    ){
        List<Property> properties = propertyRepository.searchHotels(name);
        return properties;
    }
}
