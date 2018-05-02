# Platformer

OTM-kurssin harjoitustyö: Tasohyppelypeli.</br>
Peli on niin sanottu "endless runner", eli eteneminen on automaattista, pelaajan tarvitsee vain hypätä oikeissa kohdissa. Pisteitä saa etenemisestä ja peli päättyy, jos pelaaja osuu piikkeihin.
  
## Dokumentaatio
[Käyttöohje](https://github.com/magael/otm-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/magael/otm-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/magael/otm-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Työaikakirjanpito](https://github.com/magael/otm-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

## Releaset

[Viikko 6](https://github.com/magael/otm-harjoitustyo/releases/tag/viikko6)

[Viikko 5](https://github.com/magael/otm-harjoitustyo/releases/tag/viikko5)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _Platformer-1.0-SNAPSHOT.jar_

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/magael/otm-harjoitustyo/blob/master/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_

### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_
