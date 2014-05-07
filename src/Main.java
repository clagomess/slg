
public class Main {
	public static void main(String[] args) {
		Slb slb = new Slb();
		slb.setIMG("C:\\_desenvolvimento\\_bagulho\\lerimg\\01.png");
		
		slb.imgToBit();
		slb.lerCabecalho();
		slb.ajustarIMG();
		slb.gravarBufferIMG();
	}
}
