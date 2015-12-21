package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.Usuario;
import repositorio.Repositorio;
import consultas.Consult;


public class PrincipalController {



	@FXML
	AnchorPane tabBuscar;
	@FXML
	Button btnBuscaAp;
	@FXML
	Button btnBuscaUs1;
	@FXML
	MenuItem mAbrir;
	@FXML
	MenuItem mAlterar;
	@FXML
	MenuItem mExcluir;
	@FXML
	MenuItem mSair;
	@FXML
	MenuItem mSalvar;
	@FXML
	MenuItem mSobre;
	@FXML
	RadioButton rbCategoria;
	@FXML
	RadioButton rbID;
	@FXML
	RadioButton rbNome;
	@FXML
	RadioButton rbUID1;
	@FXML
	RadioButton rbUNome1;
	@FXML
	AnchorPane tabBuscarU;
	@FXML
	Tab tabBuscarUsuario;
	@FXML
	MenuItem mAbrir1;
	@FXML
	MenuItem mAbrir2;
	@FXML
	TextField tfBuscaApostila;
	@FXML
	TextField tfBuscausuario1;
	@FXML
	AnchorPane tabApostila;
	@FXML
	HTMLEditor EditorApostila;

	Repositorio rep = new Repositorio();
	boolean p;

	static ArrayList<Consult>consultResult = new ArrayList<Consult>();
	static ArrayList<Usuario>usuarioResult = new ArrayList<Usuario>();

	public void start(Stage principal) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("telaPrincipal.fxml"));
			initTransition(root);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			principal.setScene(scene);
			principal.setTitle("Apostilas IF");
			principal.show();
			

			int id = LoginController.getId();

			ArrayList<Usuario> usuarioRI = rep.listarUsuarioPorID(id);
			for (int i = 0; i < usuarioRI.size(); i++) {
				p = usuarioRI.get(i).isPrivilegio();
			}
			

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

	}

	private void initTransition(AnchorPane stage) {
		FadeTransition transition = new FadeTransition(
				Duration.millis(4000), stage);
		transition.setFromValue(0.0);
		transition.setToValue(1.0);
		transition.play();
	}

	@FXML
	protected void buscarApostilaClick(ActionEvent event) throws IOException {

		if(event.getSource()== btnBuscaAp){

			if(rbID.isSelected()==true){
				
				if(tfBuscaApostila.getText().equals("")){
					consultResult=rep.listarTodas();

					FXMLLoader  fxmlloader = new FXMLLoader(getClass().getResource("buscaApostila.fxml"));

					tabBuscar.getChildren().add(fxmlloader.load());
					
				}else {

				consultResult=rep.consultaPorId(Integer.parseInt(tfBuscaApostila.getText()));

				FXMLLoader  fxmlloader = new FXMLLoader(getClass().getResource("buscaApostila.fxml"));
				tabBuscar.getChildren().add(fxmlloader.load());
				}
			}
			else if(rbNome.isSelected()==true){

				consultResult=rep.consultaPorTitulo(tfBuscaApostila.getText());

				FXMLLoader  fxmlloader = new FXMLLoader(getClass().getResource("buscaApostila.fxml"));

				tabBuscar.getChildren().add(fxmlloader.load());

			}

			else if(rbCategoria.isSelected()==true){

				consultResult=rep.listarPorCategoria(tfBuscaApostila.getText());

				FXMLLoader  fxmlloader = new FXMLLoader(getClass().getResource("buscaApostila.fxml"));

				tabBuscar.getChildren().add(fxmlloader.load());
			}
			else{
				consultResult=rep.listarTodas();

				FXMLLoader  fxmlloader = new FXMLLoader(getClass().getResource("buscaApostila.fxml"));

				tabBuscar.getChildren().add(fxmlloader.load());
			}



		}

	}

	@FXML
	protected void btnBuscaU(ActionEvent event) throws IOException {

		if(event.getSource() == btnBuscaUs1){

			if(rbUID1.isSelected() == true){
				if(tfBuscausuario1.getText() .equals("")){
					usuarioResult = rep.listartodosUsuarios();
					FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("buscaUsuario.fxml"));
					tabBuscarU.getChildren().add(fxmlloader.load());
				}
				else{
					usuarioResult = rep.listarUsuarioPorID(Integer.parseInt(tfBuscausuario1.getText()));
					FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("buscaUsuario.fxml"));
					tabBuscarU.getChildren().add(fxmlloader.load());
				}
				
			}

			else if(rbUNome1.isSelected() == true){
				System.out.println("locura");
				usuarioResult = rep.listarUsuarioPorNome(tfBuscausuario1.getText());
				FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("buscaUsuario.fxml"));
				tabBuscarU.getChildren().add(fxmlloader.load());
			}
			else{
				usuarioResult = rep.listartodosUsuarios();
				FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("buscaUsuario.fxml"));
				tabBuscarU.getChildren().add(fxmlloader.load());
			}

		}




	}

	@FXML
	protected void menuAbrirAction(ActionEvent event) throws IOException {

		if(p == false){
			mAbrir2.setDisable(true);
			mAbrir1.setDisable(true);
		}

		if(event.getSource()== mSair){

			Platform.exit();

		} else if(event.getSource()== mAbrir){

			setText(EditorApostila);

		}	else if(event.getSource()== mSalvar){


		} 	else if(event.getSource() == mAbrir1){
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("DialogCadastroUsuario.fxml"));
			initTransition(root);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage p = new Stage();
			p.setScene(scene);
			p.setTitle("Apostilas IF");
			p.show();
			
		}	else if(event.getSource() == mAbrir2){
			JOptionPane.showMessageDialog(null, "haha");
			TabPane painelAba = new TabPane();
			Tab tabBuscarUsuario = new Tab("tabBuscarUsuario");
			painelAba.getTabs().add(tabBuscarUsuario);
		}


	}

	protected void setText(HTMLEditor editor) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("HTML Files", "*.html"),
				new ExtensionFilter("PDF Files", "*.pdf"),
				new ExtensionFilter("All Files", "*.*"));
		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			StringBuilder stringBuffer = new StringBuilder();
			BufferedReader bufferedReader = null;
			try {
				bufferedReader = new BufferedReader
						(new FileReader(file));
				String text;
				while ((text = bufferedReader.readLine()) != null) {
					stringBuffer.append(text);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			EditorApostila.setHtmlText(stringBuffer.toString());
		}

	}


	public static ArrayList<Consult> getConsultResult() {
		return consultResult;
	}

	public void setConsultResult(ArrayList<Consult> consultResult) {
		PrincipalController.consultResult = consultResult;
	}
	public static ArrayList<Usuario> getUsuarioResult() {
		return usuarioResult;
	}

}
