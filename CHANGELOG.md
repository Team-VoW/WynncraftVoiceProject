## [1.14.0](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.13.1...v1.14.0) (2026-04-04)


### New Features

* Add a configurable setting to decide how many characters we should try matching to detect the dialogue early ([c18e047](https://github.com/Team-VoW/WynncraftVoiceProject/commit/c18e047ec677568052d994440923ff2e2a2cc32d))
* add a configurable setting to decide if we should try matching the dialogue early ([1822390](https://github.com/Team-VoW/WynncraftVoiceProject/commit/182239082d1dfdeea0c5610c98de3376fb43dc63))
* If a wrong audio starts playing, stop it when we detect it was wrong. ([2d883e5](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2d883e58dc8975e6f5797cdeb8065a3f8d7139fc))
* play dialogues early ([0240f49](https://github.com/Team-VoW/WynncraftVoiceProject/commit/0240f49ecd412edb4d5f8fe55d3f0144a4a9dbbb))
* replace player name correctly in new dialogue system ([3585fa6](https://github.com/Team-VoW/WynncraftVoiceProject/commit/3585fa6f88135b3a1287841bd91d6b3b83e9b0fa))


### Bug Fixes

* Fix Debug sounds.json working with a remote sounds file ([2813bee](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2813beec096768b20488e5307bbf4441eeb0b0b3))
* Fix lines that have no NPC name not playing ([f92c40b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f92c40b71321c132b34216ab9f88d426fb8a8ada))
* Set default minimum characters to 15 ([698700d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/698700d6eb8ae5ba7bb7ada359c94c3c57db3c88))


### Miscellaneous Chores

* Update developer cloth config and mod menu version ([5a9f76b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5a9f76b51bb2f10c80a7c270628c1cfb22800875))


### Code Refactoring

* remove old line parsing that was needed for before fruma ([e0f7bc1](https://github.com/Team-VoW/WynncraftVoiceProject/commit/e0f7bc18505d6d4e8cd7b7df595eb55b33c33f42))

## [1.13.1](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.13.0...v1.13.1) (2026-03-26)


### New Features

* Add support for new Fruma dialogue system.  ([#64](https://github.com/Team-VoW/WynncraftVoiceProject/issues/64)) ([0c17634](https://github.com/Team-VoW/WynncraftVoiceProject/commit/0c17634e6df5585d98a5a21d2291502b031f37f1))
* Replace recordings from Natedog with newer recordings for kid & officer in Acquiring credentials & missing child in Flight in Distress ([7e43f2f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/7e43f2fa87604ba37a8c92c2974f2ca273d1c823))
* **volume control:** Added voice volume control ([#63](https://github.com/Team-VoW/WynncraftVoiceProject/issues/63)) ([43de72c](https://github.com/Team-VoW/WynncraftVoiceProject/commit/43de72c1a505f3cfbdfab1b9da514e4518fdd8cf))


### Bug Fixes

* **A journey further:** made most of Aledars lines in the last scene play correctly ([03375d4](https://github.com/Team-VoW/WynncraftVoiceProject/commit/03375d425ebae7fbb1b858c02ec8a3f15ae5db12))
* **Audio Downloader:** fix audio downloader being broken & get the audio manifest from a blob instead ([95fac15](https://github.com/Team-VoW/WynncraftVoiceProject/commit/95fac15dcde2bfa2d87e481a5bdb530afff1467c))
* **Developer:** enable DevAuth in loom runs and add Tarsos DSP dependencies for developer runs ([bc46fad](https://github.com/Team-VoW/WynncraftVoiceProject/commit/bc46fad8bd8ab55597bea6d4878505dd285ecd79))
* fix villager sounds not being blocked ([#62](https://github.com/Team-VoW/WynncraftVoiceProject/issues/62)) ([b335c67](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b335c678a6aee8dd483a540f413aa470ed3298e8))


### Miscellaneous Chores

* **release:** v1.13.1 [skip ci] ([4885e1a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/4885e1ad37b237fc62e06c886143707c08d8b205))

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


### Miscellaneous Chores

* **release:** v1.13.0 [skip ci] ([edcb05c](https://github.com/Team-VoW/WynncraftVoiceProject/commit/edcb05cb93e1e39562a1ae7d59a1494ae46f3112))

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

