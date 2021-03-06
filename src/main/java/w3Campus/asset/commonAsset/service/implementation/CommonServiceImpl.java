package w3Campus.asset.commonAsset.service.implementation;

import w3Campus.asset.commonAsset.model.Enum.BloodGroup;
import w3Campus.asset.commonAsset.model.Enum.CivilStatus;
import w3Campus.asset.commonAsset.model.Enum.Gender;
import w3Campus.asset.commonAsset.model.Enum.Title;
import w3Campus.asset.commonAsset.service.CommonService;
import w3Campus.asset.employee.controller.EmployeeController;
import w3Campus.asset.employee.entity.Enum.Designation;
import w3Campus.asset.employee.entity.Enum.EmployeeStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.regex.Pattern;

@Service
public class CommonServiceImpl implements CommonService {
    //common things to employee and offender - start
    public void commonUrlBuilder(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("designations", Designation.values());
/*        model.addAttribute("provinces", Province.values());
        model.addAttribute("districtUrl", MvcUriComponentsBuilder
                .fromMethodName(WorkingPlaceRestController.class, "getDistrict", "")
                .build()
                .toString());
        model.addAttribute("stationUrl", MvcUriComponentsBuilder
                .fromMethodName(WorkingPlaceRestController.class, "getStation", "")
                .build()
                .toString());*/
        Object[] arg = {"designation", "id"};
        model.addAttribute("employeeUrl", MvcUriComponentsBuilder
                .fromMethodName(EmployeeController.class, "getEmployeeByWorkingPlace", arg)
                .build()
                .toString());
    }

    public void commonEmployeeAndStudent(Model model) {
        model.addAttribute("title", Title.values());
        model.addAttribute("gender", Gender.values());
        model.addAttribute("civilStatus", CivilStatus.values());
        model.addAttribute("employeeStatus", EmployeeStatus.values());
        model.addAttribute("designation", Designation.values());
        model.addAttribute("bloodGroup", BloodGroup.values());
    }


    //common mobile number length employee,offender,guardian, petitioner - start
    public String commonMobileNumberLengthValidator(String number) {
        if (number.length() == 9) {
            number = "0".concat(number);
        }
        return number;
    }

    //number (particular scenario) build
    public String numberIncrement(int newNumber, String code) {
        String makeNumber = "";
        if (newNumber != 0) {
            if ((newNumber < 10) && (newNumber > 0)) {
                makeNumber = code + "0000" + newNumber;
            }
            if ((newNumber < 100) && (newNumber > 10)) {
                makeNumber = code + "000" + newNumber;
            }
            if ((newNumber < 1000) && (newNumber > 100)) {
                makeNumber = code + "00" + newNumber;
            }
            if ((newNumber < 10000) && (newNumber > 1000)) {
                makeNumber = code + "0" + newNumber;
            }
            if ((newNumber > 10000)) {
                makeNumber = code + newNumber;
            }

            return makeNumber;
        } else {
            return code+"00001";
        }
    }

    @Override
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }

}
