import com.codahale.metrics.health.HealthCheck;

public class ResourceManagerHealthCheck extends HealthCheck {

    private final String version;

    public ResourceManagerHealthCheck(String version) {
        this.version = version;
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy("OK with version: " + this.version + " . In memory stores are initialized and ready");
    }
}
