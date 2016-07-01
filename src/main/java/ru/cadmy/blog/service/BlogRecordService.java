package ru.cadmy.blog.service;

import ru.cadmy.blog.model.*;

import java.util.*;

public interface BlogRecordService
{
    void addBlogRecord(BlogRecord blogRecord);

    List<BlogRecord> blogRecordList();

    List<BlogRecord> blogRecordList(User user);

    List<BlogRecord> blogRecordList(Date date);

    List<BlogRecord> blogRecordList(User user, Date date);

    List<BlogRecord> blogRecordList(User user, Date dateFrom, Date dateTo);

    BlogRecord getBlogRecordById(Long id);

    void removeBlogRecord(Long balanceRecordId);
}

