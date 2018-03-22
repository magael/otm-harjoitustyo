package com.mycompany.unicafe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {

    Kassapaate kassa;
    Maksukortti kortti;

    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(1000);
    }
    
    @Test
    public void konstruktoriAsettaaSaldonOikein() {
        assertEquals(kassa.kassassaRahaa(), 100000);
    }
    
    @Test
    public void toStringToimiiOikein() {
        assertEquals(kortti.toString(), "saldo: 10.0");
    }

    @Test
    public void myytyjenEdullistenLounaidenMaaraAluksiNolla() {
        assertEquals(kassa.edullisiaLounaitaMyyty(), 0);
    }

    @Test
    public void myytyjenMaukkaidenLounaidenMaaraAluksiNolla() {
        assertEquals(kassa.maukkaitaLounaitaMyyty(), 0);
    }
    
    //Käteinen

    @Test
    public void syoEdullisestiRiittavaKateisostoKasvattaaKassanRahamaaraa() {
        kassa.syoEdullisesti(240);
        assertEquals(kassa.kassassaRahaa(), 100240);
    }

    @Test
    public void syoMaukkaastiRiittavaKateisostoKasvattaaKassanRahamaaraa() {
        kassa.syoMaukkaasti(400);
        assertEquals(kassa.kassassaRahaa(), 100400);
    }

    @Test
    public void syoEdullisestiRiittavaKateisostoAntaaOikeanMaaranVaihtorahaa() {
        assertEquals(kassa.syoEdullisesti(380), 140);
    }

    @Test
    public void syoMaukkaastiRiittavaKateisostoAntaaOikeanMaaranVaihtorahaa() {
        assertEquals(kassa.syoMaukkaasti(420), 20);
    }

    @Test
    public void syoEdullisestiRiittavaKateisostoKasvattaaMyytyjenMaaraaOikein() {
        kassa.syoEdullisesti(240);
        kassa.syoEdullisesti(480);
        assertEquals(kassa.edullisiaLounaitaMyyty(), 2);
    }

    @Test
    public void syoMaukkaastiRiittavaKateisostoKasvattaaMyytyjenMaaraaOikein() {
        kassa.syoMaukkaasti(400);
        kassa.syoMaukkaasti(800);
        assertEquals(kassa.maukkaitaLounaitaMyyty(), 2);
    }

    @Test
    public void syoEdullisestiRiitamatonKateisostoEiMuutaKassanRahamaaraa() {
        kassa.syoEdullisesti(200);
        assertEquals(kassa.kassassaRahaa(), 100000);
    }

    @Test
    public void syoMaukkaastiRiitamatonKateisostoEiMuutaKassanRahamaaraa() {
        kassa.syoMaukkaasti(40);
        assertEquals(kassa.kassassaRahaa(), 100000);
    }

    @Test
    public void syoEdullisestiRiitamatonKateisostoAntaaRahatTakaisin() {
        assertEquals(kassa.syoEdullisesti(100), 100);
    }

    @Test
    public void syoMaukkaastiRiitamatonKateisostoAntaaRahatTakaisin() {
        assertEquals(kassa.syoMaukkaasti(399), 399);
    }
    
    @Test
    public void syoEdullisestiRiitamatonKateisostoEiMuutaMyytyjenMaaraa() {
        kassa.syoEdullisesti(0);
        assertEquals(kassa.edullisiaLounaitaMyyty(), 0);
    }
    
    @Test
    public void syoMaukkaastiRiitamatonKateisostoEiMuutaMyytyjenMaaraa() {
        kassa.syoMaukkaasti(11);
        assertEquals(kassa.maukkaitaLounaitaMyyty(), 0);
    }

    //Kortti
    
    @Test
    public void kortilleLadattaessaPositiivisestiKortinSaldoMuuttuuOikein() {
        kassa.lataaRahaaKortille(kortti, 10);
        assertEquals(kortti.saldo(), 1010);
    }
    
    @Test
    public void kortilleLadattaessaNegatiivisestiKortinSaldoEiMuutu() {
        kassa.lataaRahaaKortille(kortti, -10);
        assertEquals(kortti.saldo(), 1000);
    }
    
    @Test
    public void kortilleLadattaessaPositiivisestiKassanRahammaaraMuuttuuOikein() {
        kassa.lataaRahaaKortille(kortti, 99);
        assertEquals(kassa.kassassaRahaa(), 100099);
    }
    
    @Test
    public void kortilleLadattaessaNegatiivisestiKassanSaldoEiMuutu() {
        kassa.lataaRahaaKortille(kortti, -99);
        assertEquals(kassa.kassassaRahaa(), 100000);
    }
    
    @Test
    public void syoEdullisestiVahentaaSaldoaOikein() {
        kassa.syoEdullisesti(kortti);
        assertEquals(kortti.saldo(), 760);
    }

    @Test
    public void syoEdullisestiPalauttaaTrueJosKortillaOnRahaa() {
        assertEquals(kassa.syoEdullisesti(kortti), true);
    }

    @Test
    public void syoEdullisestiPalauttaaFalseJosKortinSaldoEiRiita() {
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(kassa.syoEdullisesti(kortti), false);
    }

    @Test
    public void syoEdullisestiEiVahennaKortinSaldoaJosSaldoEiRiita() {
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(kortti.saldo(), 40);
    }

    @Test
    public void syoEdullisestiKasvattaaMyytyjenLounaidenMaaraaOikein() {
        kassa.syoEdullisesti(kortti);
        assertEquals(kassa.edullisiaLounaitaMyyty(), 1);
    }

    @Test
    public void myytyjenEdullistenLounaidenMaaraSailyyJosKortinSaldoEiRiita() {
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(kassa.edullisiaLounaitaMyyty(), 4);
    }

    @Test
    public void syoEdullisestiEiMuutaKassanRahamaaraa() {
        kassa.syoEdullisesti(kortti);
        assertEquals(kassa.kassassaRahaa(), 100000);
    }

    @Test
    public void syoMaukkaastiEiMuutaKassanRahamaaraa() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(kassa.kassassaRahaa(), 100000);
    }

    @Test
    public void syoMaukkaastiVahentaaSaldoaOikein() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(kortti.saldo(), 600);
    }

    @Test
    public void syoMaukkaastiPalauttaaTrueJosKortillaOnRahaa() {
        assertEquals(kassa.syoMaukkaasti(kortti), true);
    }

    @Test
    public void syoMaukkaastiPalauttaaFalseJosKortinSaldoEiRiita() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(kassa.syoMaukkaasti(kortti), false);
    }

    @Test
    public void syoMaukkastiKasvattaaMyytyjenLounaidenMaaraaOikein() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(kassa.maukkaitaLounaitaMyyty(), 1);
    }

    @Test
    public void myytyjenMaukkaidenLounaidenMaaraSailyyJosKortinSaldoEiRiita() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(kassa.maukkaitaLounaitaMyyty(), 2);
    }

    @Test
    public void syoMaukkaastiEiVahennaKortinSaldoaJosSaldoEiRiita() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(kortti.saldo(), 200);
    }
}
