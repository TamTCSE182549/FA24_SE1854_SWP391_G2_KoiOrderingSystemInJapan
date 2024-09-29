package fall24.swp391.KoiOrderingSystem.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "roles")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name",nullable = false,length = 30)
    private String name;
}

