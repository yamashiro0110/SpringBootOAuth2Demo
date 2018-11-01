# OAuth2.0 Authorization Server


> @see https://openid-foundation-japan.github.io/rfc6749.ja.html

***
## Authorization

authorization end point

- `http://localhost:8100/provider/oauth/authorize`

parameter

- response_type 
  - `code`を指定すること
- client_id
  - clientの登録時に発行された値
- redirect_uri
  - clientの登録時に設定したURL
- scope
  - 以下から複数選択可、必ず1つ選択する必要がある
    - read
    - write

authorization request

```
http://localhost:8100/provider/oauth/authorize?client_id={client_id}&redirect_uri={redirect_url}&response_type=code&scope={scope}&state={state}
```

ex)

```
http://localhost:8100/provider/oauth/authorize?client_id=client_id&redirect_uri=http://localhost:8080/client/api/simple/client&response_type=code&scope=read%20write&state=VAGkIg
```
