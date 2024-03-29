package Controlador;

import Utilitis.ModeloDatos;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

public class ServletConfirmarReserva extends HttpServlet {

    private ModeloDatos bd;

    public void init(ServletConfig cfg) throws ServletException {
        bd = new ModeloDatos();
        bd.abrirConexion();
    }

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(false);

        String asientosSeleccionados = req.getParameter("asientosSeleccionados");

        ArrayList<String> listaAsientos = new ArrayList<String>(Arrays.asList(asientosSeleccionados.split("\\s")));
        ArrayList<String> filasReservadas = new ArrayList<String>();
        ArrayList<String> columnasReservadas = new ArrayList<String>();
        for (int i = 0; i < listaAsientos.size(); i++) {
            listaAsientos.set(i, listaAsientos.get(i).replaceAll("asiento_", ""));
            filasReservadas.add(Arrays.asList(listaAsientos.get(i).split("_")).get(0));
            columnasReservadas.add(Arrays.asList(listaAsientos.get(i).split("_")).get(1));
        }
        s.setAttribute("listaAsientosReservados", listaAsientos);
        s.setAttribute("filasReservadas", filasReservadas);
        s.setAttribute("columnasReservadas", columnasReservadas);

        res.sendRedirect(res.encodeRedirectURL("PasarelaPago.jsp"));

    }

    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
