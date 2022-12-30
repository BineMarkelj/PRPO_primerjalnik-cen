package si.fri.prpo.api.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
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
import si.fri.prpo.entitete.Izdelek;
import si.fri.prpo.entitete.Trgovina;
import si.fri.prpo.entitete.Uporabnik;
import si.fri.prpo.zrna.UporabnikiZrno;
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
        ref = "Uporabniki",
        description = "API metode za operacije nad uporabniki"
)
@ApplicationScoped
@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
public class UporabnikiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;


    @GET
    @Operation(summary = "Vrni seznam uporabnikov", description = "Metoda vrne vse uporabnike")
    @APIResponses({
            @APIResponse(
                    description = "Seznam vrnjenih uporabnikov",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Uporabnik.class)
                    )
            )
    })
    public Response vrniUporabnike(){

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Uporabnik> uporabniki = uporabnikiZrno.getAllUporabniki(query);
        long total_count = uporabnikiZrno.getAllUporabnikiCount(query);

        return Response
                .status(Response.Status.OK)
                .header("X-Total-Count", total_count)
                .entity(uporabniki)
                .build();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Vrni enega uporabnika", description = "Metoda vrne enega uporabnika")
    @APIResponses({
            @APIResponse(
                    description = "En vrnjen uporabnik",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Uporabnik.class)
                    )
            ),
            @APIResponse(
                    description = "Uporabnik s tem IDjem ne obstaja",
                    responseCode = "404"
            )
    })
    public Response vrniUporabnika(
            @Parameter(
                    description = "ID uporabnika",
                    required = true
            )
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
    @Operation(summary = "Dodaj uporabnika", description = "Metoda doda uporabnika")
    @APIResponses({
            @APIResponse(
                    description = "Uporabnik dodan",
                    responseCode = "201"
            )
    })
    public Response dodajUporabnika(
            @RequestBody(
                    description = "Objekt Uporabnik za dodajanje",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Uporabnik.class)
                    )
            )
            Uporabnik uporabnik
    ){
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
    @Operation(summary = "Uredi enega uporabnika", description = "Metoda uredi enega uporabnika")
    @APIResponses({
            @APIResponse(
                    description = "Uporabnik urejen",
                    responseCode = "201"
            ),
            @APIResponse(
                    description = "Uporabnik s tem IDjem ne obstaja",
                    responseCode = "404"
            )
    })
    public Response posodobiUporabnika(
            @Parameter(
                    description = "ID uporabnika za urejanje",
                    required = true
            )
            @PathParam("id")
            Integer id,
            @RequestBody(
                    description = "Objekt Uporabnik za urejanje",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Uporabnik.class)
                    )
            )
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
    @Operation(summary = "Izbriši enega uporabnika", description = "Metoda izbriše enega uporabnika")
    @APIResponses({
            @APIResponse(
                    description = "Uporabnik izbrisan",
                    responseCode = "204"
            ),
            @APIResponse(
                    description = "Uporabnik s tem IDjem ne obstaja",
                    responseCode = "404"
            )
    })
    public Response odstraniUporabnika(
            @Parameter(
                    description = "ID uporabnika za brisanje",
                    required = true
            )
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
