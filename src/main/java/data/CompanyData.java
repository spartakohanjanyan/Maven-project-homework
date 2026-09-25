package data;

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
}