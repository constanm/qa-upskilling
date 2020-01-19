package objects.tests.junit.rules;

import objects.webtestsbase.WebDriverFactory;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * This class represents a rule - junit mechanism for adding awesome functionality in test running process.
 * This rule adds screenshot taking when test fails
 */
public class ScreenShotOnFailRule implements TestRule {

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } catch (Throwable t) {
                    WebDriverFactory.takeScreenShot(description.getMethodName());
                    throw t;
                }
            }
        };
    }
}
