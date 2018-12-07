/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;
import java.util.Random;
import java.util.Scanner;

public class Oyuncu {
   private char harf; // Oyuncunun hangi harf ile oynadığını tutar X or O
   private boolean insanMi; // oyuncunun insan mi bilgisayar mı olduğunu tutar true ise insan
   private String kullaniciAdi; // oyununcunun adı
   private String hamleXy; // oyuncunun yaptığı hamleyi tutar

    public Oyuncu(){  
        harf = 'X';  
        insanMi = true; 
    }

    public Oyuncu(boolean insanMiKontrol){  
        insanMi = insanMiKontrol; 
        if(insanMiKontrol)  
            harf = 'X';
        else
            harf = 'O';
    }

    public Oyuncu(boolean insanMiKontrol,char ch){ 
        insanMi = insanMiKontrol; 
        harf = ch;
    }

    public String insanOyuncuHamlesiKontrol(){ 
        System.out.print("Hamle yapmak istediğiniz koordinatları giriniz (x,y) : ");
        Scanner scn = new Scanner(System.in); 
        String hamle = scn.nextLine(); 
        hamleXy = hamle; 
        return hamle; 
    }


   public String bilgisayarHamlesiUret(int n, char[][] oyunTahtasi){ 
        Random random = new Random(); 
        int x = random.nextInt(n); 
        int y = random.nextInt(n);

        while (oyunTahtasi[x][y] != '-'){ 
            x = random.nextInt(n);
            y = random.nextInt(n);
        }
        return String.valueOf(x) + "," + String.valueOf(y); 
    }


    public String oyuncununHamlesiniAl() {
        return hamleXy;
    }  

    public void setHamleXy(String hamleXy) {
        this.hamleXy = hamleXy;
    } 

    public char getHarf() {
        return harf;
    } 

    public void setHarf(char harf) {
        this.harf = harf;
    } 

    public boolean isInsanMi() {
        return insanMi;
    } 

    public void setInsanMi(boolean insanMi) {
        this.insanMi = insanMi;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    } 

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }
}



