# OAuth Resource Server

> https://openid-foundation-japan.github.io/rfc6750.ja.html

***

- resource access

```sh
curl -i -X GET \
   -H "Authorization:Bearer {access_token}" \
 'http://localhost:8090/resource/api/simple/resource'
```

> NOTE: Get Access Token
> [oauth20.authorization/README.md](../oauth20.authorization/README.md)
