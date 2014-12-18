package ua.nure.doyun.slideassistant.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.doyun.slideassistant.db.DBManager;
import ua.nure.doyun.slideassistant.entities.SlidePackage;

public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CreateServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SlidePackage pack = new SlidePackage();
		pack.setId("");
		pack.setClear("0");
		pack.setConnectionsNumber("0");
		pack.setName(request.getParameter("name"));
		pack.setIsClear("0");
		pack.setQuestion("");
		pack.setSlideNumber(request.getParameter("slideNumber"));
		pack.setType("desktop");
		try {
			DBManager.getInstance().create(pack);
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("result", pack.toString());
		request.getRequestDispatcher("WEB-INF/jsp/result.jsp").forward(request, response);
	}

}
