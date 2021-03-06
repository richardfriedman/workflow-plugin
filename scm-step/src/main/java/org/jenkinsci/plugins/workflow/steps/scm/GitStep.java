/*
 * The MIT License
 *
 * Copyright 2014 Jesse Glick.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jenkinsci.plugins.workflow.steps.scm;

import hudson.Extension;
import hudson.plugins.git.BranchSpec;
import hudson.plugins.git.GitSCM;
import hudson.plugins.git.SubmoduleConfig;
import hudson.plugins.git.UserRemoteConfig;
import hudson.scm.SCM;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.jenkinsci.plugins.workflow.steps.Step;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Runs Git using {@link GitSCM}.
 */
public final class GitStep extends SCMStep {

    private final String url;
    private final String branch;

    @DataBoundConstructor public GitStep(String url, String branch, boolean poll, boolean changelog) {
        super(poll, changelog);
        this.url = url;
        this.branch = branch;
    }

    public String getUrl() {
        return url;
    }
    
    public String getBranch() {
        return branch;
    }

    @Override protected SCM createSCM() {
        return new GitSCM(createRepoList(url), Collections.singletonList(new BranchSpec("*/" + branch)), false, Collections.<SubmoduleConfig>emptyList(), null, null, null);
    }

    // copied from GitSCM
    static private List<UserRemoteConfig> createRepoList(String url) {
        List<UserRemoteConfig> repoList = new ArrayList<UserRemoteConfig>();
        repoList.add(new UserRemoteConfig(url, null, null, null));
        return repoList;
    }

    @Extension public static final class DescriptorImpl extends SCMStepDescriptor {

        @Override public String getFunctionName() {
            return "git";
        }

        @Override public Step newInstance(Map<String,Object> arguments) {
            String branch = (String) arguments.get("branch");
            if (branch == null) {
                branch = "master";
            }
            return new GitStep((String) arguments.get("url"), branch, !Boolean.FALSE.equals(arguments.get("poll")), !Boolean.FALSE.equals(arguments.get("changelog")));
        }

        @Override public String getDisplayName() {
            return "Git";
        }

    }

}
