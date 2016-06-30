package ru.cadmy.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cadmy.blog.model.*;

import javax.persistence.criteria.*;
import java.util.List;

@Service
public class CommentServiceImpl extends ModelService implements CommentService {

    @Autowired
    private BlogRecordService blogRecordService;

    @Override
    @Transactional
    public void addComment(Comment comment) {
        em.persist(comment);
    }

    @Override
    @Transactional
    public List<Comment> commentListForBlogRecordId(Long blogid) {
        BlogRecord blogRecord = blogRecordService.getBlogRecordById(blogid);
        if (blogRecord != null) {
            CriteriaQuery<Comment> criteriaQuery = em.getCriteriaBuilder().createQuery(Comment.class);
            Root<Comment> commentRequest = criteriaQuery.from(Comment.class);
            Expression<String> exp = commentRequest.get("blogRecord");
            Predicate predicate = exp.in(blogRecord);
            criteriaQuery.where(predicate);
            return em.createQuery(criteriaQuery).getResultList();
        } else {
            return null;
        }
    }
}
