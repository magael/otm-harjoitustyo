# Vaatimusmäärittely


## Soveluksen tarkoitus
Sovellus on selaimella (PC:llä, "on-" tai "offline") pelattavissa oleva tasohyppelypeli.

(Kielenä toimii JavaScript. Tarkoitus olisi pärjätä ilman valmiita pelimoottoreita ym. kehyksiä.)

## Käyttöliittymäluonnos
![kayttoliittymaluonnos](https://github.com/magael/otm-harjoitustyo/blob/master/dokumentaatio/otm_kayttoliittymaluonnos.jpg)

## Perusversion tarjoama toiminnallisuus
Lähtökohtana on "The Impossible Game"-tyylinen kaksiulotteisen sivuttaisnäkymän yhden painikkeen "endless runner", eli eteneminen on automaattista, pelaajan tarvitsee vain hypätä oikeissa kohdissa.

* Pelissä on itse tasohyppelynäkymän lisäksi aloitus- ja päättymisnäkymät.
* Pisteitä saa etenemisestä, ja ne näytetään pelin lopuksi (ehkä myös pelin aikana).
* Peli päättyy, jos pelaaja jää jumiin tai osuu "piikkeihin" epäonnistuneen hypyn seurauksena (mahdollisesti myös onnistuttuaan pääsemään ennalta määriteltyjen "tasojen" päättymispisteisiin).


Pelistä voi kehittää ajan salliessa yhä monimutkaisemman version.

## Jatkokehitysideoita

#### "Endless runner"-versioon sopivia ominaisuuksia:
* Kerättävät "kolikot" tms
* Vaihtoehtoisia polkuja
* Tuplahyppy
* Taustojen parallaksi eteneminen
* "Juice": ruudun tärähdysefekti, grafiikat, äänet jne.


#### Klassinen (esim. Super Mario -tyylinen) tasohyppelypeli
Automaattinen sivuttaisliike voidaan vaihtaa manuaalisesti pelaajan hallitsemaksi (esim. nuoli-, a- ja d-näppäimillä). Myös tämä versio on mahdollista pitää yksinkertaisena ja toimivana, lisäten hiljalleen lisää ominaisuuksia.

* "Elämät" tms.
* Vihollisia
* Keinoja vuorovaikuttaa tietokoneen ohjaamien hahmojen kanssa
  * Pelaajan "elämän" tai vastaavan menettäminen
  * Vihollisten päälle hyppiminen
  * Projektiilit
* Nopea "dash"-liike ilmassa
* Seiniä pitkin hyppiminen
* "Grappling hook": köysi & koukku
* Salaisia polkuja ja kerättäviä esineitä
* Liikkuvia tasoja
* Ympäristöön vaikuttavia kytkimiä
* "Power-ups": Erityisominaisuuksia antavat kerättävät esineet
* "One way platforms": Vain hteen suuntaan liikkeen estävät tasot
* Kaltevat mäet ym.

Pelistä voi myös jatkossa kehittää PC-version lisäksi mobiiliversio.
