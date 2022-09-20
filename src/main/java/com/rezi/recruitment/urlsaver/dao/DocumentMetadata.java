package com.rezi.recruitment.urlsaver.dao;

import java.time.LocalDateTime;
import java.util.Objects;
import org.hibernate.Hibernate;

import javax.persistence.*;

@Entity
public class DocumentMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "source", nullable = false, unique = false, length = 1024)
    private String source;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime timestamp) {
        this.created = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DocumentMetadata that = (DocumentMetadata) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
