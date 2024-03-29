package com.booking.app.repository;

import com.booking.app.model.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query(value="select * from comment where hotel_id=?1 order by date desc",nativeQuery=true)
    Iterable<Comment> getComments(long hotel_id);

    void delete(long id_comment);

    Comment findOne(long id_comment);
}
