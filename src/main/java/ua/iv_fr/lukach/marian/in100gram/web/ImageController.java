package ua.iv_fr.lukach.marian.in100gram.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.iv_fr.lukach.marian.in100gram.entity.ImageModel;
import ua.iv_fr.lukach.marian.in100gram.payload.response.MessageResponse;
import ua.iv_fr.lukach.marian.in100gram.services.ImageService;

import java.io.IOException;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("api/image")
public class ImageController {
    @Autowired
    ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        imageService.uploadImageToUser(file, principal);
        return ResponseEntity.ok(new MessageResponse("Image upload Successfully!"));
    }

    @PostMapping("/{postId}/upload")
    public ResponseEntity<MessageResponse> uploadImageToPost(@PathVariable("postId") String postId,
                                                             @RequestParam("file") MultipartFile file,
                                                             Principal principal) throws IOException{
        imageService.uploadImageToPost(file,principal,Long.parseLong(postId));
        return ResponseEntity.ok(new MessageResponse("Image upload Successfully"));
    }
    @GetMapping("/profileImage")
    public ResponseEntity<ImageModel> getImageToUser(Principal principal){
        ImageModel imageModel = imageService.getImageToUser(principal);
        return new ResponseEntity<>(imageModel, HttpStatus.OK);
    }
    @GetMapping("/{postId}/Image")
    public ResponseEntity<ImageModel> getImageToPost(@PathVariable("postId") String postId){
        ImageModel postImage = imageService.getImageToPost(Long.parseLong(postId));
        return new ResponseEntity<>(postImage, HttpStatus.OK);
    }



}
