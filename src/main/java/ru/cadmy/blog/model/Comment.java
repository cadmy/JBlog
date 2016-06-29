package ru.cadmy.blog.model;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Cadmy on 21.03.2016.
 */
@Entity
@Table(name = "RECORD_COMMENT")
public
@Data
class Comment
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "blog_record")
    private BlogRecord blogRecord;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Type(type = "date")
    private Date date;

    @Column(name = "content", nullable = true, length = 1000)
    private String content;

    public Comment(){

    }

    public Comment(User user, BlogRecord blogRecord, Date date, String content){
        this.user = user;
        this.blogRecord = blogRecord;
        this.date = date;
        this.content = content;
    }

}
