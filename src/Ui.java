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
		
	}
	
	public void sobre(){		
		JFrame janela = this.getJanela(350, 280, "Sobre");
		janela.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		
		String html = "";
		html += "<html>";
		html += "<center>";
		html += "<h4>Sistema Leitor de Gabarito</h4>";
		html += "Cláudio Gomes - cla.gomess@gmail.com - gomespro.com.br<br/><br/>";
		html += "Versão: " + this.versao();
		html += "</center>";
		html += "</html>";
		JLabel lHtml = new JLabel(html);
		
		janela.add(lHtml);		
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
				//janelaAberta.setVisible(false);
				//Interface ui = new Interface();
				//ui.pesquisarVisitantes();
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
