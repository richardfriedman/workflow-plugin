package org.jenkinsci.plugins.workflow.steps.build;

import hudson.Extension;
import org.jenkinsci.plugins.workflow.steps.AbstractStepDescriptorImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractStepImpl;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author Vivek Pandey
 */
public class BuildTriggerStep extends AbstractStepImpl {

    public final String buildJobPath;

    @DataBoundConstructor
    public BuildTriggerStep(String value) {
        this.buildJobPath = value;
    }

    public String getValue() {
        return buildJobPath;
    }

    @Extension
    public static class DescriptorImpl extends AbstractStepDescriptorImpl {

        public DescriptorImpl() {
            super(BuildTriggerStepExecution.class);
        }

        @Override
        public String getFunctionName() {
            return "build";
        }

        @Override
        public String getDisplayName() {
            return "Build a Job";
        }
    }
}
