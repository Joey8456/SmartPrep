package com.BTA.SmartPrep.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Proficiencies")
public class Proficiency {

    @EmbeddedId
    private ProficiencyId id;

    @Column(name = "proficiency", nullable = false)
    private int proficiency;

    public Proficiency() {
    }

    public Proficiency(ProficiencyId id, int proficiency) {
        this.id = id;
        this.proficiency = proficiency;
    }

    public ProficiencyId getId() {
        return id;
    }

    public void setId(String userId, int categoryId) {
        this.id.userId = userId;
        this.id.categoryId = categoryId;
    }

    public int getProficiency() {
        return proficiency;
    }

    public void setProficiency(int proficiency) {
        this.proficiency = proficiency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proficiency that = (Proficiency) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Proficiency{" +
                "id=" + id +
                ", proficiency=" + proficiency +
                '}';
    }

    // Needed because Proficiency uses a composite key: user_ID + category_ID
    @Embeddable
    public static class ProficiencyId implements Serializable {

        @Column(name = "user_ID", nullable = false)
        private String userId;

        @Column(name = "category_ID", nullable = false)
        private int categoryId;

        public ProficiencyId() {
        }

        public ProficiencyId(String userId, int categoryId) {
            this.userId = userId;
            this.categoryId = categoryId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProficiencyId that = (ProficiencyId) o;
            return Objects.equals(userId, that.userId) && Objects.equals(categoryId, that.categoryId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, categoryId);
        }

        @Override
        public String toString() {
            return "ProficiencyId{" +
                    "userId=" + userId +
                    ", categoryId=" + categoryId +
                    '}';
        }
    }
}
