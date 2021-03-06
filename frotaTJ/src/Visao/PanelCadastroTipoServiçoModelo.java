package Visao;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.MaskFormatter;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;

import util.IntegerDocument;

import mb.MBMarca;
import mb.MBModelo;
import mb.MBTipoServico;
import mb.MBTipoServicoVeiculo;
import mb.MBTipoServišoModelo;
import mb.MBVeiculo;

import dao.Marca;
import dao.Modelo;
import dao.TipoServico;
import dao.TipoServicoModelo;
import dao.TipoServicoModeloDAO;
import dao.TipoServicoModeloId;
import dao.TipoServicoVeiculo;
import dao.TipoServicoVeiculoId;
import dao.Veiculo;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class PanelCadastroTipoServišoModelo extends PanelExemplo {
	private JComboBox<Modelo> comboBoxModelo;
	private JComboBox<TipoServico> comboBoxTipoServišo;
	private String winDir= ("imagens\\");
	private JTextField textFieldKM;
	private JTextField textFieldData;
	private JLabel lblModelo;
	/**
	 * Create the panel.
	 */
	public PanelCadastroTipoServišoModelo(final int idModeloSelecionado, final int idTipoServišoselecionado) {

		JLabel lblCadastroTipoServicoModelo = new JLabel("  Cadastro de Tipos Servi\u00E7o por Modelo");
		lblCadastroTipoServicoModelo.setIcon(new ImageIcon("imagens\\11988_32x32.png"));
		lblCadastroTipoServicoModelo.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon("imagens\\7464_32x32.png"));
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelListagemTipoServišoModelo();
			}
		});

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon("imagens\\7484_16x16.png"));
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MBTipoServico mbTipoServico = MBTipoServico.getInstance();
				MBModelo mbModelo = MBModelo.getInstance();
				
				TipoServicoModeloId tipoServicoModeloId = new TipoServicoModeloId(
						new Integer(comboBoxTipoServišo.getItemAt(comboBoxTipoServišo.getSelectedIndex()).getIdtipoServico()), 
						new Integer(comboBoxModelo.getItemAt(comboBoxModelo.getSelectedIndex()).getIdmodelo()));
				
				MBTipoServišoModelo mbTipoServišoModelo = MBTipoServišoModelo.getInstance();
				
				Modelo modelo = mbModelo.retornarModelo(comboBoxModelo.getItemAt(comboBoxModelo.getSelectedIndex()).getIdmodelo());
				TipoServico tipoServico =  mbTipoServico.retornarTipoServico(comboBoxTipoServišo.getItemAt(comboBoxTipoServišo.getSelectedIndex()).getIdtipoServico());
				TipoServicoModelo t = new TipoServicoModelo(tipoServicoModeloId, modelo , tipoServico, Integer.parseInt(textFieldKM.getText()), Integer.parseInt(textFieldData.getText()));

				try {
					if (idModeloSelecionado==0 && idTipoServišoselecionado == 0){
						if (t.getId().getModeloIdmodelo()==0 && t.getId().getTipoServicoIdtipoServico()==0){

							t.setId(tipoServicoModeloId);
						}

						String retorno = mbTipoServišoModelo.inserir(t);
						//String retorno = "ok";
						if (retorno.equals("ok")){
							AtualizarTipoServicosdosveiculos(t);
							JOptionPane.showMessageDialog(null,"Inserido!");

						}else{
							JOptionPane.showMessageDialog(null,retorno);
						}
					}else{

						String retorno =  mbTipoServišoModelo.editar(t);
						if (retorno.equals("ok")){
							JOptionPane.showMessageDialog(null,"Alterado!");

						}else{
							JOptionPane.showMessageDialog(null,retorno);
						}
					}
				}
				catch (Exception e) {
					// TODO: handle exception
				}
				PanelListagemTipoServišoModelo();
			}

		});

		JLabel lblTipoServišo = new JLabel("Tipo Servi\u00E7o");
		lblTipoServišo.setFont(new Font("Tahoma", Font.PLAIN, 17));

		lblModelo = new JLabel("Modelo");
		lblModelo.setFont(new Font("Tahoma", Font.PLAIN, 17));

		MBModelo mbModelo = MBModelo.getInstance();
		comboBoxModelo = new JComboBox<Modelo>();

		try {
			List<Modelo> listaModelo = mbModelo.listarModelos();
			Vector<Modelo> modelo = new Vector<Modelo>(listaModelo);
			comboBoxModelo.setModel(new DefaultComboBoxModel<Modelo>(modelo));

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}



		//DefaultComboBoxModel<Fornecedor>(mbFornecedor.listarFornecedores());
		comboBoxModelo.setFont(new Font("Tahoma", Font.PLAIN, 15));

		MBTipoServico mbTipoServico = MBTipoServico.getInstance();
		comboBoxTipoServišo = new JComboBox<TipoServico>();

		try {
			List<TipoServico> listaTipoServico = mbTipoServico.listarTipoServicos();
			Vector<TipoServico> tipoServico = new Vector<TipoServico>(listaTipoServico);
			comboBoxTipoServišo.setModel(new DefaultComboBoxModel<TipoServico>(tipoServico));
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		comboBoxTipoServišo.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel labelKm = new JLabel("KM");
		labelKm.setFont(new Font("Tahoma", Font.PLAIN, 17));

		textFieldKM = new JTextField();
		textFieldKM.setDocument(new IntegerDocument(6));



		textFieldKM.setColumns(10);

		JLabel labelData = new JLabel("Tempo");
		labelData.setFont(new Font("Tahoma", Font.PLAIN, 17));



		textFieldData = new JTextField();
		textFieldData.setDocument(new IntegerDocument(3));
		textFieldData.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
										.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(btnCancelar))
										.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
												.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
														.addGroup(groupLayout.createSequentialGroup()
																.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																		.addComponent(lblTipoServišo)
																		.addComponent(lblModelo)
																		.addComponent(labelKm, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
																		.addGap(18))
																		.addGroup(groupLayout.createSequentialGroup()
																				.addComponent(labelData, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
																				.addGap(55)))
																				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																						.addComponent(comboBoxModelo, 0, 429, Short.MAX_VALUE)
																						.addComponent(comboBoxTipoServišo, 0, 429, Short.MAX_VALUE)
																						.addComponent(textFieldKM, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
																						.addComponent(textFieldData, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)))
																						.addComponent(lblCadastroTipoServicoModelo))
																						.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblCadastroTipoServicoModelo)
						.addGap(57)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblTipoServišo)
								.addComponent(comboBoxTipoServišo, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(comboBoxModelo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblModelo))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(labelKm, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
												.addComponent(textFieldKM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(labelData, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
														.addComponent(textFieldData, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
														.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
																.addComponent(btnSalvar)
																.addComponent(btnCancelar))
																.addContainerGap())
				);
		setLayout(groupLayout);
		if (idModeloSelecionado>0 || idTipoServišoselecionado>0){
			MBTipoServišoModelo mbt = MBTipoServišoModelo.getInstance();

			try {
				TipoServicoModeloId id = new TipoServicoModeloId(new Integer(idTipoServišoselecionado), new Integer(idModeloSelecionado));
				TipoServicoModelo t = mbt.retornarTipoServicoModelo(id);
				textFieldData.setText(t.getTempo().toString());
				textFieldKM.setText(t.getKm().toString());

				boolean aux = false ;
				int  i=0; 

				while(aux==false){
					aux= mbModelo.listarModelos().get(i).getIdmodelo()==t.getModelo().getIdmodelo();
					if (aux==true) break; 
					i++;

				}
				comboBoxModelo.setSelectedIndex(i);
				i=0;
				aux = false;
				while(aux==false){
					aux= mbTipoServico.listarTipoServicos().get(i).equals(t.getTipoServico());
					if (aux==true) break; 
					i++;

				}
				comboBoxTipoServišo.setSelectedIndex(i);

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,"erro - "+e);
				// TODO: handle exception
			}

		}

	}

	public void PanelListagemTipoServišoModelo(){
		try {
			TelaPrincipal	parent = (TelaPrincipal)getParent().getParent().getParent();
			parent.PanelListagemTipoServišoModelo();
		} catch (Exception e) {
			try {
				TelaPrincipal	parent = (TelaPrincipal)getParent().getParent().getParent().getParent();
				parent.PanelListagemTipoServišoModelo();
			} catch (Exception e1) {
				TelaPrincipal	parent = (TelaPrincipal)getParent().getParent().getParent().getParent().getParent();
				parent.PanelListagemTipoServišoModelo();
			}
		}
	}

	public void AtualizarTipoServicosdosveiculos( TipoServicoModelo tipoServicoModelo){

		MBTipoServicoVeiculo mbTipoServicoVeiculo = MBTipoServicoVeiculo.getInstance();
		MBVeiculo mbVeiculo = MBVeiculo.getInstance();
		List<Veiculo> listaVeiculo= mbVeiculo.ListarosVeiculodoModelo(tipoServicoModelo.getModelo());
		for(int i = 0;i<listaVeiculo.size();i++){
			Veiculo v = listaVeiculo.get(i);
			TipoServico t = tipoServicoModelo.getTipoServico();
			TipoServicoVeiculoId tipoServicoVeiculoId = new TipoServicoVeiculoId(listaVeiculo.get(i).getIdveiculo(), tipoServicoModelo.getTipoServico().getIdtipoServico());
			TipoServicoVeiculo tipoServicoVeiculo = new TipoServicoVeiculo(tipoServicoVeiculoId,v , t, "OK");
			mbTipoServicoVeiculo.inserir(tipoServicoVeiculo);
		}


	}

}


