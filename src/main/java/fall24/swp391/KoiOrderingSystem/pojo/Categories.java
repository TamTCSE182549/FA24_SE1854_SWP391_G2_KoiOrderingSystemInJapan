//package fall24.swp391.KoiOrderingSystem.pojo;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//@Entity
//@Table(name = "categories")
//public class Categories {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "cate_name")
//    private String cateName;
//
//    @Column(name = "description")
//    private String description;
//
//    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
//    @JsonIgnore
//    private List<Kois> kois;
//}
