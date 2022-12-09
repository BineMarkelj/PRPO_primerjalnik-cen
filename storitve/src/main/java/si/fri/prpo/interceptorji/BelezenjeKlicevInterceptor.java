package si.fri.prpo.interceptorji;

import org.jboss.logging.Logger;
import si.fri.prpo.anotacije.BeleziKlice;
import si.fri.prpo.zrna.BelezenjeKlicevZrno;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Priority(1)
@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {

    @Inject
    private BelezenjeKlicevZrno belezenjeKlicevZrno;

    private Logger log = Logger.getLogger(BelezenjeKlicevInterceptor.class.getName());

    @AroundInvoke
    public Object prestejKlice(InvocationContext context) throws Exception {
        System.out.println("Interceptor beleženje klicev:");
        //System.out.println(context.getContextData());
        //System.out.println(context.getParameters());
        //System.out.println(context.getMethod().getName());

        String metoda = context.getMethod().getName();
        System.out.println(metoda);

        if (metoda == "vrniIzdelke") {
            belezenjeKlicevZrno.pristej_klic_vrniIzdelke(metoda);
        }

        if (metoda == "vrniIzdelek") {
            belezenjeKlicevZrno.pristej_klic_vrniIzdelek(metoda);
        }

        if (metoda == "dodajIzdelek") {
            belezenjeKlicevZrno.pristej_klic_dodajIzdelek(metoda);
        }

        if (metoda == "posodobiIzdelek") {
            belezenjeKlicevZrno.pristej_klic_urediIzdelek(metoda);
        }

        if (metoda == "odstraniIzdelek") {
            belezenjeKlicevZrno.pristej_klic_izbrisiIzdelek(metoda);
        }

        return context.proceed();
    }
}