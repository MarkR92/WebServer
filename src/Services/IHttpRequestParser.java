package Services;

import Models.HttpRequestModel;

public interface IHttpRequestParser {
    HttpRequestModel ParseHttpRequest(String input);
}
