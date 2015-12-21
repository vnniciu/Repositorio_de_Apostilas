package modelo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Usuario {
	private int idUsuario;
	private boolean privilegio;
	private String nome;
	private String senha;
	
	
	
	public Usuario(int idUsuario, boolean privilegio, String nome, String senha) {
		super();
		this.idUsuario = idUsuario;
		this.privilegio = privilegio;
		this.nome = nome;
		setSenha(senha);
	}
	public Usuario(int idUsuario){
		this.idUsuario = idUsuario;
	}
	
	public Usuario(int idUsuario, String nome, boolean privilegio){
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.privilegio = privilegio;
	}
	
	public Usuario(String nome, String senha){
		this.nome = nome;
		setSenha(senha);
	}
	
	public Usuario( String nome, String senha, boolean privilegio){
		this.nome = nome;
		setSenha(senha);
		this.privilegio = privilegio;
	}
	
	public Usuario(){
		
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public boolean isPrivilegio() {
		return privilegio;
	}
	public void setPrivilegio(boolean privilegio) {
		this.privilegio = privilegio;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String s) {
		MessageDigest algorithm=null;
		try {
			algorithm = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		
		byte messageDigest[]=null;
		try {
			messageDigest = algorithm.digest(s.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		 
		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
		  hexString.append(String.format("%02X", 0xFF & b));
		}
		 this.senha = hexString.toString();
		
		
	}
	
}

