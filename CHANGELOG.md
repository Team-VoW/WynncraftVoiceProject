## [2.0.3](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v2.0.2...v2.0.3) (2026-05-30)


### Bug Fixes

* Fix latest quest addition not being present in the mod ([a74cb54](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a74cb54489a69b3c45e3e8f342260bdec624e94a))

## [2.0.2](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v2.0.1...v2.0.2) (2026-05-25)


### New Features

* **Revelations in Fall:** Added Voice acting ([a79454f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a79454f6ca29d39f8f91d86b65898dd3cf0fb975))
* **The Cursed One:** Added Voice acting ([ad840cb](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ad840cbc89557d6dcf8eadf99703fafd5b2bdaf3))


### Bug Fixes

* **The Cursed One & Revelations in Fall:** Fixed a bunch of not playing lines ([2862425](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2862425611626153db3ad61d2bc24919605e5f77))


### Miscellaneous Chores

* **release:** v2.0.2 [skip ci] ([09ab4d6](https://github.com/Team-VoW/WynncraftVoiceProject/commit/09ab4d662a82c564d8c593782d42dfc89d8a937a))

## [2.0.1](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v2.0.0...v2.0.1) (2026-05-19)


### Bug Fixes

* Fix version checker showing non-correct upgrade message ([12dd892](https://github.com/Team-VoW/WynncraftVoiceProject/commit/12dd8929accfe0eb279ab34dfe64a7d63699d241))


### Miscellaneous Chores

* **release:** v2.0.1 [skip ci] ([64c3d28](https://github.com/Team-VoW/WynncraftVoiceProject/commit/64c3d28a1d820e6be7630189374356f6bfcf3218))

## [2.0.0](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.14.2...v2.0.0) (2026-05-16)


### ⚠ BREAKING CHANGES

* upgrade to new minor version

### New Features

* **A new Beginning:** Add Voice Acting ([178f1e1](https://github.com/Team-VoW/WynncraftVoiceProject/commit/178f1e1d3ae7908d1cc3c61bc8f6d294f8bdf68c))
* Add custom design for download toast ([#86](https://github.com/Team-VoW/WynncraftVoiceProject/issues/86)) ([85689ec](https://github.com/Team-VoW/WynncraftVoiceProject/commit/85689ec45e57324f0a5e10a4554d9406ad8511b1))
* Add retry when fetching audio to avoid transient errors ([c78990c](https://github.com/Team-VoW/WynncraftVoiceProject/commit/c78990c259c87282e806a338147ddf6a1bca10d2))
* Add support for creating beta builds for Patreon supports and Voice Actors ([#71](https://github.com/Team-VoW/WynncraftVoiceProject/issues/71)) ([e2530d4](https://github.com/Team-VoW/WynncraftVoiceProject/commit/e2530d4b91efd5d76a6e14a858f6bc5e1602a5c4))
* Replaced Robots whose voice came from TTS to Voice actors ([3c6829a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/3c6829a06ef9be6185293fba87ebc5fa746e330a))
* **The Envoy Part 2:** replace Reiva & Corkus Guard VAs and add one missing Corkus Guard line ([cf453a4](https://github.com/Team-VoW/WynncraftVoiceProject/commit/cf453a40bf99336a41617d813f6a3913134f7a6e))


### Bug Fixes

* Add step to startup to clean up corrupt config file which if corrupt caused crashes ([#88](https://github.com/Team-VoW/WynncraftVoiceProject/issues/88)) ([7396481](https://github.com/Team-VoW/WynncraftVoiceProject/commit/7396481839a35eed46c2b31f22e7f555ede2fd65))
* **Beta:** Correctly use beta sounds URL in beta builds ([2928169](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2928169cacdf602959065a6d81d6cc098d64a6b2))
* **cloth-config:** replace deprecated AutoConfig.getConfigScreen with AutoConfigClient ([49574d8](https://github.com/Team-VoW/WynncraftVoiceProject/commit/49574d8dc5efb6e885d0745092619d0bb86e84cc))
* Fix all lines with elements not working ([41655a9](https://github.com/Team-VoW/WynncraftVoiceProject/commit/41655a9608286d85b30fe7e1d4339c738738b7e8))
* Fix lines with quotes not playing ([8130627](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8130627c389e80e65b22afe80b5513a56c704e1e))
* Fix typo in config ([8bb796d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8bb796d1ca8224fe5ff00c8a32404986a77ec172))
* Fix various lines that use mythical languages (such as Wynnic or similar) not playing ([055c0dc](https://github.com/Team-VoW/WynncraftVoiceProject/commit/055c0dcb4c154c7a6ab2636c945d311e882e337c))
* **Order of the Grook:** Fixed one line from the fire teacher not playing ([7b3a10c](https://github.com/Team-VoW/WynncraftVoiceProject/commit/7b3a10ccb9eb0d4ebc7251d2b49b76c965ce9db1))
* **Queens Recruit:** Fixed Gendarme Commander lines and two Sovereign Majin lines not playing ([74152dd](https://github.com/Team-VoW/WynncraftVoiceProject/commit/74152dd05e877330f9b37df1dc51f405a2c154af))
* Silent Ravenger sounds during dialogue. This sound plays loudly with for example Bak'als lines ([27886a2](https://github.com/Team-VoW/WynncraftVoiceProject/commit/27886a2375d07c22757e865b8cf29c9ea6394ade))
* Silent Silverfish death sounds during dialogue. This sound plays loudly during Ankou's lines ([47d5b1c](https://github.com/Team-VoW/WynncraftVoiceProject/commit/47d5b1c8fc99e377c8160c4623f3e78c4d2bf2ff))


### Documentation

* Add documentation on reverb ([3c71436](https://github.com/Team-VoW/WynncraftVoiceProject/commit/3c714369a49e3640761bf3389d556d6ae247ec6a))


### Miscellaneous Chores

* **release:** v2.0.0 [skip ci] ([2a1def9](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2a1def93c035934cb6f29d63112c57a1cf5c8524))
* upgrade to new minor version ([f9dfb63](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f9dfb63aca3588c626f1e1e99f3fd957c16fec45))

## [1.14.2](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.14.1...v1.14.2) (2026-04-13)


### ⚠ BREAKING CHANGES

* **Audio Downloader:** Fix the Audio Downloader not working

### Bug Fixes

* **Audio Downloader:** Fix the Audio Downloader not working ([9176c8d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/9176c8d1fc89cff69ccd09402332192fbc703c18))
* **Line Detection:** Widely decreased the chance of lines starting to play, stopping and then playing again when Wynncraft Server Lags ([#83](https://github.com/Team-VoW/WynncraftVoiceProject/issues/83)) ([71cf060](https://github.com/Team-VoW/WynncraftVoiceProject/commit/71cf060392ae478fd2ad392a26792a94dac14b52))
* **Version Checker:** Fix the Version checker not working properly sometimes because of a race condition ([cb7ddcc](https://github.com/Team-VoW/WynncraftVoiceProject/commit/cb7ddcc962118632ede4357dca7bd098bd3fd531))


### Miscellaneous Chores

* **release:** v1.14.2 [skip ci] ([69c4a0f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/69c4a0f6e423770bb3b4d3318000a67e1e843139))

