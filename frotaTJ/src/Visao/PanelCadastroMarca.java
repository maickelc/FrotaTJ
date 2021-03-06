package Visao;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import dao.Marca;
import mb.MBMarca;
import javax.swing.ImageIcon;

public class PanelCadastroMarca extends PanelExemplo {
	private JTextField textFieldNome;

	/**
	 * Create the panel.
	 */
	public PanelCadastroMarca(final int idMarcaSelecionado) {

		JLabel lblCadastroMarca = new JLabel("Cadastro de Marcas");
		lblCadastroMarca.setIcon(new ImageIcon("imagens\\M.jpg"));
		lblCadastroMarca.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon("imagens\\7464_32x32.png"));
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelListagemMarca();
			}
		});

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon("imagens\\7484_16x16.png"));
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MBMarca mbMarca = MBMarca.getInstance();

				
				Marca m;
				try {
					System.out.println(idMarcaSelecionado);
					if ( idMarcaSelecionado < 0 ){ 					// Vem da tela de cadastro de modelo

						Marca marca = new Marca( 0, textFieldNome.getText());
						marca.setIdmarca(null);
						String retorno = mbMarca.inserir(marca);
						if (retorno.equals("ok")){

							JOptionPane.showMessageDialog(null,"Marca inserido!");

						}else{
							JOptionPane.showMessageDialog(null,retorno);
						}
						
						PanelCadastroModelo(textFieldNome.getText());

					}else{
						m =  new Marca(idMarcaSelecionado, textFieldNome.getText());
						if (idMarcaSelecionado==0){
							if (m.getIdmarca()==0){
								m.setIdmarca(null);
							}
							String retorno = mbMarca.inserir(m);
							if (retorno.equals("ok")){

								JOptionPane.showMessageDialog(null,"Marca inserido!");

							}else{
								JOptionPane.showMessageDialog(null,retorno);
							}
						}else{

							String retorno =  mbMarca.editar(m);
							if (retorno.equals("ok")){
								JOptionPane.showMessageDialog(null,"Marca alterado!");

							}else{
								JOptionPane.showMessageDialog(null,retorno);
							}
						}
						PanelListagemMarca();	
					}
				
				} catch (Exception e) {
					// TODO: handle exception
				}
			}});

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 17));

		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldNome.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
										.addComponent(lblNome)
										.addGap(6)
										.addComponent(textFieldNome, GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE))
										.addComponent(lblCadastroMarca)
										.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
												.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnCancelar)))
												.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblCadastroMarca)
						.addGap(61)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNome)
								.addComponent(textFieldNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnCancelar)
										.addComponent(btnSalvar))
										.addContainerGap())
				);
		setLayout(groupLayout);

		if (idMarcaSelecionado>0){
			MBMarca mbMarca = MBMarca.getInstance();

			try {
				Marca m = mbMarca.retornarMarca(idMarcaSelecionado);
				textFieldNome.setText(m.getNome());

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,"erro - "+e);
				// TODO: handle exception
			}

		}

		}
		public void PanelListagemMarca(){
			try {
				TelaPrincipal	parent = (TelaPrincipal)getParent().getParent().getParent();
				parent.PanelListagemMarca();
			} catch (Exception e) {
				try {
					TelaPrincipal	parent = (TelaPrincipal)getParent().getParent().getParent().getParent();
					parent.PanelListagemMarca();
				} catch (Exception e1) {
					TelaPrincipal	parent = (TelaPrincipal)getParent().getParent().getParent().getParent().getParent();
					parent.PanelListagemMarca();
				}
			}
		}
		
		public void PanelCadastroModelo(String marca){
			try {
				System.out.println("Castro modelo");
				TelaPrincipal	parent = (TelaPrincipal)getParent().getParent().getParent();
				parent.PanelCadastroModelo(0, marca);
			} catch (Exception e) {
				try{
					TelaPrincipal	parent = (TelaPrincipal)getParent().getParent().getParent().getParent();
					parent.PanelCadastroModelo(0, marca);
				} catch (Exception e1) {
					TelaPrincipal	parent = (TelaPrincipal)getParent().getParent().getParent().getParent().getParent();
					parent.PanelCadastroModelo(0, marca);
				}
			}
		}
	}
