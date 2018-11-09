# OAuth2.0 Authorization Server


> @see https://openid-foundation-japan.github.io/rfc6749.ja.html

***
## Authorization

authorization end point

```
http://localhost:8100/provider/oauth/authorize
```

request url

```
http://localhost:8100/provider/oauth/authorize?client_id={client_id}&redirect_uri={redirect_url}&response_type=code&scope={scope}&state={state}
```

ex)

```
http://localhost:8100/provider/oauth/authorize?client_id=client_id&redirect_uri=http://localhost:8080/client/api/simple/client&response_type=code&scope=read%20write&state=VAGkIg
```

***
## token endpoint

end point

```
http://localhost:8100/provider/oauth/token
```

- authorization code grant

```
curl -i -X POST \
   -H "Authorization:Basic Y2xpZW50X2lkOmNsaWVudF9zZWNyZXQ=" \
   -H "Content-Type:application/x-www-form-urlencoded" \
 'http://localhost:8100/provider/oauth/token?response_type=code&client_id=client_id&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fclient%2Fapi%2Fsimple%2Fclient&scope=read%2Bwrite&state=state_hoge&grant_type=authorization_code'
```

- implicit grant
> not support


- client credential grant

```sh
curl -i -X POST \
   -H "Authorization:Basic Y2xpZW50X2lkOmNsaWVudF9zZWNyZXQ=" \
   -H "Content-Type:application/x-www-form-urlencoded" \
 'http://localhost:8100/provider/oauth/token?scope=read%2Bwrite&grant_type=client_credentials'
```

- resource owner password credential grant

```sh
curl -i -X POST \
   -H "Authorization:Basic Y2xpZW50X2lkOmNsaWVudF9zZWNyZXQ=" \
   -H "Content-Type:application/x-www-form-urlencoded" \
 'http://localhost:8100/provider/oauth/token?username=client_id&password=client_secret&scope=read%2Bwrite&grant_type=password'
```
