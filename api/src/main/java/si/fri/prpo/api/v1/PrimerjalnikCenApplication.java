package si.fri.prpo.api.v1;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("v1")
@OpenAPIDefinition(info = @Info(
        title = "PrimerjalnikCenAPI",
        description = "API za primejalnik cen izdelkov",
        version = "v1",
        contact = @Contact(name = "si.fri.prpo"),
        license = @License(name="si.fri.prpo")),
        servers = @Server(url = "http://52.226.243.238:8080"),
        security = @SecurityRequirement(name = "openid-connect")
)
public class PrimerjalnikCenApplication extends javax.ws.rs.core.Application {
}
