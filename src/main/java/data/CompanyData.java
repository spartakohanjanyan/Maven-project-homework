package data;

import java.util.ArrayList;
import java.util.List;

public class CompanyData {

    private final String category;
    private final String filterName;

    public CompanyData(String category, String filterName) {
        this.category = category;
        this.filterName = filterName;
    }

    public String getCategory() {
        return category;
    }

    public String getFilterName() {
        return filterName;
    }

    public static List<CompanyData> getCompanyData() {

        List<CompanyData> data = new ArrayList<>();

        data.add(new CompanyData(
                "Job category",
                "Banking/credit"
        ));

        data.add(new CompanyData(
                "Specialist level",
                "Junior"
        ));

        data.add(new CompanyData(
                "Job salary",
                "Mentioned"
        ));

        return data;
    }
}