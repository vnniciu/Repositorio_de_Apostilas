package modelo;

public class Apostila {

	private int id;
	private String titulo;
	private String categoria;
	private byte[] capa;
	private byte[] apostila;
	private Usuario idUsuario;

	public Apostila(int id, String titulo, String categoria, byte[] capa,
			byte[] apostila) {
		this.id = id;
		this.titulo = titulo;
		this.categoria = categoria;
		this.capa = capa;
		this.apostila = apostila;
	}

	public Apostila(int id) {
		this.id = id;
	}

	public Apostila(String titulo, String categoria, byte[] capa,
			byte[] apostila) {
		super();
		this.titulo = titulo;
		this.categoria = categoria;
		this.capa = capa;
		this.apostila = apostila;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public byte[] getCapa() {
		return capa;
	}

	public void setCapa(byte[] capa) {
		this.capa = capa;
	}

	public byte[] getApostila() {
		return apostila;
	}

	public void setApostila(byte[] apostila) {
		this.apostila = apostila;
	}

	public Usuario getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Usuario idUsuario) {
		this.idUsuario = idUsuario;
	}
}
