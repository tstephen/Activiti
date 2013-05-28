package org.activiti.jaxrs.resources;

import java.net.URI;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

import com.sun.jersey.api.view.Viewable;

@Path("/template")
public class ProcessTemplateResource {

    private ProcessEngine processEngine;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces({ MediaType.TEXT_HTML })
    public Viewable listProcessTemplatesAsHtml() {
        System.out.println("listProcessTemplatesAsHtml");
        // List<ProcessDefinition> list = getProcessEngine()
        // .getRepositoryService().createProcessDefinitionQuery().active()
        // .list();
        return new Viewable("/template/index.jspx");
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML })
    public Viewable listProcessTemplates() {
        System.out.println("listProcessTemplates");
        // List<ProcessDefinition> list = getProcessEngine()
        // .getRepositoryService().createProcessDefinitionQuery().active()
        // .list();
        return new Viewable("/template/index.jspx");
    }

    @GET
    @Path("/create")
    @Produces(MediaType.TEXT_HTML)
    public Viewable getPlanningForm() {
        return new Viewable("/template/create.jspx");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/deploy")
    public Viewable getDeployTemplateForm() {
        return new Viewable("/template/deploy.jspx");
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.TEXT_HTML })
    public Viewable getProcessTemplateAsHtml(@PathParam("id") String id) {
        System.out.println("getProcessTemplateAsHtml");
        // ProcessDefinition definition = getProcessEngine()
        // .getRepositoryService().createProcessDefinitionQuery()
        // .processDefinitionId(id).singleResult();
        return new Viewable("/template/show.jspx");
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML })
    public Viewable getProcessTemplate(@PathParam("id") String id) {
        System.out.println("getProcessTemplate");
        // ProcessDefinition definition = getProcessEngine()
        // .getRepositoryService().createProcessDefinitionQuery()
        // .processDefinitionId(id).singleResult();
        return new Viewable("/template/show.jspx");
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response deployBpmnResource(@FormParam("templateUrl") String url) {
        getLogger().info(String.format("Deploying %1$s", url));
        RepositoryService repoSvc = getProcessEngine().getRepositoryService();
        // String resource = url.toExternalForm().substring(
        // url.toExternalForm().indexOf(".jar!") + ".jar!".length()
        // + 1);
        String resource = url;
        try {
            Deployment deployment = repoSvc.createDeployment()
                    .addClasspathResource(resource).deploy();
            getLogger()
                    .info(String.format("... deployment ok: %1$s at %2$s",
                            deployment.getId(), deployment.getDeploymentTime()));

            ProcessDefinition template = getProcessEngine()
                    .getRepositoryService().createProcessDefinitionQuery()
                    .deploymentId(deployment.getId()).singleResult();
            URI createdUri = uriInfo.getBaseUriBuilder().path(getClass())
                    .path(getClass(), "getProcessTemplate")
                    .build(template.getId());
            // String createdContent = create(processInstance);
            return Response.created(createdUri).build();// entity(createdContent).build();
        } catch (Exception e) {
            getLogger().severe(
                    String.format("Exception during deployment: %1$s: %2$s", e
                            .getClass().getName(), e.getMessage()));
            return Response.serverError().build();
        }

    }
	private ProcessEngine getProcessEngine() {
        if (processEngine == null) {
            processEngine = ProcessEngines.getDefaultProcessEngine();
        }
        return processEngine;
    }

    private Logger getLogger() {
        return Logger.getLogger(getClass().getName());
    }

}
