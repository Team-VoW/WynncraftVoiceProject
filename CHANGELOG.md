## [1.9.3](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.9.2...v1.9.3) (2024-09-24)


### Miscellaneous Chores

* try to release v1.9.3 ([afac581](https://github.com/Team-VoW/WynncraftVoiceProject/commit/afac581693266760407e7f2be683540d3d059f42))

## [1.9.2](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.9.1...v1.9.2) (2024-09-24)


### Bug Fixes

* Added custom audio playback system and fix crash with new Text displays ([#36](https://github.com/Team-VoW/WynncraftVoiceProject/issues/36)) ([45ddc2f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/45ddc2f0583fb50a0503cdeca32dc004003f061f))
* Fixed a dialogue form Therck not playing in enzans brother ([62efedd](https://github.com/Team-VoW/WynncraftVoiceProject/commit/62efedd498e8c2cd8621421eb89612a0735da55d))
* Fixed sounds that where stereo and mono which would not play ([fa98520](https://github.com/Team-VoW/WynncraftVoiceProject/commit/fa985204de03455703e7b20de14432e30428389e))
* Removed mix to mono function which causes crashes ([513c1d4](https://github.com/Team-VoW/WynncraftVoiceProject/commit/513c1d415f0e0af4b7c3f59d5d26f8177dffd175))


### Miscellaneous Chores

* fix merge issues between release and main ([#38](https://github.com/Team-VoW/WynncraftVoiceProject/issues/38)) ([ee615ad](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ee615adaefb60f85a85577fe4adcbc97d11582c6))
* **release:** v1.9.2 [skip ci] ([518b82d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/518b82d4e3dc8260916fdb77aca378a5a925fd5e))

## [1.9.1](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.8.2...v1.9.1) (2024-08-30)


### ⚠ BREAKING CHANGES

* Allow VoW to be used with 1.21.1 (along with 1.21) (#33)

### New Features

* Add automated releases to Github Releases and CurseForge with Github Actions ([#30](https://github.com/Team-VoW/WynncraftVoiceProject/issues/30)) ([8fee8ff](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8fee8ffd35df299e708bf042f722b537ef6c485a))
* Add Spotless to make the code base have a standard format, fix up licenses in repository ([#21](https://github.com/Team-VoW/WynncraftVoiceProject/issues/21)) ([dd0a7ac](https://github.com/Team-VoW/WynncraftVoiceProject/commit/dd0a7ac9874dbb806c120bfcf7d7d728da9192df))
* Allow VoW to be used with 1.21.1 (along with 1.21) ([#33](https://github.com/Team-VoW/WynncraftVoiceProject/issues/33)) ([9fbee2b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/9fbee2b0545575b380772b3ab69c7b6fb31e6259))
* Introduce sound_info.json and add everything needed to load it ([#28](https://github.com/Team-VoW/WynncraftVoiceProject/issues/28)) ([b1d2653](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b1d26530b13c12534900643de03e5be6d4af495d))


### Bug Fixes

* Don't upload build artifacts ([#25](https://github.com/Team-VoW/WynncraftVoiceProject/issues/25)) ([0458896](https://github.com/Team-VoW/WynncraftVoiceProject/commit/04588964a4160a88849750c5722200aa422c6240))
* Make format-build.yml use the correct token ([#24](https://github.com/Team-VoW/WynncraftVoiceProject/issues/24)) ([ce37cc6](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ce37cc6b23c13f23df2b60dc0fbe876524998610))
* Removed debugging ([378a9ed](https://github.com/Team-VoW/WynncraftVoiceProject/commit/378a9edd1b08f8c8c4c99a3b958923145e15859a))
* Revert LICENSE (which accidentally got changed during the folder refactors) ([#26](https://github.com/Team-VoW/WynncraftVoiceProject/issues/26)) ([61df23a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/61df23a269fa9731ed0cb6dbcf59b1136fb1370c))


### Documentation

* Improve the project README file ([#29](https://github.com/Team-VoW/WynncraftVoiceProject/issues/29)) ([762c7d8](https://github.com/Team-VoW/WynncraftVoiceProject/commit/762c7d8ae1f30212781df76821d47cb2d220c325))


### Miscellaneous Chores

* Change some sound ids to better follow a format, remove color codes from sound lines ([#27](https://github.com/Team-VoW/WynncraftVoiceProject/issues/27)) ([e5b9af8](https://github.com/Team-VoW/WynncraftVoiceProject/commit/e5b9af8f71f9fd44fe1868208092a1b1b8c392a8))
* **release:** v1.9.0 [skip ci] ([b94be27](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b94be27c5cbb394ea38eac788d08e29ec8a5cad3))
* **release:** v1.9.1 [skip ci] ([482a2dc](https://github.com/Team-VoW/WynncraftVoiceProject/commit/482a2dcbdc3823f125c2b103821d36308a028c57))
* Update dependency versions (mainly Fabric Loader) ([#32](https://github.com/Team-VoW/WynncraftVoiceProject/issues/32)) ([b60285b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b60285b1fc332af3618b54b8d8fe1ab7461fa13c))


### Code Refactoring

* Remove the shared resources folder, move the mod sources into the top level folder ([#22](https://github.com/Team-VoW/WynncraftVoiceProject/issues/22)) ([61c3445](https://github.com/Team-VoW/WynncraftVoiceProject/commit/61c344574335cf7217baba1630e5444f2d2947c8))
* Rewrite the gradle buildscript, update dependencies and populate fabric.mod.json fields automatically ([f747e23](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f747e23784f4a67facbea3c85415b536ea9038a5))

## [1.8.2](https://github.com/Team-VoW/WynncraftVoiceProject/compare/ddf7b698b6dd33b458e11a3a50bda34bfa40c765...v1.8.2) (2024-07-07)


### New Features

* Port to 1.19.4 ([ddf7b69](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ddf7b698b6dd33b458e11a3a50bda34bfa40c765))


### Bug Fixes

* revert accidental change ([ad499fb](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ad499fb2efe80e336d2e466f04088b316ff0fc50))


### Reverts

* Revert "Trained a better model and added player" ([3efc4c5](https://github.com/Team-VoW/WynncraftVoiceProject/commit/3efc4c57f38b08d7e9ad8cd05fab9c88b1b3807e))

