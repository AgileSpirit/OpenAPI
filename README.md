## Pré-requis

Les programmes suivant doivent être installés et opérationnels :

1. Java JDK 1.7
2. Maven 3.0

## Configurer et lancer l'application

### Dans Eclipse
OpenAPI est approuvée pour Eclipse > 4.2.1.

1. Générer le projet via maven
<pre><code>$ mvn eclipse:eclipse -Dwtpversion=2.0</code></pre>

2. Importer le projet dans Eclipse
<pre><code>"File" -> "Import..." -> "General" -> "Existing Projects into Workspace"</code></pre>
3. Exécuter la classe Main.java
<pre><code>Menu contextuel -> "Run As" -> "Java Application"</code></pre>

### En ligne de commande

<pre><code>$ mvn clean package ; java -cp target/classes:"target/dependency/*" com.agile.spirit.openapi.Main</code></pre>

### Avec Maven

<pre><code>$ mvn clean compile exec:java</code></pre>

Quelque soit la méthode, si tout s'est bien passé, la console / le terminal affiche :
<pre><code>Jersey app started with WADL available at http://0.0.0.0:9998/application.wadl
Hit enter to stop it...</code></pre>

Pour arrêter l'application, dans la console Eclipse ou dans le terminal, presser la touche "Entrée"

## Définition d'une Note

<table>
  <tr>
    <td><b>Attribut</b></td>
    <td><b>Type</b></td>
    <td><b>Description</b></td>
  </tr>
  <tr>
    <td>note_id/</td>
    <td>Integer</td>
    <td>Identifiant unique</td>
  </tr>
  <tr>
    <td>user_id</td>
    <td>Integer</td>
    <td>Référence de l'auteur</td>
  </tr>
  <tr>
    <td>title</td>
    <td>String</td>
    <td>Titre de la note</td>
  </tr>
  <tr>
    <td>content</td>
    <td>String</td>
    <td>Contenu de la note</td>
  </tr>
  <tr>
    <td>creation_date</td>
    <td>Datetime</td>
    <td>Date de création</td>
  </tr>
  <tr>
    <td>modification_date</td>
    <td>Datetime</td>
    <td>Date de dernière mise à jour</td>
  </tr>
</table>

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
    <td>notes?:owner_id</td>
    <td>GET</td>
    <td>Récupérer la liste des notes de l'utilisateur</td>
  </tr>
  <tr>
    <td>notes/all/</td>
    <td>GET</td>
    <td>Récupérer la liste de toutes les notes</td>
  </tr>
  <tr>
    <td>notes/all/export/</td>
    <td>GET</td>
    <td>Exporter la liste de toutes les notes au format PDF</td>
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

## Exemple en ligne

Une version de démonstration est accessible en ligne, sur Heroku : http://openapi.herokuapp.com/notes

## Exemple d'appel JQuery

<pre><code>function loadNotes() {
  $.ajax({
    type: 'GET',
    url: 'http://openapi.herokuapp.com/notes',
    success: function(data) {
      renderNotes(data);
    }
  });  
}

function renderNotes(data) {
  // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
  var list = (data == null || data.note == null) ? [] : (data.note instanceof Array ? data.note : [data.note]);

  $('#noteList .note').remove();
  
  $.each(list, function(index, note) {
    // TEMPLATE, FORMAT AND DISPLAY NOTE
  }
}</code></pre>
