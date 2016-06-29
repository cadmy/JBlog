package ru.cadmy.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ru.cadmy.blog.model.BlogRecord;
import ru.cadmy.blog.model.Comment;

/**
 * Created by Cadmy on 29.06.2016.
 */
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
            Root<Comment> balanceRequest = criteriaQuery.from(Comment.class);
            Expression<String> exp = balanceRequest.get("blog_record");
            Predicate predicate = exp.in(blogRecord);
            criteriaQuery.where(predicate);
            return em.createQuery(criteriaQuery).getResultList();
        } else {
            return null;
        }
    }
}
