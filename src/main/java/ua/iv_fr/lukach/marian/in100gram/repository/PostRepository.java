package ua.iv_fr.lukach.marian.in100gram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.iv_fr.lukach.marian.in100gram.entity.Post;
import ua.iv_fr.lukach.marian.in100gram.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByUserOrderByDateCreatedDesc(User user);
    List<Post> findAllByOrderByDateCreatedDesc();
    Optional<Post> findPostByIdAndUser(Long id, User user);

}
