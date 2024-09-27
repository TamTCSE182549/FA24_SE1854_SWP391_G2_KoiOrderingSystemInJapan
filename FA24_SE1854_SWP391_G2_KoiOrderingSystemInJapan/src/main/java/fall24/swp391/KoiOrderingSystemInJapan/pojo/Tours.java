package fall24.swp391.KoiOrderingSystemInJapan.pojo;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tours")
public class Tours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tour_name")
    private String tourName;

    @Column(name = "unit_price")
    private float unitPrice;

    @Column(name = "max_participants")
    private int maxParticipants;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "tour_img")
    private String tourImg;

    @Column(name = "create_by")
    private int createBy;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_by")
    private int updateBy;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

}
