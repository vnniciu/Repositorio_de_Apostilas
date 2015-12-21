package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.Apostila;
import repositorio.Repositorio;
import consultas.Consult;





public class ApostilaController   implements Initializable {

	public class ItensProperty {

		private SimpleIntegerProperty id;
		private SimpleStringProperty titulo;
		private SimpleStringProperty categoria;
		private SimpleStringProperty nome;


		public ItensProperty(int id, String titulo, String categoria,String nome) {
			this.id = new SimpleIntegerProperty(id);
			this.titulo = new SimpleStringProperty(titulo);
			this.categoria = new SimpleStringProperty(categoria);
			this.nome= new SimpleStringProperty(nome);
		}


		public int getId() {
			return id.get();
		}


		public void setId(int id) {
			this.id.set(id);
		}


		public String getTitulo() {
			return titulo.get();
		}


		public void setTitulo(String titulo) {
			this.titulo.set(titulo);
		}


		public String getCategoria() {
			return categoria.get();
		}


		public void setCategoria(String categoria) {
			this.categoria.set(categoria);
		}


		public String getNome() {
			return nome.get();
		}


		public void setNome(String nome) {
			this.nome.set(nome);
		}


	}


	@FXML
	AnchorPane tabelaPane;
	@FXML
	TableView<ItensProperty> tabela;

	@FXML
	TableColumn<ItensProperty, Integer> id;	

	@FXML
	TableColumn<ItensProperty, String> titulo;

	@FXML
	TableColumn<ItensProperty, String> categoria;

	@FXML
	TableColumn<ItensProperty, String> nome;

	@FXML
	MenuItem  cmEditar;

	@FXML
	MenuItem cmExcluir;

	@FXML
	MenuItem cmSalvar;

	@FXML
	Button  NovaA;

	@FXML
	Button  btnEditar;

	@FXML
	Button  btnExcluir;

	private ItensProperty ap = null;
	private int idap = 0;

	private  ObservableList<ItensProperty> listItens = FXCollections.observableArrayList();

	private static ArrayList<Consult> consultResult; 

	public ArrayList<Consult> getConsultResult() {
		return consultResult;
	}

	public static void setConsultResult(ArrayList<Consult> consul) {
		consultResult=consul;
	}



	public void initComponents(){

		id.setCellValueFactory(new PropertyValueFactory<ItensProperty, Integer>("id"));
		titulo.setCellValueFactory(new PropertyValueFactory<ItensProperty, String>("titulo"));
		categoria.setCellValueFactory(new PropertyValueFactory<ItensProperty, String>("categoria"));
		nome.setCellValueFactory(new PropertyValueFactory<ItensProperty, String>("nome"));

	}

	public void initItens(){

		ArrayList<Consult>consultResult = PrincipalController.getConsultResult();
		System.out.println(consultResult.size());

		for (Consult c : consultResult){

			listItens.add(new ItensProperty(c.getIdApostila(), c.getNomeApostila(), c.getCategoriaApostila(), c.getNomeUsuario()));

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
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("buscaApostila.fxml"));
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
		tabela.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> loadDetailsApostila(newValue));


	}

	@FXML
	protected void acaoNovo(ActionEvent event) throws IOException {

		loadDialog("Nova Apostila", NovaA, null);



	}

	@FXML
	protected void acaoEditar(ActionEvent event) throws IOException {

		loadDialog("Alterar uma apostila", btnEditar, ap);
	}

	@FXML
	protected void acaoExcluir(ActionEvent event) throws IOException {

		int selectedIndex = tabela.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Apostila ap = new Apostila(idap);
			Repositorio rep = new Repositorio();
			rep.remover(ap);
			tabela.getItems().remove(selectedIndex);
		} 

	}

	@FXML
	protected void acaoSalva(ActionEvent event) throws IOException {

		int selectedIndex = tabela.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {

			Repositorio rep = new Repositorio();

			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(

					new ExtensionFilter("PDF Files", "*.pdf"),
					new ExtensionFilter("All Files", "*.*"));
			File file = fileChooser.showSaveDialog(null);
			
			Path caminho = Paths.get(file.getPath());

			System.out.println("O caminho do arquivo � "+caminho);
			rep.salvarArquivo(caminho, idap);
			

		} 

	}

	public void loadDialog(String title, Button btn, ItensProperty apo) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("DialogAlterar.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			Stage stage = new Stage();
			DialogAlterarController controller = loader.getController();
			controller.setApostila(ap);

			stage.setScene(new Scene(root));
			stage.setTitle(title);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(btn.getScene().getWindow());
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadDetailsApostila(ItensProperty newValue) {
		ap = newValue;
		idap = ap.getId();

	}






}
