package elar.api.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CreateCarrierRequest {

    private String carrier_type;
    private String status;
    private Boolean ifta;
    private Integer address_id;
    private String insurance;
    private String policy_expiration;
    private String policy_number;
    private Boolean other_licenses;
    private String license_name;
    private List<Object> contacts_phone;
    private List<Object> contacts_fax;
    private String mc_number;
    private String dot_number;
    private String abbreviation;
    private String carrier_name;

    public void setDefaultValues() {
        carrier_type = "Broker company";
        status = "Active";
        ifta = false;
        address_id = 7561;
        insurance = "Progressive";
        policy_expiration = "2027-07-07";
        other_licenses = false;
        license_name = "";
        contacts_phone = new ArrayList<>();
        contacts_fax = new ArrayList<>();
    }
}
