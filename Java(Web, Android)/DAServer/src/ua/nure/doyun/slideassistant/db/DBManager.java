package ua.nure.doyun.slideassistant.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import ua.nure.doyun.slideassistant.entities.SlidePackage;

import com.mysql.jdbc.Statement;

public class DBManager {
	
	private static final String CREATE_PRESENTATION = "INSERT INTO presentations (name, title) VALUES (?, ?)";
	private static final String CREATE_SLIDE= "INSERT INTO slides (number, clear, id_presentation) VALUES (?, ?, ?)";
	private static final String UPDATE_SLIDE= "UPDATE slides SET clear=? WHERE number=? AND id_presentation=?";
	
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

	public void create(SlidePackage pack) throws NumberFormatException, SQLException{
		int id = createPresentation(pack.getName(), pack.getName());
		if(id == -1){
			throw new IndexOutOfBoundsException();
		}
		createSlide(Integer.valueOf(pack.getSlideNumber()), Integer.valueOf(pack.getClear()), id);
		pack.setId(String.valueOf(id));
	}
	
	public int createPresentation(String title, String name) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		int id = -1;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(CREATE_PRESENTATION, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, name);
			pstmt.setString(2, title);
			
			if(pstmt.executeUpdate() > 0){
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			pstmt.close();
			con.close();
		}
		return id;
	}
	
	public void createSlide(int number, int clear, int idPresentation) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(CREATE_SLIDE, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setInt(1, number);
			pstmt.setInt(2, clear);
			pstmt.setInt(3, idPresentation);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			pstmt.close();
			con.close();
		}		
	}
	
	synchronized public void updateSlide(int number, int clear, int idPresentation) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(UPDATE_SLIDE);
			
			pstmt.setInt(1, clear);
			pstmt.setInt(2, number);
			pstmt.setInt(3, idPresentation);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			pstmt.close();
			con.close();
		}				
	}
}
