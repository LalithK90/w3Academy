package lk.w3Campus.asset.commonAsset.service;

import org.springframework.ui.Model;

public interface CommonService {
    void commonUrlBuilder(Model model);
    void commonEmployeeAndStudent(Model model);
    String commonMobileNumberLengthValidator(String number);
    String numberIncrement(int newNumber, String code);

    boolean isValidEmail(String email);
}
