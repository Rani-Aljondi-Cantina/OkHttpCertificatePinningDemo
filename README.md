# OkHttpCertificatePinningDemo
Demo of Certificate Pinning feature in OkHttp

Investigating ways of using SocketRocket and OkHttp APIs to validate a Certificate Chain based on URL of an intermediate Certificate Authority. 
Important terms: Keys: In this document, refers to the chain of public keys enclosed with digital certificate. 
Certificate Authority (CA): entity that issues digital certificates; this falls into three categories: root, intermediary, 
and end entity.

Trust chain validation: conventional process of validating certificates,Does not check for CA identifiers 
the same way certificate pinning does.

Pinned Certificate validation: assigns a specific certificate or public key to an intermediate CA. 
Client receiving certificate has a copy of certificate(s) that given certificate has to include to be considered valid.

Verdict: best method of certifying that Bose.com is Intermediary Certificate Authority is by Pinned Certificate Validation.

OkHttp allows pinning with public key hashes


See this page for use cases and functionality.
https://square.github.io/okhttp/3.x/okhttp/okhttp3/CertificatePinner.html

"Warning: Certificate Pinning is Dangerous!

"Pinning certificates limits your server team's abilities to update their TLS certificates. 
By pinning certificates, you take on additional operational complexity and limit your ability to 
migrate between certificate authorities. Do not use certificate pinning without the blessing of your 
server's TLS administrator!"

In order to implement, must synchronize public key hashes in trust stores with pinned public keys with 
intermediate Certificate Authorities.
