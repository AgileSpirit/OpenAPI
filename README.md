## Configurer et lancer l'application

### Dans Eclipse
OpenAPI est approuvée pour Eclipse > 4.2.1.

1. Générer le projet via maven
<pre><code>$ mvn eclipse:eclipse -Dwtpversion=2.0</code></pre>

2. Importer le projet dans Eclipse
<pre><code>"File" -> "Import..." -> "General" -> "Existing Projects into Workspace"</code></pre>
3. Exécuter la classe Main.java
<pre><code>Menu contextuel -> "Run As" -> "Java Application"</code></pre>

Si tout s'est bien passé, la console affiche :
<pre><code>Jersey app started with WADL available at http://0.0.0.0:9998/application.wadl
Hit enter to stop it...</code></pre>

Pour arrêter l'application, dans la console Eclipse ou dans le terminal, presser la touche "Entrée"

### En ligne de commande

1. Compiler et packager l'application
<pre><code>$ mvn install</code></pre>

2. Lancer l'application
<pre><code>$ java -cp target/classes:"target/dependency/*" com.agile.spirit.openapi.Main</code></pre>

Si tout s'est bien passé, le terminal affiche :
<pre><code>Jersey app started with WADL available at http://0.0.0.0:9998/application.wadl
Hit enter to stop it...</code></pre>

## Définition d'une Note

AttributTypeDescription
note_id Integer Identifiant unique 
user_id Integer Référence de l'auteur 
title String Titre de la note 
content String Contenu de la note 
creation_date Datetime Date de création
modification_date Datetime Date de dernière mise à jour 

Exemple d'une Note au format JSON :

<pre><code>{
  "note_id" : 12345,
  "user_id" : 67890,
  "title" : "Titre de ma note",
  "content" : "Contenu de ma note",
  "creation_date" : 1361715327,
  "modification_date" : 1361819642
}</code></pre>

## Définition de l'API

<table>
  <tr>
    <td><b>Service</b></td>
    <td><b>Verbe</b></td>
    <td><b>Description</b></td>
  </tr>
  <tr>
    <td>notes/</td>
    <td>GET</td>
    <td>Récupérer la liste des notes de l'utilisateur</td>
  </tr>
  <tr>
    <td>notes/export/</td>
    <td>GET</td>
    <td>Exporter la liste des notes de l'utilisateur au format PDF</td>
  </tr>
  <tr>
    <td>notes/search/{pattern}</td>
    <td>GET</td>
    <td>Récupérer les notes de l'utilisateur dont le titre ou le contenu correspondent à l'expression</td>
  </tr>
  <tr>
    <td>notes/details/{noteId}</td>
    <td>GET</td>
    <td>Récupérer le détail d'une note</td>
  </tr>
  <tr>
    <td>notes</td>
    <td>POST</td>
    <td>Ajouter une note</td>
  </tr>
  <tr>
    <td>notes</td>
    <td>PUT</td>
    <td>Mettre à jour une note</td>
  </tr>
  <tr>
    <td>notes/{noteId}</td>
    <td>DELETE</td>
    <td>Supprimer une note</td>
  </tr>
</table>
