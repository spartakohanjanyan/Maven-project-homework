import basetest.BaseTest;
import data.CompanyData;
import enums.FilterGroupName;
import helpers.CompanyDataHelper;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.JobDetailsPage;

import java.util.List;

public class JobsFiltersTest extends BaseTest {

    @DataProvider(name = "JobsFiltersData")
    public Object[][] getFilterData() {
        List data = CompanyDataHelper.getCompanyData();
        Object[][] result = new Object[data.size()][1];

        for (int i = 0; i < data.size(); i++) {
            result[i][0] = data.get(i);
        }
        return result;
    }
    @Test(dataProvider = "JobsFiltersData")

    public void verifyFilterInFirstJob(CompanyData filterData) {

        String category = filterData.getCategory();
        String filterName = filterData.getFilterName();

        Allure.parameter("Filter category", category);
        Allure.parameter("Filter name", filterName);

        FilterGroupName group = FilterGroupName.fromCategoryName(category);
        applyFilterStep(group, filterName);
        verifyFilterIsCheckedStep(group, category, filterName);
        JobDetailsPage jobDetailsPage = openFirstJobStep();
        verifyJobDetailsStep(jobDetailsPage, group, category, filterName);
    }

    @Step("Expand and select filter '{filterName}' in group '{group}'")
    private void applyFilterStep(FilterGroupName group, String filterName) {
        jobsPage.clickViewMoreIfPresent(group);
        jobsPage.filter(group, filterName);
    }

    @Step("Verify that the checkbox for '{filterName}' is displayed as checked")
    private void verifyFilterIsCheckedStep(FilterGroupName group, String category, String filterName) {
        boolean isChecked = jobsPage.isFilterChecked(group, filterName);
        Assert.assertTrue(isChecked,
                "Filter checkicon was not displayed for: " + category + " -> " + filterName);
    }

    @Step("Open the first job offer from the filtered results list")
    private JobDetailsPage openFirstJobStep() {
        return jobsPage.openFirstJob();
    }

    @Step("Verify parameter matching on the job details page")
    private void verifyJobDetailsStep(JobDetailsPage jobDetailsPage, FilterGroupName group, String category, String filterName) {
        String expectedFilter = filterName.trim();

        switch (group) {
            case JOB_CATEGORY:
                String actualCategory = jobDetailsPage.getCategory().trim();
                Assert.assertEquals(actualCategory, expectedFilter,
                        "Job Category does not match selected filter");
                break;

            case SPECIALIST_LEVEL:
                String actualCandidateLevel = jobDetailsPage.getCandidateLevel().trim();
                Assert.assertEquals(actualCandidateLevel, expectedFilter,
                        "Required candidate level does not match selected filter");
                break;

            case JOB_SALARY:
                String actualSalary = jobDetailsPage.getSalary().trim();
                Assert.assertFalse(actualSalary.isEmpty(),
                        "Salary field should not be empty for salary filter");
                break;

            default:
                throw new IllegalArgumentException("Unsupported filter category: " + category);
        }
    }
}