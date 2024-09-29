package fall24.swp391.KoiOrderingSystem.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Type_info")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class TypeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_info_name")
    private String typeInfoName;

    @OneToMany(mappedBy = "typeInfo", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Information> informations;
}
