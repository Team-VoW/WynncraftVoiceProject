## [1.13.0](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.12.0...v1.13.0) (2026-01-15)


### ⚠ BREAKING CHANGES

* upgrade to Minecraft version 1.21.11 (#58)

### New Features

* Add reverb functionality ([6c94803](https://github.com/Team-VoW/WynncraftVoiceProject/commit/6c94803d6cb38f68fea94b2969d3a0020bf91e97))
* upgrade to Minecraft version 1.21.11 ([#58](https://github.com/Team-VoW/WynncraftVoiceProject/issues/58)) ([8043f3f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8043f3f7c71c806b7f5eab5b57990f25fbed6b24))


### Bug Fixes

* **Acquiring Credentials:** Cut the black market members lines and one uncut dialogue from Doan ([f3ff4ac](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f3ff4acdff44bd882dc1a53351e3f81f1228dc79))
* **From the Bottom:** fix volume being low and audio being in wrong order ([c44bd46](https://github.com/Team-VoW/WynncraftVoiceProject/commit/c44bd46c9ff4e7c854e44488cf43549f940596e4))
* Improve error message for when an audio fails to fetch ([a7c1b42](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a7c1b4211d13a63224af5bba70349a5ba6fbcba4))
* **Lazarus Pit:** Cut the lines from the undead ([443e3ba](https://github.com/Team-VoW/WynncraftVoiceProject/commit/443e3ba8b6f5cbf35853e5dfbe0bfaba1e41f3d1))
* **Misadventure on the Sea:** Cut the Seaskipper captains lines ([e36903d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/e36903d19673d22d24a077867536f193683c3272))
* sendMessage thread safety ([#60](https://github.com/Team-VoW/WynncraftVoiceProject/issues/60)) ([2ad80e6](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2ad80e61189822b0d5102c8df8bc4d0853c872ac))
* **Temple of Legends:** Cut Garull, Jorkin & Rayshyroths lines ([b8606c0](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b8606c0042338dcdb919a5c6a2c0fddc00aa560c))
* **The Envoy part 1:** Cut the lines from Olivin and the C.S.S Wavebreaker Captain ([a85d5ec](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a85d5ecb661bbfb4a70aa4f5088b5caac814e7c9))

## [1.12.0](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.11.3...v1.12.0) (2025-11-14)


### ⚠ BREAKING CHANGES

* fixed the Sounds updater to properly update

### New Features

* **OceanCitizens:** Added voice acting for most Ocean citizen ([07a71d8](https://github.com/Team-VoW/WynncraftVoiceProject/commit/07a71d8242cbae70b2d8696c18f6f608cc3689d4))


### Bug Fixes

* fixed the Sounds updater to properly update ([4892bef](https://github.com/Team-VoW/WynncraftVoiceProject/commit/4892bef317b148e79a805fc7e9cee5038177ae63))


### Miscellaneous Chores

* **release:** v1.12.0 [skip ci] ([e2f55db](https://github.com/Team-VoW/WynncraftVoiceProject/commit/e2f55db0c24f492779ed0fe5747dc6c7299bedcd))

## [1.11.3](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.11.2...v1.11.3) (2025-08-27)


### New Features

* Added missing Tasim lines for multiple quests ([68c7c77](https://github.com/Team-VoW/WynncraftVoiceProject/commit/68c7c77c0a162829d6b01f87f94668e2cb3c9ed4))


### Bug Fixes

* **Arachnids Ascent:** fixed one line from Aledar not playing ([45fee9b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/45fee9b5ed519b69107291032f51c747d9c4038e))
* **Canyon Conder:** fixed one linefrom Jankan not playing ([1c4b4d2](https://github.com/Team-VoW/WynncraftVoiceProject/commit/1c4b4d2977ff65568dec90cc6657777dd1eb9ee7))
* **Dark Descent:** fixed one of the Scouts lines not playing ([f7c2fd2](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f7c2fd2c95af9c175cd12e036fe1c304679ac60e))
* **Maltic's Well:** Fix Rynend's last line not playing ([f4b4a58](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f4b4a586fb168220287c1e6de294bbd3bcadc070))
* **Maltic's Well:** fixed one not playing line from the Witch and cut up audio in the cutscene ([552c7de](https://github.com/Team-VoW/WynncraftVoiceProject/commit/552c7ded321d127fef2c10272085d7abe5b019e4))
* redownload audio file if it gets corrupted. ([b6a2586](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b6a25861e8a24daa1ef53fc6ec9a844d30194cb2))


### Miscellaneous Chores

* **release:** v1.11.3 [skip ci] ([5418f5f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5418f5fdb12695502e2c731938123d286faa1421))

## [1.11.2](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.11.1...v1.11.2) (2025-07-08)


### Bug Fixes

* fixed 6 lines not playing (spanning multiple quests) ([ffb9779](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ffb97790f94ee7792293a13c5ea6426b79b70bf7))


### Miscellaneous Chores

* **release:** v1.11.2 [skip ci] ([ad3ddb8](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ad3ddb8eb993bd36a817f17ebf3275e69bcb3483))


### Build System

* exclude PipeDecoder class due to CurseForge restrictions ([d462bcf](https://github.com/Team-VoW/WynncraftVoiceProject/commit/d462bcf262e920e533d274d10764f94b5585f479))

## [1.11.1](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.11.0...v1.11.1) (2025-07-07)


### New Features

* **A Marauders Dues:** Added all lines (all had been changed) ([fde7949](https://github.com/Team-VoW/WynncraftVoiceProject/commit/fde7949e6e4ec40811f35985f2446bfefa7fa760))
* **Dark Descent:** Added new lines from Lost Soul ([104d97e](https://github.com/Team-VoW/WynncraftVoiceProject/commit/104d97e3f508d3eb07b98283cafd5d9a05a2742a))


### Bug Fixes

* **Frost Bite:** Cut up two of Eppo's dialogues which where not cut ([eea5f67](https://github.com/Team-VoW/WynncraftVoiceProject/commit/eea5f6741b1fd2679ce1c37738ab36967c40d7bf))
* Made the "Play all sounds on player" setting work correctly to disable 3d audio playback. ([9893286](https://github.com/Team-VoW/WynncraftVoiceProject/commit/9893286fdabcecce642d35a650e299e280322d06))
* **Mushroom Man:** fixed 5 not playing lines ([e7aa66f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/e7aa66f156aad064f3b4b38f5b3b82af228c4e0b))
* **Recover the past:** Fixed 3 Tasim lines not playing ([5563c47](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5563c47874f0971a9d97b383298d0501a9a18058))


### Miscellaneous Chores

* **release:** v1.11.1 [skip ci] ([813179a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/813179a50c1b2ec6288dcb9ff19fb2b3601e5885))


### Build System

* Configure shadow plugin to relocate TarsosDSP dependencies ([8c54b19](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8c54b1954e86724d3ec703c772b56ddfe1fac878))
* Copy over the latest sounds.json to be bundled with the mod ([dc1c8fa](https://github.com/Team-VoW/WynncraftVoiceProject/commit/dc1c8fa197fa95852f8508f52fb191e30dfdd972))

