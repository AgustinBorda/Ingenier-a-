import java.util.Arrays;
public class Reinas{
	private int[] filas; //tablero
}
	public Reinas(){
		filas = new int[8];

	public static boolean chequeaColumnas(int[] filas){
		int i = 0;
		while (i<8){
			for(int j = i+1; j<8;j++){
				if (filas[i] == filas[j]){
					return false;
				}
			}
			i++;
		}
		return true;
	}

	public static boolean diagonales(int[] tablero){
		for(int i = 0;i<8;i++){
			for(int j=1;j<8;j++){
				if(i-j >= 0 && i-j <=7){
					if(tablero[i-j]+j == tablero[i] || tablero[i-j]-j == tablero[i]){
						return false;
					}
				}
				if(i+j >= 0 && i+j <=7){
					if(tablero[i+j]+j == tablero[i] || tablero[i+j]-j == tablero[i]){
						return false;
					}
				}
			}
		}
		return true;
	}

	public String ochoReinas (){
		String res = new String();
		res = "";
		for (int i = 0; i<8;i++){
			this.filas[i] = i;
			for (int j = 0; j<8;j++){
				this.filas[j] = j;
				for (int k = 0; k<8;k++){
					this.filas[k] = k;
					for (int l = 0; l<8;l++){
						this.filas[l] = l;
						for (int m = 0; m<8;m++){
							this.filas[m] = m;
							for (int n = 0; n<8;n++){
								this.filas[n] = n;
								for (int o = 0; o<8;o++){
									this.filas[o] = o;
									for (int p = 0; p<8;p++){
										this.filas[p] = p;
										if (this.chequeaColumnas() && this.diagonales()){
											res+= Arrays.toString(this.filas) + " ";
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return res;
	}

}
