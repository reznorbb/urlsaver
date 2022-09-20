# UrlSaver
UrlSaver to program do sciągania zasobów internetowych (stron, plików, itp.) do bazy danych poprzez przekazanie zasobu URL do sciągnięcia.

# Instalacja i uruchamianie
Do uruchomienia aplikacji potrzebna jest Java 17 i wolny port 8080. Aplikacja uruchamiana jest po wywołaniu poniższej komendy:

```bash
mvnw spring-boot:run
```

# Korzystanie z aplikacji

Aplikacja spodziewa się zapytania POST pod adresem:

http://localhost:8080/urlsaver/api/documents/queue

Gdzie w treści zapytania (body) powinien znajdować sie parametr 'url'.

```properties
{
  "url": "https://www.filmweb.pl/"
}
```  

## Swagger

Jedną z metod wysłania zapytania jest użycie interfejsu Swagger'a dostępnego pod adresem:

http://localhost:8080/urlsaver/api/swagger-ui/index.html

# Baza danych
Zaoby internetowe sciągane są do bazy danych H2. Jest to baza danych w pamięci i bedzie przechowywać dane tylko na czas demonstrowania programu.
Baza danych dosępna jest pod adresem http://localhost:8080/urlsaver/api/h2-console/

```properties
{
  JDBC URL: jdbc:h2:mem:db
  User Name: sa
  No password!
}
``` 

# Specyfikacja
## Parametr 'url'

1. Musi być poprawnym adresem URL zgodnie z definicją https://docs.jboss.org/hibernate/validator/5.4/api/org/hibernate/validator/constraints/URL.html
2. Nie może być dłuższy niż 1024 znaków.

## Kolejka

Pobiernie zasobów odbywa się asynchronicznie w stosunku do przyjmowanych żądań. Wykorzystano do tego adnotację Springową @Async.
Zadania asynchroniczne są wewnętrznie kolejkowane przez Spring'a zgodnie z konfiguracją ThreadPoolTaskExecutor, która dla aplikacji została skonfigurowana do działania na jednym wątku i kolejce o wartosci 1000 zadań.

Wartości te konfigurowalną są w application.properties. W przypadku zapełnienia kolejki kolejne zapytania są odrzucane.

## Streaming a wielkość plików
Zasoby są streamowane do bazy danych. Żeby zabezpieczyć się przed zapisywaniem do bazy danych zbyt dużych plików streamowanie przerywane jest po 50MB. 
Wartość ta jest konfigurowalna w application.properties.

## Niedostępne zasoby
Jesli zasob internetowy jest niedostępny tj. nie udało się otworzyc połączenia do zasobu 'URL().openStream()' aplikacja zapisuje ten fakt w logach i przechodzi do następnego zadania w kolejce.

## Rozbudowa aplikacji
Obecnie w aplikacji znajduje się tylko jedna implementacja klasy AbstractDatabaseDocumentSaverService to jest UrlDatabaseDocumentSaverServiceImpl, która wraz z serwisem UrlStreamProviderServiceImpl służy do zapisywania dokumentów na podstawie URL.
W przyszłośći aplikacje można rozbudować do zapisywania dokumentów z innych źródeł.