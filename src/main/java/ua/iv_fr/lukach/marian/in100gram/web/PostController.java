package ua.iv_fr.lukach.marian.in100gram.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.iv_fr.lukach.marian.in100gram.dto.PostDTO;
import ua.iv_fr.lukach.marian.in100gram.entity.Post;
import ua.iv_fr.lukach.marian.in100gram.facade.PostFacade;
import ua.iv_fr.lukach.marian.in100gram.payload.response.MessageResponse;
import ua.iv_fr.lukach.marian.in100gram.services.PostService;
import ua.iv_fr.lukach.marian.in100gram.validations.ResponseErrorValidation;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("ape/post")
@CrossOrigin
public class PostController {
    @Autowired
    private PostFacade postFacade;
    @Autowired
    private PostService postService;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDTO dto, BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Post post = postService.createPost(dto,principal);
        PostDTO createdPost = postFacade.postToPostDTO(post);
        return new ResponseEntity<>(createdPost, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        List<PostDTO> postDTO = postService.getAllPosts()
                .stream()
                .map(postFacade::postToPostDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @GetMapping("/user/posts")
    public ResponseEntity<List<PostDTO>> getAllPostsForUser(Principal principal){
        List<PostDTO> postDTO = postService.getAllPostsByUser(principal)
                .stream()
                .map(postFacade::postToPostDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(postDTO,HttpStatus.OK);
    }

    @PostMapping("/{postId}/{username}/like")
    public ResponseEntity<PostDTO> likePost(@PathVariable("postId") String postId,
                                            @PathVariable("username") String username){
        Post post = postService.likedPost(Long.parseLong(postId), username);
        PostDTO postDTO = postFacade.postToPostDTO(post);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }


    @PostMapping("/{postId}/delete")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable("postId") String postId, Principal principal){
        postService.deletePost(Long.parseLong(postId), principal);
        return new ResponseEntity<>(new MessageResponse("Post was deleted"), HttpStatus.OK);
    }
}
