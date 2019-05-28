"# ProjektZespolowyAPI"

Get Reguły - zwracy json z wszystkimi regułami \n
http://localhost:8080/RestExample/resources/clips/clipsFilePath
przykładowy zwracany JSON: https://pastebin.com/aCrK5FjN?fbclid=IwAR273FNObT6ol5wkqShN4I_s6rV0nCRjqPwmqk77_xxagyAHTEQtHqF4GyA
przykład wykorzysztania:
curl -i -H "Accept: application/json" "http://localhost:8080/RestExample/resources/clips/rules"

Dodaj regułe - dodaje JSON z daną regułą 
http://localhost:8080/RestExample/resources/clips/post
przykład wykorzystania:
curl -d "{"""attrName1""":"""preferred-body""","""attr1""":"""stuka""","""attrName2""":"""best-body""","""attr2""":"""stuka""","""certainty""":30}" -H "Content-Type: application/json" -X POST http://localhost:8080/RestExample/resources/clips/post
(potrójne " przez głupi windows)

Usun regułe - podajemy JSON z regulą która chcemy usunąć
http://localhost:8080/RestExample/resources/clips/delete
przykład wykorzystania:
curl -d "{"""attrName1""":"""preferred-body""","""attr1""":"""stuka""","""attrName2""":"""best-body""","""attr2""":"""stuka""","""certainty""":30}" -H "Content-Type: application/json" -X POST http://localhost:8080/RestExample/resources/clips/delete

Sprawdź pod jaką ścieżką server szuka pliku clipsowego
http://localhost:8080/RestExample/resouarces/clips/path
