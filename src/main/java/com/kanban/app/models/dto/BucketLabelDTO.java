package com.kanban.app.models.dto;

public class BucketLabelDTO {
    private Long id;
    private EntityIdentifier label;
    private EntityIdentifier bucket;
    private EntityIdentifier createdBy;

    public BucketLabelDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public EntityIdentifier getLabel() { return label; }
    public void setLabel(EntityIdentifier label) { this.label = label; }
    public EntityIdentifier getBucket() { return bucket; }
    public void setBucket(EntityIdentifier bucket) { this.bucket = bucket; }
    public EntityIdentifier getCreatedBy() { return createdBy; }
    public void setCreatedBy(EntityIdentifier createdBy) { this.createdBy = createdBy; }
}
