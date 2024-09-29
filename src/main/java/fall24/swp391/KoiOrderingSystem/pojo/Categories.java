package fall24.swp391.KoiOrderingSystem.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "categories")
public class Categories {

//    @JoinColumn(name = "category_id")
//    @OneToMany
//    private Kois kois;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cate_name")
    private String cateName;

    @Column(name = "description")
    private String description;
}
