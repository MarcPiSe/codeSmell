# Pràctica 2

# Testing + Refactoring

## 1 Introducció

L'objectiu d'aquest exercici és refactoritzar i posar sota test un software ja existent. Al final de la pràctica haureu d'entregar el codi resultant i una memòria explicant el procés que heu seguit.

Aquesta pràctica la fareu per parelles. Si no podeu trobar un com pany per fer-la, aviseu-me l'abans possible.

Les entregues es faràn a través de la plataforma Github Classrooms. Mireu-vos bé les instruccions a la secció 5.


## 2 Material

Al Github Classroom  trobareu el codi inicial i les instruccions.

## 3 Exercici

Un programador no gaire bò ha desenvolupat aquest software i se us encarrega la tasca de millorar el disseny i afegir tests unitaris al codi existent.

Com que el codi està en producció, els canvis s'han d'aplicar i testejar de la manera més progressiva possible.

### Consideracions:

- Les classes als paquets FAKE i EXTERNAL **no es poden modifcar i no s'han de testejar**
- Les classes al paquet EXAMPLE no s'han de testejar, simplement serveixen per a que veieu com es farien servir els serveis. Només es poden modificar com a conseqüència d'algún refactor aplicat a un altre classe
- Les classes al paquet exceptions tampoc s'han de testejar
- **El ftxer** pom.xml **no es pot modffcar**. Si per algún motiu necessiteu fer un canvi, m'heu de consultar primer
- TOTA la resta de classes han d'estar sota test. Recordeu que nom és cal testejar els mètodes que tenen més d'un path.
- **El codi por contenir bugs**. Si en trobeu, els heu de corretgir
- Els tests han d'estar sota el directori _src/test/java_

## 4 Descripció del codi

Quan us entreguen aquest codi no us donen gaire informació, a part de la poca documentació que podeu trobar a les fonts. El que sabeu és:

### 4.1 Idea principal

El codi serveix per gestionar clients (_User_) que fan inversions en mercats financers, concretament en accions (_Stock_) i en metalls preciosos com or i plata (_Gold_, _Silver_).

Cada usuari pot tenir una o més carteres (_Portfolio_), que serveixen per agrupar un conjunt d'inversions. A més a més, els usuaris poden estar agrupats en conjunts (_UserGroup_).


L'API del sistema, principalment localitzada a _BusinessService_ i _UtilsService_, permet fer dos grups fonamentals d'operacions:

1. Comprar valors
2. Fer consultes

Les compres es realitzen fent servir la classe _Order_ que conté:

- Tipus d'ordre
- El seu estat respecte el mercat
- Quantitat objectiu
- Preu objectiu
- Quantitat executada (igual o menor que la objectiu)
- Preu d'execució (igual o major que el preu objectiu)

### 4.2 Serveis externs

El codi utilitza uns serveis proporcionats per llibreries externes (no es poden modificar ni s'han de testejar):

- **PersistenceService**: Serveix per fer persistència de les entitats _User_, _Stock_ i _Porfolio_
- **BrokerService**: Encarregat de fer efectives les compres. Aquest servei funciona de forma asíncrona, i per comunicar el resultats de les ordres enviades fa servir un sistema de esdeveniments (_EventBus_ de la llibreria Guava).
- **StockMarket**: servei que ens proporciona les cotitzacions en temps real de diferents actius financers


## 5 Refactoring

Els possibles refactors a aplicar són els que hem vist a classe, més alguns possibles extra. Són:

1. Aplicació de patrons de creació d'objectes com:
    1. [Replace Constructors with Creation Methods](https://industriallogic.com/xp/refactoring/constructorCreation.html)
    2. [Introduce Polymorphic Creation with Factory Method](https://industriallogic.com/xp/refactoring/polymorphicCreationFactory.html)
    3. [Encapsulate Classes with Factory](https://industriallogic.com/xp/refactoring/classesWithFactory.html)

    I en general aplicació de [patrons de creació d'objectes](https://sourcemaking.com/design_patterns/creational_patterns).

2. Aplicació de patrons com _Commnand_, _Strategy_, _State_, _Com posite_, _Facade_, ... que podeu trobar [aquí](https://sourcemaking.com/design_patterns).
3. Qualsevol modificació de codi que porti a una millora dels principis [S.O.L.I.D.](https://en.wikipedia.org/wiki/SOLID), en especial la cohesió i l'acoblament.

**IMPORTANT: NO S'HA D'APLICAR TOT EL QUE US ACABO DE DESCRIURE**. I podeu
aplicar d'altres que no estan aquí. Heu de veure què té sentit i què no.

## 6 Bones pràctiques

### 6.1 Desenvolupament de la pràctica

El procés **recomanat** (no obligatori) per fer la pràctica és el següent:

1. En una primera fase, analitzeu el codi per intentar veure els refactors més obvis i porteu-los a terme
2. Després començeu a afegir els tests. Afegiu els més obvis
3. A partir d'aquest punt, aneu iterant afegint refactors nous i completant els tests

### 6.2 Tests

En aquest exercici ja hem d'intentar fer els tests de la manera que els faríem en un entorn de producció. Heu de seguir els següents principis:

- Els tests han de ser el més simples possibles
- Feu servir la API behaviour de JMockit (expectations i verifications) com hem vist a classe
- Eviteu al màxim la duplicitat de codi als tests. Feu servir _@BeforeEach_ i classes base sempre que podeu (i tingui sentit)
- Al moment de decidir quines dependències _mockejar_ , tingueu en compte el  següent:
  - Afegiu mocks de manera progressiva: començeu per el que us sem bli més evident
  - Per a que els tests siguin 100% unitaris, heu de mockejar totes les dependències
- En qualsevol cas, NO cal _mockejar_ les classes standard de java (ArrayList, Iterators, ...)

### 6.3 Refactors

- Intenteu planificar al màxim el que voleu fer. Això vol dir tenir clar quins refactors simples aplicareu i en quin ordre
- Penseu en quins principis voleu millorar amb cada refactor
- Apliqueu el refactors també als tests

### 6.4 Git

- Feu un commit per cada refactor simple que apliqueu
- Pels refactors complexes, creeu una branca específica. Intenteu decomposar un refactor gros en varis commits.
- En general, intenteu fer commits el més petits possible de manera que es refereixin a una sola cosa
- Afegiu **sempre** una bona descripció del commit
- **IMPORTANT** : assegureu-vos de que a github els commits que feu queden associats al vostre usuari. Per això el nom d'usuari de cada commit ha de sortir enllaçat al vostre perfil
- **LLEGIU EL [LLIBRE DE GIT](https://git-scm.com/book).** Bàsicament el capítol 2, apartats 2.1 – 2.2


## 7 Entrega

La entrega consistirà en un repositori **privat** a github.com, creat a partir del Github Classrooms. Els components del grup us haureu de posar d'acord per triar un repositori, donant accés a l'altre integrant per a que pugui afegir commits.

Es valorarà que els _commits_ estiguin repartits entre els dos alumnes que integren el grup.

La memòria s'entregarà en format PDF i l'haureu de col·locar **a la carpeta arrel del repositori** amb el nom memoria.pdf.

Els enllaços als _commits_ dintre del document PDF **s'hauran de poder obrir automàticament** amb el navegador fent click sobre l'enllaç.


### 7.1 Format de la memòria

La memòria tindrà 4 apartats:

1 -  Descripció general dels canvis que heu fet i un diagrama de classes

- La descripció general dels canvis és bàsicament l'enumeració dels refactors aplicats, sempre sobre el resultat final.

- Els diagrama els podeu generar amb eines com [aquesta](https://www.modelio.org/), o des de els vostres IDEs (IntelliJ pot generar diagarames de classe)

2 - Descripció detallada dels canvis per ordre d'aplicació. **Important** : nom és han d'aparèixer els canvis definitius. Si heu fet refactors que després han desaparegut, **NO** han de sortir a la memòria.


3 - (opcional) Comentaris. Aquí podeu explicar possibles problemes que heu tingut, i possibles sol·lucions alternatives o futures que es podrien aplicar.

4 - Percentatge de participació de cada component del grup. La suma dels percentatges haurà de ser el 100%. Més info sobre l'avaluació de la pràctica 2 a la [guía docent](https://moodle2.udg.edu/pluginfile.php/1167387/mod_resource/content/2/Guia%20docent%20CAES%201920.pdf) (moodle)

### Descripcions detallades (apartat 2)

Aquesta és la part més important i ha de seguir un esquema molt concret. Consisteix en una llista de descripcions detallades de tots els canvis que heu fet. Cada canvi haurà d'estar en una de les següents 3 categories: **Refactor, Test, Altre**.

- Cada element corresponent a **Refactor** contindrà:
    1. Descripció de la refactorització
    2. (Opcional) Patró o patrons aplicats
    3. Causa: bàsicament, quin _codi smell_ heu detectat
    4. Objectiu: quins principis bàsics heu millorat amb el refactor (acoblament, cohesió, ...). Molt breu
    5. Llista de commits corresponents, incloent els canvis dels tests que s'hàgin vist afectats pel refactor

- Cada element corresponent a **Test** contindrà canvis relacionats amb un o varis tests. Aquí **NO s'han d'incloure** canvis provocats per refactors al codi.
    Contindrà:
    1. Tipus de canvi. **Modifcació** o **Nou**
    2. Descripció del canvi. Segons el tipus de canvi heu de descriure:
       1. Modificació: quines millores o quins refactors heu aplicat als tests, i quins principis de disseny heu millorat
       2. Nou: quin cas o casos heu afegit i per què
    3. Llista de commits corresponents

- Cada element corresponent a **Altre** contindrà:
    1. Causa
    2. Solució
    3. Llista de com mits corresponents

**IMPORTANT:**

- Les llistes de commits han de contenir un enllaç al github que es pugui clickar per accedir directament des del PDF
