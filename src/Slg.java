import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Slg {	
	private BufferedImage img;
	private int sizeX;
	private int sizeY;
	private int ponto[][] = new int[2][2];
	private int brilho = 150;
	private int dimencao = 400;
	private Ui ui = new Ui();
	private String log = "";
	
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log += log;
	}
	public int getDimencao() {
		return dimencao;
	}
	public void setDimencao(int dimencao) {
		this.dimencao = dimencao;
	}
	public int getBrilho() {
		return brilho;
	}
	public void setBrilho(int brilho) {
		this.brilho = brilho;
	}
	public BufferedImage getImg() {
		return img;
	}
	private void setImg(BufferedImage img) {
		this.img = img;
	}
	private int getSizeX() {
		return sizeX;
	}
	private void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}
	private int getSizeY() {
		return sizeY;
	}
	private void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}
	
	public boolean setIMG(String src){
		
		try{
			BufferedImage originalImg = ImageIO.read(new File(src));
			
			int n_width = this.getDimencao();
			int n_height = (originalImg.getHeight() * n_width) / originalImg.getWidth();
			
			BufferedImage resizedImg = new BufferedImage(n_width,n_height,BufferedImage.TYPE_BYTE_GRAY);
			Graphics2D g = resizedImg.createGraphics();
			g.drawImage(originalImg, 0, 0, n_width, n_height, null);
			g.dispose();
			
			this.setImg(resizedImg);
			this.setSizeX(resizedImg.getWidth());
			this.setSizeY(resizedImg.getHeight());
			return true;
		}catch(IOException e){
			ui.alert(e.getMessage());
			return false;
		}		
	}
	
	public void gravarBufferIMG(String src, String extensao){
		try{
			ImageIO.write(this.getImg(),extensao, new File(src));
		}catch(IOException e){
			ui.alert(e.getMessage());
		}
	}
	
	public int[][] imgToBit(){
		int pixel[][] = new int[this.getSizeY()][this.getSizeX()];
		
		for(int y = 0; y < this.getSizeY(); y++){
			for(int x = 0; x < this.getSizeX(); x++){
				pixel[y][x] = this.padraoCorPixel(this.getImg().getRGB(x, y));
			}
		}
		
		return pixel;
	}
	
	private int padraoCorPixel(int rgb){
		int r = (rgb >> 16) & 0xFF;
		int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        
        int bit = 0;
        
        if(r < this.getBrilho() && g < this.getBrilho() && b < this.getBrilho()){
        	bit = 1;
        }
        
        return bit;
	}
	
	private int xinicio = 0;
	private int xfim = 0;
	
	private void lerCabecalho(){
		int imgPixel[][] = this.imgToBit();
		
		boolean encontroubit = false;
		boolean fimcabecalho = false;
		
		// Onde: 0 = A, 1 = B
		// Onde: 0 = x, 1 = y
		this.ponto[0][0] = 0;
		this.ponto[0][1] = 0;
		this.ponto[1][0] = 0;
		this.ponto[1][1] = 0;
		
		int y = 0; //vai ser usado em todo while
		
		while(!fimcabecalho){
			
			fimcabecalho = true;
			
			for(int x = 0; x < this.getSizeX(); x++){
				if(!encontroubit){
					fimcabecalho = false;
					
					if(imgPixel[y][x] == 1){
						encontroubit = true;
						this.xinicio = x;
					}else{
						continue;
					}
				}
				
				if(encontroubit){
					//Ponto A
					if(this.ponto[0][1] == 0){
						this.ponto[0][1] = y;
						this.ponto[0][0] = x;
					}
					
					//Ponto B
					if(this.ponto[1][0] == 0){
						this.ponto[1][1] = y;
						this.ponto[1][0] = x;
					}
					
					if(imgPixel[y][x] == 1){
						fimcabecalho = false;
						
						if(x > this.ponto[1][0]){
							this.ponto[1][1] = y;
							this.ponto[1][0] = x;
						}
						this.xfim = x;
					}
				}
			}
			y++;
		}
		
		System.out.println(new Integer(xinicio).toString() + " - " + new Integer(xfim).toString());
		
		this.ponto[0][0] += 1;
		this.ponto[0][1] += 1;
		this.ponto[1][0] += 1;
		this.ponto[1][1] += 1;
		
		System.out.println("AX: " + new Integer(this.ponto[0][0]).toString());
		System.out.println("AY: " + new Integer(this.ponto[0][1]).toString());
		System.out.println("BX: " + new Integer(this.ponto[1][0]).toString());
		System.out.println("BY: " + new Integer(this.ponto[1][1]).toString());
	}
	
	public void ajustarIMG(){
		this.lerCabecalho();
		
		// Calcular o angulo do GIRO
		int catetoA;
		
		// Quando a reta for / inverter calculo do cateto
		if(this.xinicio > this.xfim){
			catetoA = this.ponto[0][0] - this.ponto[1][0];
		}else{
			catetoA = this.ponto[1][0] - this.ponto[0][0];
		}
		
		int catetoO = this.ponto[1][1] - this.ponto[0][1];
		
		double sin = catetoA / (Math.sqrt(Math.pow(catetoA, 2) + Math.pow(catetoO, 2)));
		double angulo = Math.toDegrees(Math.asin(sin));
		
		System.out.println("Angulo: " + new Double(angulo).toString());
		System.out.println("Cateto: " + new Double(catetoO).toString());
		
		//Calcula o Giro
		double giro;
		
		if(this.xinicio > this.xfim){
			giro = angulo * -1;
		}else{
			giro = (90 - angulo) * -1;
		}
		
		if(new Double(giro).isNaN()){
			giro = 0;
		}
		
		System.out.println("GIRO: " + new Double(giro).toString());
		
		//Rodando, Rodando		
		BufferedImage resizedImg = new BufferedImage(this.getSizeX(),this.getSizeY(),this.getImg().getType());
		Graphics2D g = resizedImg.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getSizeX(), this.getSizeY());
		g.rotate(Math.toRadians(giro));
		g.drawImage(this.getImg(), 0, 0, this.getSizeX(), this.getSizeY(), null);
		g.dispose();
		
		this.setImg(resizedImg);
	}
	
	public void bitToImg(){
		int pixel[][] = this.imgToBit();
		
		BufferedImage bufferimg = new BufferedImage(this.getSizeX(), this.getSizeY(), BufferedImage.TYPE_INT_ARGB);
		
		for(int y = 0; y < this.getSizeY(); y++){
			for(int x = 0; x < this.getSizeX(); x++){
				if(pixel[y][x] == 1){
					bufferimg.setRGB(x, y, Color.BLACK.getRGB());
				}else{
					bufferimg.setRGB(x, y, Color.WHITE.getRGB());
				}
			}
		}
		
		bufferimg.setRGB(this.ponto[0][0], this.ponto[0][1], Color.RED.getRGB());
		bufferimg.setRGB(this.ponto[1][0], this.ponto[1][1], Color.RED.getRGB());
		
		this.setImg(bufferimg);
	}
	
	public BufferedImage createThumb(int width, int height){
		int n_width, n_height;
		
		BufferedImage originalImg = this.getImg();
		
		n_width = width;
		n_height = (originalImg.getHeight() * n_width) / originalImg.getWidth();
		
		if(n_height > height){
			n_width = (originalImg.getWidth() * n_height) / originalImg.getHeight();
		}
		
		BufferedImage resizedImg = new BufferedImage(n_width,n_height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImg.createGraphics();
		g.drawImage(originalImg, 0, 0, n_width, n_height, null);
		g.dispose();
		
		return resizedImg;
	}
	
	private int[][] getGabarito (){
		// Pegando pontos da imagem redimencionada
		this.lerCabecalho();
		this.bitToImg();
		
		// Considerando que o cabeçalho equivale a 12 campos de largura
		int n_width = (24 * this.getSizeX()) / (this.ponto[1][0] - this.ponto[0][0]);
		System.out.println(n_width);
		
		// Redimencionando
		this.setImg(this.createThumb(n_width, 1000));
		this.setSizeX(this.getImg().getWidth());
		this.setSizeY(this.getImg().getHeight());
		
		// Pegando novos pontos
		this.lerCabecalho();
		
		// Varredura da imagem
		return this.imgToBit();
	}
	
	public String criarArquivoGabarito(String src){
		/* Composição:
		 * --- Cabecalho ---
		 * Identificador: 4 (char)
		 * n_questoes: 3 (int) pad 3 - '0'
		 * --- Fim Cabecalho 6 (char) ---
		 * Posições Questões: ^ 2(char)
		 */
		
		String file = "%SLG";
		String buffer = "";
		int pixel[][] = this.getGabarito();
		int n_questoes = 0;
		
		for(int y = (this.ponto[0][1] + 2); y < this.getSizeY(); y++){
			for(int x = (this.ponto[0][0] + 2); x < this.getSizeX(); x++){
				if(pixel[y][x] == 1){
					buffer += (char) (x - this.ponto[0][0]);
					buffer += (char) (y - this.ponto[0][1]);
					n_questoes ++;
				}
			}
		}
		
		file += String.format("%03d", n_questoes);
		file += buffer;
		
		try{
			File slg = new File(src);
			FileWriter fwrite = new FileWriter(slg);
			fwrite.write(file);
			fwrite.flush();
			fwrite.close();
		}catch(Exception e){
			ui.alert(e.getMessage());
		}
		
		return file;
	}
	
	public String[] lerArquivoGabarito(String src) {
		String buffer = "";
		String linha = "";
		
		try{
			BufferedReader slg = new BufferedReader(new FileReader(src));
			while ((linha = slg.readLine()) != null) {
				buffer += linha;
			}
			slg.close();
		}catch(IOException e){
			ui.alert(e.getMessage());
		}
		
		if(buffer.length() < 8){
			ui.alert("Não foi possivel ler o arquivo!");
			return null;
		}
		
		if(!buffer.substring(0, 4).equals("%SLG")){
			ui.alert("Arquivo não corresponde ao gabarito SLG ou está corrompido.");
			return null;
		}
		
		String posxfita = buffer.substring(7);
		String posx[] = new String[posxfita.length()];
		int posxid = 0;
		for(int i = 0; i < posxfita.length(); i++){
			posx[posxid] = "";
			posx[posxid] += posxfita.charAt(i);
			i++;
			posx[posxid] += posxfita.charAt(i);
			posxid++;
		}
		
		return posx;
	}
}
