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
import si.fri.prpo.entitete.Trgovina;
import si.fri.prpo.zrna.TrgovineZrno;
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
        ref = "Trgovine",
        description = "API metode za operacije nad trgovinami"
)
@Path("trgovine")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class TrgovineVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private TrgovineZrno trgovineZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;


    @GET
    @Operation(summary = "Vrni seznam trgovin", description = "Metoda vrne vse trgovine")
    @APIResponses({
            @APIResponse(
                    description = "Seznam vrnjenih trgovin",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Trgovina.class)
                    )
            )
    })
    public Response vrniTrgovine(){

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Trgovina> trgovine = trgovineZrno.getAllTrgovine(query);
        long total_count = trgovineZrno.getAllTrgovineCount(query);

        return Response
                .status(Response.Status.OK)
                .header("X-Total-Count", total_count)
                .entity(trgovine)
                .build();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Vrni eno trgovino", description = "Metoda vrne eno trgovino")
    @APIResponses({
            @APIResponse(
                    description = "Ena vrnjena trgovina",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Trgovina.class)
                    )
            ),
            @APIResponse(
                    description = "Trgovina s tem IDjem ne obstaja",
                    responseCode = "404"
            )
    })
    public Response vrniTrgovino(
            @Parameter(
                    description = "ID trgovine",
                    required = true
            )
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
    @Operation(summary = "Dodaj trgovino", description = "Metoda doda trgovino")
    @APIResponses({
            @APIResponse(
                    description = "Trgovina dodana",
                    responseCode = "201"
            )
    })
    public Response dodajTrgovino(
            @RequestBody(
                    description = "Objekt Trgovina za dodajanje",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Trgovina.class)
                    )
            )
            Trgovina trgovina
    ){
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
    @Operation(summary = "Uredi eno trgovino", description = "Metoda uredi eno trgovino")
    @APIResponses({
            @APIResponse(
                    description = "Trgovina urejena",
                    responseCode = "201"
            ),
            @APIResponse(
                    description = "Trgovina s tem IDjem ne obstaja",
                    responseCode = "404"
            )
    })
    public Response posodobiTrgovino(
            @Parameter(
                    description = "ID trgovine za urejanje",
                    required = true
            )
            @PathParam("id")
            Integer id,
            @RequestBody(
                    description = "Objekt Trgovina za urejanje",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Trgovina.class)
                    )
            )
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
    @Operation(summary = "Izbriši eno trgovino", description = "Metoda izbriše eno trgovino")
    @APIResponses({
            @APIResponse(
                    description = "Trgovina izbrisana",
                    responseCode = "204"
            ),
            @APIResponse(
                    description = "Trgovina s tem IDjem ne obstaja",
                    responseCode = "404"
            )
    })
    public Response odstraniTrgovino(
            @Parameter(
                    description = "ID trgovine za brisanje",
                    required = true
            )
            @PathParam("id")
            Integer id
    ){
        boolean izbrisan = trgovineZrno.deleteTrgovina(id);
        return Response
                .status(Response.Status.OK)
                .entity(izbrisan)
                .build();
    }


    @POST
    @Path("povprecje")
    @Operation(summary = "Vrni povprečje cen izdelkov iz trgovine", description = "Metoda vrne povprečje cen izdelkov iz trgovine")
    @APIResponses({
            @APIResponse(
                    description = "Povprečje cen izdelkov iz trgovine",
                    responseCode = "200"
            )
    })
    public Response vrniPovprecjeCenIzdelkovTrgovin(
            @RequestBody(
                    description = "Objekt TrgovinaPovprecjeCenDto za računanje povprečja cen izdelkov iz trgovine",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = TrgovinaCeneDto.class)
                    )
            )
            TrgovinaCeneDto trgovinaCeneDto
    ) {
        TrgovinaPovprecjeCenDto trgovinaPovprecjeCenDto = upravljanjeIzdelkovZrno.povpracnaCenaIzdelkovVTrgovini(trgovinaCeneDto);
        //System.out.println(povpracjeCenTrgovine);

        return Response
                .status(Response.Status.OK)
                .entity(trgovinaPovprecjeCenDto)
                .build();
    }
}
