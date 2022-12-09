package si.fri.prpo.api.v1.viri;

import si.fri.prpo.dtos.*;
import si.fri.prpo.entitete.Trgovina;
import si.fri.prpo.zrna.TrgovineZrno;
import si.fri.prpo.zrna.UpravljanjeIzdelkovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("trgovine")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class TrgovineVir {

    @Inject
    private TrgovineZrno trgovineZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;


    @GET
    public Response vrniTrgovine(){

        List<Trgovina> trgovine = trgovineZrno.getAllTrgovine();

        return Response
                .status(Response.Status.OK)
                .entity(trgovine)
                .build();
    }

    @GET
    @Path("{id}")
    public Response vrniTrgovino(
            @PathParam("id")
            Integer id
    ){

        Trgovina trgovina = trgovineZrno.getTrgovina(id);
        if (trgovina != null) {
            return Response
                    .status(Response.Status.OK)
                    .entity(trgovina)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(trgovina)
                    .build();
        }
    }

    @POST
    public Response dodajTrgovino(Trgovina trgovina){
        if (trgovina != null) {
            trgovineZrno.createTrgovina(trgovina);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(trgovina)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(trgovina)
                    .build();
        }
    }

    @PUT
    @Path("{id}")
    public Response posodobiTrgovino(
            @PathParam("id")
            Integer id,
            Trgovina trgovina
    ){
        if (trgovina != null) {
            trgovineZrno.editTrgovina(id,trgovina);
            return Response
                    .status(Response.Status.OK)
                    .entity(trgovina)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(trgovina)
                    .build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response odstraniTrgovino(
            @PathParam("id")
            Integer id
    ){
        boolean izbrisan = trgovineZrno.deleteTrgovina(id);
        return Response
                .status(Response.Status.OK)
                .entity(izbrisan)
                .build();
    }


    @GET
    @Path("povprecje")
    public Response vrniPovprecjeCenIzdelkovTrgovin(TrgovinaCeneDto trgovinaCeneDto) {
        TrgovinaPovprecjeCenDto trgovinaPovprecjeCenDto = upravljanjeIzdelkovZrno.povpracnaCenaIzdelkovVTrgovini(trgovinaCeneDto);
        //System.out.println(povpracjeCenTrgovine);

        return Response
                .status(Response.Status.OK)
                .entity(trgovinaPovprecjeCenDto)
                .build();
    }
}
