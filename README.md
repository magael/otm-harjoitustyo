# Platformer

OTM-kurssin harjoitustyö: Tasohyppelypeli (JavaFX).</br>
  
## Dokumentaatio
[Vaatimusmäärittely](https://github.com/magael/otm-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/magael/otm-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Työaikakirjanpito](https://github.com/magael/otm-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

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

Tiedostoon [checkstyle.xml](https://github.com/mluukkai/OtmTodoApp/blob/master/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_

