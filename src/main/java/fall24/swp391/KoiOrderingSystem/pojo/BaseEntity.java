package fall24.swp391.KoiOrderingSystem.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity
{
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Account createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private Account updatedBy;

    @PrePersist
    protected void onCreate(){

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        DateTimeFormatter createdAt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter updatedAt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    }
    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
        DateTimeFormatter updatedAt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }

}
