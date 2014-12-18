package ua.nure.doyun.slideassistant.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import ua.nure.doyun.slideassistant.entities.SlidePackage;

public class DBManager {

	private static final String CREATE_PRESENTATION = "INSERT INTO presentations (name, title) VALUES (?, ?)";
	private static final String CREATE_SLIDE = "INSERT INTO slides (number, clear, id_presentation) VALUES (?, ?, ?)";
	private static final String FIND_SLIDE = "SELECT * FROM slides WHERE id_presentation=? AND number=?";
	private static final String INCREMENT_CLEAR_SLIDE = "UPDATE slides SET clear=clear+1 WHERE id_presentation=? AND number=?";

	private static DBManager instance;
	private DataSource ds;

	public static synchronized DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	private DBManager() {
		try {
			ds = (DataSource) new InitialContext().lookup(Fields.JNDI_NAME);
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	public void create(SlidePackage pack) throws NumberFormatException,
			SQLException {
		int id = createPresentation(pack.getName(), pack.getName());
		if (id == -1) {
			throw new IndexOutOfBoundsException();
		}
		createSlide(Integer.valueOf(pack.getSlideNumber()),
				Integer.valueOf(pack.getClear()), id);
		pack.setId(String.valueOf(id));
	}

	public int createPresentation(String title, String name)
			throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int id = -1;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(CREATE_PRESENTATION,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, name);
			pstmt.setString(2, title);

			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			con.close();
		}
		return id;
	}

	private void createSlide(int number, int clear, int idPresentation)
			throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(CREATE_SLIDE,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, number);
			pstmt.setInt(2, clear);
			pstmt.setInt(3, idPresentation);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			con.close();
		}
	}

	public void createSlide(SlidePackage pack) throws NumberFormatException,
			SQLException {
		int clear = findSlide(Integer.valueOf(pack.getSlideNumber()),
				Integer.valueOf(pack.getId()));
		if (clear != -1) {
			pack.setClear(String.valueOf(clear));
		} else {
			createSlide(Integer.valueOf(pack.getSlideNumber()), 0,
					Integer.valueOf(pack.getId()));
			pack.setClear(String.valueOf(0));
		}
	}

	private int findSlide(int number, int idPresentation) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int clear = -1;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(FIND_SLIDE);

			pstmt.setInt(1, idPresentation);
			pstmt.setInt(2, number);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				clear = rs.getInt("clear");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			con.close();
		}
		return clear;
	}

	public int updateSlide(SlidePackage pack) throws NumberFormatException,
			SQLException {
		updateSlide(Integer.valueOf(pack.getSlideNumber()),
				Integer.valueOf(pack.getId()));
		return findSlide(Integer.valueOf(pack.getSlideNumber()),
				Integer.valueOf(pack.getId()));
	}

	synchronized private void updateSlide(int number, int idPresentation)
			throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(INCREMENT_CLEAR_SLIDE);

			pstmt.setInt(1, idPresentation);
			pstmt.setInt(2, number);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			con.close();
		}
	}
}
