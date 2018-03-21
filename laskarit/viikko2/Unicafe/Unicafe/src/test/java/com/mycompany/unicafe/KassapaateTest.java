package com.mycompany.unicafe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

//    käteisosto toimii sekä edullisten että maukkaiden lounaiden osalta
//        jos maksu riittävä: kassassa oleva rahamäärä kasvaa lounaan hinnalla ja vaihtorahan suuruus on oikea
//        jos maksu on riittävä: myytyjen lounaiden määrä kasvaa
//        jos maksu ei ole riittävä: kassassa oleva rahamäärä ei muutu, kaikki rahat palautetaan vaihtorahana ja myytyjen lounaiden määrässä ei muutosta
//    seuraavissa testeissä tarvitaan myös Maksukorttia jonka oletetaan toimivan oikein
//    korttiosto toimii sekä edullisten että maukkaiden lounaiden osalta
//        jos kortilla on tarpeeksi rahaa, veloitetaan summa kortilta ja palautetaan true
//        jos kortilla on tarpeeksi rahaa, myytyjen lounaiden määrä kasvaa
//        jos kortilla ei ole tarpeeksi rahaa, kortin rahamäärä ei muutu, myytyjen lounaiden määrä muuttumaton ja palautetaan false
//        kassassa oleva rahamäärä ei muutu kortilla ostettaessa
//    kortille rahaa ladattaessa kortin saldo muuttuu ja kassassa oleva rahamäärä kasvaa ladatulla summalla
//
//Huomaat että kassapääte sisältää melkoisen määrän "copypastea". Nyt kun kassapäätteellä on automaattiset testit, on sen rakennetta helppo muokata eli refaktoroida siistimmäksi koko ajan kuitenkin varmistaen, että testit menevät läpi. Tulemme tekemään refaktoroinnin myöhemmin kurssilla.

public class KassapaateTest {
      
    Kassapaate kassa;

    @Before
    public void setUp() {
        kassa = new Kassapaate();
    }

    @Test
    public void konstruktoriAsettaaSaldonOikein() {
        assertEquals(kassa.kassassaRahaa(), 100000);
    }
    
    @Test
    public void myytyjenEdullistenLounaidenMaaraAluksiNolla() {
        assertEquals(kassa.edullisiaLounaitaMyyty(), 0);
    }
    
    @Test
    public void myytyjenMaukkaidenLounaidenMaaraAluksiNolla() {
        assertEquals(kassa.maukkaitaLounaitaMyyty(), 0);
    }

//    @Test
//    public void syoEdullisestiVahentaaSaldoaOikein() {
//        kortti.syoEdullisesti();
//        assertEquals("Kortilla on rahaa 7.5 euroa", kortti.toString());
//    }
//
//    @Test
//    public void syoMaukkaastiVahentaaSaldoaOikein() {
//        kortti.syoMaukkaasti();
//        assertEquals("Kortilla on rahaa 6.0 euroa", kortti.toString());
//    }
//
//    @Test
//    public void syoEdullisestiEiVieSaldoaNegatiiviseksi() {
//        kortti.syoMaukkaasti();
//        kortti.syoMaukkaasti();
//        kortti.syoEdullisesti();
//        assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
//    }
//    
//    @Test
//    public void syoMaukkaastiEiVieSaldoaNegatiiviseksi() {
//        kortti.syoMaukkaasti();
//        kortti.syoMaukkaasti();
//        kortti.syoMaukkaasti();
//        assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
//    }
//    
//    @Test
//    public void syoEdullisestiToimiiKunJaljellaEdullisenHinta() {
//        kortti.syoEdullisesti();
//        kortti.syoEdullisesti();
//        kortti.syoEdullisesti();
//        kortti.syoEdullisesti();
//        assertEquals("Kortilla on rahaa 0.0 euroa", kortti.toString());
//    }
//    
//    @Test
//    public void syoMaukkaastiToimiiKunJaljellaMaukkaanHinta() {
//        Maksukortti uusiKortti = new Maksukortti(4);
//        uusiKortti.syoMaukkaasti();
//        assertEquals("Kortilla on rahaa 0.0 euroa", uusiKortti.toString());
//    }
//
//    @Test
//    public void kortilleVoiLadataRahaa() {
//        kortti.lataaRahaa(25);
//        assertEquals("Kortilla on rahaa 35.0 euroa", kortti.toString());
//    }
//
//    @Test
//    public void kortinSaldoEiYlitaMaksimiarvoa() {
//        kortti.lataaRahaa(200);
//        assertEquals("Kortilla on rahaa 150.0 euroa", kortti.toString());
//    }
//    
//    @Test
//    public void negatiivisenSummanLataaminenEiMuutaSaldoa() {
//        kortti.lataaRahaa(-100);
//        assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
//    }
}
