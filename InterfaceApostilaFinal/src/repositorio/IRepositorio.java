package repositorio;

import java.nio.file.Path;
import java.util.ArrayList;

import consultas.Consult;
import modelo.Apostila;
import modelo.Usuario;


public interface IRepositorio {

	public byte[] carregarArquivo(String caminho);

	public void salvarArquivo(Path caminho, int id);

	public int inserir(Apostila ap, Usuario us, Path capa, Path apostila);

	public void alterar(Apostila ap, Usuario us,Path c, Path a);

	public int remover(Apostila rem);
	
	public ArrayList<Consult> consultaPorId(int id);
	
	public ArrayList<Consult> consultaPorTitulo(String t);
	
	public ArrayList<Consult> listarPorCategoria(String categoriaApostila);

	public ArrayList<Consult> listarTodas();
	
	public int inserirUsuario(Usuario u);
	
	public void alterarUsuario(Usuario u);
	
	public int removerUsuario(Usuario u);
	
	public ArrayList<Usuario> listartodosUsuarios();
	
	public ArrayList<Usuario> listarUsuarioPorID(int id);
	
	public ArrayList<Usuario> listarUsuarioPorNome(String nome);
	
	public boolean verificaCredenciais(String nome, String senha);
	
	public boolean verificaPrivilegio(String nome, String senha);
	
	public int retornaId(String nome, String senha);
}
