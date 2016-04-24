
import ohtu.verkkokauppa.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class verkkokauppaTest {
    
    public verkkokauppaTest() {
    }
    
    /**
     * Test of aloitaAsiointi method, of class Kauppa.
     */
   @Test
public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
    // luodaan ensin mock-oliot
     Pankki pankki = mock(Pankki.class);

    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    // määritellään että viitegeneraattori palauttaa viitten 42
    when(viite.uusi()).thenReturn(42);

    Varasto varasto = mock(Varasto.class);
    // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 1
    when(varasto.saldo(1)).thenReturn(10); 
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

    // sitten testattava kauppa 
    Kauppa k = new Kauppa(varasto, pankki, viite);              

    // tehdään ostokset
    k.aloitaAsiointi();
    k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
    k.tilimaksu("pekka", "12345");

    // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
    verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(5));   
}
@Test
public void ostosKahdellaEriTuotteella() {
    // luodaan ensin mock-oliot
     Pankki pankki = mock(Pankki.class);

    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    // määritellään että viitegeneraattori palauttaa viitten 42
    when(viite.uusi()).thenReturn(42);

    Varasto varasto = mock(Varasto.class);
    // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 1
    when(varasto.saldo(1)).thenReturn(10); 
    when(varasto.saldo(2)).thenReturn(1);
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    when (varasto.haeTuote(2)).thenReturn(new Tuote (2, "mehu", 3));

    // sitten testattava kauppa 
    Kauppa k = new Kauppa(varasto, pankki, viite);              

    // tehdään ostokset
    k.aloitaAsiointi();
    k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
    k.lisaaKoriin(2);      //ostetaaan tuotettta numero 2 eli mehua
    k.tilimaksu("pekka", "12345");

    // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
    verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(8));   
}
@Test
public void ostosKahdellaSamallaTuotteella() {
    // luodaan ensin mock-oliot
     Pankki pankki = mock(Pankki.class);

    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    // määritellään että viitegeneraattori palauttaa viitten 42
    when(viite.uusi()).thenReturn(42);

    Varasto varasto = mock(Varasto.class);
    // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 1
    when(varasto.saldo(1)).thenReturn(2); 
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    

    // sitten testattava kauppa 
    Kauppa k = new Kauppa(varasto, pankki, viite);              

    // tehdään ostokset
    k.aloitaAsiointi();
    k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
    k.lisaaKoriin(1);      
    k.tilimaksu("pekka", "12345");

    // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
    verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(10));   
}
@Test
public void ostosKahdellaEriTuotteellaToinenLoppu() {
    // luodaan ensin mock-oliot
     Pankki pankki = mock(Pankki.class);

    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    // määritellään että viitegeneraattori palauttaa viitten 42
    when(viite.uusi()).thenReturn(42);

    Varasto varasto = mock(Varasto.class);
    // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 1
    when(varasto.saldo(1)).thenReturn(1); 
    when (varasto.saldo(2)).thenReturn(0);
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    when (varasto.haeTuote(2)).thenReturn(new Tuote (2, "mehu", 3));

    // sitten testattava kauppa 
    Kauppa k = new Kauppa(varasto, pankki, viite);              

    // tehdään ostokset
    k.aloitaAsiointi();
    k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
    k.lisaaKoriin(2);      //ostetaaan tuotettta numero 2 eli mehua, joka loppu
    k.tilimaksu("pekka", "12345");

    // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
    verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(5));   
}
@Test
public void uudetHintaJaViitenumeroOstoksienPaatyttya() {
    // luodaan ensin mock-oliot
     Pankki pankki = mock(Pankki.class);

    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    
    Varasto varasto = mock(Varasto.class);
    when(varasto.saldo(1)).thenReturn(10); 
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    when(varasto.saldo(2)).thenReturn(1);
    when (varasto.haeTuote(2)).thenReturn(new Tuote (2, "mehu", 3));

    // sitten testattava kauppa 
    Kauppa k = new Kauppa(varasto, pankki, viite);              

    // tehdään ostokset
    k.aloitaAsiointi();
    k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
    k.tilimaksu("pekka", "12345");

    // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
    verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(5));   
    // tarkistetaan että tässä vaiheessa viitegeneraattorin metodia uusi()
    // on kutsuttu kerran
    verify(viite, times(1)).uusi();

    // tehdään ostokset
    k.aloitaAsiointi();
    k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
    k.lisaaKoriin(2);   //ostetaan tuotetta numero 2 eli mehua
    k.tilimaksu("pekka", "12345");

    // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
    verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(8));   
    // tarkistetaan että tässä vaiheessa viitegeneraattorin metodia uusi()
    // on kutsuttu kahdesti
    verify(viite, times(2)).uusi();
    
    // tehdään ostokset
    k.aloitaAsiointi();
    k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
    k.lisaaKoriin(1);      
    k.tilimaksu("pekka", "12345");

    // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
    verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(10));   
     // tarkistetaan että tässä vaiheessa viitegeneraattorin metodia uusi()
    // on kutsuttu kahdesti
    verify(viite, times(3)).uusi();
    
    // tehdään ostokset
    k.aloitaAsiointi();
    k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
    k.lisaaKoriin(2);      //ostetaaan tuotettta numero 2 eli mehua, joka loppu
    k.tilimaksu("pekka", "12345");

    // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
    verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(5));   
     // tarkistetaan että tässä vaiheessa viitegeneraattorin metodia uusi()
    // on kutsuttu kahdesti
    verify(viite, times(4)).uusi();
}

//coberturan raportin mukaan puuttuu vielä poistaKorista metodin testaus
//tätä viimeistä testiä rivikattavuuden 100% saavuttamiseksi en saanut toimimaan
//@Test
//public void poistaKoristaPalauttaaPoistetunVarastoon() {
    // luodaan ensin mock-oliot
  //  Pankki pankki = mock(Pankki.class);
  //  Viitegeneraattori viite = mock(Viitegeneraattori.class);
  //  Varasto varasto = mock(Varasto.class);
    
  //  when(varasto.saldo(1)).thenReturn(10); 
    //when (varasto.saldo(2)).thenReturn(2);
    //when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    //when (varasto.haeTuote(2)).thenReturn(new Tuote (2, "mehu", 3));   
    
    // sitten testattava kauppa 
    //Kauppa k = new Kauppa(varasto, pankki, viite);              

    // tehdään ostokset
    //k.aloitaAsiointi();
    //k.lisaaKoriin(1);     
    //k.lisaaKoriin(2);      
    //k.poistaKorista(2);
    //k.tilimaksu ("pekka", "12345");

    //verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(5));   
     
    
}
