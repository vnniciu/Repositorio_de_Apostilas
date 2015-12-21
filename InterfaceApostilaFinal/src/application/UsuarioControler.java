package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.Usuario;
import repositorio.Repositorio;

public class UsuarioControler implements Initializable{
	public class ItensPropertyUsuario{
		private SimpleIntegerProperty id;
		private SimpleStringProperty nome;
		private SimpleBooleanProperty privilegio;

		public ItensPropertyUsuario(int id, String nome, boolean privilegio){
			this.id = new SimpleIntegerProperty(id);
			this.nome = new SimpleStringProperty(nome);
			this.privilegio = new SimpleBooleanProperty(privilegio);
		}

		public int getId() {
			return id.get();
		}

		public void setId(int id) {
			this.id.set(id);
		}

		public String getNome() {
			return nome.get();
		}

		public void setNome(String nome) {
			this.nome.set(nome);
		}

		public Boolean getPrivilegio() {
			return privilegio.get();
		}

		public void setPrivilegio(Boolean privilegio) {
			this.privilegio.set(privilegio);;
		}
	}


	@FXML
	AnchorPane tabelaPane1;
	@FXML
	TableView<ItensPropertyUsuario> tabela;

	@FXML
	TableColumn<ItensPropertyUsuario, Integer> idU;	

	@FXML
	TableColumn<ItensPropertyUsuario, String> nomeU;
	
	@FXML
	TableColumn<ItensPropertyUsuario, Boolean> privilegio;
	
	@FXML
	Button btnAlterar;
	
	@FXML
	Button NovoU;
	
	@FXML
	Button ExcluirU;
	
	
	
	private ItensPropertyUsuario us = null;
	
	private int user1 = 0;

	

	private  ObservableList<ItensPropertyUsuario> listItens = FXCollections.observableArrayList();

	private static ArrayList<Usuario> usuarioResult; 

	public ArrayList<Usuario> getConsultResult() {
		return usuarioResult;
	}

	public static void setConsultResult(ArrayList<Usuario> user) {
		usuarioResult=user;
	}

	public void initComponents(){
		idU.setCellValueFactory(new PropertyValueFactory<ItensPropertyUsuario, Integer>("id"));
		nomeU.setCellValueFactory(new PropertyValueFactory<ItensPropertyUsuario, String>("nome"));
		privilegio.setCellValueFactory(new PropertyValueFactory<ItensPropertyUsuario, Boolean>("privilegio"));
	}

	public void initItens(){
		
		ArrayList<Usuario>usuarioResult = PrincipalController.getUsuarioResult();
		
		for(Usuario u : usuarioResult){
			listItens.add(new ItensPropertyUsuario(u.getIdUsuario(), u.getNome(), u.isPrivilegio()));
		}
		
		tabela.setItems(listItens);
		
		
	}
	
	private void initTransition(AnchorPane stage) {
	      FadeTransition transition = new FadeTransition(
	      Duration.millis(4000), stage);
	      transition.setFromValue(0.0);
	      transition.setToValue(1.0);
	      transition.play();
	   }

	   public void start(Stage principal) {
	      try {
	         AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("buscaUsuario.fxml"));
	         Scene scene = new Scene(root);
	         initTransition(root);
	         scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	         principal.setScene(scene);
	         principal.show();


	      } catch(Exception e) {
	         e.printStackTrace();
	      }

	   }
	   public static void main(String[] args) {

	   }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initComponents();
		initItens();
		tabela.getSelectionModel().selectedItemProperty().addListener((observable,oldValue, newValue) -> loadDetailsUsuario(newValue));


	}
	
	@FXML
	protected void acaoalterar(ActionEvent event) throws IOException{
		loadDialog("Alterar Usuario", btnAlterar, us);
	}
	
	
	@FXML
	protected void acaoNovo(ActionEvent event) throws IOException {

		loadDialog("Novo Usuario", NovoU, null);

	}



	@FXML
	protected void acaoExcluir(ActionEvent event) throws IOException {

		int selectedIndex = tabela.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Usuario user = new Usuario(user1);
			Repositorio rep = new Repositorio();
			rep.removerUsuario(user);
			tabela.getItems().remove(selectedIndex);
		} 

	}
	
	public void loadDialog(String titulo, Button btn, ItensPropertyUsuario usu) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("DialogCadastroUsuario.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			initTransition(root);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle(titulo);
			stage.show();


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadDetailsUsuario(ItensPropertyUsuario newValue) {
		us = newValue;
		user1 = us.getId();

	}







}