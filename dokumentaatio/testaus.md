# Testausdokumentti

Ohjelmaa on testattu sekä automatisoiduin yksikkötestein JUnitilla sekä manuaalisesti tapahtunein järjestelmätason testein.

## Yksikkötestaus

### Testauskattavuus
76% (ei yksinkertaisia gettereitä ja settereitä).

## Järjestelmätestaus

Sovelluksen järjestelmätestaus on suoritettu manuaalisesti.

### Asennus ja konfigurointi

Sovellus on haettu ja sitä on testattu [käyttöohjeen](https://github.com/magael/otm-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md) kuvaamalla tavalla Windows-ympäristöihin.

Sovellusta on testattu sekä tilanteissa, joissa huipputuloksen tallettava tiedosto on ollut olemassa ja jossa sitä ei ole ollut jolloin ohjelma on luonut sen itse.

### Toiminnallisuudet
Kaikki määrittelydokumentin ja käyttöohjeen listaamat toiminnallisuudet on käyty läpi. Kaikkien toiminnallisuuksien yhteydessä on yritetty käyttää myös virheellisiä syötteitä.

## Sovellukseen jääneet laatuongelmat

Ainakaan release:n .jarissa äänet eivät toimi.
