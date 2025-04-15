package ru.prodcontest.app.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Связь между ментором и студентом")
public class Connection {
    @Id
    UUID uuid = UUID.randomUUID();
    public enum Status {
        REQUEST,
        CANCEL,
        ACTIVE,
        STOP
    }
    String url;

    @ManyToOne
    @Schema(description = "Ментор, связанный со студентом", requiredMode = Schema.RequiredMode.REQUIRED)
    private Mentor mentor;

    @ManyToOne
    @Schema(description = "Студент, связанный с ментором", requiredMode = Schema.RequiredMode.REQUIRED)
    private Student student;

    @Enumerated(EnumType.ORDINAL)
    @Column
    @Schema(description = "Статус связи", example = "REQUEST", requiredMode = Schema.RequiredMode.REQUIRED)
    private Status status;

    public Connection(Mentor mentor, Student student) {
        this.mentor = mentor;
        this.student = student;
        this.status = Status.REQUEST;
    }
}