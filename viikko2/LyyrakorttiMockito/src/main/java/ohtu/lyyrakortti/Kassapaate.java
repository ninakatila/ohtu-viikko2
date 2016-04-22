package ohtu.lyyrakortti;

public class Kassapaate {
    private int myytyjaLounaita;
    public static final int HINTA = 5;
    private int summa;

    public Kassapaate() {
        this.myytyjaLounaita = 0;
    }
    
    public void lataa(Lyyrakortti kortti, int summa){
        if (summa > 0){
            kortti.lataa(summa);
        }
    }
    
    public void ostaLounas(Lyyrakortti kortti) {
        int kredit=kortti.getSaldo();
        if (HINTA<kredit){
        kortti.osta(HINTA);
        myytyjaLounaita++;
      }
    }
    
    public int getMyytyjaLounaita() {
        return myytyjaLounaita;
    }
}