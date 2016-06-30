package ru.cadmy.blog.service;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.cadmy.blog.configuration.ApplicationContextConfig;
import ru.cadmy.blog.model.*;

import java.sql.Date;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = ApplicationContextConfig.class)
@ActiveProfiles("dev")
public class BlogRecordServiceImplIT {

    @Autowired
    BlogRecordService balanceService;

    @Test
    @Rollback(true)
    public void addBalanceRecord() throws Exception {
        User user = new User("IT@test.com", "test", "test", "test", "test", Role.GUEST, State.ACTIVE, "IT");
        BlogRecord blogRecord = new BlogRecord(user, Date.valueOf("2016-06-02"),  "Test", "Test");
        balanceService.addBlogRecord(blogRecord);
        BlogRecord blogRecordTest = balanceService.getBlogRecordById(blogRecord.getId());
        assertEquals(blogRecord, blogRecordTest);
    }

    @Test
    @Rollback(true)
    @Ignore
    public void addBalanceRecordWithSameData() throws Exception {
        User user = new User("IT@test.com", "test", "test", "test", "test", Role.GUEST, State.ACTIVE, "IT");
        BlogRecord blogRecord1 = new BlogRecord(user, Date.valueOf("2016-06-02"),  "Test", "Test");
        BlogRecord blogRecord2 = new BlogRecord(user, Date.valueOf("2016-06-02"),  "Test", "Test");
        balanceService.addBlogRecord(blogRecord1);
        balanceService.addBlogRecord(blogRecord2);
        BlogRecord blogRecordTest1 = balanceService.getBlogRecordById(blogRecord1.getId());
        BlogRecord blogRecordTest2 = balanceService.getBlogRecordById(blogRecord2.getId());
        assertThat(blogRecordTest1, not(blogRecordTest2));
    }

    @Test
    @Rollback(true)
    public void removeBalanceRecord() throws Exception {
        User user = new User("IT@test.com", "test", "test", "test", "test", Role.GUEST, State.ACTIVE, "IT");
        BlogRecord blogRecord = new BlogRecord(user, Date.valueOf("2016-06-02"), "Test", "Test");
        balanceService.addBlogRecord(blogRecord);
        long balanceRecordId = blogRecord.getId();
        balanceService.removeBlogRecord(balanceRecordId);
        BlogRecord blogRecordTest = balanceService.getBlogRecordById(balanceRecordId);
        assertEquals(blogRecordTest, null);
    }

    @Test
    @Rollback(true)
    public void getBalanceRecordList() throws Exception {
    }


}