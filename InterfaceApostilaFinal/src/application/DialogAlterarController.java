package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JOptionPane;
import application.ApostilaController.ItensProperty;
import repositorio.Repositorio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import modelo.Apostila;
import modelo.Usuario;

public class DialogAlterarController {

	private Apostila ap;
	public Repositorio repos;
	public Usuario user;
	private byte[] capa;
	private byte[] apostila;
	private File file;
	private Path caminhoC=null;
	private Path caminhoA=null;
	ItensProperty ip = null;


	@FXML
	TextField txtTitulo;
	@FXML
	TextField txtCategoria;
	@FXML
	TextField txtCapa;
	@FXML
	TextField txtApostila;
	@FXML
	Button btnInserir;
	@FXML
	Button btnCapa;
	@FXML
	Button btnApostila;


	public void setApostila(ItensProperty ap2) {
		this.ip = ap2;

		if(ip != null) {
			txtTitulo.setText(ip.getTitulo());
			txtCategoria.setText(ip.getCategoria());
		}


	}

	@FXML
	protected void acaoInsere(ActionEvent event) throws IOException {

		if( ip == null) {

			String tit = txtTitulo.getText();
			String cat = txtCategoria.getText();
			int id = LoginController.getId();

			user= new Usuario(id);
			ap = new Apostila(tit, cat, capa, apostila);
			repos= new Repositorio();
			repos.inserir(ap,user,caminhoC,caminhoA);
			JOptionPane.showMessageDialog(null, "Apostila inserida com sucesso!", "Inserida",JOptionPane.WARNING_MESSAGE);

		}else {
			
			String tit = txtTitulo.getText();
			String cat = txtCategoria.getText();
			int id = LoginController.getId();
            int idAp = ip.getId();
			user= new Usuario(id);
			ap = new Apostila(idAp,tit, cat, capa, apostila);
			repos= new Repositorio();
			repos.alterar(ap,user,caminhoC,caminhoA);
			JOptionPane.showMessageDialog(null, "Apostila alterada com sucesso!", "Alterada",JOptionPane.WARNING_MESSAGE);

		}


	}

	@FXML
	protected void acaoCapa(ActionEvent event) throws IOException {

		FileChooser fileChooser = new FileChooser();

		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("JPG Files", "*.jpg"),
				new ExtensionFilter("All Files", "*.*"));

		file = fileChooser.showOpenDialog(null);

        caminhoC=Paths.get(file.getPath());

		txtCapa.setText(file.getPath());
	}

	@FXML
	protected void acaoApostila(ActionEvent event) throws IOException {

		FileChooser fileChooser = new FileChooser();

		fileChooser.getExtensionFilters().addAll(

				new ExtensionFilter("PDF Files", "*.pdf"),
				new ExtensionFilter("All Files", "*.*"));
				file = fileChooser.showOpenDialog(null);
		
		caminhoA= Paths.get(file.getPath());

		txtApostila.setText(file.getCanonicalPath());

	}

}
