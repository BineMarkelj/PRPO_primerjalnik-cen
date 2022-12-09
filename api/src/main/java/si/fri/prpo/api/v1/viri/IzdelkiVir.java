package si.fri.prpo.api.v1.viri;

import si.fri.prpo.anotacije.BeleziKlice;
import si.fri.prpo.dtos.*;
import si.fri.prpo.entitete.Izdelek;
import si.fri.prpo.zrna.IzdelkiZrno;
import si.fri.prpo.zrna.UpravljanjeIzdelkovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("izdelki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class IzdelkiVir {

    @Inject
    private IzdelkiZrno izdelkiZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;


    @GET
    @BeleziKlice
    public Response vrniIzdelke(){

        List<Izdelek> izdelki = izdelkiZrno.getAllIzdelki();

        return Response
                .status(Response.Status.OK)
                .entity(izdelki)
                .build();
    }

    @GET
    @Path("{id}")
    @BeleziKlice
    public Response vrniIzdelek(
            @PathParam("id")
            Integer id
    ){

        Izdelek izdelek = izdelkiZrno.getIzdelek(id);
        if (izdelek != null) {
            return Response
                    .status(Response.Status.OK)
                    .entity(izdelek)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(izdelek)
                    .build();
        }
    }

    @POST
    @BeleziKlice
    public Response dodajIzdelek(Izdelek izdelek){
        if (izdelek != null) {
            izdelkiZrno.createIzdelek(izdelek);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(izdelek)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(izdelek)
                    .build();
        }
    }

    @PUT
    @Path("{id}")
    @BeleziKlice
    public Response posodobiIzdelek(
            @PathParam("id")
            Integer id,
            Izdelek izdelek
    ){
        if (izdelek != null) {
            izdelkiZrno.editIzdelek(id,izdelek);
            return Response
                    .status(Response.Status.OK)
                    .entity(izdelek)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(izdelek)
                    .build();
        }
    }

    @DELETE
    @Path("{id}")
    @BeleziKlice
    public Response odstraniIzdelek(
            @PathParam("id")
            Integer id
    ){
        boolean izbrisan = izdelkiZrno.deleteIzdelek(id);
        return Response
                .status(Response.Status.OK)
                .entity(izbrisan)
                .build();
    }


    @GET
    @Path("razlika")
    public Response vrniRazlikoCenIzdelkov(IzdelkaCenaDto izdelkaCenaDto) {
        IzdelkaCenaRazlikaDto izdelkaCenaRazlikaDto = upravljanjeIzdelkovZrno.vrniRazlikoCen(izdelkaCenaDto);

        return Response
                .status(Response.Status.OK)
                .entity(izdelkaCenaRazlikaDto)
                .build();
    }
}
