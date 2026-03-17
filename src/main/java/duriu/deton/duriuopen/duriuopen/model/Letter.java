package duriu.deton.duriuopen.duriuopen.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("letter")
public class Letter {

    @Id
    private Long id;

    private String gmail;

    private String content;

    private LocalDateTime createdAt;

    public Letter() {
    }

    public Letter(Long id, String gmail, String content, LocalDateTime createdAt) {
        this.id = id;
        this.gmail = gmail;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

