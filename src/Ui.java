import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Ui {
	private JFrame getJanela(int w, int h, String titulo){
		JFrame returnJanela = new JFrame();
		returnJanela.setTitle("SLG - " + titulo);
		returnJanela.setSize(w,h);
		returnJanela.setResizable(false);
		returnJanela.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		returnJanela.setLocationRelativeTo(null);
		returnJanela.setIconImage(new ImageIcon("src/icon.png").getImage());  
		returnJanela.addWindowListener( new WindowAdapter( ){
			public void windowClosing(WindowEvent w){	
				System.exit(0);
			}
		});
		return returnJanela;
	}
	
	public void home(){
		JPanel panel;
		
		JFrame janela = this.getJanela(700, 540, "Sistema Leitor de Gabarito");
		janela.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Dados"));
		panel.setPreferredSize(new Dimension(670, 60));
		
		panel.add(new JLabel("Dimenção:"));

		final JTextField tDimencao = new JTextField(5);
		tDimencao.setText("400");
		panel.add(tDimencao);
		
		panel.add(new JLabel("Brilho:"));
		
		final JTextField tBrilho = new JTextField(5);
		tBrilho.setText("180");
		panel.add(tBrilho);
		
		final JLabel lOrigem = new JLabel("-");
		final JLabel lDestino = new JLabel("-");
		
		panel.add(new JLabel("Imagem Gabarito:"));
		JButton bArquivo = new JButton("Selecionar Arquivo");
		bArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				JFileChooser fArquivo = new JFileChooser("");
				fArquivo.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "png"));
				fArquivo.showOpenDialog(null);
				
				lOrigem.setText(fArquivo.getSelectedFile().getPath());
			}
		});
		
		panel.add(bArquivo);
		
		panel.add(new JLabel("Pasta Saida:"));
		JButton bPasta = new JButton("Selecionar Pasta");
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
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Ação"));
		panel.setPreferredSize(new Dimension(670, 60));		
		JButton bExecute = new JButton("Executar");
		bExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Slg slg = new Slg();
				
				slg.setDimencao(new Integer(tDimencao.getText()));
				slg.setBrilho(new Integer(tBrilho.getText()));
				
				if(slg.setIMG(lOrigem.getText())){				
					lImgOriginal.setIcon(new ImageIcon(slg.createThumb(200, 200)));
					
					slg.imgToBit();
					slg.lerCabecalho();
					slg.ajustarIMG();
					
					lImgCorrigida.setIcon(new ImageIcon(slg.createThumb(200, 200)));
					slg.gravarBufferIMG(lDestino.getText() + "/buffer.png","png");
					
					slg.bitToImg();
					lImgBit.setIcon(new ImageIcon(slg.createThumb(200, 200)));
					slg.gravarBufferIMG(lDestino.getText() + "/buffer_bit.png","png");
					
					JOptionPane.showMessageDialog(null, "Operação realizada com sucesso!");
				}
			}
		});
		panel.add(bExecute);
		janela.add(panel);
		
		janela.setVisible(true);
	}
	
	public void alert(String msg){
		JOptionPane.showMessageDialog(null, msg);
		System.out.println(msg);
	}
}
