import basetest.BaseTest;
import data.CompanyData;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.JobDetailsPage;

import java.util.List;

public class JobsFiltersTest extends BaseTest {

    @DataProvider(name = "JobsFiltersData")
    public Object[][] getFilterData() {

        List<CompanyData> data =
                CompanyData.getCompanyData();

        Object[][] result =
                new Object[data.size()][1];

        for (int i = 0; i < data.size(); i++) {

            result[i][0] = data.get(i);
        }
        return result;
    }

    @Test(dataProvider = "JobsFiltersData")
    public void verifyFilterInFirstJob(CompanyData filterData) {

        JobDetailsPage jobDetailsPage = jobsBusiness.applyFilterAndOpenFirstJob(
                filterData.getCategory(),
                filterData.getFilterName()
        );

        String category = filterData.getCategory();
        String expectedFilter = filterData.getFilterName().trim();

        switch (category) {

            case "Job category":
                String actualCategory = jobDetailsPage.getCategory().trim();
                Assert.assertEquals(
                        actualCategory,
                        expectedFilter,
                        "Job Category does not match selected filter"
                );
                break;

            case "Specialist level":
                String actualCandidateLevel = jobDetailsPage.getCandidateLevel().trim();
                Assert.assertEquals(
                        actualCandidateLevel,
                        expectedFilter,
                        "Required candidate level does not match selected filter"
                );
                break;

            case "Job salary":
                String actualSalary = jobDetailsPage.getSalary().trim();
                Assert.assertFalse(
                        actualSalary.isEmpty(),
                        "Salary field should not be empty for salary filter"
                );
                break;

            default:
                throw new IllegalArgumentException("Unsupported filter category: " + category);
        }
    }
}