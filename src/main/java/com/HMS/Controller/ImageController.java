package com.HMS.Controller;

import com.HMS.Entity.AppUser;
import com.HMS.Entity.Images;
import com.HMS.Entity.Property;
import com.HMS.Repository.ImagesRepository;
import com.HMS.Repository.PropertyRepository;
import com.HMS.Service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private BucketService bucketService;
    private PropertyRepository propertyRepository;
    private ImagesRepository imagesRepository;

    public ImageController(BucketService bucketService, PropertyRepository propertyRepository, ImagesRepository imagesRepository) {
        this.bucketService = bucketService;
        this.propertyRepository = propertyRepository;
        this.imagesRepository = imagesRepository;
    }

    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadPropertyPhotos(@RequestParam MultipartFile file,
                                        @PathVariable String bucketName,
                                        @PathVariable long propertyId,
                                        @AuthenticationPrincipal AppUser user
    ) {
        Property property = propertyRepository.findById(propertyId).get();
        String imageUrl = bucketService.uploadFile(file, bucketName);
        Images images = new Images();
        images.setUrl(imageUrl);
        images.setProperty(property);
        Images save = imagesRepository.save(images);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

    // http://locahost:8080/api/v1/images/properties/1
    @GetMapping("/properties/{propertyId}")
    public ResponseEntity<List<Images>> getAllImages(
            @PathVariable long propertyId
    ){
        Property property = propertyRepository.findById(propertyId).get();
        List<Images> images = imagesRepository.findByProperty(property);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
}
