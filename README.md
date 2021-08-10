# vizsgaremek
Java Backend Vizsgaremek feladat

---
Készültségi állapot.
A dátum JSON vs SWAGGER vs JAVA problémákkal nehezen boldogultam, elment vele egy csomó időm sajnos.
Így most csak az legalapabb üzleti logikával lettem kész.

amit most tud a program:
- lehet alkalmazottakat felvenni benne (CRUD) + lista, amelyet prefixxel szűrhető 
- lehet műszakokat felvenni, amelyek névvel elláthatóak (CRUD) + lista, amelyet prefixxel szűrhető
- egy alkalmazottnál be lehet napi dátum szerint állítani egy tervezett műszakot
  a műszak egy elvárt munkaidő kezdeti időt tartalmaz, 
  adott műszak munkaóraidőtartamát,
  és munkaközi szünet időtartamát is be lehet állítani (pl ebédidő céljából)
  munkavégzés befejezésének időpontja szándékosan nincs megadvam, mivel az kiszámítható.
  kezdési időpont + órák száma + szünet.
- alklamazottaknál változtatható a beállított műsuzak minden nap (ha esetleg meggondolnánk magunkat)
- minden egyes nap meg lehet adni a munkavégzés kezdetét (entryTime) és végét (exitTime)

amit most még NEM tud a program:
- nem lehet egy hétre minden napra egy lépésben ugyanazt a műszakot beállítani
- ugyanezt nem lehet alkalmazotti csoportonként beállítani mivel eleve nem lett csoport 
  mint olyan implementálva - de később könnyen hozzáadható (egy kommenttel a kódban megjelöltem)
- nem lehet megállapítani azt hoyg az adott napon az alkalmazott rendben kitöltötte a 
  teljes munkaidejét, vagy túlórázott vagy hamarabb "lelépett"
- egyenlőre nincsen műszakoknál megadva hogy ez normál műszak, vagy hétvégi vagy éjjeli, vagy ünnepnapokra eső
  ez a fizetés végső kiszámításánál lehet szempont. Igez, ez csak egy munakidő nyilvántartás. 
  Front-enden ezekkel az adatokkal már lehet további -komplex- számításokat végezni ... 


Megjegyzés:
A program teszteléséhez mellékeltem egy "endpoint-test.http" file-t mivel a swagger 
nem mindenhol mutatja jól a dátum JSON objektumokat.
Csak végig kel nyomkodni egyesével a beállított HTTP requesteket, és lehet látni hogy jól 
vagy rosszul műxik a program.
