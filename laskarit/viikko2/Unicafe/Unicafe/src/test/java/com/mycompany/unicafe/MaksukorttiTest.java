package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void konstruktoriAsettaaSaldonOikein() {
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void lataaRahaaKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(4);
        assertEquals(14, kortti.saldo());
    }

    @Test
    public void otaRahaaVahentaaSaldoaOikein() {
        kortti.otaRahaa(2);
        assertEquals(8, kortti.saldo());
    }

    @Test
    public void otaRahaaEiVieSaldoaNegatiiviseksi() {
        kortti.otaRahaa(8);
        kortti.otaRahaa(4);
        assertEquals(2, kortti.saldo());
    }

    @Test
    public void otaRahaaPalauttaaTrueOikein() {
        assertEquals(true, kortti.otaRahaa(3));
    }

    @Test
    public void otaRahaaPalauttaaFalseOikein() {
        assertEquals(false, kortti.otaRahaa(13));
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti != null);
    }
}
