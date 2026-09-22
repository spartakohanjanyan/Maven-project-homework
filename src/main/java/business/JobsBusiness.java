package business;

import pages.JobDetailsPage;
import pages.JobsPage;

public class JobsBusiness {

    private final JobsPage jobsPage;

    public JobsBusiness(JobsPage jobsPage) {
        this.jobsPage = jobsPage;
    }

    public JobDetailsPage applyFilterAndOpenFirstJob(
            String category,
            String filterName
    ) {
        jobsPage.filter(
                category,
                filterName
        );
        boolean isFilterApplied =
                jobsPage.isFilterChecked(
                        category,
                        filterName
                );
        if (!isFilterApplied) {
            throw new AssertionError(
                    "Filter was not applied: "
                            + category
                            + " -> "
                            + filterName
            );
        }
        return jobsPage.openFirstJob();
    }
}