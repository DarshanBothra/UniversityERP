package edu.univ.erp.api;

import edu.univ.erp.domain.*;
import edu.univ.erp.service.StudentService;
import edu.univ.erp.auth.session.SessionManager;
import edu.univ.erp.auth.session.SessionUser;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class StudentAPI {
    private final StudentService studentService = new StudentService();

    /**
     * Fetch profile of the logged in user
     */

    @GET
    @Path("/{studentId}/profile")

    public Response getProfile(@PathParam("studentId") int studentId){
        SessionUser user = SessionManager.getActiveSession();
        if ((user == null) || (user.getUserId() != studentId) || (user.getRole() != Role.STUDENT)){
            return Response.status(Response.Status.FORBIDDEN).entity("Access Denied: Invalid Session or Unauthorized Role").build();
        }
        Student profile = studentService.getProfile(studentId);
        if (profile == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found.").build();
        }

        return Response.ok(profile).build();
    }

    /**
     * Get the full course catalog (sections + instructions + courses)
     */
    @GET
    @Path("/catalog")
    public Response getCatalog(){
        List<SectionDetail> catalog = studentService.getCatalog();
        return Response.ok(catalog).build();
    }

    /**
     * Register the logged in user to the database
     */

    @POST
    @Path("/{studentId}/register/{sectionId}")
    public Response registerSection(@PathParam("studentId") int studentId, @PathParam("sectionId") int sectionId){
        SessionUser user = SessionManager.getActiveSession();

        if ((user == null) || (user.getUserId() != studentId) || (user.getRole() != Role.STUDENT)){
            return Response.status(Response.Status.FORBIDDEN).entity("Access Denied: Invalid session or Unauthorized Role.").build();
        }

        String result = studentService.registerSection(studentId, sectionId);
        return Response.ok(result).build();
    }

    @DELETE
    @Path("/{studentId}/drop/{sectionId}")
    public Response dropSection(@PathParam("studentId") int studentId, @PathParam("sectionId") int sectionId){
        SessionUser user = SessionManager.getActiveSession();

        if ((user == null) || (user.getUserId() != studentId) || (user.getRole() != Role.STUDENT)){
            return Response.status(Response.Status.FORBIDDEN).entity("Access Denied: Invalid session or unauthorized access.").build();
        }
        String result = studentService.dropSection(studentId, sectionId);
        return Response.ok(result).build();
    }

    @GET
    @Path("/{studentId}/registrations")
    public Response getRegistrations(@PathParam("studentId") int studentId){
        SessionUser user = SessionManager.getActiveSession();
        if ((user == null) || (user.getUserId() != studentId) || (user.getRole() != Role.STUDENT)){
            return Response.status(Response.Status.FORBIDDEN).entity("Access Denied: Invalid Session or unauthorized user.").build();
        }
        List<SectionDetail> registrations = studentService.getMyRegistrations(studentId);
        return Response.ok(registrations).build();
    }

    @GET
    @Path("/{studentId}/grades")
    public Response getGrades(@PathParam("studentId") int studentId){
        SessionUser user = SessionManager.getActiveSession();
        if ((user == null) || (user.getUserId() != studentId) || (user.getRole() != Role.STUDENT)){
            return Response.status(Response.Status.FORBIDDEN).entity("Access Denied: Invalid session or unauthorized access").build();
        }
        List<Grade> grades = studentService.getMyGrades(studentId);
        return Response.ok(grades).build();
    }

    @GET
    @Path("/{studentId}/transcript")
    public Response generateTranscript(@PathParam("studentId") int studentId, @QueryParam("path") String filePath){
        SessionUser user = SessionManager.getActiveSession();
        if ((user == null) || (user.getUserId() != studentId) || (user.getRole() != Role.STUDENT)){
            return Response.status(Response.Status.FORBIDDEN).entity("Access Denied: Invalid session or unauthorized access").build();
        }
        boolean success = studentService.generateTranscript(studentId, filePath);

        if (success){
            return Response.ok("Transcript generated successfully at: " + filePath).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to generate transcript.").build();
        }
    }
}


