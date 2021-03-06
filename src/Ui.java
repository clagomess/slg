import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;


public class Ui {
	public String versao(){
		String slg_versao = "[huebr]";
		String path = getClass().getResource("/resources/VERSAO").getPath();
		
		try{
			BufferedReader fversao = new BufferedReader(new FileReader(path));
			slg_versao = fversao.readLine();
			fversao.close();
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
		
		return slg_versao;
	}
	
	private JFrame getJanela(int w, int h, String titulo){
		JFrame returnJanela = new JFrame();
		returnJanela.setTitle("SLG - " + titulo);
		returnJanela.setSize(w,h);
		returnJanela.setResizable(false);
		returnJanela.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		returnJanela.setLocationRelativeTo(null);
		returnJanela.setIconImage(new ImageIcon(getClass().getResource("/resources/icon.png")).getImage());
		return returnJanela;
	}
	
	public void home(){
		JFrame janela = this.getJanela(700, 540, "Sistema Leitor de Gabarito");
		janela.addWindowListener( new WindowAdapter( ){
			public void windowClosing(WindowEvent w){	
				System.exit(0);
			}
		});
		janela.setJMenuBar(this.MenuComponente(janela));
		janela.setVisible(true);
	}
	
	public void dev(){
		JPanel panel;
		
		JFrame janela = this.getJanela(700, 540, "DEV");
		janela.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Dados"));
		panel.setPreferredSize(new Dimension(670, 60));
		
		panel.add(new JLabel("Dimensao:"));

		final JTextField tDimensao = new JTextField(5);
		tDimensao.setText("400");
		panel.add(tDimensao);
		
		panel.add(new JLabel("Brilho:"));
		
		final JTextField tBrilho = new JTextField(5);
		tBrilho.setText("180");
		panel.add(tBrilho);
		
		final JLabel lOrigem = new JLabel("-");
		final JLabel lDestino = new JLabel("-");
		
		panel.add(new JLabel("Gabarito:"));
		JButton bArquivo = new JButton("Selecionar");
		bArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				JFileChooser fArquivo = new JFileChooser("");
				fArquivo.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "png"));
				fArquivo.showOpenDialog(null);
				
				lOrigem.setText(fArquivo.getSelectedFile().getPath());
			}
		});
		
		panel.add(bArquivo);
		
		panel.add(new JLabel("Saida:"));
		JButton bPasta = new JButton("Selecionar");
		bPasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				JFileChooser fArquivo = new JFileChooser();
				fArquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fArquivo.showOpenDialog(null);
				
				lDestino.setText(fArquivo.getSelectedFile().getPath());
			}
		});
		
		panel.add(bPasta);
		janela.add(panel);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Src Imagem Gabarito:"));
		panel.setPreferredSize(new Dimension(670, 50));
		panel.add(lOrigem);
		janela.add(panel);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Src Pasta Saida:"));
		panel.setPreferredSize(new Dimension(670, 50));
		panel.add(lDestino);
		janela.add(panel);
		
		//BOTTOM		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		final JLabel lImgOriginal = new JLabel("");
		panel.add(lImgOriginal);
		panel.setPreferredSize(new Dimension(215, 200));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Original"));
		janela.add(panel);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		final JLabel lImgCorrigida = new JLabel("");
		panel.add(lImgCorrigida);
		panel.setPreferredSize(new Dimension(215, 200));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Corrigida"));
		janela.add(panel);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		final JLabel lImgBit = new JLabel("");
		panel.add(lImgBit);
		panel.setPreferredSize(new Dimension(215, 200));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "BIT"));
		janela.add(panel);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Acao"));
		panel.setPreferredSize(new Dimension(670, 60));		
		JButton bExecute = new JButton("Executar");
		bExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Slg slg = new Slg();
				
				slg.setDimencao(new Integer(tDimensao.getText()));
				slg.setBrilho(new Integer(tBrilho.getText()));
				
				if(slg.setIMG(lOrigem.getText())){				
					lImgOriginal.setIcon(new ImageIcon(slg.createThumb(200, 200)));
					
					slg.ajustarIMG();
					
					lImgCorrigida.setIcon(new ImageIcon(slg.createThumb(200, 200)));
					slg.gravarBufferIMG(lDestino.getText() + "/buffer.png","png");
					
					slg.bitToImg();
					lImgBit.setIcon(new ImageIcon(slg.createThumb(200, 200)));
					slg.gravarBufferIMG(lDestino.getText() + "/buffer_bit.png","png");
					
					JOptionPane.showMessageDialog(null, "Operacao realizada com sucesso!");
				}
			}
		});
		panel.add(bExecute);
		janela.add(panel);
		janela.setJMenuBar(this.MenuComponente(janela));
		janela.setVisible(true);
	}
	
	public void criargabarito(){
		JPanel panel;
		
		JFrame janela = this.getJanela(500, 280, "Criar Gabarito");
		janela.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Dados"));
		panel.setPreferredSize(new Dimension(470, 60));
		
		final JLabel lOrigem = new JLabel("-");
		final JLabel lDestino = new JLabel("-");
		
		panel.add(new JLabel("Gabarito:"));
		JButton bArquivo = new JButton("Selecionar");
		bArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				JFileChooser fArquivo = new JFileChooser("");
				fArquivo.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "png"));
				fArquivo.showOpenDialog(null);
				
				lOrigem.setText(fArquivo.getSelectedFile().getPath());
			}
		});
		
		panel.add(bArquivo);
		
		panel.add(new JLabel("Saida:"));
		JButton bPasta = new JButton("Selecionar");
		bPasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				JFileChooser fArquivo = new JFileChooser();
				fArquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fArquivo.showOpenDialog(null);
				
				lDestino.setText(fArquivo.getSelectedFile().getPath());
			}
		});
		
		panel.add(bPasta);
		janela.add(panel);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Src Imagem Gabarito:"));
		panel.setPreferredSize(new Dimension(470, 50));
		panel.add(lOrigem);
		janela.add(panel);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Src Pasta Saida:"));
		panel.setPreferredSize(new Dimension(470, 50));
		panel.add(lDestino);
		janela.add(panel);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		panel.setPreferredSize(new Dimension(470, 60));		
		JButton bExecute = new JButton("Executar");
		bExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Slg slg = new Slg();
				
				slg.setDimencao(800);
				slg.setIMG(lOrigem.getText());
				slg.setBrilho(180);
				slg.ajustarIMG();
				
				slg.criarArquivoGabarito(lDestino.getText() + "/gabarito.slg");
			}
		});
		panel.add(bExecute);
		janela.add(panel);
		
		janela.setVisible(true);
	}
	
	public void corrigirprovas(){
		JPanel panel;
		
		JFrame janela = this.getJanela(500, 500, "Corrigir Provas");
		janela.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Seleção"));
		panel.setPreferredSize(new Dimension(470, 60));
		
		JButton bProvas = new JButton("Selecionar Provas");
		final JFileChooser fProvas = new JFileChooser();
		fProvas.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "png"));
		fProvas.setMultiSelectionEnabled(true);
		
		JButton bGabarito = new JButton("Selecionar Gabarito");
		final JFileChooser fGabarito = new JFileChooser("");
		fGabarito.setFileFilter(new FileNameExtensionFilter("Gabarito", "slg"));
		
		JButton bSaida = new JButton("Pasta Saida");
		final JFileChooser fSaida = new JFileChooser();
		fSaida.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		panel.add(bProvas);
		panel.add(bGabarito);
		panel.add(bSaida);
		janela.add(panel);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Provas Selecionadas"));
		panel.setPreferredSize(new Dimension(470, 100));
		
		final JTextArea tProvas = new JTextArea(6,48);
		tProvas.setFont(new Font("Verdana", Font.PLAIN, 9));
		tProvas.setEditable(false);
		JScrollPane sProvas = new JScrollPane(tProvas);
		sProvas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(sProvas);	
		janela.add(panel);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Gabarito"));
		panel.setPreferredSize(new Dimension(470, 50));
		final JLabel lGabarito = new JLabel("-");
		panel.add(lGabarito);
		janela.add(panel);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Pasta de saida"));
		panel.setPreferredSize(new Dimension(470, 50));
		final JLabel lSaida = new JLabel("-");
		panel.add(lSaida);
		janela.add(panel);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Progresso"));
		panel.setPreferredSize(new Dimension(470, 60));
		
		final JProgressBar progresso = new JProgressBar(0, 100);
		//progresso.setStringPainted(true);
		progresso.setPreferredSize(new Dimension(450, 20));
		panel.add(progresso);
		janela.add(panel);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Ação"));
		panel.setPreferredSize(new Dimension(470, 60));
		JButton bExecutar = new JButton("Executar");
		panel.add(bExecutar);
		janela.add(panel);
		
		//# Ações
		bProvas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				fProvas.showOpenDialog(null);
				
				if(fProvas.getSelectedFiles().length > 0){
					Integer idx = 1;
					tProvas.setText("");
					String text = "";
					
					for(int i = 0; i < fProvas.getSelectedFiles().length; i++){
						text += idx.toString() + " - " + fProvas.getSelectedFiles()[i].getName() + "\n";
						idx++;
					}
					
					tProvas.setText(text);
				}
			}
		});
		
		bGabarito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fGabarito.showOpenDialog(null);
				lGabarito.setText(fGabarito.getSelectedFile().getPath());
			}
		});
		
		bSaida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fSaida.showOpenDialog(null);				
				lSaida.setText(fSaida.getSelectedFile().getPath());
			}
		});
		
		bExecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(fProvas.getSelectedFiles().length == 0){
					JOptionPane.showMessageDialog(null,"É necessário selecionar as provas!");
					return;
				}
				
				if(lGabarito.getText().equals("-")){
					JOptionPane.showMessageDialog(null,"É necessário selecionar o gabarito!");
					return;
				}
				
				if(lSaida.getText().equals("-")){
					JOptionPane.showMessageDialog(null,"É necessário selecionar a pasta de saida!");
					return;
				}
				
				String provas_corrigidas[][] = new String[fProvas.getSelectedFiles().length][3];
				
				for(int i = 0; i < fProvas.getSelectedFiles().length; i++){
					Slg slg = new Slg();					
					slg.setDimencao(800);
					slg.setIMG(fProvas.getSelectedFiles()[i].getPath());
					slg.setBrilho(180);
					slg.ajustarIMG();
					slg.gravarBufferIMG(lSaida.getText() + "/prova_" + fProvas.getSelectedFiles()[i].getName(),"jpg");
					int dados[] = slg.corrigirprova(slg.lerArquivoGabarito(fGabarito.getSelectedFile().getPath()));
					slg.gravarBufferIMG(lSaida.getText() + "/corrigida_" + fProvas.getSelectedFiles()[i].getName() + "_.png","png");
					
					//Progresso
					int percent = (int) Math.ceil((fProvas.getSelectedFiles().length * 100) / (i+1));
					progresso.setValue(percent);
					
					//Resultado
					provas_corrigidas[i][0] = new Integer((i+1)).toString();
					provas_corrigidas[i][1] = fProvas.getSelectedFiles()[i].getName();
					provas_corrigidas[i][2] = dados[0] + "/" + dados[1];
				}
				
				JOptionPane.showMessageDialog(null,"Operação realizada com sucesso!");
				Ui ui = new Ui();
				ui.resultadoCorrecao(provas_corrigidas);
			}
		});
		
		
		//# Fim Ações
		
		janela.setVisible(true);
	}
	
	public void sobre(){
		JFrame janela = this.getJanela(450, 280, "Sobre");
		janela.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		
		String html = "";
		html += "<html>";
		html += "<center>";
		html += "<h1>Sistema Leitor de Gabarito</h1>";
		html += "Cláudio Gomes - cla.gomess@gmail.com - gomespro.com.br<br/><br/>";
		html += "Licensa: (Ainda não definida)<br/><br/>";
		html += "Versão: " + this.versao();
		html += "</center>";
		html += "</html>";
		JLabel lHtml = new JLabel(html);
		
		janela.add(lHtml);		
		janela.setVisible(true);
	}
	
	public void resultadoCorrecao(String rs[][]){
		JPanel panel = new JPanel();;
		
		JFrame janela = this.getJanela(500, 500, "Resultado Provas");
		janela.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));

		JTable tResultado = new JTable(rs,new String []{"#","Prova","Nota"});
		
		JScrollPane sResultado = new JScrollPane(tResultado);
		sResultado.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		panel.setPreferredSize(new Dimension(480,480));
		panel.setLayout(new BorderLayout());
		panel.add(tResultado.getTableHeader(), BorderLayout.PAGE_START);
		panel.add(sResultado, BorderLayout.CENTER);
		
		janela.add(panel);
		janela.setVisible(true);
	}
	
	private JMenuBar MenuComponente (final JFrame janelaAberta){
		JMenuBar menuBarra = new JMenuBar();
		JMenu menuArquivo = new JMenu("Arquivo");
		JMenu menuProva = new JMenu("Prova");
		
		JMenuItem mCProva = new JMenuItem("Criar Prova");
		JMenuItem mCGabarito = new JMenuItem("Criar Gabarito");
		JMenuItem mCoProvas = new JMenuItem("Corrigir Provas");
		JMenuItem mSobre = new JMenuItem("Sobre");
		JMenuItem mSair = new JMenuItem("Sair");
		JMenuItem mDev = new JMenuItem("Dev");
		
		mCProva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//janelaAberta.setVisible(false);
				//Interface ui = new Interface();
				//ui.index();
			}
		});
		
		mCGabarito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ui ui = new Ui();
				ui.criargabarito();
			}
		});
		
		mCoProvas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ui ui = new Ui();
				ui.corrigirprovas();
			}
		});
		
		mSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ui ui = new Ui();
				ui.sobre();
			}
		});
		
		mDev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ui ui = new Ui();
				ui.dev();
			}
		});
		
		mSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		menuProva.add(mCProva);
		menuProva.add(mCGabarito);
		menuProva.add(mCoProvas);
		menuArquivo.add(mSobre);
		menuArquivo.add(mDev);
		menuArquivo.add(mSair);
		
		menuBarra.add(menuArquivo);
		menuBarra.add(menuProva);
		
		return menuBarra;
	}
	
	public void alert(String msg){
		JOptionPane.showMessageDialog(null, msg);
		System.out.println(msg);
	}
}
