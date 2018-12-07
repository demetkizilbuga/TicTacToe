/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

/**
 *
 * @author demetkizilbuga
 */
public class OyunTahtasi {
    char[][] oynTahtasi; 
    int boyut; 
    boolean finish = false; // oyunun bitip bitmediğini belirtir. True ise oyun bitmiştir.

    public OyunTahtasi(int n) { 
        boyut = n;
        oynTahtasi = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n ; j++) {
                oynTahtasi[i][j] = '-';
            }
        }
    }

    public OyunTahtasi(char[][] oynTahtasi) { 
        boyut = oynTahtasi.length; 
        this.oynTahtasi = oynTahtasi; 
    }

    public char[][] getOynTahtasi() {
        return oynTahtasi;
    } 

    public void oyunTahtasiniYazdir(){ 
        for (int i = 0; i < boyut; i++) { 
            System.out.print("   " + i);
        }
        System.out.println(""); 

        for (int i = 0; i < boyut ; i++) {
            System.out.print(i + "| "); 
            for (int j = 0; j < boyut ; j++) {
                System.out.print(oynTahtasi[i][j] + "   "); 
            }
            System.out.println(""); 
        }
        System.out.println("-------------------------------------------");
    }

    boolean hamleyiYaz(String koordinat,char oyuncu){ // Gelen koordinat değerine oyuncu yani X yada O değerini yazar
        int x ,y;
        try {
            
           x = Integer.parseInt(koordinat.split(",")[0]); 
           y = Integer.parseInt(koordinat.split(",")[1]);  
            
        }catch (Exception e){ 
            System.out.println("Koordinat değerleri x,y şeklinde girilmelidir ! \n"); 
            return false; 
        }

        try {
            if (oynTahtasi[x][y] == '-') { 
                oynTahtasi[x][y] = oyuncu; 
                return true; 
            } else {
                System.out.println("Boş kutu seçiniz! \n"); 
                return false;
            }
        }catch (Exception e){
            System.out.println("Hamle yazilamadı \n"); 
            return false;
        }

    }


    boolean kazanan(int x,int y,char oyuncu){ 
        int col,row,diag,rdiag; // col -> sütun , row -> satır , diag -> diagonal , rdiag -> ters diagonal
        col = row = diag = rdiag = 0; // başlangıç değeri olarak hepsine 0 atanır
        for (int i = 0; i < boyut ; i++) {
            if(oynTahtasi[x][i] == oyuncu) col++; // 
            if(oynTahtasi[i][y] == oyuncu) row++; // 
            if(oynTahtasi[i][i] == oyuncu) diag++; // 
            if(oynTahtasi[i][boyut-i-1] == oyuncu) rdiag++; // 
        }

        if(col == boyut || row == boyut || diag == boyut || rdiag == boyut){ 
            finish = true;                                                   
            return true;  // oyun bitti kazanan var anlamında true döner
        }else {
            return false; // yok ise kazanan yok anlamında false döner
        }
    }


    boolean beraberlikKontrol(){
        for (int i = 0; i < boyut ; i++) {
            for (int j = 0; j < boyut ; j++) {
                if(oynTahtasi[i][j] == '-') 
                    return false; 
            }
        }

        for (int i = 0; i < boyut ; i++) {
            for (int j = 0; j < boyut ; j++) { 
                if(kazanan(i,j,'X') || kazanan(i,j,'O')){ 
                    return false;
                }
            }
        }
        finish = true; // kazanan yok ve oyunda ki tüm hücreler dolu ise oyun biter
        return true; // beraberlik olur
    }

    boolean oyunBittiMi(){
        return finish;
    } 



}

