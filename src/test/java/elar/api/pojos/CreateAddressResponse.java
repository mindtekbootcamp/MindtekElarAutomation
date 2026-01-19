package elar.api.pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAddressResponse {

    private String address;
    private String city;
    private String state;
    private String zip_code;
    private String name;
    private Integer id;
    private String uuid;
    private String full_address;
    private String created_at;
    private String updated_at;

}
