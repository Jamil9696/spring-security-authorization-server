package com.integrationtest.client;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "classpath:specification" },
    glue = { "com.mtag.ambsrvrm" },
    snippets = CucumberOptions.SnippetType.CAMELCASE,
    plugin = { "pretty", "junit:target/cucumber-report.xml", "html:target/cucumber-report.html",
        "json:target/cucumber-report.json" }
)
public class CucumberIntegrationTest {
}
