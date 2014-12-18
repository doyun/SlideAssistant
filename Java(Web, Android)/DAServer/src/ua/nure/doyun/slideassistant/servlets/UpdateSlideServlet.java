package ua.nure.doyun.slideassistant.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.doyun.slideassistant.db.DBManager;
import ua.nure.doyun.slideassistant.entities.SlidePackage;

public class UpdateSlideServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateSlideServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SlidePackage pack = new SlidePackage();
		pack.setId(request.getParameter("id"));
		pack.setSlideNumber(request.getParameter("slideNumber"));
		try {
			int clear = DBManager.getInstance().updateSlide(pack);
			pack.setClear(String.valueOf(clear));
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("result", pack.toString());
		request.getRequestDispatcher("WEB-INF/jsp/result.jsp").forward(request, response);
	}
}
