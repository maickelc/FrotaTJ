package Visao;
import javax.persistence.Query;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import mb.MBMotorista;
import mb.MBServico;
import mb.MBTipoServicoVeiculo;
import mb.MBUnidade;
import mb.MBVeiculo;

import dao.EntityManagerHelper;
import dao.Motorista;
import dao.Servico;
import dao.TipoServicoVeiculo;
import dao.Unidade;
import dao.Veiculo;
import dao.VeiculoDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;

import util.Filtros;
import util.Pesquisa;
import util.UsuarioUtil;


public class PanelListagemVeiculo extends PanelExemplo {
	private JTable table;
	private int idVeiculoSelecionado;
	private JTextField textFieldPlaca;
	private JComboBox<String> comboBoxUnidade;
	private JComboBox<String> comboBoxSituacao;
	private JComboBox<String> comboBoxMotorista;
	private String unidade;	
	String pendencia;

	final MBVeiculo mbVeiculo = MBVeiculo.getInstance();
	final MBUnidade mbUnidade = MBUnidade.getInstance();
	final MBMotorista mbMotorista = MBMotorista.getInstance();
	final MBTipoServicoVeiculo mbTipoServicoVeiculo = MBTipoServicoVeiculo.getInstance();

	/**
	 * Create the panel.
	 */
	public PanelListagemVeiculo() {	
		final UsuarioUtil usuarioLogado = UsuarioUtil.getInstance();

		// ------------------- Label -----------------------\\
		//setarUnidade();		
		JLabel lblListagemVeiculos = new JLabel("Listagem de Veiculos\r\n");
		lblListagemVeiculos.setIcon(new ImageIcon("imagens\\1519_32x32.png"));
		lblListagemVeiculos.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel lblPlaca = new JLabel("Placa");
		lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblSituacao = new JLabel("Situa\u00E7\u00E3o");
		lblSituacao.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblMotrista = new JLabel("Motrista");
		lblMotrista.setFont(new Font("Tahoma", Font.PLAIN, 14));

		textFieldPlaca = new JTextField();
		textFieldPlaca.setColumns(10);

		JLabel lblUnidade = new JLabel("Unidade");
		lblUnidade.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JScrollPane scrollPane = new JScrollPane();

		//------------------------- Bot�es ----------------------------\\
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setIcon(new ImageIcon("imagens\\1408_16x16.png"));
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((DefaultTableModel)table.getModel()).setRowCount(0);				
				List<Veiculo> listaVeiculo = new ArrayList<Veiculo>();

				for (int i=0; i<table.getRowCount(); i++){
					((DefaultTableModel)table.getModel()).removeRow(i);
				}

				listaVeiculo = findParametrizado(textFieldPlaca.getText(), comboBoxUnidade.getItemAt(comboBoxUnidade.getSelectedIndex()), comboBoxMotorista.getItemAt(comboBoxMotorista.getSelectedIndex()), comboBoxSituacao.getItemAt(comboBoxSituacao.getSelectedIndex()));

				for (int i=0; i<listaVeiculo.size(); i++){
					((DefaultTableModel)table.getModel()).addRow(new String[]{
							listaVeiculo.get(i).getIdveiculo()+"", 
							listaVeiculo.get(i).getPlaca(), listaVeiculo.get(i).getrenavan(), listaVeiculo.get(i).getChassi(),
							listaVeiculo.get(i).getOdometro().toString(), listaVeiculo.get(i).getSituacao(), listaVeiculo.get(i).getModelo().getNome(),
							listaVeiculo.get(i).getUnidade().getNome(),	listaVeiculo.get(i).getMotorista().getNome()});

				}

				/*((DefaultTableModel)table.getModel()).setRowCount(0);				
				ArrayList<Veiculo> listaVeiculo = new ArrayList<>();

				for (int i=0; i<table.getRowCount(); i++){
					((DefaultTableModel)table.getModel()).removeRow(i);
				}

				try {
					listaVeiculo.addAll(mbVeiculo.listarVeiculos());

					for (int i=0; i<listaVeiculo.size()-1; i++){
						if(listaVeiculo.get(i).getPlaca().equals(textFieldPlaca.getText()) &&
						listaVeiculo.get(i).getUnidade().getNome().equals(comboBoxUnidade.getSelectedItem().toString()) &&
						listaVeiculo.get(i).getSituacao().equals(comboBoxSituacao.getSelectedItem().toString()) && 
						listaVeiculo.get(i).getMotorista().getNome().equals(comboBoxMotorista.getSelectedItem().toString())){

						((DefaultTableModel)table.getModel()).addRow(new String[]{
									listaVeiculo.get(i).getIdveiculo()+"", 
									listaVeiculo.get(i).getPlaca(), listaVeiculo.get(i).getrenavan(), listaVeiculo.get(i).getChassi(),
									listaVeiculo.get(i).getOdometro().toString(), listaVeiculo.get(i).getSituacao(), listaVeiculo.get(i).getModelo().getNome(),
									listaVeiculo.get(i).getUnidade().getNome(),	listaVeiculo.get(i).getMotorista().getNome()});

						}
					}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		});
		btnPesquisar.setFont(new Font("Tahoma", Font.PLAIN, 15));		

		JButton btnNovo = new JButton("Novo");
		btnNovo.setIcon(new ImageIcon("imagens\\8391_16x16.png"));
		btnNovo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelCadastroVeiculo(0);
			}
		});

		final JButton btnDetalhes = new JButton("Detalhes");
		btnDetalhes.setIcon(new ImageIcon("imagens\\8390_16x16.png"));
		btnDetalhes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDetalhes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaDetalhesVeiculo t = new TelaDetalhesVeiculo(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0)+""));
				t.show();
			}
		});


		final JButton btnApagar = new JButton("Apagar");
		btnApagar.setIcon(new ImageIcon("imagens\\7464_32x32.png"));
		btnApagar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Veiculo v = mbVeiculo.retornarVeiculo(idVeiculoSelecionado);
					int op = JOptionPane.showConfirmDialog(null,"Deseja realmente apagar o Ve�culo selecionado?");
					if (op==JOptionPane.YES_OPTION ) {


						JOptionPane.showMessageDialog(null,mbVeiculo.apagar(v));
						atualizarTabela();
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,"erro - "+e1);
					// TODO: handle exception
				}
			}
		});

		final JButton btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon("imagens\\8427_16x16.png"));
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				idVeiculoSelecionado = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0)+"");
				PanelCadastroVeiculo(idVeiculoSelecionado);
			}
		});


		//-------------------------- ComboBox -----------------------------------\\

		comboBoxUnidade = new JComboBox<String>();
		comboBoxUnidade.setFont(new Font("Tahoma", Font.PLAIN, 15));
		DefaultComboBoxModel<String> modelComboBoxUnidade;
		final Vector<Unidade> listaUnidade = new Vector<>();
		final Vector<String> listaNomeUnidade = new Vector<>();

		try {
			listaUnidade.addAll(mbUnidade.listarUnidades());

			listaNomeUnidade.add("Selecionar");
			for (int i = 0; i<listaUnidade.size();i++){
				listaNomeUnidade.add(listaUnidade.get(i).getNome());
			}
			modelComboBoxUnidade = new DefaultComboBoxModel<String>(listaNomeUnidade);
			comboBoxUnidade.setModel(modelComboBoxUnidade);
		} catch (Exception e) {
			// TODO: handle exception
		}

		comboBoxSituacao = new JComboBox<String>();
		comboBoxSituacao.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxSituacao.addItem("Selecionar");
		comboBoxSituacao.addItem("OK");
		comboBoxSituacao.addItem("A Fazer");
		comboBoxSituacao.addItem("Atrasado");


		comboBoxMotorista = new JComboBox<String>();
		comboBoxMotorista.setFont(new Font("Tahoma", Font.PLAIN, 15));
		DefaultComboBoxModel<String> modelComboBoxMotorista;
		final Vector<Motorista> listaMotorista = new Vector<>();
		final Vector<String> listaNomeMotorista = new Vector<>();
		try {
			listaMotorista.addAll(mbMotorista.listarMotoristas());

			listaNomeMotorista.add("Selecionar");
			for (int i = 0; i<listaMotorista.size();i++){
				listaNomeMotorista.add(listaMotorista.get(i).getNome());
			}
			modelComboBoxMotorista = new DefaultComboBoxModel<String>(listaNomeMotorista);
			comboBoxMotorista.setModel(modelComboBoxMotorista);
		} catch (Exception e) {
			// TODO: handle exception
		}


		//----------------------------Layout do Panel ----------------------------\\
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
										.addComponent(btnNovo)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnDetalhes)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnEditar)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnApagar))
										.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
												.addComponent(lblPlaca, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(textFieldPlaca, GroupLayout.DEFAULT_SIZE, 6, Short.MAX_VALUE)
												.addGap(18)
												.addComponent(lblUnidade, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(comboBoxUnidade, 0, 4, Short.MAX_VALUE)
												.addGap(18)
												.addComponent(lblMotrista, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(comboBoxMotorista, 0, 4, Short.MAX_VALUE)
												.addGap(18)
												.addComponent(lblSituacao, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(comboBoxSituacao, 0, 12, Short.MAX_VALUE)
												.addGap(40)
												.addComponent(btnPesquisar))
												.addComponent(lblListagemVeiculos))
												.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblListagemVeiculos)
										.addGap(28)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblPlaca, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
														.addComponent(textFieldPlaca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblUnidade, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
														.addComponent(comboBoxUnidade, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblMotrista, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
														.addComponent(comboBoxMotorista, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
														.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
																.addComponent(lblSituacao, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
																.addComponent(comboBoxSituacao, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))))
																.addGroup(groupLayout.createSequentialGroup()
																		.addGap(70)
																		.addComponent(btnPesquisar, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))
																		.addGap(18)
																		.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
																		.addGap(18)
																		.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
																				.addComponent(btnNovo)
																				.addComponent(btnApagar)
																				.addComponent(btnEditar)
																				.addComponent(btnDetalhes))
																				.addContainerGap())
				);

		if (!usuarioLogado.ehAdministrador()){
			btnNovo.setVisible(false);
			btnEditar.setVisible(false);
			btnApagar.setVisible(false);
			btnDetalhes.setVisible(false);
		}else{
			btnNovo.setVisible(true);
			btnEditar.setVisible(false);
			btnApagar.setVisible(false);
			btnDetalhes.setVisible(false);
		}


		//--------------------------------------Tabela ------------------------------\\
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if(usuarioLogado.ehAdministrador()){
					idVeiculoSelecionado = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0)+"");
					btnEditar.setVisible(true);
					btnApagar.setVisible(true);

				}
				btnDetalhes.setVisible(true);

			}
		});
		table.setModel(new DefaultTableModel(
				new Object[][] {
						{null, null, null, null, null, null, null, null, null},
				},
				new String[] {
						"Id", "Placa", "Renavan", "Chassi", "Odometro", "Situa\u00E7\u00E3o", "Modelo", "Unidade", "Motorista"
				}
				));
		scrollPane.setViewportView(table);

		//--------------------------------Atualizando a Tabela ---------------------------\\	
		try {
			//pintarTabela();
			//pendencias();
			atualizarTabela();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setLayout(groupLayout);

	}

	//-----------------------------------M�todos ---------------------------------\\
	public void PanelCadastroVeiculo(int id){
		try {
			TelaPrincipal	parent = (TelaPrincipal)getParent().getParent().getParent();
			parent.PanelCadastroVeiculo(id);
		} catch (Exception e) {
			try {
				TelaPrincipal	parent = (TelaPrincipal)getParent().getParent().getParent().getParent();
				parent.PanelCadastroVeiculo(id);
			} catch (Exception e1) {
				TelaPrincipal	parent = (TelaPrincipal)getParent().getParent().getParent().getParent().getParent();
				parent.PanelCadastroVeiculo(id);
			}
		}
	}

	public void PanelInicial(){
		try {
			TelaPrincipal	parent = (TelaPrincipal)getParent().getParent().getParent();
			parent.PanelInicial();
		} catch (Exception e) {
			TelaPrincipal	parent = (TelaPrincipal)getParent().getParent().getParent().getParent();
			parent.PanelInicial();
		}
	}

	/*public void setarUnidade(){
		try {
			TelaPrincipal	parent = (TelaPrincipal)getParent().getParent();
			this.unidade = parent.retornarUnidadeSelecionada();
		} catch (Exception e) {
			TelaPrincipal	parent = (TelaPrincipal)getParent().getParent().getParent();
			this.unidade = parent.retornarUnidadeSelecionada();
		}
	}*/

	public void pendencias() throws ClassNotFoundException, SQLException{
		List<Veiculo> listaVeiculo = mbVeiculo.listarVeiculos();
		//List<Veiculo> listaVeiculo = mbVeiculo.listarVeiculosPorUnidade(Filtros.getIdUnidadeSelecionada());
		for (int i=0;i<listaVeiculo.size();i++){
			List<TipoServicoVeiculo> listaTipoServicoVeiculo = mbTipoServicoVeiculo.ListarosTipoServicoVeiculo(Integer.parseInt(table.getValueAt(i, 0).toString()));
			for (int j=0; j<listaTipoServicoVeiculo.size(); j++){
				if (listaTipoServicoVeiculo.get(i).getSituacao().equals("A Fazer")){
					pendencia = pendencia+listaTipoServicoVeiculo.get(i).getTipoServico().getNome()+", ";
				}else if (listaTipoServicoVeiculo.get(i).getSituacao().equals("Atrasado")) {
					pendencia = pendencia+listaTipoServicoVeiculo.get(i).getTipoServico().getNome()+", ";
				}
			}
		}
	}

	public void atualizarTabela() throws ClassNotFoundException, SQLException{
		((DefaultTableModel)table.getModel()).setRowCount(0);
		//List<Veiculo> listaVeiculo = mbVeiculo.listarVeiculos();

		MBUnidade mbUnidade = MBUnidade.getInstance();
		Unidade unidade = mbUnidade.retornarUnidade(Filtros.getIdUnidadeSelecionada());
		List<Veiculo> listaVeiculo = mbVeiculo.VeiculosUnidade(unidade);

		for (int i=0;i<listaVeiculo.size();i++){
			((DefaultTableModel)table.getModel()).addRow(new String[]{
					listaVeiculo.get(i).getIdveiculo()+"", 
					listaVeiculo.get(i).getPlaca(), listaVeiculo.get(i).getrenavan(), listaVeiculo.get(i).getChassi(),
					listaVeiculo.get(i).getOdometro().toString(), listaVeiculo.get(i).getSituacao(), listaVeiculo.get(i).getModelo().getNome(),
					listaVeiculo.get(i).getUnidade().getNome(),	listaVeiculo.get(i).getMotorista().getNome()});
		}

		/*
		 		((DefaultTableModel)table.getModel()).setRowCount(0);
		List<TipoServicoVeiculo> listaTipoServicoVeiculo = mbTipoServicoVeiculo.ListarosTipoServicoVeiculo(idVeiculoSelecionado);
		System.out.println(listaTipoServicoVeiculo);
		System.out.println(idVeiculoSelecionado);
		for (int i=0;i<listaTipoServicoVeiculo.size();i++){
			((DefaultTableModel)table.getModel()).addRow(new String[]{listaTipoServicoVeiculo.get(i).getTipoServico().getNome(), listaTipoServicoVeiculo.get(i).getSituacao()});
		}
		 */
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {  

			public Component getTableCellRendererComponent(JTable table, Object value,  
					boolean isSelected, boolean hasFocus, int row, int column) {  
				super.getTableCellRendererComponent(table, value, isSelected,  
						hasFocus, row, column);  

					// para definir cores para a linha da tabela de acordo com a situacao do servico
				int ama ;
				if (table.getValueAt(row,5) == null){
					ama = 0;
				}else{
					String cor =  table.getValueAt(row, 5).toString();
	
					ama = "verde".compareToIgnoreCase(cor);
				}
					if (ama ==-9) {  
						setBackground(Color.RED);
						setForeground(Color.WHITE);
					} 
					else if (ama ==21) {  
						setBackground(Color.YELLOW);
						setForeground(Color.BLACK);
					} 
					else if(ama==0) {  
						setBackground(Color.GREEN);
						setForeground(Color.BLACK);

					}
					else{  
						setBackground(null);
						setForeground(null);
					}	
	

				return this;  
			}  
		});
	}

	public void pintarTabela() throws ClassNotFoundException, SQLException{
		for (int i=0; i<table.getRowCount(); i++){
			table.getCellRenderer(i, 5);
		}

	}


	//---------------------------PESQUISA PARAMETRIZADA ----------------------\\

	@SuppressWarnings("unchecked")
	public List<Veiculo> findParametrizado(String param1, String param2, String param3, String param4) {
		EntityManagerHelper.log("finding all abastecimento instances",
				Level.INFO, null);
		System.out.println(param2);
		System.out.println(param3);
		System.out.println(param4);
		if (param2.equals("Selecionar") ){
			param2 = "";
		}
		if (param3.equals("Selecionar") ){
			param3 = "";
		}
		if (param4.equals("Selecionar") ){
			param4 = "";
		}

		try {
			String queryString = "select * from veiculo ";
			boolean temWhere=false;
			if (param1.length()>0){
				queryString += "where placa='"+param1+"'";
				temWhere=true;
			}
			if (param2.length()>0){
				if (temWhere){
					queryString+="and";
				}else{
					queryString += " where ";
				}
				queryString += " unidade_idunidade='"+param2+"'";
				temWhere=true;
			}
			if (param3.length()>0){
				if (temWhere){
					queryString+="and";
				}else{
					queryString += " where ";
				}
				queryString += " motorista_idmotorista='"+param3+"'";
				temWhere=true;
			}
			if (param4.length()>0){
				if (temWhere){
					queryString+="and";
				}else{
					queryString += " where ";
				}
				queryString += " situacao='"+param4+"'";
				temWhere=true;
			}	
			queryString+=";";
			System.out.println(queryString);

			Query query = EntityManagerHelper.getEntityManager().createNativeQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}
	/*

	public void atualizarTabela() throws ClassNotFoundException, SQLException{
		((DefaultTableModel)table.getModel()).setRowCount(0);
		List<Veiculo> listaVeiculo = mbVeiculo.listarVeiculos();
		for (int i=0;i<listaVeiculo.size();i++){
			if(listaVeiculo.get(i).getUnidade().getNome().equals(this.unidade) || unidade.equals("Selecione uma Unidade")){
				((DefaultTableModel)table.getModel()).addRow(new String[]{
						listaVeiculo.get(i).getIdveiculo()+"", 
						listaVeiculo.get(i).getPlaca(), listaVeiculo.get(i).getrenavan(), listaVeiculo.get(i).getChassi(),
						listaVeiculo.get(i).getOdometro().toString(), listaVeiculo.get(i).getSituacao(), listaVeiculo.get(i).getModelo().getNome(),
						listaVeiculo.get(i).getUnidade().getNome(),	listaVeiculo.get(i).getMotorista().getNome()});
			}
		}

	}

	 */
}

