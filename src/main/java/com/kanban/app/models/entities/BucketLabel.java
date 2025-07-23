package com.kanban.app.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bucket_label")
public class BucketLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "label_id")
    private Label label;

    @ManyToOne
    @JoinColumn(name = "bucket_id")
    private Bucket bucket;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    public BucketLabel() {}
    public BucketLabel(Label label, Bucket bucket) {
        this.label = label;
        this.bucket = bucket;
    }
    public BucketLabel(Long id, Label label, Bucket bucket, User createdBy) {
        this.id = id;
        this.label = label;
        this.bucket = bucket;
        this.createdBy = createdBy;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Label getLabel() { return label; }
    public void setLabel(Label label) { this.label = label; }
    public Bucket getBucket() { return bucket; }
    public void setBucket(Bucket bucket) { this.bucket = bucket; }
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
}
