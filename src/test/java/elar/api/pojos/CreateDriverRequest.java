package elar.api.pojos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateDriverRequest {

    private String full_name;
    private String logbook_email;
    private String logbook_password;
    private Boolean is_staff;
    private Boolean is_local;
    private Boolean twic;
    private String driving_license_exp;
    private String medical_certification_exp;
    private List<Object> contacts_phone;
    private List<Object> contacts_viber;
    private List<Object> contacts_other;

    public void setDefaultValues() {
        full_name = "Patel Harsh";
        logbook_email = "";
        logbook_password = "";
        is_staff = false;
        is_local = false;
        twic = false;
        driving_license_exp = "2026-10-29";
        medical_certification_exp = "2026-10-29";
        contacts_phone = new ArrayList<>();
        contacts_viber = new ArrayList<>();
        contacts_other = new ArrayList<>();
    }
}
