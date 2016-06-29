package ru.cadmy.blog.service;

import ru.cadmy.blog.model.*;

import java.util.*;

/**
 * Created by Cadmy on 22.03.2016.
 */
public interface BlogRecordService
{
    void addBlogRecord(BlogRecord blogRecord);

    List<BlogRecord> blogRecordList();

    List<BlogRecord> blogRecordList(User user);

    List<BlogRecord> blogRecordList(Date date);

    List<BlogRecord> blogRecordList(User user, Date date);

    List<BlogRecord> blogRecordList(User user, Date dateFrom, Date dateTo);

    List<BlogRecord> blogRecordList(User user, String category);

    List<BlogRecord> blogRecordList(User user, Date date, String category);

    List<BlogRecord> blogRecordList(User user, Date dateFrom, Date dateTo, String category);

    BlogRecord getBlogRecordById(Long id);

    void removeBlogRecord(Long balanceRecordId);
}

