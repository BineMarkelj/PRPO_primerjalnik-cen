package si.fri.prpo.api.v1.viri;

import si.fri.prpo.dtos.*;
import si.fri.prpo.entitete.Kategorija;
import si.fri.prpo.zrna.KategorijeZrno;
import si.fri.prpo.zrna.UpravljanjeIzdelkovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("kategorije")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class KategorijeVir {

    @Inject
    private KategorijeZrno kategorijeZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;


    @GET
    public Response vrniKategorije(){

        List<Kategorija> kategorije = kategorijeZrno.getAllKategorije();

        return Response
                .status(Response.Status.OK)
                .entity(kategorije)
                .build();
    }

    @GET
    @Path("{id}")
    public Response vrniKategorijo(
            @PathParam("id")
            Integer id
    ){

        Kategorija kategorija = kategorijeZrno.getKategorija(id);
        if (kategorija != null) {
            return Response
                    .status(Response.Status.OK)
                    .entity(kategorija)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(kategorija)
                    .build();
        }
    }

    @POST
    public Response dodajKategorijo(Kategorija kategorija){
        if (kategorija != null) {
            kategorijeZrno.createKategorija(kategorija);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(kategorija)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(kategorija)
                    .build();
        }
    }

    @PUT
    @Path("{id}")
    public Response posodobiKategorijo(
            @PathParam("id")
            Integer id,
            Kategorija kategorija
    ){
        if (kategorija != null) {
            kategorijeZrno.editKategorija(id,kategorija);
            return Response
                    .status(Response.Status.OK)
                    .entity(kategorija)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(kategorija)
                    .build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response odstraniKategorijo(
            @PathParam("id")
            Integer id
    ){
        boolean izbrisan = kategorijeZrno.deleteKategorija(id);
        return Response
                .status(Response.Status.OK)
                .entity(izbrisan)
                .build();
    }


    @GET
    @Path("povprecje")
    public Response vrniPovprecjeCenIzdelkovKategorije(KategorijaCenaDto kategorijaCenaDto) {
        KategorijaPovprecjeCenDto kategorijaPovprecjeCenDto = upravljanjeIzdelkovZrno.povpracnaCenaIzdelkovSKategorijo(kategorijaCenaDto);
        //System.out.println(povprecjeCenKategorije);

        return Response
                .status(Response.Status.OK)
                .entity(kategorijaPovprecjeCenDto)
                .build();
    }
}
