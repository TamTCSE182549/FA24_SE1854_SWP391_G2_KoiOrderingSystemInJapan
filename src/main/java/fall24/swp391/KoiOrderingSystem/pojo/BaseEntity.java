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
    private LocalDateTime createdDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Account createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private Account updatedBy;

    @PrePersist
    protected void onCreate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        createdDate = LocalDateTime.parse(LocalDateTime.now().format(formatter));  // formatted date-time string
        updatedDate = LocalDateTime.parse(LocalDateTime.now().format(formatter));
    }
    @PreUpdate
    protected void onUpdate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        updatedDate = LocalDateTime.parse(LocalDateTime.now().format(formatter));
    }

}
