package bridge.backend.domain.user.infra.naver;

import bridge.backend.domain.user.infra.OAuth2Response;

import java.util.Map;

public class NaverResponse implements OAuth2Response {
    private final Map<String, Object> response;
    public NaverResponse(Map<String, Object> attribute) {

        this.response = (Map<String, Object>) attribute.get("response");
    }
    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return response.get("id").toString();
    }
}
