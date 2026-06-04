package webAutomation.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final int MAX_RETRY_COUNT = 1;
    private static final ThreadLocal<Integer> retryCount = ThreadLocal.withInitial(() -> 0);

    public static boolean isRetrying() {
        return retryCount.get() > 0;
    }

    @Override
    public boolean retry(ITestResult result) {
        int count = retryCount.get();
        if (count < MAX_RETRY_COUNT) {
            retryCount.set(count + 1);
            System.out.println("Retrying test: " + result.getName() +
                    " (attempt " + retryCount.get() + " of " + MAX_RETRY_COUNT + ")");
            return true;
        }
        retryCount.set(0);
        return false;
    }

}
