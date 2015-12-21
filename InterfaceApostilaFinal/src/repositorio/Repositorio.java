package repositorio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import consultas.Consult;
import modelo.Apostila;
import modelo.Usuario;
import util.DBUtil;

public class Repositorio implements IRepositorio {
	
	private static Repositorio instancia = null;
	public static Repositorio getInstancia(){
		if(instancia == null){
			instancia = new Repositorio();
		}
		if(instancia == null){
			instancia = new Repositorio();
		}
		return instancia;
		
	}
	

	@Override
	public int inserir(Apostila ap, Usuario us, Path c, Path a) {
		int result = 0;
		PreparedStatement ps;
		try {
			Apostila apostila = ap;
			Usuario usuario = us;
			String insertTableSQL = "INSERT INTO apostila (titulo, categoria, capa, apostila, id_usuario) VALUES(?,?,LOAD_FILE('"+c+"'),LOAD_FILE('"+a+"'),?); ";
			System.out.println(insertTableSQL);
			ps = DBUtil.getConnetion().prepareStatement(insertTableSQL);
			ps.setString(1, apostila.getTitulo());
			ps.setString(2, apostila.getCategoria());
			ps.setInt(3, usuario.getIdUsuario());
			result = ps.executeUpdate();
			if (result > 0) {
				System.out.println(insertTableSQL+" Inserido com sucesso");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);

			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public byte[] carregarArquivo(String caminho) {
		File file = new File(caminho);
		byte[] bFile = new byte[(int) file.length()];
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
			System.out.println("Carregado com sucesso");
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return bFile;
	}

	@Override
	public void salvarArquivo(Path caminho, int id) {
		PreparedStatement ps;
		try {
			String selTabelaSQL = "select apostila INTO OUTFILE '"+caminho+"' from apostila where id=? ";
			ps = DBUtil.getConnetion().prepareStatement(selTabelaSQL);
			ps.setInt(1, id);
			ps.executeQuery();
				JOptionPane.showMessageDialog(null, "Salvo com sucesso", "Salvo",
						JOptionPane.WARNING_MESSAGE);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}

	@Override
	public void alterar(Apostila ap, Usuario us,Path c, Path a) {
		PreparedStatement ps;
		int result = 0;
		try {
			Apostila apos = ap;
			Usuario usuario = us;
			String updateSQL = "UPDATE apostila SET titulo=? , categoria=?, capa=LOAD_FILE('"+c+"'), apostila=LOAD_FILE('"+a+"'), id_usuario=? WHERE apostila.id= ? ";
			System.out.println(updateSQL);
			ps = DBUtil.getConnetion().prepareStatement(updateSQL);
			ps.setString(1, apos.getTitulo());
			ps.setString(2, apos.getCategoria());
			ps.setInt(3, apos.getId());
			ps.setInt(4, usuario.getIdUsuario());
			result = ps.executeUpdate();
			if (result > 0) {
				System.out.println(updateSQL+" Alterado com sucesso");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}

	@Override
	public int remover(Apostila rem) {
		PreparedStatement ps;
		try {
			Apostila r = rem;
			String removeSQL = " DELETE from apostila WHERE id = ?";
			ps = DBUtil.getConnetion().prepareStatement(removeSQL);
			ps.setInt(1, r.getId());
			int result = ps.executeUpdate();
			if (result > 0) {
				System.out.println("Apostila deletada com sucesso! \n");
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<Consult> consultaPorId(int idApostila) {
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<Consult>consultResult = new ArrayList<Consult>();
		String titulo;
		String categoria;
		String nomeUsuario;
		try {
			String selectTableSQL = "SELECT a.id, a.titulo, a.categoria, u.nome FROM apostila a LEFT JOIN usuario u ON a.id_usuario = u.id_usuario WHERE a.id = "
					+ idApostila + "";
			ps = DBUtil.getConnetion().prepareStatement(selectTableSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				idApostila = rs.getInt("a.id");
				titulo = rs.getString("a.titulo");
				categoria = rs.getString("a.categoria");
				nomeUsuario = rs.getString("u.nome");
				Consult consultR = new Consult(idApostila, titulo,
						categoria, nomeUsuario );
				consultResult.add(consultR);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return consultResult;
	}

	@Override
	public ArrayList<Consult> consultaPorTitulo(String nomeApostila) {
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<Consult>consultResult = new ArrayList<Consult>();
		int idApostila;
		String categoria;
		String nomeUsuario;
		try {
			String selectTableSQL = "SELECT a.id, a.titulo, a.categoria, u.nome "
					+ "FROM apostila a LEFT JOIN usuario u ON a.id_usuario = u.id_usuario "
					+ "WHERE a.titulo Like'%" + nomeApostila + "%'";
			ps = DBUtil.getConnetion().prepareStatement(selectTableSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				idApostila = rs.getInt("a.id");
				nomeApostila = rs.getString("a.titulo");
				categoria = rs.getString("a.categoria");
				nomeUsuario = rs.getString("u.nome");
				Consult consultR = new Consult(idApostila, nomeApostila,
						categoria, nomeUsuario );
				consultResult.add(consultR);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return consultResult;
	}

	@Override
	public ArrayList<Consult> listarPorCategoria(String categoriaApostila) {
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<Consult>consultResult = new ArrayList<Consult>();
		int idApostila;
		String nomeApostila;
		String nomeUsuario;
		try {
			String selectTableSQL = "SELECT a.id, a.titulo, a.categoria, u.nome "
					+ "FROM apostila a LEFT JOIN usuario u ON a.id_usuario = u.id_usuario "
					+ "WHERE a.categoria Like'%" + categoriaApostila + "%'";
			ps = DBUtil.getConnetion().prepareStatement(selectTableSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				idApostila = rs.getInt("a.id");
				nomeApostila = rs.getString("a.titulo");
				categoriaApostila = rs.getString("a.categoria");
				nomeUsuario = rs.getString("u.nome");
				Consult consultR = new Consult(idApostila, nomeApostila,
						categoriaApostila, nomeUsuario );
				consultResult.add(consultR);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return consultResult;
	}

	@Override
	public ArrayList<Consult> listarTodas() {
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<Consult>consultResult = new ArrayList<Consult>();
		int idApostila;
		String nomeApostila;
		String categoriaApostila;
		String nomeUsuario;
		try {
			String selectTableSQL = "SELECT a.id, a.titulo, a.categoria, u.nome "
					+ "FROM apostila a LEFT JOIN usuario u ON a.id_usuario = u.id_usuario";
			ps = DBUtil.getConnetion().prepareStatement(selectTableSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				idApostila = rs.getInt("a.id");
				nomeApostila = rs.getString("a.titulo");
				categoriaApostila = rs.getString("a.categoria");
				nomeUsuario = rs.getString("u.nome");
				Consult consultR = new Consult(idApostila, nomeApostila,
						categoriaApostila, nomeUsuario );
				consultResult.add(consultR);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return consultResult;
	}

	@Override
	public int inserirUsuario(Usuario us) {
		int result = 0;
		PreparedStatement ps;
		try {
			Usuario usu = us;
			String insertTableSQL = "INSERT INTO usuario (privilegio, nome, senha) VALUES(?,?,?); ";
			ps = DBUtil.getConnetion().prepareStatement(insertTableSQL);
			ps.setBoolean(1, usu.isPrivilegio());
			ps.setString(2, usu.getNome());
			ps.setString(3, usu.getSenha());
			result = ps.executeUpdate();
			if (result > 0) {
				System.out.println("Inserido com sucesso");
			}
		}

		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return result;
	}

	public void alterarUsuario(Usuario usu) {
		PreparedStatement ps;
		int result = 0;
		try {
			Usuario usuario = usu;
			String updateSQL = "UPDATE usuario SET nome=? , privilegio=?, senha=? WHERE usuario.id_usuario= ? ";
			ps = DBUtil.getConnetion().prepareStatement(updateSQL);
			ps.setString(1, usuario.getNome());
			ps.setBoolean(2, usuario.isPrivilegio());
			ps.setString(3, usuario.getSenha());
			ps.setInt(4, usuario.getIdUsuario());
			result = ps.executeUpdate();

			if (result > 0) {
				System.out.println("Alterado com sucesso");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}

	@Override
	public int removerUsuario(Usuario usu) {
		PreparedStatement ps;
		try {
			Usuario usuario = usu;
			String removeSQL = " DELETE from usuario WHERE id_usuario = ?";
			ps = DBUtil.getConnetion().prepareStatement(removeSQL);
			ps.setInt(1, usuario.getIdUsuario());
			int result = ps.executeUpdate();
			if (result > 0) {
				System.out.println("Usuario deletado com sucesso! \n");
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<Usuario> listartodosUsuarios() {
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<Usuario> usuarioResult = new ArrayList<Usuario>();
		int id_usuario;
		String nome;
		boolean privilegio;
		try {
			String selectTableSQL = "SELECT * FROM usuario ";
			ps = DBUtil.getConnetion().prepareStatement(selectTableSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				id_usuario = rs.getInt("id_usuario");
				nome = rs.getString("nome");
				privilegio = rs.getBoolean("privilegio");
				Usuario usuarioR = new Usuario(id_usuario, nome, privilegio);
				usuarioResult.add(usuarioR);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return usuarioResult;
	}

	@Override
	public ArrayList<Usuario> listarUsuarioPorID(int id) {
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<Usuario> usuarioResult = new ArrayList<Usuario>();
		String nome;
		boolean privilegio;
		try {
			String selectTableSQL = "SELECT * FROM usuario where id_usuario = " + id;
			ps = DBUtil.getConnetion().prepareStatement(selectTableSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getInt("id_usuario");
				nome = rs.getString("nome");
				privilegio = rs.getBoolean("privilegio");
				Usuario usuarioR = new Usuario(id, nome, privilegio);
				usuarioResult.add(usuarioR);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return usuarioResult;
	}

	@Override
	public ArrayList<Usuario> listarUsuarioPorNome(String nome) {
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<Usuario> usuarioResult = new ArrayList<Usuario>();
		int id_usuario;
		boolean privilegio;
		try {
			String selectTableSQL = "SELECT * FROM usuario where nome like '%" + nome+"%'";
			ps = DBUtil.getConnetion().prepareStatement(selectTableSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				id_usuario = rs.getInt("id_usuario");
				nome = rs.getString("nome");
				privilegio = rs.getBoolean("privilegio");
				Usuario usuarioR = new Usuario(id_usuario, nome, privilegio);
				usuarioResult.add(usuarioR);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return usuarioResult;
	}

	@Override
	public boolean verificaCredenciais(String nome, String senha) {
		boolean usuarioExiste = false;
		try{
			PreparedStatement stm = DBUtil.getConnetion().prepareStatement("SELECT * FROM usuario WHERE nome = ? AND senha = ?");  
			stm.setString(1,nome);  
			stm.setString(2, senha);  
			ResultSet rs = stm.executeQuery(); 
			if(rs.next()){  
				usuarioExiste = true;  
			}
		}catch(SQLException e){
			e.printStackTrace();
		}

		return usuarioExiste;
	}

	@Override
	public boolean verificaPrivilegio(String nome, String senha) {
		boolean privilegio = false;
		try {
			PreparedStatement ps = DBUtil
					.getConnetion()
					.prepareStatement(
							"SELECT * FROM usuario WHERE nome = ? AND senha = ?");
			ps.setString(1, nome);
			ps.setString(2, senha);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				nome = rs.getString("nome");
				privilegio = rs.getBoolean("privilegio");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return privilegio;
	}

	@Override
	public int retornaId(String nome, String senha) {
		int idUsuario = 0;
		try {
			PreparedStatement ps = DBUtil
					.getConnetion().prepareStatement("SELECT * FROM usuario "
							+ "WHERE nome = ? AND senha = ?");
			ps.setString(1, nome);
			ps.setString(2, senha);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				idUsuario=rs.getInt("id_usuario");
				nome = rs.getString("nome");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idUsuario;
	}
}
