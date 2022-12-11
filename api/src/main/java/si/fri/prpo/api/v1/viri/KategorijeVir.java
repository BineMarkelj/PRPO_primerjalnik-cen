package si.fri.prpo.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import si.fri.prpo.anotacije.BeleziKlice;
import si.fri.prpo.dtos.*;
import si.fri.prpo.entitete.Izdelek;
import si.fri.prpo.entitete.Kategorija;
import si.fri.prpo.zrna.KategorijeZrno;
import si.fri.prpo.zrna.UpravljanjeIzdelkovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Tag(
        ref = "Kategorije",
        description = "API metode za operacije nad kategorijami"
)
@Path("kategorije")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class KategorijeVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private KategorijeZrno kategorijeZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;


    @GET
    @Operation(summary = "Vrni seznam kategorij", description = "Metoda vrne vse kategorije")
    @APIResponses({
            @APIResponse(
                    description = "Seznam vrnjenih kategorij",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Kategorija.class)
                    )
            )
    })
    public Response vrniKategorije(){

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Kategorija> kategorije = kategorijeZrno.getAllKategorije(query);
        long total_count = kategorijeZrno.getAllKategorijeCount(query);

        return Response
                .status(Response.Status.OK)
                .header("X-Total-Count", total_count)
                .entity(kategorije)
                .build();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Vrni eno kategorijo", description = "Metoda vrne eno kategorijo")
    @APIResponses({
            @APIResponse(
                    description = "Ena vrnjena kategorija",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Kategorija.class)
                    )
            ),
            @APIResponse(
                    description = "Kategorija s tem IDjem ne obstaja",
                    responseCode = "404"
            )
    })
    public Response vrniKategorijo(
            @Parameter(
                    description = "ID kategorije",
                    required = true
            )
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
    @Operation(summary = "Dodaj kategorijo", description = "Metoda doda kategorijo")
    @APIResponses({
            @APIResponse(
                    description = "Kategorija dodana",
                    responseCode = "201"
            )
    })
    public Response dodajKategorijo(
            @RequestBody(
                    description = "Objekt Kategorija za dodajanje",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Kategorija.class)
                    )
            )
            Kategorija kategorija
    ){
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
    @Operation(summary = "Uredi eno kategorijo", description = "Metoda uredi eno kategorijo")
    @APIResponses({
            @APIResponse(
                    description = "Kategorija urejena",
                    responseCode = "201"
            ),
            @APIResponse(
                    description = "Kategorija s tem IDjem ne obstaja",
                    responseCode = "404"
            )
    })
    public Response posodobiKategorijo(
            @Parameter(
                    description = "ID kategorije za urejanje",
                    required = true
            )
            @PathParam("id")
            Integer id,
            @RequestBody(
                    description = "Objekt Kategorija za urejanje",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Kategorija.class)
                    )
            )
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
    @Operation(summary = "Izbriši eno kategorijo", description = "Metoda izbriše eno kategorijo")
    @APIResponses({
            @APIResponse(
                    description = "Kategorija izbrisana",
                    responseCode = "204"
            ),
            @APIResponse(
                    description = "Kategorija s tem IDjem ne obstaja",
                    responseCode = "404"
            )
    })
    public Response odstraniKategorijo(
            @Parameter(
                    description = "ID kategorije za brisanje",
                    required = true
            )
            @PathParam("id")
            Integer id
    ){
        boolean izbrisan = kategorijeZrno.deleteKategorija(id);
        return Response
                .status(Response.Status.OK)
                .entity(izbrisan)
                .build();
    }


    @POST
    @Path("povprecje")
    @Operation(summary = "Vrni povprečje cen izdelkov iz kategorije", description = "Metoda vrne povprečje cen izdelkov iz kategorije")
    @APIResponses({
            @APIResponse(
                    description = "Povprečje cen izdelkov iz kategorije",
                    responseCode = "200"
            )
    })
    public Response vrniPovprecjeCenIzdelkovKategorije(
            @RequestBody(
                    description = "Objekt KategorijaPovprecjeCenDto za računanje povprečja cen izdelkov iz kategorije",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = KategorijaCenaDto.class)
                    )
            )
            KategorijaCenaDto kategorijaCenaDto
    ) {
        KategorijaPovprecjeCenDto kategorijaPovprecjeCenDto = upravljanjeIzdelkovZrno.povpracnaCenaIzdelkovSKategorijo(kategorijaCenaDto);
        //System.out.println(povprecjeCenKategorije);

        return Response
                .status(Response.Status.OK)
                .entity(kategorijaPovprecjeCenDto)
                .build();
    }
}
