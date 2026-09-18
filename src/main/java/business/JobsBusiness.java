package business;

import pages.JobsPage;

public class JobsBusiness {

    private final JobsPage jobsPage;

    public JobsBusiness(JobsPage jobsPage) {
        this.jobsPage = jobsPage;
    }

    public void applyFilter(String category, String filterName) {
        jobsPage.filter(category, filterName);
    }

    public boolean isFilterApplied(String category, String filterName) {
        return jobsPage.isFilterChecked(category, filterName);
    }
}