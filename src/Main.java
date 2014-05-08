import javax.swing.UIManager;


public class Main {
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel (UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			
		}
		/*
		Slg slg = new Slg();
		
		slg.setDimencao(200);
		slg.setIMG("C:\\_desenvolvimento\\_bagulho\\lerimg\\03.png");
		
		slg.setBrilho(210);
		
		slg.imgToBit();
		slg.lerCabecalho();
		slg.ajustarIMG();
		
		slg.gravarBufferIMG("C:\\buffer.jpg","jpg");
		slg.bitToImg();
		slg.gravarBufferIMG("C:\\buffer_bit.png","png");*/
		
		Ui ui = new Ui();
		ui.home();
	}
}
