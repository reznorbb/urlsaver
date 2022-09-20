package com.rezi.recruitment.urlsaver.dao;

import java.sql.Blob;
import java.util.Objects;
import org.hibernate.Hibernate;

import javax.persistence.*;

@Entity
public class DocumentContent {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "data", nullable = false)
    private Blob data;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private DocumentMetadata documentMetadata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getData() {
        return data;
    }

    public void setData(Blob data) {
        this.data = data;
    }

    public DocumentMetadata getDocumentMetadata() {
        return documentMetadata;
    }

    public void setDocumentMetadata(DocumentMetadata documentMetadata) {
        this.documentMetadata = documentMetadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DocumentContent that = (DocumentContent) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
