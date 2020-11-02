# Java CC4F1L Lázár Dániel mérnökinformatikus beadandó

```java
public boolean isValidArtistInLib(String artist) {
   for (Song song : songs) {
      if (song.getArtist().equals(artist)) {
         return true;
      }
   }
   return false;
}
```