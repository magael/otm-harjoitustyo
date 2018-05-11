# Arkkitehtuurikuvaus

Käyttöliittymä on toteutettu JavaFX:llä.

Ui-pakkauksen World käynnistää sovelluksen ja alustaa tarvittavat muuttujat ja luokat.

Peli käyttää itsensä päivittämiseen AnimationTimer-luokkaa.

Pelin oliot, kuten esteet ja pelaajahahmo, käyttävät abstraktia luokkaa GameObject.
Jotkin GameObjecteista soveltavat liikkumiseen GameObjectMover-luokkaa.

Pisteiden hallinta tapahtuu Score-pakkauksen luokilla.

Törmäilyjen määrittämisestä vastaa CollisionHandler-luokka.

Level-pakkauksen luokat luovat pelin tason ja määrittelevät, missä maan taso kulkee.

Input-luokka käsittelee pelaajan näppäinpainallukset.

Utils-pakkauksessa on tason tiedoston lukuuun tarkoitettu luokka.

## Luokka-/pakkauskaavio
![luokka-pakkauskaavio](https://github.com/magael/otm-harjoitustyo/blob/master/dokumentaatio/luokka-pakkauskaavio.png)

## Sekvenssikaavioita
Pelin alustukset ja pääosa "game loopista":</br>
![World](https://github.com/magael/otm-harjoitustyo/blob/master/dokumentaatio/game_loop.png)

World-luokan AnimationTimerin silmukassa esteiden liikuttelu:</br>
![Esteen liikkuminen](https://github.com/magael/otm-harjoitustyo/blob/master/dokumentaatio/Esteen%20liikkuminen.png)
