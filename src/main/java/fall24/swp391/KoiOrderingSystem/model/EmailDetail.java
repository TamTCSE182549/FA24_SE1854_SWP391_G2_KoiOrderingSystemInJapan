package fall24.swp391.KoiOrderingSystem.model;


import fall24.swp391.KoiOrderingSystem.pojo.Account;
import lombok.Data;

@Data
public class EmailDetail {
    Account receiver;
    String subject;
    String link;
}
