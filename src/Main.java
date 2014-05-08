
public class Main {
	public static void main(String[] args) {
		Slg slg = new Slg();
		slg.setIMG("/home/claudio/01.jpg");
		
		slg.imgToBit();
		slg.lerCabecalho();
		slg.ajustarIMG();
		slg.gravarBufferIMG();
	}
}
