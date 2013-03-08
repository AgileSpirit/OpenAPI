Launch the application in a terminal :
<pre><code>$ java -cp target/classes:"target/dependency/*" com.agile.spirit.openapi.Main</code></pre>

## Définition d'une Note

AttributTypeDescription
note_id Integer Identifiant unique 
user_id Integer Référence de l'auteur 
title String Titre de la note 
content String Contenu de la note 
creation_date Datetime Date de création
modification_date Datetime Date de dernière mise à jour 

Exemple d'une Note au format JSON :

<pre><code>
{
  "note_id" : 12345,
  "user_id" : 67890,
  "title" : "Titre de ma note",
  "content" : "Contenu de ma note",
  "creation_date" : 1361715327,
  "modification_date" : 1361819642
}
</code></pre>

## Définition de l'API

<table>
  <tr>
    <td>*Service*</td>
    <td>*Verb*</td>
    <td>*Description*</td>
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
