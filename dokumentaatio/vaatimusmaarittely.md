# Vaatimusmäärittely

## Soveluksen tarkoitus
Sovellus on tasohyppelypeli PC:lle. Tarkoitus on viihdyttää pelaajaa hetki.

## Perusversion tarjoama toiminnallisuus
Lähtökohtana on The Impossible Game -tyylinen kaksiulotteisen sivuttaisnäkymän yhden painikkeen "endless runner", eli eteneminen on automaattista, pelaajan tarvitsee vain hypätä oikeissa kohdissa.

Pisteitä saa etenemisestä, ja ne näytetään lopuksi ja pelin aikana.

Peli päättyy, jos pelaaja osuu piikkeihin epäonnistuneen hypyn seurauksena.

#### Toiminnallisuudet:
* Käyttöliittymäikkunan muodostaminen ja sulkeminen
* Tasojen ja pelaajahahmon piirtäminen ja päivittäminen
* Hyppiminen
* Törmäysten havaitseminen
* Valmiiksi suunniteltuja tasoja
* Tasot liikkuvat kohti pelaajaa (näyttää, kuin pelaaja liikkuisi niitä kohti)
* Alkuvalikko: levelin valinta ja valikkonavigointi
* Game over -näkymä /-valikko: uudelleenyrittämisen tai valikkoon paluun mahdollisuus
* Options-valikko: äänten ja efektien hallinta
* Info-valikko: näppäinohjeet, credits, pysyväistallennustiedosto
* Pelin päättyminen
* Pisteiden lasku ja tulostus
* Eri "leveleitä"
* Huipputulosten tallentaminen ja lataaminen paikallisesti, eri pisteet eri tasoilla
* Pelin laittaminen tauolle
* Taustamusiikki ja ääniefekti

## Jatkokehitysideoita

* Pelisilmukan ym. "programming patterns" kehittäminen hienostuneemmiksi
* Monimutkaisemmat levelit ja törmäysten havaitseminen
* Vaihtoehtoisia polkuja, proc. gen. algoritmeja, toimivampia, kekseliäämpiä, haastavampia tasoja
* Pelitilanteen tallentaminen
* Kerättävät kolikot tms.
* Tuplahyppy
* Hypyn korkeuden hallitseminen pitämällä näppäintä pohjassa
* Lisää "juicea": Usean taustan parallaksi eteneminen, hienot "spritet" ja animaatiot, partikkeliefektit, jne.
* Musiikin luuppauksen parantaminen ja tärähtelyefektin hiomista
* Pistetilastot verkossa
* Mobiiliversio
