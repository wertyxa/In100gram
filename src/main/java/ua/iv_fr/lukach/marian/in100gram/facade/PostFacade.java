package ua.iv_fr.lukach.marian.in100gram.facade;

import org.springframework.stereotype.Component;
import ua.iv_fr.lukach.marian.in100gram.dto.PostDTO;
import ua.iv_fr.lukach.marian.in100gram.entity.Post;

@Component
public class PostFacade {
    public PostDTO postToPostDTO (Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setCaption(post.getCaption());
        postDTO.setLikes(post.getLikes());
        postDTO.setLocation(post.getLocation());
        postDTO.setUserLikes(post.getLikedUsers());
        return postDTO;
    }
}
