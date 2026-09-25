package enums;

public enum FilterGroupName {
    JOB_CATEGORY("Job category", "Category"),
    SPECIALIST_LEVEL("Specialist level", "Required candidate level"),
    JOB_SALARY("Job salary", "Salary");

    private final String nameInJobsPage;
    private final String nameInJobsDetailsPage;

    FilterGroupName(String nameInJobsPage, String nameInJobsDetailsPage) {
        this.nameInJobsPage = nameInJobsPage;
        this.nameInJobsDetailsPage = nameInJobsDetailsPage;
    }

    public String getNameInJobsPage() {
        return nameInJobsPage;
    }

    public String getNameInJobsDetailsPage() {
        return nameInJobsDetailsPage;
    }

    public static FilterGroupName fromCategoryName(String categoryName) {
        if (categoryName == null) {
            throw new IllegalArgumentException("Category name cannot be null");
        }
        for (FilterGroupName group : values()) {
            if (group.getNameInJobsPage().equalsIgnoreCase(categoryName.trim())) {
                return group;
            }
        }
        throw new IllegalArgumentException("Unsupported filter category: " + categoryName);
    }
}