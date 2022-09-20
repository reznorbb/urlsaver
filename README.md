# UrlSaver
UrlSaver to program do sciągania zasobów internetowych (stron, plików, itp.) do bazy danych poprzez przekazanie strony URL do sciągnięcia.

# Instalacja i uruchamianie
Do uruchomienia aplikacji potrzebna jest Java 17 i wolny port 8080. Użyj poniższej komendy po sciągnięciu repozytorium.

```bash
mvnw spring-boot:run
```

# Korzystanie z aplikacji

Aplikacja spodziewa się zapytania POST pod adresem:

[Link] http://localhost:8080/urlsaver/api/documents/queue

Gdzie w treści (body) zapytania powinienen znajdować sie parametr 'url'.

```properties
{
  "url": "https://www.filmweb.pl/"
}
```  

## Swagger

Najprostszą metodą wysłania zapytania jest użycie interfejsu Swagger'a dostępnego pod adresem:

[Link] http://localhost:8080/urlsaver/api/swagger-ui/index.html

