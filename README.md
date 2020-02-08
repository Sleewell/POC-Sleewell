# POC-Sleewell
Final proto for the forward

- Proto endormissement
- Nfc
- Reveil
- Spotify

## Spotify
- Il y a des chances que cela marche pas, il faut modifier le client id par celui que vous avez créé sur le site https://developer.spotify.com/dashboard/ et modifier les variables dans les activités SonndsFragment et ProtoActivated
- Dans les settings de l'app sur le dashboard spotify, ajoutez l'uri de redirection : http://com.example.sleewell/callback
- Retrouvez le fingerprint de votre app et ajoutez le sur le site de spotify tjr dans les settings de l'app : https://stackoverflow.com/questions/15727912/sha-1-fingerprint-of-keystore-certificate et le nom du package c'est com.example.sleewell

Et il faudra l'app spotify sur votre tel pour que cela fonctionne voila ! Have fun 
