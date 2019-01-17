package si.fri.rso.samples.feedback.api.v1.resources;

import si.fri.rso.samples.feedback.entities.Feedback;
import si.fri.rso.samples.feedback.services.FeedbackBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("/feedback")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeedbackResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private FeedbackBean feedbackBean;

    @GET
    public Response getFeedback() {

        List<Feedback> feedbacks = feedbackBean.getFeedback(uriInfo);

        return Response.ok(feedbacks).build();
    }

    @GET
    @Path("/{feedbackId}")
    public Response getFeedback(@PathParam("feedbackId") Integer feedbackId) {

        Feedback feedback = feedbackBean.getFeedback(feedbackId);

        if (feedback == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(feedback).build();
    }

    @POST
    public Response createFeedback(Feedback feedback) {

       if ((feedback.getCustomerId() == null || feedback.getCustomerId().isEmpty())||(feedback.getComment()==null || feedback.getComment().isEmpty())
               || (feedback.getOrderId()== null || feedback.getOrderId().isEmpty())|| (feedback.getSatisfactionGrade()== null || feedback.getSatisfactionGrade().isEmpty())){
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            feedback = feedbackBean.createFeedback(feedback);
        }

        if (feedback.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(feedback).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(feedback).build();
        }
    }

    @PUT
    @Path("{feedbackId}")
    public Response putFeedback(@PathParam("feedbackId") Integer feedbackId, Feedback feedback) {

        feedback = feedbackBean.putFeedback(feedbackId, feedback);

        if (feedback == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (feedback.getId() != null)
                return Response.status(Response.Status.OK).entity(feedback).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("{feedbackId}")
    public Response deleteFeedback(@PathParam("feedbackId") String feedbackId) {

        boolean deleted = feedbackBean.deleteFeedback(feedbackId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
