package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import modelo.Usuario;
import repositorio.Repositorio;

public class LoginController{

	@FXML
	private  TextField  fieldUsuario;

	@FXML
	private PasswordField PfieldSenha;

	@FXML
	private Button btnEntrar;

	@FXML
	private AnchorPane telaPrincipal;

	@FXML
	private AnchorPane login;

	String nome = null;
	String senha = null;
	static int id = 0;

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		LoginController.id = id;
	}

	boolean log;
	Repositorio re = new Repositorio();
	Stage stage;

	@FXML	
	private void entrarClick(ActionEvent event) throws IOException{



		if(event.getSource()== btnEntrar){
			nome =  fieldUsuario.getText();
			senha = PfieldSenha.getText();

			Usuario user = new Usuario(nome, senha);

			senha= user.getSenha();

			 id = re.retornaId(nome, senha);	
			 
			if (re.verificaCredenciais(nome, senha) == true) {

				
                 PrincipalController telap = new PrincipalController();
				 telap.start(new Stage());
				 LoginController login = Main.getLogin();
				 login.stage.close();
				 

			}
			else{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Erro!");
				alert.setHeaderText(null);
				alert.setContentText("Usuario ou senha incorretos");
				alert.showAndWait();
				
			}


		}


	}







}
