package si.fri.prpo.servleti;

import si.fri.prpo.entitete.Izdelek;
import si.fri.prpo.zrna.IzdelkiZrno;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private IzdelkiZrno izdelkiZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();

        List<Izdelek> izdelki = izdelkiZrno.getAllIzdelki();
        pw.append("Izpis vseh izdelkov v bazi:\n");

        for (int i = 0; i < izdelki.size(); i++) {
            Izdelek izdelek = izdelki.get(i);

            Integer id = izdelek.getId();
            String ime = izdelek.getIme();
            String opis = izdelek.getOpis();
            float cena = izdelek.getCena();

            pw.append("ID: " + id);
            pw.append(", Ime: " + ime);
            pw.append(", Opis: " + opis);
            pw.append(", Cena: " + cena);
            pw.append("\n");
        }

        pw.append("\n");
        pw.append("\n");
        pw.append("\n");
        pw.append("\n");

        //Izpis s CriteriaAPI
        List<Izdelek> izdelkiCriteriaAPI = izdelkiZrno.getAllIzdelkiCriteriaAPI();
        pw.append("Izpis vseh izdelkov v bazi z uporabo CriteriaAPI:\n");

        for (int i = 0; i < izdelkiCriteriaAPI.size(); i++) {
            Izdelek izdelekCriteriaAPI = izdelkiCriteriaAPI.get(i);

            Integer id = izdelekCriteriaAPI.getId();
            String ime = izdelekCriteriaAPI.getIme();
            String opis = izdelekCriteriaAPI.getOpis();
            float cena = izdelekCriteriaAPI.getCena();

            pw.append("ID: " + id);
            pw.append(", Ime: " + ime);
            pw.append(", Opis: " + opis);
            pw.append(", Cena: " + cena);
            pw.append("\n");
        }
    }
}