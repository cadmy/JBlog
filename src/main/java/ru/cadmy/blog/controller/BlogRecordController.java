package ru.cadmy.blog.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.cadmy.blog.model.*;
import ru.cadmy.blog.service.*;

import java.util.*;

import static ru.cadmy.blog.controller.UserController.*;

@Controller
public class BlogRecordController
{

    private final static Logger logger = Logger.getLogger(BlogRecordController.class);
    private String systemMessage = "";
    private String messageStyle = HIDDEN_STYLE;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogRecordService blogRecordService;

    @Autowired
    CommentService commentService;

    @RequestMapping(value = {"/home", "/", "/JBlog"})
    public String index(Map<String, Object> map) {
        map.put("messageStyle", messageStyle);
        map.put("systemMessage", systemMessage);
        clearSystemMessage();
        return "index";
    }

    @RequestMapping(value = {"/JBlog/newrecord", "/newrecord"})
    public String newrecord(Map<String, Object> map) {
        map.put("blogRecord", new BlogRecord());
        return "newrecord";
    }

    @RequestMapping(value = "/login")
    public String loginindex(Map<String, Object> map,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        if (error != null) {
            map.put("messageStyle", ALERT_DANGER_STYLE);
            map.put("systemMessage", "Неверное имя пользователя или пароль");
            clearSystemMessage();
            return "index";
        }

        if (logout != null) {
            map.put("messageStyle", ALERT_SUCCESS_STYLE);
            map.put("systemMessage", "Вы успешно вышли из системы.");
            clearSystemMessage();
            return "index";
        }
        return "index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBlogRecord(@ModelAttribute("blogRecord") BlogRecord blogRecord, BindingResult result) {
        blogRecord.setUser(userService.getCurrentUser());
        if (blogRecord.getDate() == null)
        {
            blogRecord.setDate(new Date());
        }
        blogRecordService.addBlogRecord(blogRecord);
        logger.info("BlogRecord #".join(blogRecord.getId().toString()).join(" was created"));
        return "redirect:/";
    }

    @RequestMapping(value = {"/refresh", "/JBlog/refresh"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String refreshBlogTable() {
        if (!blogRecordService.blogRecordList().isEmpty())
        {
            StringBuilder jsonResult =  new StringBuilder();
            jsonResult.append("{ \"data\": [");
            for (BlogRecord blogRecord : blogRecordService.blogRecordList())
            {
                jsonResult.append("[");
                jsonResult.append("\"");
                jsonResult.append("<a href=\'/JBlog/blogentry?blogid=" + blogRecord.getId() + "\'>" + blogRecord.getTitle() + "</a>");
                jsonResult.append("\" ,\"");
                String body = blogRecord.getBody();
                if (body.length() > 160){
                    body = body.substring(0, 159);
                }
                jsonResult.append(body);
                jsonResult.append("\"],");
            }
            jsonResult.deleteCharAt(jsonResult.length()-1);
            jsonResult.append("]}");
            return jsonResult.toString();
        }
        return "{}";
    }

    @RequestMapping(value = "/delete/{blogRecordId}", method = RequestMethod.POST)
    public String deleteBalanceRecord(@PathVariable("blogRecordId") Long blogRecordId) {
        blogRecordService.removeBlogRecord(blogRecordId);
        logger.info("Blog record #".join(blogRecordId.toString()).join(" was deleted"));
        return "redirect:/JBlog/";
    }

    @RequestMapping(value = {"/blogentry", "/JBlog/blogentry"}, params = {"blogid"})
    public String blogentry(Map<String, Object> map, @RequestParam(value = "blogid") String blogid) {
        map.put("comment", new Comment());

        try
        {
            Long id = Long.valueOf(blogid);
            map.put("blogRecord", blogRecordService.getBlogRecordById(id));
        }
        catch (NumberFormatException e)
        {
            logger.info("Not able to parse blog id");
        }
        return "blogentry";
    }

    @RequestMapping(value = {"/refresh_comments", "/JBlog/refresh_comments"},
                    method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String refreshComments( @RequestParam(value = "blogid") String blogid) {
        try
        {
            Long id = Long.valueOf(blogid);
            List<Comment> comments = commentService.commentListForBlogRecordId(id);
            if (!comments.isEmpty())
            {
                StringBuilder jsonResult =  new StringBuilder();
                jsonResult.append("{ \"data\": [");
                for (Comment comment : comments)
                {
                    jsonResult.append("[");
                    jsonResult.append("\"");
                    jsonResult.append(comment.getDate().toString());
                    jsonResult.append("\" ,\"");
                    jsonResult.append(comment.getUser().getUsername());
                    jsonResult.append("\" ,\"");
                    jsonResult.append(comment.getContent()); //TODO cut 160
                    jsonResult.append("\"],");
                }
                jsonResult.deleteCharAt(jsonResult.length()-1);
                jsonResult.append("]}");
                return jsonResult.toString();
            }
        }
        catch (NumberFormatException e)
        {
            logger.info("Comments are not available");
        }
        return "{}";
    }

    @RequestMapping(value = "/add_comment", method = RequestMethod.POST)
    public String addComment(@ModelAttribute("comment") Comment comment,
                             @RequestParam(value = "blogid") String blogid, BindingResult result) {
        comment.setUser(userService.getCurrentUser());
        comment.setDate(new Date());
        Long id;
        try {
            id = Long.valueOf(blogid);
            comment.setBlogRecord(blogRecordService.getBlogRecordById(id));
        }
        catch (NumberFormatException e)
        {
            logger.info("Comments are not available");
            return "redirect:/JBlog/error";
        }
        commentService.addComment(comment);
        logger.info("Comment was added");
        return "redirect:/JBlog/blogentry?blogid="+id;
    }


    public void getSystemMessage(String message, String style){
        messageStyle = style;
        systemMessage = message;
    }

    private void clearSystemMessage() {
        messageStyle = HIDDEN_STYLE;
        systemMessage = "";
    }
}
