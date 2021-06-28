package br.com.agoliveira.springbootcommysql.model;

import br.com.agoliveira.springbootcommysql.model.audit.DateAudit;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "votes", uniqueConstraints = { @UniqueConstraint(columnNames = { "post_id", "user_id" }) })
public class Vote extends DateAudit {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "choice_id", nullable = false)
    private Choice choice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Vote() {

    }

    public Vote(final User user, final Post post, final Choice choice) {
        super();

        this.setCreatedAt(Instant.now());
        this.user = user;
        this.post = post;
        this.choice = choice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
