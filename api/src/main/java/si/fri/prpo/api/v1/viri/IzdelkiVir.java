package si.fri.prpo.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
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
import si.fri.prpo.zrna.IzdelkiZrno;
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
        ref = "Izdelki",
        description = "API metode za operacije nad izdelki"
)
@Path("izdelki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class IzdelkiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private IzdelkiZrno izdelkiZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;


    @GET
    @BeleziKlice
    @Operation(summary = "Vrni seznam izdelkov", description = "Metoda vrne vse izdelke")
    @APIResponses({
            @APIResponse(
                    description = "Seznam vrnjenih izdelkov",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(
                                    implementation = Izdelek.class
                            )
                    )
            )
    })
    public Response vrniIzdelke(){

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Izdelek> izdelki = izdelkiZrno.getAllIzdelki(query);
        long total_count = izdelkiZrno.getAllIzdelkiCount(query);

        return Response
                .status(Response.Status.OK)
                .header("X-Total-Count", total_count)
                .entity(izdelki)
                .build();
    }

    @GET
    @Path("{id}")
    @BeleziKlice
    @Operation(summary = "Vrni en izdelek", description = "Metoda vrne en izdelek")
    @APIResponses({
            @APIResponse(
                    description = "En vrnjen izdelek",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Izdelek.class)
                    )
            ),
            @APIResponse(
                    description = "Izdelek s tem IDjem ne obstaja",
                    responseCode = "404"
            )
    })
    public Response vrniIzdelek(
            @Parameter(
                    description = "ID izdelka",
                    required = true
            )
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
    @Operation(summary = "Dodaj izdelek", description = "Metoda doda izdelek")
    @APIResponses({
            @APIResponse(
                    description = "Izdelek dodan",
                    responseCode = "201"
            ),
            @APIResponse(
                    description = "Napaka pri dodajanju novega izdelka: Cena izdelka ne sme biti negativna.",
                    responseCode = "406"
            )
    })
    public Response dodajIzdelek(
            @RequestBody(
                    description = "Objekt Izdelek za dodajanje",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Izdelek.class)
                    )
            )
            Izdelek izdelek
    ){
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
    @Operation(summary = "Uredi en izdelek", description = "Metoda uredi en izdelek")
    @APIResponses({
            @APIResponse(
                    description = "Izdelek urejen",
                    responseCode = "201"
            ),
            @APIResponse(
                    description = "Izdelek s tem IDjem ne obstaja",
                    responseCode = "404"
            ),
            @APIResponse(
                    description = "Napaka pri spreminjanju izdelka: Cena izdelka ne sme biti negativna.",
                    responseCode = "406"
            )
    })
    public Response posodobiIzdelek(
            @Parameter(
                    description = "ID izdelka za urejanje",
                    required = true
            )
            @PathParam("id")
            Integer id,
            @RequestBody(
                    description = "Objekt Izdelek za urejanje",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Izdelek.class)
                    )
            )
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
    @Operation(summary = "Izbriši en izdelek", description = "Metoda izbriše en izdelek")
    @APIResponses({
            @APIResponse(
                    description = "Izdelek izbrisan",
                    responseCode = "204"
            ),
            @APIResponse(
                    description = "Izdelek s tem IDjem ne obstaja",
                    responseCode = "404"
            )
    })
    public Response odstraniIzdelek(
            @Parameter(
                    description = "ID izdelka za brisanje",
                    required = true
            )
            @PathParam("id")
            Integer id
    ){
        boolean izbrisan = izdelkiZrno.deleteIzdelek(id);
        return Response
                .status(Response.Status.OK)
                .entity(izbrisan)
                .build();
    }


    @POST
    @Path("razlika")
    @Operation(summary = "Vrni razliko cen dveh izdelkov", description = "Metoda vrne razliko cen dveh izdelkov")
    @APIResponses({
            @APIResponse(
                    description = "Razlika cen dveh izdelkov",
                    responseCode = "200"
            )
    })
    public Response vrniRazlikoCenIzdelkov(
            @RequestBody(
                    description = "Objekt IzdelkaCenaDto za računanje razlike cen dveh izdelkov",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = IzdelkaCenaDto.class)
                    )
            )
            IzdelkaCenaDto izdelkaCenaDto
    ) {
        IzdelkaCenaRazlikaDto izdelkaCenaRazlikaDto = upravljanjeIzdelkovZrno.vrniRazlikoCen(izdelkaCenaDto);

        return Response
                .status(Response.Status.OK)
                .entity(izdelkaCenaRazlikaDto)
                .build();
    }
}
