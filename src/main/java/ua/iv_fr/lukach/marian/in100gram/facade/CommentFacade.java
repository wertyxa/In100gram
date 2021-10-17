package ua.iv_fr.lukach.marian.in100gram.facade;

import org.springframework.stereotype.Component;
import ua.iv_fr.lukach.marian.in100gram.dto.CommentDTO;
import ua.iv_fr.lukach.marian.in100gram.entity.Comment;

@Component
public class CommentFacade {
    public CommentDTO commentToCommentDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setMessage(comment.getMessage());
        commentDTO.setUsername(comment.getUsername());

        return commentDTO;

    }
}
