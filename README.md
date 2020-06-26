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
