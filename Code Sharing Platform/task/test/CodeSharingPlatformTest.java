import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.mocks.web.response.HttpResponse;
import org.hyperskill.hstest.stage.SpringTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import platform.CodeSharingPlatform;

import static org.hyperskill.hstest.testing.expect.Expectation.expect;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isObject;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isString;

public class CodeSharingPlatformTest extends SpringTest {
    public CodeSharingPlatformTest() {
        super(CodeSharingPlatform.class);
    }

    String API_CODE = "/api/code";
    String WEB_CODE = "/code";

    static void checkStatusCode(HttpResponse resp, int status) {
        if (resp.getStatusCode() != status) {
            throw new WrongAnswer(
                resp.getRequest().getMethod() + " " +
                    resp.getRequest().getLocalUri() +
                    " should respond with status code " + status + ", " +
                    "responded: " + resp.getStatusCode() + "\n\n" +
                    "Response body:\n\n" + resp.getContent()
            );
        }
    }

    @DynamicTestingMethod
    public DynamicTesting[] dt = new DynamicTesting[] {
        this::getApiCode,
        this::checkApiCode,
        this::checkWebCode,
    };

    String apiSnippet;

    private CheckResult getApiCode() {
        HttpResponse resp = get(API_CODE).send();
        checkStatusCode(resp, 200);

        expect(resp.getContent()).asJson().check(
            isObject()
                .value("code", isString(value -> {
                    apiSnippet = value;
                    return true;
                }))
        );

        return CheckResult.correct();
    }

    private CheckResult checkApiCode() {
        HttpResponse resp = get(API_CODE).send();
        checkStatusCode(resp, 200);

        expect(resp.getContent()).asJson().check(
            isObject()
                .value("code", isString(value -> apiSnippet.equals(value)))
        );

        return CheckResult.correct();
    }

    private CheckResult checkWebCode() {
        HttpResponse resp = get(WEB_CODE).send();
        checkStatusCode(resp, 200);

        String html = resp.getContent();
        Document doc = Jsoup.parse(html);

        if (!doc.title().equals("Code")) {
            return CheckResult.wrong("GET " + WEB_CODE +
                " should contain title \"Code\"");
        }

        Elements pre = doc.getElementsByTag("pre");

        if (pre.size() != 1) {
            return CheckResult.wrong("GET " + WEB_CODE +
                " should contain a single <pre> element, found: " + pre.size());
        }

        Element tag = pre.get(0);
        String webSnippet = tag.text();

        if (!webSnippet.trim().equals(apiSnippet.trim())) {
            return CheckResult.wrong("Web code snippet " +
                "and api code snippet are different");
        }

        return CheckResult.correct();
    }
}
