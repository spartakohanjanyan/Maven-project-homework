package helpers;

import data.CompanyData;
import java.util.ArrayList;
import java.util.List;

public class CompanyDataHelper {

    public static List getCompanyData() {
        List data = new ArrayList<>();

        data.add(new CompanyData("Specialist level", "Junior"));
        data.add(new CompanyData("Job salary", "Mentioned"));
        data.add(new CompanyData("Job category", "Sales/service management"));

        return data;
    }
}