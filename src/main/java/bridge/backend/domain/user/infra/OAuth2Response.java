package bridge.backend.domain.user.infra;

public interface OAuth2Response {
    String getProvider();
    String getProviderId();
}
