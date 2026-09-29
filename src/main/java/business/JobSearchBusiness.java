package business;

import pages.JobSearchPage;

public class JobSearchBusiness {

    private final JobSearchPage jobSearchPage;

    public JobSearchBusiness(JobSearchPage jobSearchPage) {
        this.jobSearchPage = jobSearchPage;
    }

    public void openJobsPage() {
        jobSearchPage.open();
    }

    public void searchForJobWithButton(String keyword) {
        jobSearchPage.scrollToSearchInput();
        jobSearchPage.enterSearchKeyword(keyword);
        jobSearchPage.clickSearchButton();
    }

    public void searchForJobWithEnter(String keyword) {
        jobSearchPage.enterSearchKeyword(keyword);
        jobSearchPage.submitSearchWithEnter();
    }

    public void searchWithScrollToTop(String keyword) {
        jobSearchPage.enterSearchKeyword(keyword);
        jobSearchPage.scrollToTop();
        jobSearchPage.clickSearchButton();
    }

    public boolean isClearFiltersVisible() {
        return jobSearchPage.isClearFiltersVisible();
    }

    public boolean isClearFiltersInvisible() {
        return jobSearchPage.isClearFiltersInvisible();
    }

    public boolean isDataLoaded() {
        return jobSearchPage.isDataLoaded();
    }

    public boolean isNoJobsMessageVisible() {
        return jobSearchPage.isNoJobsMessageVisible();
    }
}