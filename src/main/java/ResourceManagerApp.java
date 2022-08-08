import api.resources.ScheduleResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

/**
 * author Miara Omer @ 08/08/2022
 */

public class ResourceManagerApp extends Application<ResourceManagerConfiguration> {

    public static void main(String[] args) throws Exception {
        new ResourceManagerApp().run(args);
    }

    @Override
    public void run(ResourceManagerConfiguration config, Environment env) {
        final ScheduleResource schedule = new ScheduleResource();
        env.jersey().register(schedule);

        env.healthChecks().register("template",
                new ResourceManagerHealthCheck(config.getVersion()));
    }
}
