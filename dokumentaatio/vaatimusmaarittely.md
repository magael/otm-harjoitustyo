# Vaatimusmäärittely

## Soveluksen tarkoitus
Sovellus on tasohyppelypeli PC:lle. Tarkoitus on viihdyttää pelaajaa hetki.

(Kielenä toimii JavaScript. Yritän pärjätä ilman valmiita pelimoottori-kirjastoja ym.)

## Käyttöliittymäluonnos
![kayttoliittymaluonnos](https://github.com/magael/otm-harjoitustyo/blob/master/dokumentaatio/otm_kayttoliittymaluonnos.jpg)

## Perusversion tarjoama toiminnallisuus
Lähtökohtana on The Impossible Game -tyylinen kaksiulotteisen sivuttaisnäkymän yhden painikkeen "endless runner", eli eteneminen on automaattista, pelaajan tarvitsee vain hypätä oikeissa kohdissa.

Pisteitä saa etenemisestä, ja ne näytetään lopuksi ja/tai pelin aikana.

Peli päättyy, jos pelaaja jää jumiin tai osuu piikkeihin epäonnistuneen hypyn seurauksena.

#### Toiminnallisuudet:
* Tasojen ja pelaajahahmon piirtäminen ja päivittäminen
* Hyppiminen
* Törmäysten havaitseminen
* Valmiiksi suunniteltuja tasoja
* Tasot liikkuvat kohti pelaajaa (näyttää, kuin pelaaja liikkuisi niitä kohti)
* Uusien tasojen lataaminen edetessä
* Pelin päättyminen
* Pisteiden lasku ja tulostus

## Jatkokehitysideoita

#### "Endless runner"-versioon sopivia ominaisuuksia:
* Aloitus- ja päättymisnäkymät
* Eri "leveleitä" (aluksi siis 1 "level"-taso / kartta, jossa erilaisia "platform"-tasoja)
* Proseduraalisten ja/tai käsin tehtyjen tasojen arkkitehtuurin edelleen kehittäminen
  * Vaihtoehtoisia polkuja, parempia proc. gen. algoritmeja, toimivampia, kekseliäämpiä, haastavampia tasoja
* Pelitilanteen tallentaminen
* Kerättävät kolikot tms.
* Tuplahyppy
* Hypyn korkeuden hallitseminen pitämällä näppäintä pohjassa
* "Juice": ruudun tärähdysefekti, taustojen parallaksi eteneminen, hienot "spritet" ja animaatiot, partikkeliefektit, äänet jne.
* Pelin laittaminen tauolle
* Paikallisesti tallennettavat ja ladattavat pistetulokset
* Pistetilastot verkossa
* Mobiiliversio
* Koodin ja dokumentaation kääntäminen englanniksi, ellei ole jo

#### Klassinen (esim. Super Mario -tyylinen) tasohyppelypeli:
Automaattinen sivuttaisliike voidaan vaihtaa manuaalisesti pelaajan hallitsemaksi (esim. nuoli-, a- ja d-näppäimillä).

* Kamera "lerp" (“linear interpolation”) seuraa pelaajaa
* "Camera box"
* Tarina
* Elämät tms.
* Vihollisia (eri tavoin partioivia ja liikkuvia)
* Keinoja vuorovaikuttaa tietokoneen ohjaamien hahmojen ("NPC") kanssa
  * Pelaajan elämän tai vastaavan menettäminen osuessaan viholliseen
  * Vihollisten päälle hyppiminen
  * Lyöminen tms.
  * Projektiilit
  * Puhuminen ym. ystävällisten NPC:iden kanssa
* "Power-ups": Erityisominaisuuksia antavat kerättävät esineet
* Varustevalikko
* Nopea "dash"-liike ilmassa
* Seiniä pitkin hyppiminen
* "Grappling hook": köysi & koukku
* Salaisia polkuja ja sieltä kerättäviä erityisiä esineitä
* Liikkuvia tasoja
* Ympäristöön vaikuttavia kytkimiä
* Hajoavia tason osia
* "One way platforms": Vain yhteen suuntaan liikkeen estävät tasot
* Kehittyneemmät törmäyksen havaitsemiset
* Kaltevat mäet, portaat ym.
* Köydet, tikkaat ym.
* Moninpeli
* Konsoliohjaimen tuki
