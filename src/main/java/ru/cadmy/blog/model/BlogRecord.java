package ru.cadmy.blog.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BLOG")
@Data
@ToString
@EqualsAndHashCode
public class BlogRecord
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Type(type = "date")
    private Date date;

    @Column(name = "title", nullable = true, length = 500)
    private String title;

    @Column(name = "body", nullable = true, length = 10000)
    private String body;

    public BlogRecord(){

    }

    public BlogRecord(User user, Date date, String title, String body){
        this.user = user;
        this.date = date;
        this.title = title;
        this.body = body;
    }

}
