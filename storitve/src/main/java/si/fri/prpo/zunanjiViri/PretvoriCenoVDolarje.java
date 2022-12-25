package si.fri.prpo.zunanjiViri;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;
import si.fri.prpo.zrna.UpravljanjeIzdelkovZrno;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class PretvoriCenoVDolarje {

    private Logger log = Logger.getLogger(PretvoriCenoVDolarje.class.getName());

    //metoda PostConstruct, ki se izvede takoj po inicializaciji zrna
    @PostConstruct
    private void init() {
        log.info("Zrno PretvoriCenoVDolarje se je inicializiralo.");
    }

    //metoda PreDestroy, ki se izvede tik pred uničenjem zrna
    @PreDestroy
    private void destroy() {
        log.info("Zrno PretvoriCenoVDolarje se bo uničilo.");
    }

    public float pretvoriCenoVDolarje(float cena_v_evrih) {
        String apiUrl = "https://api.currencybeacon.com/v1/latest";
        String apiKey = "b64b32be64a873d566c4b1089e0f3c1e";

        float EUR_to_dollar_rate = -1;
        float cena_v_dolarjih = -1;

        try {
            Client httpClient = ClientBuilder.newClient();
            WebTarget target = httpClient.target(apiUrl);
            target = target.queryParam("api_key", apiKey);
            //System.out.println(target);
            Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);
            Response response = builder.get();
            String jsonResponse = response.readEntity(String.class);

            //System.out.println("API RESPONSE: " + response);

            //parse json
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode responseNode = root.get("response");
            JsonNode rates = responseNode.get("rates");
            double EUR_rate = rates.get("EUR").asDouble();

            EUR_to_dollar_rate = 1 / (float)EUR_rate;
            cena_v_dolarjih = EUR_to_dollar_rate * cena_v_evrih;

        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerErrorException(e);
        }

        cena_v_dolarjih = (float)Math.round(cena_v_dolarjih * 100) / 100;
        return cena_v_dolarjih;
    }
}
