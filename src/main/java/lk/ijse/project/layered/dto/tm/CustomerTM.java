package lk.ijse.project.layered.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class CustomerTM {
    private String cid;
    private String name;
    private String address;
    private String contact;
    private String email;
}
