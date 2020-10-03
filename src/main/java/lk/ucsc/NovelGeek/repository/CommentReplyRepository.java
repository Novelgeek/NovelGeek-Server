package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Comment;
import lk.ucsc.NovelGeek.model.CommentReply;
import lk.ucsc.NovelGeek.model.PostsComments;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentReplyRepository extends CrudRepository<CommentReply, Long> {
    List<CommentReply> findByPostscomments(PostsComments comment);
}
