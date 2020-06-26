JERSEY-CLIENT
=============

Bauen
-----

```
gradle build
# ... erzeugt build/lib/jersey-client.jar
```

Ausführen
---------

```
java -jar build/libs/jersey-client.jar http://internal.daemons-point.com
```

Ausgabe:

```
302
<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
<html><head>
<title>302 Found</title>
</head><body>
<h1>Found</h1>
<p>The document has moved <a href="https://internal.daemons-point.com/">here</a>.</p>
<hr>
<address>Apache/2.4.7 (Ubuntu) Server at internal.daemons-point.com Port 80</address>
</body></html>
```

Verwendung eines nicht-existierenden HTTP-Proxies
-------------------------------------------------

```
java -Dhttp.proxyHost=localhost -Dhttp.proxyPort=8888 -jar build/libs/jersey-client.jar http://internal.daemons-point.com
```

... liefert die Ausgabe:

```
302
<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
<html><head>
<title>302 Found</title>
</head><body>
<h1>Found</h1>
<p>The document has moved <a href="https://internal.daemons-point.com/">here</a>.</p>
<hr>
<address>Apache/2.4.7 (Ubuntu) Server at internal.daemons-point.com Port 80</address>
</body></html>
```

Es wirkt so, als würden die Proxy-Angaben ignoriert!

Verwendung eines HTTP-Proxies
-----------------------------

### Fenster-1: Dummy-Proxy

```
nc -l 8888
```

### Fenster-2: Abfrage

```
java -Dhttp.proxyHost=localhost -Dhttp.proxyPort=8888 -jar build/libs/jersey-client.jar http://internal.daemons-point.com
```

### Fenster-1: Ausgabe der Proxy-Anfrage

```
GET http://internal.daemons-point.com HTTP/1.1
Accept: text/plain
User-Agent: Jersey/2.25.1 (HttpUrlConnection 1.8.0_252)
Host: internal.daemons-point.com
Proxy-Connection: keep-alive
```

Verwendung eines HTTPS-Proxies
------------------------------

### Fenster-1: Dummy-Proxy

```
nc -l 8888
```

### Fenster-2: Abfrage

```
java -Dhttps.proxyHost=localhost -Dhttps.proxyPort=8888 -jar build/libs/jersey-client.jar https://internal.daemons-point.com
```

### Fenster-1: Ausgabe der Proxy-Anfrage

```
CONNECT internal.daemons-point.com:443 HTTP/1.1
User-Agent: Java/1.8.0_252
Host: internal.daemons-point.com
Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
Proxy-Connection: keep-alive
```

### Unklar: Wird wirklich ein Jersey-Client verwendet?

Die Proxy-Anfrage enthält als User-Agent keinerlei Hinweise auf Jersey.
Deshalb ist unklar, ob wirklich der Jersey-Client verwendet wird.

Etwas Klarheit schafft dies:

- Ablauf wie oben
- Fenster-1: Nach der Ausgabe abbrechen mit Strg-C
- Fenster-2: Eine Exception erscheint!

Hier die Exception:

```
Exception in thread "main" javax.ws.rs.ProcessingException: java.net.SocketException: Unexpected end of file from server
	at org.glassfish.jersey.client.internal.HttpUrlConnector.apply(HttpUrlConnector.java:287)
	at org.glassfish.jersey.client.ClientRuntime.invoke(ClientRuntime.java:252)
	at org.glassfish.jersey.client.JerseyInvocation$1.call(JerseyInvocation.java:684)
	at org.glassfish.jersey.client.JerseyInvocation$1.call(JerseyInvocation.java:681)
	at org.glassfish.jersey.internal.Errors.process(Errors.java:315)
	at org.glassfish.jersey.internal.Errors.process(Errors.java:297)
	at org.glassfish.jersey.internal.Errors.process(Errors.java:228)
	at org.glassfish.jersey.process.internal.RequestScope.runInScope(RequestScope.java:444)
	at org.glassfish.jersey.client.JerseyInvocation.invoke(JerseyInvocation.java:681)
	at org.glassfish.jersey.client.JerseyInvocation$Builder.method(JerseyInvocation.java:411)
	at org.glassfish.jersey.client.JerseyInvocation$Builder.get(JerseyInvocation.java:311)
	at JerseyClient.main(JerseyClient.java:34)
Caused by: java.net.SocketException: Unexpected end of file from server
	at sun.net.www.http.HttpClient.parseHTTPHeader(HttpClient.java:851)
	at sun.net.www.http.HttpClient.parseHTTP(HttpClient.java:678)
	at sun.net.www.protocol.http.HttpURLConnection.doTunneling(HttpURLConnection.java:2061)
	at sun.net.www.protocol.https.AbstractDelegateHttpsURLConnection.connect(AbstractDelegateHttpsURLConnection.java:183)
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream0(HttpURLConnection.java:1570)
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream(HttpURLConnection.java:1498)
	at java.net.HttpURLConnection.getResponseCode(HttpURLConnection.java:480)
	at sun.net.www.protocol.https.HttpsURLConnectionImpl.getResponseCode(HttpsURLConnectionImpl.java:352)
	at org.glassfish.jersey.client.internal.HttpUrlConnector._apply(HttpUrlConnector.java:399)
	at org.glassfish.jersey.client.internal.HttpUrlConnector.apply(HttpUrlConnector.java:285)
	... 11 more
```

Man kann zweifelsfrei erkennen, dass ein Jersey-Client im Einsatz ist!
