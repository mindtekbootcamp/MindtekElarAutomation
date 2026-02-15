package elar.api.utils;

import elar.api.pojos.CreateCarrierRequest;

public class CarrierRequestFactory {

    private CarrierRequestFactory() {
    }

    /**
     * Default carrier request with generated unique fields
     */
    public static CreateCarrierRequest defaultCarrier() {

        CarrierDataGenerator generator = CarrierDataGenerator.generateCarrierData();

        CreateCarrierRequest request = new CreateCarrierRequest();
        request.setDefaultValues();

        request.setPolicy_number(generator.policyNumber);
        request.setMc_number(generator.mc);
        request.setDot_number(generator.dot);
        request.setAbbreviation(generator.abbr);
        request.setCarrier_name(generator.carrierName);

        return request;
    }
}
