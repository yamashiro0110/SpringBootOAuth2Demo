# OAuth2.0 Authorization Server

***

authorization request

```
http://localhost:8100/provider/oauth/authorize?client_id={client_id}&redirect_uri={redirect_url}&response_type={response_type}&scope={scope}&state={state}
```

ex)

```
http://localhost:8100/provider/oauth/authorize?client_id=client_id&redirect_uri=http://localhost:8080/client/api/simple/client&response_type=code&scope=read%20write&state=VAGkIg
```
