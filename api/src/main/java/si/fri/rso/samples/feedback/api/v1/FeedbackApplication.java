package si.fri.rso.samples.feedback.api.v1;

import com.kumuluz.ee.discovery.annotations.RegisterService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@RegisterService
@ApplicationPath("/v1")
public class FeedbackApplication extends Application {
}