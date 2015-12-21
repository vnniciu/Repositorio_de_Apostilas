package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DBUtil {

	public static Connection getConnetion(){
		Connection connection = null;
		String urlConexao = "jdbc:mysql://localhost:3306/trabalho";
		String driverJDBC = "com.mysql.jdbc.Driver";
		String usuarioConexaoBanco  = "apostila";
		String passwordConexaoBanco  = "123456";

		try {
			Class.forName(driverJDBC);
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection(urlConexao, usuarioConexaoBanco, passwordConexaoBanco);
			}
		} 
		catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}

		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}

		return connection;
	}
}
