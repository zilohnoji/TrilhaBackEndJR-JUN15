package com.donatoordep.rg.code.entities;

import com.donatoordep.rg.code.builders.entities.EmailCodeConfirmationSpecificationBuilder;
import jakarta.persistence.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_email_code")
public class EmailCodeConfirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String code;

    private LocalDateTime expiredAt;

    private EmailCodeConfirmation() {
    }

    public static class EmailCodeConfirmationBuilder implements EmailCodeConfirmationSpecificationBuilder {

        private EmailCodeConfirmation entity;

        private EmailCodeConfirmationBuilder() {
            this.reset();
        }

        @Override
        public EmailCodeConfirmation build() {
            return this.entity;
        }

        public static EmailCodeConfirmationBuilder builder() {
            return new EmailCodeConfirmationBuilder();
        }

        @Override
        public EmailCodeConfirmationSpecificationBuilder code(String code) {
            this.entity.setCode(code);
            return this;
        }

        @Override
        public EmailCodeConfirmationSpecificationBuilder expiredAt(LocalDateTime expiredAt) {
            this.entity.setExpiredAt(expiredAt);
            return this;
        }

        @Override
        public void reset() {
            this.entity = new EmailCodeConfirmation();
        }
    }


    public static EmailCodeConfirmation createCodeConfirmation(LocalDateTime expiredAt, Integer strength) {
        return EmailCodeConfirmation.EmailCodeConfirmationBuilder.builder()
                .expiredAt(expiredAt)
                .code(RandomStringUtils.randomAlphabetic(strength))
                .build();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        EmailCodeConfirmation that = (EmailCodeConfirmation) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}