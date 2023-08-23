```tex
POST http://10.150.210.21:8080/services/workorder/instance/uploadAttachment
Content-Type: multipart/form-data; boundary=WebAppBoundary
X-Auth-Token: c3efefd6e930451f8ed56ae7386157a0

--WebAppBoundary
Content-Disposition: form-data; name="instanceId"
Content-Type: text/plain

155
--WebAppBoundary
Content-Disposition: form-data; name="type"
Content-Type: text/plain

1
--WebAppBoundary
Content-Disposition: form-data; name="userId"
Content-Type: text/plain

9de90ca4048141caaba1ec4e3afa868e
--WebAppBoundary
Content-Disposition: form-data; name="files"; filename="Lark20201204183701.png"
Content-Type: image/png

< C:/Users/Administrator/Desktop/Lark20201204183701.png
--WebAppBoundary--
```

```tex
POST http://hacmes.dlgona.com/loginServlet
Content-Type: application/json

{
  "name": "201412009",
  "password": "201412009"
}
```