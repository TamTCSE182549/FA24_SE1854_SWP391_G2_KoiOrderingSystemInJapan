package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleRequest {
    String email;
    String firstName;
    String lastName;
}
