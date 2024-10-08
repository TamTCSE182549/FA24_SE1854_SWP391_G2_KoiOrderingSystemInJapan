package fall24.swp391.KoiOrderingSystem.model.response;

import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.User;
import lombok.Data;

@Data
public class NofiticationRespone {
    Account email;
    User phone;
    String subject; //Tua de thong bao
    String message;// Noi dung thong bao
    String link;
}
