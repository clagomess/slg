import javax.swing.UIManager;


public class Main {	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel (UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
		
		/*Slg slg = new Slg();
		
		slg.setDimencao(800);
		slg.setIMG("C:\\scan.jpg");
		
		slg.setBrilho(180);
		
		slg.ajustarIMG();
		slg.criarArquivoGabarito("C:\\gabarito.slg");
		slg.lerArquivoGabarito("C:\\gabarito.slg");*/
		
		//slg.gravarBufferIMG("C:\\buffer.jpg","jpg");
		//slg.getGabarito();
		//slg.bitToImg();
		//slg.gravarBufferIMG("C:\\buffer_bit.png","png");
		
		Ui ui = new Ui();
		ui.home();
		System.out.print("SLG - Versão: " + ui.versao());
	}
}
