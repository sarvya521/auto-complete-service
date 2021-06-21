package com.backend.boilerplate.config;

import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.execution.RuntimeInformation;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
    <properties>
        <api.version>3.0.0-M3</api.version>
        <maven.version>2.0.9</maven.version>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.apache.maven.enforcer</groupId>
            <artifactId>enforcer-api</artifactId>
            <version>${api.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.maven</groupId>
            <artifactId>maven-project</artifactId>
            <version>${maven.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.maven</groupId>
            <artifactId>maven-core</artifactId>
            <version>${maven.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.maven</groupId>
            <artifactId>maven-artifact</artifactId>
            <version>${maven.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.maven</groupId>
            <artifactId>maven-plugin-api</artifactId>
            <version>${maven.version}</version>
        </dependency>
        <dependency>
            <groupId>org.codehaus.plexus</groupId>
            <artifactId>plexus-container-default</artifactId>
            <version>1.0-alpha-9</version>
        </dependency>
    </dependencies>
 */

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
public class LocalRunsSonarAnalysis implements EnforcerRule {

    private boolean shouldIfail = false;

    public void execute(EnforcerRuleHelper helper)
        throws EnforcerRuleException {
        Log log = helper.getLog();

        try {
            // get the various expressions out of the helper.
            MavenProject project = (MavenProject) helper.evaluate("${project}");
            MavenSession session = (MavenSession) helper.evaluate("${session}");
            String target = (String) helper.evaluate("${project.build.directory}");
            String artifactId = (String) helper.evaluate("${project.artifactId}");

            // retrieve any component out of the session directly
            ArtifactResolver resolver = (ArtifactResolver) helper.getComponent(ArtifactResolver.class);
            RuntimeInformation rti = (RuntimeInformation) helper.getComponent(RuntimeInformation.class);

            log.info("Retrieved Target Folder: " + target);
            log.info("Retrieved ArtifactId: " + artifactId);
            log.info("Retrieved Project: " + project);
            log.info("Retrieved RuntimeInfo: " + rti);
            log.info("Retrieved Session: " + session);
            log.info("Retrieved Resolver: " + resolver);

            if (this.shouldIfail) {
                if (this.validateSonarMetrics(artifactId)) {
                    throw new EnforcerRuleException("Build Failed");
                }
            }
        } catch (ComponentLookupException e) {
            throw new EnforcerRuleException("Unable to lookup a component " + e.getLocalizedMessage(), e);
        } catch (ExpressionEvaluationException e) {
            throw new EnforcerRuleException("Unable to lookup an expression " + e.getLocalizedMessage(), e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * If your rule is cacheable, you must return a unique id when parameters or conditions
     * change that would cause the result to be different. Multiple cached results are stored
     * based on their id.
     * <p>
     * The easiest way to do this is to return a hash computed from the values of your parameters.
     * <p>
     * If your rule is not cacheable, then the result here is not important, you may return anything.
     */
    public String getCacheId() {
        //no hash on boolean...only parameter so no hash is needed.
        return "" + this.shouldIfail;
    }

    /**
     * This tells the system if the results are cacheable at all. Keep in mind that during
     * forked builds and other things, a given rule may be executed more than once for the same
     * project. This means that even things that change from project to project may still
     * be cacheable in certain instances.
     */
    public boolean isCacheable() {
        return false;
    }

    /**
     * If the rule is cacheable and the same id is found in the cache, the stored results
     * are passed to this method to allow double checking of the results. Most of the time
     * this can be done by generating unique ids, but sometimes the results of objects returned
     * by the helper need to be queried. You may for example, store certain objects in your rule
     * and then query them later.
     */
    public boolean isResultValid(EnforcerRule arg0) {
        return false;
    }

    public boolean validateSonarMetrics(String artifactId) throws InterruptedException, IOException {
        String gitBranch = getCurrentGitBranch();
        System.out.println("git branch:" + gitBranch);
        String[] command = {"curl", "-u", "exxxxx" + ":", "https://sonarcloud" +
            ".io/api/qualitygates/project_status?projectKey="+ artifactId + "&branch=+" + gitBranch};
        Thread.sleep(2000);
        ProcessBuilder process = new ProcessBuilder(command);
        Process p;
        try {
            p = process.start();
            Thread.sleep(2000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            String result = builder.toString();
            System.out.print(result);
            if (result.contains("NONE")) {
                Thread.sleep(2000);
            }

            if (result.contains("ERROR")) {
                return true;
            }

        } catch (IOException e) {
            System.out.print("error");
            e.printStackTrace();
        }
        return false;
    }

    public static String getCurrentGitBranch() throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec("git rev-parse --abbrev-ref HEAD");
        process.waitFor();
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(process.getInputStream()));
        return reader.readLine();
    }
}
