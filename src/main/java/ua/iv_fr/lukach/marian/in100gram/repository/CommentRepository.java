package ua.iv_fr.lukach.marian.in100gram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.iv_fr.lukach.marian.in100gram.entity.Comment;
import ua.iv_fr.lukach.marian.in100gram.entity.Post;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPost(Post post);
    Comment findByIdAndUserId(Long commentId, Long userId);
}
