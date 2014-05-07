import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Slb {	
	private BufferedImage img;
	private int sizeX;
	private int sizeY;
	private int ponto[][] = new int[2][2];
	
	private BufferedImage getImg() {
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
	
	public void setIMG(String src){
		
		try{
			BufferedImage originalImg = ImageIO.read(new File(src));
			int type = (originalImg.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImg.getType());
			
			int n_width = 400;
			int n_height = (originalImg.getHeight() * n_width) / originalImg.getWidth();
			
			BufferedImage resizedImg = new BufferedImage(n_width,n_height,type);
			Graphics2D g = resizedImg.createGraphics();
			g.drawImage(originalImg, 0, 0, n_width, n_height, null);
			g.dispose();
			
			this.setImg(resizedImg);
			this.setSizeX(resizedImg.getWidth());
			this.setSizeY(resizedImg.getHeight());
			
		}catch(IOException e){
			System.out.print(e.getMessage());
		}		
	}
	
	public void gravarBufferIMG(){
		try{
			ImageIO.write(this.getImg(),"jpg", new File("c:\\buffer.jpg"));
		}catch(IOException e){
			System.out.print(e.getMessage());
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
        
        int claridade = 200;
        int bit = 0;
        
        if(r < claridade && g < claridade && b < claridade){
        	bit = 1;
        }
        
        return bit;
	}
	
	public void lerCabecalho(){
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
		
		boolean reverso = false;
		
		while(!fimcabecalho){
			
			fimcabecalho = true;
			
			for(int x = 0; x < this.getSizeX(); x++){
				if(!encontroubit){
					fimcabecalho = false;
					
					if(imgPixel[y][x] == 1){
						encontroubit = true;
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
						
						System.out.println(new Integer(x).toString());
					}
				}
			}
			
			y++;
		}
		
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
		// Calcular o angulo do GIRO
		int catetoA = this.ponto[1][0] - this.ponto[0][0];
		int catetoO = this.ponto[1][1] - this.ponto[0][1];
		
		double sin = catetoA / (Math.sqrt(Math.pow(catetoA, 2) + Math.pow(catetoO, 2)));
		double angulo = Math.toDegrees(Math.asin(sin));
		
		System.out.println("Angulo: " + new Double(angulo).toString());
		System.out.println("Cateto: " + new Double(catetoO).toString());
		
		//Calcula o Giro
		double giro;
		if(catetoO < 0){
			giro = 90 - angulo; //Reta /
			System.out.println("Reta: /");
		}else{
			giro = (90 -angulo) * -1; //Reta \
			System.out.println("Reta: \\");
		}
		
		System.out.println("GRAU: " + new Double(giro).toString());
		
		//Rodando, Rodando		
		BufferedImage resizedImg = new BufferedImage(this.getSizeX(),this.getSizeY(),this.getImg().getType());
		Graphics2D g = resizedImg.createGraphics();
		g.rotate(Math.toRadians(giro));
		g.drawImage(this.getImg(), 0, 0, this.getSizeX(), this.getSizeY(), null);
		g.dispose();
		
		this.setImg(resizedImg);
	}
}
