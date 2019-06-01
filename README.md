"# ProjektZespolowyAPI" - API do zmieniania reguł dla systemu ekspertowego w projekcie zespołowym w firmie Comarch

Get Reguły - zwraca json z wszystkimi regułami  
http://localhost:8080/RestExample/resources/clips/rules  
przykład wykorzysztania:  
curl -i -H "Accept: application/json" "http://localhost:8080/RestExample/resources/clips/rules"  
  
Dodaj regułe - dodaje JSON z daną regułą   
http://localhost:8080/RestExample/resources/clips/post  
przykład wykorzystania:  
curl -d "{"""name""":"""Daniel""","""word""":"""hamulce""","""predicament""":"""nalezy""","""certainty""":"""75"""}" -H "Content-Type: application/json" -X POST http://localhost:8080/RestExample/resources/clips/post  

Usun regułe - podajemy JSON z regulą która chcemy usunąć  
http://localhost:8080/RestExample/resources/clips/delete  
przykład wykorzystania:  
curl -d "{"""name""":"""Daniel""","""word""":"""hamulce""","""predicament""":"""nalezy""","""certainty""":"""75"""}" -H "Content-Type: application/json" -X POST http://localhost:8080/RestExample/resources/clips/delete  
  
Sprawdź pod jaką ścieżką server szuka pliku clipsowego  
http://localhost:8080/RestExample/resources/clips/clipsFilePath  
