package si.fri.prpo.api.v1.viri;

import si.fri.prpo.entitete.Uporabnik;
import si.fri.prpo.zrna.UporabnikiZrno;
import si.fri.prpo.zrna.UpravljanjeIzdelkovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UporabnikiVir {

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;


    @GET
    public Response vrniUporabnike(){

        List<Uporabnik> uporabniki = uporabnikiZrno.getAllUporabniki();

        return Response
                .status(Response.Status.OK)
                .entity(uporabniki)
                .build();
    }

    @GET
    @Path("{id}")
    public Response vrniUporabnika(
            @PathParam("id")
            Integer id
    ){

        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(id);
        if (uporabnik != null) {
            return Response
                    .status(Response.Status.OK)
                    .entity(uporabnik)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(uporabnik)
                    .build();
        }
    }

    @POST
    public Response dodajUporabnika(Uporabnik uporabnik){
        if (uporabnik != null) {
            uporabnikiZrno.createUporabnik(uporabnik);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(uporabnik)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(uporabnik)
                    .build();
        }
    }

    @PUT
    @Path("{id}")
    public Response posodobiUporabnika(
            @PathParam("id")
            Integer id,
            Uporabnik uporabnik
    ){
        if (uporabnik != null) {
            uporabnikiZrno.editUporabnik(id,uporabnik);
            return Response
                    .status(Response.Status.OK)
                    .entity(uporabnik)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(uporabnik)
                    .build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response odstraniUporabnika(
            @PathParam("id")
            Integer id
    ){
        boolean izbrisan = uporabnikiZrno.deleteUporabnik(id);
        return Response
                .status(Response.Status.OK)
                .entity(izbrisan)
                .build();
    }
}
