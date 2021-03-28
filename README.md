# SkiResortAPI

Le dossier SkiResortAPI contient un projet Spring Tool Suite/Eclipse.
Pour importer le projet dans STS/Eclipse:

1. Télécharger les fichiers de Github.
2. Choisir File/Import.
3. Choisir Maven/Existing Maven Projects.
4. Sélectionner le dossier SkiReportAPI.
5. Sélectionner le pom et cliquer Finish.

Le dossier Deploy contient un Jar déjà compilé pour lancer l'API. Commande pour lancer le Jar : java -jar SkiResortAPI-1.0.0.jar

URL pour L'API: http://localhost:8080/skiResorts/

Appels possibles:

GET http://localhost:8080/skiResorts/

GET http://localhost:8080/skiResorts/{id}

POST http://localhost:8080/skiResorts/
Body: Objet SkiResort. 
Exemple: 
{
    "name":"Nom",
    "description":"Description"
}

PUT http://localhost:8080/skiResorts/{id}
Body: Objet SkiResort. 
Exemple: 
{
    "name":"Nom",
    "description":"Description"
}

DELETE http://localhost:8080/skiResorts/{id}
