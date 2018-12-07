/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class TicTacToe {
    
    static Scanner scn = new Scanner(System.in); // Terminalden yazı okumak için oluşturulan scanner
    static int boyut = 0; // oyun tahtasini boyutunu tutar
    static String kullaniciAdi = ""; //
    static String harfBilgisi = "";
    static OyunTahtasi oyunTahtasi = new OyunTahtasi(boyut); 
    static Oyuncu oyuncu = new Oyuncu(); // boş bir oyuncu oluşturulur
    static Oyuncu player_ai = new Oyuncu(); // bilgisayar için boş oyuncu oluşturulur
    static int turn = 0; // sıranın kimde olduğunu belirten değişken. Eğer değer 0 ise sıra oyuncudadır.
    static int x ,y; // girilen kooordinat değerlerini integer olarak tutar
    static char[][] kayitliOyunTahtasi; // kayitli oyun var ise okunup bu değişkene atanır

    public static void main(String[] args) {

        System.out.println("|--------------------------------|");
        System.out.println("|           Tic-Tac-Toe          |");
        System.out.println("|--------------------------------|");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Kayıtlı oyundan devam edilsin mi ? (Y/n)");
        String yanit = scanner.nextLine();
        System.out.println("Oyunu kaydetmek istiyorsanız S (büyük veya küçük) ile beraber entera basınız.\n(Sadece bir oyun kaydedilebilir.)");

        if(yanit.equals("Y") || yanit.equals("y")){ 
            try {
                FileReader fileReader = new FileReader("kayit.txt"); 
                BufferedReader bufferedReader = new BufferedReader(fileReader); 
                String line; // dosyadan okunacak satırların geçici olarak tutulacağı string değişkeni
                line = bufferedReader.readLine(); 
                boyut = Integer.parseInt(line); 
                line = bufferedReader.readLine(); 
                kullaniciAdi = line; 
                line = bufferedReader.readLine(); 
                harfBilgisi = line; 
                int row = 0; 
                line = bufferedReader.readLine(); 
                char[] tempLine = line.toCharArray(); // okunan ilk satır string olarak --X şeklinde ise bunu char dizisine atar dizi 0. index = -  | 1.index = -  | 2.index = X
                kayitliOyunTahtasi = new char[tempLine.length][tempLine.length]; // okunan ilk satır sonucu elde edilen boyut kadar alan açılır
                kayitliOyunTahtasi[row] = tempLine; // okunan ilk satır char dizisi tahtanin ilk satırına yazılır
                row++; // ikinci satıra geçilir
                while( (line = bufferedReader.readLine()) != null){ 
                    kayitliOyunTahtasi[row] = line.toCharArray();
                    row++;
                }

                bufferedReader.close(); // okuyucu kapatılır

                oyunTahtasi = new OyunTahtasi(kayitliOyunTahtasi); // kayıtlı oyunu oluşturdu

                if(harfBilgisi.equals("X")){ // Oyuncunun harf bilgisine bakarak oyuncu ve bilgisayar nesnelerini oluşturur.
                    oyuncu = new Oyuncu(true,'X');
                    oyuncu.setKullaniciAdi(kullaniciAdi);
                    player_ai = new Oyuncu(false,'O');
                }else if(harfBilgisi.equals("O")){
                    oyuncu = new Oyuncu(true,'O');
                    oyuncu.setKullaniciAdi(kullaniciAdi);
                    player_ai = new Oyuncu(false,'X');
                }else{ // Oyuncu harf bilgisini doğru girmez veya hiç girmez ise parametresiz yapıcılar kullanılır
                    oyuncu = new Oyuncu();
                    oyuncu.setKullaniciAdi(kullaniciAdi);
                    player_ai = new Oyuncu(false);
                }

            }catch (Exception e){ // Kayitli dosya yok ise hata oluşur ve ekrana mesaj bastırılır ve yeni oyun açılır
                System.out.println("Kayit bulunamadı. Yeni oyun açılıyor");
                yeniOyunOlustur(); 
            }
        }else{ 
            yeniOyunOlustur();
        }

        oyunTahtasi.oyunTahtasiniYazdir(); // oyun tahtasi ekrana yazdırılır

        while (!oyunTahtasi.oyunBittiMi()){ // oyun bitene kadar bu while çalışır

            if(turn == 0){ // kullanıcın sırası ise
              String hamle = oyuncu.insanOyuncuHamlesiKontrol(); // kullanıcıdan koordinat bilgisi alınır

              if(hamle.equals("s") || hamle.equals("S")){ // Eğer koordinat girmek yerine kaydetme  işlemi yapılmak istenirse
                  try {
                      FileWriter fileWriter = new FileWriter("kayit.txt"); // Dosya yazmak için filewriter nesnesi oluşturulur
                      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); // tampon yazıcı nesnesi oluştururlur
                      bufferedWriter.write(boyut + "\n"); 
                      bufferedWriter.write(kullaniciAdi + "\n"); 
                      bufferedWriter.write(harfBilgisi + "\n"); 

                      for (int i = 0; i < boyut; i++) { // Oyun tahtasinin şuan ki durumu text dosyasına matris halinde yazılır
                        bufferedWriter.write(oyunTahtasi.getOynTahtasi()[i]);
                        bufferedWriter.write("\n");
                      }
                      System.out.println("Kayit işlemi başarılı");
                      bufferedWriter.close(); // dosya yazma işlemi sonlandırılır
                      return; // program sonlanır
                  }catch (Exception e){
                        
                      System.out.println("Kayıt işlemi başarısız");
                  }
              }
              if(oyunTahtasi.hamleyiYaz(hamle,oyuncu.getHarf()) ){ 
                  x = Integer.parseInt(hamle.split(",")[0]); 
                  y = Integer.parseInt(hamle.split(",")[1]);

                 if( oyunTahtasi.kazanan(x,y,oyuncu.getHarf()) ) { 
                     System.out.println(oyuncu.getKullaniciAdi() + " KAZANDI "); 
                     break; // while sonlanır
                 }else if(oyunTahtasi.beraberlikKontrol()){ 
                     System.out.println("Berabere");
                     break; // while sonlanır.
                 }

                 turn = 1; // sıra bilgisayara geçer

                 oyunTahtasi.oyunTahtasiniYazdir(); 
              }
            }else{
                String hamle = player_ai.bilgisayarHamlesiUret(boyut,oyunTahtasi.getOynTahtasi()); // hamleyi bilgisayara ürettirir
                if(oyunTahtasi.hamleyiYaz(hamle,player_ai.getHarf())){ 
                    x = Integer.parseInt(hamle.split(",")[0]);
                    y = Integer.parseInt(hamle.split(",")[1]);

                    if(oyunTahtasi.kazanan(x,y,player_ai.getHarf())){ 
                        System.out.println("Bilgisayar KAZANDI");
                        break;
                    }else if(oyunTahtasi.beraberlikKontrol()){
                        System.out.println("Berabere");
                        break;
                    }
                    turn = 0; // sıra oyuncuya geçer
                    oyunTahtasi.oyunTahtasiniYazdir();
                }
            }
        }
    }

    public static void yeniOyunOlustur(){
        // yeni oyun oluştururken gerekli bilgiler kullanıcıdan alınır ve değişkenlere atanır.
        System.out.print("Oyun Tahtasi Boyutunu Giriniz (3,5,7 , ... , n) :");
        boyut = scn.nextInt();
        System.out.print("Kullanıcı adını giriniz :");
        kullaniciAdi = scn.next();
        System.out.printf("Hangi harfi kullanmak istersiniz (X,O) :");
        harfBilgisi = scn.next();

        oyunTahtasi = new OyunTahtasi(boyut);

        if(harfBilgisi.equals("X")){
            oyuncu = new Oyuncu(true,'X');
            oyuncu.setKullaniciAdi(kullaniciAdi);
            player_ai = new Oyuncu(false,'O');
        }else if(harfBilgisi.equals("O")){
            oyuncu = new Oyuncu(true,'O');
            oyuncu.setKullaniciAdi(kullaniciAdi);
            player_ai = new Oyuncu(false,'X');
        }else{
            oyuncu = new Oyuncu();
            oyuncu.setKullaniciAdi(kullaniciAdi);
            player_ai = new Oyuncu(false);
        }
    }}

