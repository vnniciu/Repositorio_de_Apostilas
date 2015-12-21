package application;



import java.io.IOException;

import javax.swing.JOptionPane;

import application.UsuarioControler.ItensPropertyUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import modelo.Usuario;
import repositorio.Repositorio;

public class DialogCadastroUsuarioController{

	private Usuario user;
	public Repositorio rep;
	ItensPropertyUsuario ip1 = null;
	
	@FXML
	TextField txtNome;
	@FXML
	PasswordField txtSenha;
	@FXML
	RadioButton rbCprivilegio;
	@FXML
	RadioButton rbSprivilegio;
	@FXML
	Button btsalvar;
	
	public void setUsuario(ItensPropertyUsuario user2){
		this.ip1 = user2;
		
		if(ip1 != null){
			txtNome.setText(ip1.getNome());
			if(user.isPrivilegio() == true){
				rbSprivilegio.isSelected();	
			}else
				if(user.isPrivilegio() == false){
					rbCprivilegio.isSelected();		
				}
		}
	}
	
	@FXML
	protected void acaoInserirUsuario(ActionEvent event) throws IOException{
		if( ip1 == null) {

			String nome = txtNome.getText();
			String senha = txtSenha.getText();
			boolean privilegio = false;
			if(rbSprivilegio.isSelected() == true){
				privilegio = true;
			}else
				if(rbCprivilegio.isSelected() == true){
					privilegio = false;
				}
			

			user= new Usuario(nome, senha, privilegio);
			Repositorio rep = new Repositorio();
			rep.inserirUsuario(user);
			JOptionPane.showMessageDialog(null, "Usuario inserido com sucesso!", null,JOptionPane.WARNING_MESSAGE);

		}else {
			
			String nome = txtNome.getText();
			String senha = txtSenha.getText();
			boolean privilegio = false;
			
			if(user.isPrivilegio() == true){
				rbSprivilegio.isSelected();	
			}else
				if(user.isPrivilegio() == false){
					rbCprivilegio.isSelected();		
				}
			
			if(rbSprivilegio.isSelected() == true){
				privilegio = true;
			}else
				if(rbCprivilegio.isSelected() == true){
					privilegio = false;
				}
			

			user= new Usuario(nome, senha, privilegio);
			Repositorio rep = new Repositorio();
			rep.alterarUsuario(user);
			JOptionPane.showMessageDialog(null, "Cadastro de Usuario alterado com sucesso!",null, JOptionPane.WARNING_MESSAGE);

		}
	}

	
	
}
