# MockWebServer - Testing HTTP Interactions in Spring

## Lernziele

- [ ] Verstehen, was der MockWebServer ist und warum er in der Entwicklung wichtig ist.
- [ ] Erlernen der Verwendung von `@beforeEach` und `@afterEach` Annotations in Spring-Tests.
- [ ] Kennenlernen von Setup- und Teardown-Methoden in Testklassen.
- [ ] Integration des MockWebServer in ein Spring-Projekt mit Maven.
- [ ] Konfiguration des MockWebServers für unterschiedliche Testfälle.
- [ ] Schreiben eines Tests mithilfe des MockWebServers, um HTTP-Interaktionen zu überprüfen.
- [ ] Anwenden von `MockResponse` und `MockWebServer.enqueue()` für präzise Testergebnisse.

## Einführung

Der **MockWebServer** ist ein mächtiges Werkzeug, das uns ermöglicht, HTTP-Anfragen und -Antworten in unseren Tests zu simulieren. Dies ist besonders nützlich, um unabhängige und zuverlässige Tests für Anwendungen zu schreiben, die mit externen APIs oder Diensten interagieren. Wir werden lernen, wie wir den MockWebServer in Spring-Tests einbinden und nutzen können.

## Einbinden und Konfigurieren des MockWebServers

Um den MockWebServer in unserem Spring-Projekt zu verwenden, müssen wir ihn zuerst als Abhängigkeit in unser Maven-Projekt einbinden. Fügen Sie dazu die folgende Abhängigkeit in Ihre `pom.xml`-Datei ein:

```xml
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>mockwebserver</artifactId>
    <version>4.9.1</version>
    <scope>test</scope>
</dependency>
```

Nachdem wir die Abhängigkeit hinzugefügt haben, können wir den MockWebServer in unseren Tests verwenden.

## Testen mit dem MockWebServer

### Setup und Teardown

Bevor wir den MockWebServer verwenden, können wir die `@BeforeEach`- und `@AfterEach`-Annotationen verwenden, um Setup- und Teardown-Aufgaben für unsere Tests durchzuführen. Zum Beispiel:

```java
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyApiTest {

    private MockWebServer mockWebServer;

    @BeforeEach
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @DynamicPropertySource
    static void backendProperties(DynamicPropertyRegistry registry) {
        registry.add("rickandmorty.url", () -> mockWebServer.url("/").toString());
    }


    // Weitere Testmethoden...
}
```

### Schreiben eines Tests

Wir können nun den MockWebServer verwenden, um einen Test für unsere HTTP-Interaktionen zu schreiben. Hier ist ein Beispiel, wie wir den MockWebServer verwenden können, um eine erfolgreiche Antwort zu simulieren:

```java
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;

@SpringBootTest
public class MyApiTest {

    private MockWebServer mockWebServer;

    @BeforeEach
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @DynamicPropertySource
    static void backendProperties(DynamicPropertyRegistry registry) {
        registry.add("rickandmorty.url", () -> mockWebServer.url("/").toString());
    }

    @Test
    public void testApiCall() throws IOException {
        
        mockWebServer.enqueue(new MockResponse()
                .setBody("""
                            [{
                                "name": "Mayo",
                                "id": "1"
                            }]
                        """)
                .addHeader("Content-Type", "application/json"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/items/"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                            "name": "Mayo",
                            "id": "1"
                        }]
                        """));
    }
}
```

> 💡 Achtet darauf, die tatsächliche Anfrage-URL basierend auf der generierten Basis-URL des MockWebServers zu erstellen.

```
@Service
public class RickAndMortyService {

    private final WebClient webClient;

    public RickAndMortyService(@Value("${rickandmorty.url}") String url) {
        this.webClient = WebClient.create(url);
    }

```

## Ressourcen

- [OkHttp MockWebServer Dokumentation](https://github.com/square/okhttp/tree/master/mockwebserver)
- [Spring Testing Dokumentation](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html)
