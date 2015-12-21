package consultas;

public class Consult {
	private int idApostila;
	private String nomeApostila;
	private String categoriaApostila;
	private String nomeUsuario;

	public Consult(int idApostila, String nomeApostila,
			String categoriaApostila, String nomeUsuario) {
		this.idApostila = idApostila;
		this.nomeApostila = nomeApostila;
		this.categoriaApostila = categoriaApostila;
		this.nomeUsuario = nomeUsuario;
	}

	public int getIdApostila() {
		return idApostila;
	}

	public void setIdApostila(int idApostila) {
		this.idApostila = idApostila;
	}

	public String getNomeApostila() {
		return nomeApostila;
	}

	public void setNomeApostila(String nomeApostila) {
		this.nomeApostila = nomeApostila;
	}

	public String getCategoriaApostila() {
		return categoriaApostila;
	}

	public void setCategoriaApostila(String categoriaApostila) {
		this.categoriaApostila = categoriaApostila;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

}
