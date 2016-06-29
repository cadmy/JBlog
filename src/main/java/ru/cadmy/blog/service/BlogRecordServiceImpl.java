package ru.cadmy.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.cadmy.blog.model.*;

import javax.persistence.criteria.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Cadmy on 22.03.2016.
 */
@Service
public class BlogRecordServiceImpl extends ModelService implements BlogRecordService
{

    @Override
    @Transactional
    public void addBlogRecord(BlogRecord blogRecord) {
        em.persist(blogRecord);
    }

    @Override
    public List<BlogRecord> blogRecordList() {
        CriteriaQuery<BlogRecord> c = em.getCriteriaBuilder().createQuery(BlogRecord.class);
        c.from(BlogRecord.class);
        return em.createQuery(c).getResultList();
    }

    @Override
    @Transactional
    public List<BlogRecord> blogRecordList(User user) {
        if (user != null && user.getState() != null && user.getState().equals(State.ACTIVE)) {
            CriteriaQuery<BlogRecord> criteriaQuery = em.getCriteriaBuilder().createQuery(BlogRecord.class);
            Root<BlogRecord> balanceRequest = criteriaQuery.from(BlogRecord.class);
            Expression<String> exp = balanceRequest.get("user");
            Predicate predicate = exp.in(user);
            criteriaQuery.where(predicate);
            return em.createQuery(criteriaQuery).getResultList();
        } else {
            return null;
        }
    }

    @Override
    public List<BlogRecord> blogRecordList(Date date) {
        return null;
    }

    @Override
    public List<BlogRecord> blogRecordList(User user, Date date) {
        return null;
    }

    @Override
    public List<BlogRecord> blogRecordList(User user, Date dateFrom, Date dateTo) {
        return null;
    }

    @Override
    public List<BlogRecord> blogRecordList(User user, String category) {
        return null;
    }

    @Override
    public List<BlogRecord> blogRecordList(User user, Date date, String category) {
        return null;
    }

    @Override
    public List<BlogRecord> blogRecordList(User user, Date dateFrom, Date dateTo, String category) {
        return null;
    }

    @Override
    public BlogRecord getBlogRecordById(Long id) {
        return em.find(BlogRecord.class, id);
    }

    @Override
    @Transactional
    public void removeBlogRecord(Long balanceRecordId) {
        BlogRecord blogRecord = em.find(BlogRecord.class, balanceRecordId);
        if (blogRecord != null) {
            em.remove(blogRecord);
        }
    }
}



