## [1.10.5](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.10.4...v1.10.5) (2025-03-02)


### New Features

* Added a stopSounds settings to define if a sound should stop playing audio when encountered ([95b4695](https://github.com/Team-VoW/WynncraftVoiceProject/commit/95b4695ae9e98a973880d23097efd10be267556e))
* Allow multiple sound lines to be played at the same time ([683662f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/683662f18d75a24a52fc3a9cd4748570959fd840))


### Bug Fixes

* Fixed a few hundred lines that where not playing because of special characters ([b354e70](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b354e702526865f7bad905c706b297d395d2c963))
* Fixed an issue where the bundled sounds.json is not read correctly if download failed leading to no sounds at all playing for some people the first time they use the mod ([f229576](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f2295761ca77e221a11243f2ee77ce8453fde914))
* Fixed some lines not playing in Ragnis ultimate discovery and made some of the lines be able to overlap ([b6d22b6](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b6d22b6734cc9db0266513ea00e040a8ccaf9ebf))

## [1.10.4](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.10.3...v1.10.4) (2025-02-24)


### New Features

* Add configuration option to enable or disable sound downloads. Default setting is now false meaning all files are streamed. ([4328e4b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/4328e4b81dfcf51bdb8694681c4d115772ec576b))
* Add fallback mechanism for fetching audio from multiple servers if the first fails. ([d671450](https://github.com/Team-VoW/WynncraftVoiceProject/commit/d6714506004c1b09960a5e48bc7429b9377419f0))
* Client now fetches active audio streaming servers. ([240141d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/240141d58dabaa46119476b6377b320a52ac1989))
* The client now detects the closest server to stream the audio from to minimize latency. ([5de2cea](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5de2cea4b12bbe7095a2dac820b61c8dd228f5c2))


### Bug Fixes

* Cleaned up the mods configuration screen and added more tooltips ([1aed2dd](https://github.com/Team-VoW/WynncraftVoiceProject/commit/1aed2dd8d2519eec5bc9bb693ee3968e43d5de52))
* Only try playing local audio file if download sound setting is enabled ([86b1f92](https://github.com/Team-VoW/WynncraftVoiceProject/commit/86b1f9286a20cc5e7d021db2a18ac2402453cde5))


### Miscellaneous Chores

* **release:** v1.10.4 [skip ci] ([59d9be8](https://github.com/Team-VoW/WynncraftVoiceProject/commit/59d9be899fc50e5c5874bd66bb02c8a28cbcefe3))

## [1.10.3](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.10.2...v1.10.3) (2025-02-11)


### New Features

* Added missing Desperate Metal voice acting ([95c5907](https://github.com/Team-VoW/WynncraftVoiceProject/commit/95c5907c4649d6f5783d19f19978f535be3141c4))
* Added new kingdom of sand ([ab31fa1](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ab31fa1d68670fa2a28565b790b7623883accd7a))


### Bug Fixes

* Fixed a very rare crash that can happen if you get a tick before your sound engine was started ([5259168](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5259168aba6af3301a772e9078afba42cce38fa7))


### Miscellaneous Chores

* Added log messages that say which files failed to download ([9f8af19](https://github.com/Team-VoW/WynncraftVoiceProject/commit/9f8af1915f02fbcac4d005ec4ed059a6fd2de33f))
* **release:** v1.10.3 [skip ci] ([b71174d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b71174dcf027df0cdc20507985b8a34ab3e2a350))
* Updated game version in release pipeline ([8de90cf](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8de90cfd65ad14799038f81424785fb53f561aa0))

## [1.10.2](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.10.1...v1.10.2) (2025-01-26)


### New Features

* If any audio is not downloaded yet and it's trying to be played it will be streamed. ([0607ea7](https://github.com/Team-VoW/WynncraftVoiceProject/commit/0607ea7b89883ea18f86a0724097cba1e876b370))
* Upgraded to Minecraft version 1.21.4 ([0513b93](https://github.com/Team-VoW/WynncraftVoiceProject/commit/0513b93b831187a92606c9df67f423da07721d6b))


### Miscellaneous Chores

* **release:** v1.10.2 [skip ci] ([457be8a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/457be8a1a15baec879c6e4233dfde7fe2561a500))

## [1.10.1](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.10.0...v1.10.1) (2025-01-25)


### New Features

* Update sounds.json file dynamically to automatically support new updates ([84500f8](https://github.com/Team-VoW/WynncraftVoiceProject/commit/84500f8a36efae936ec71ea409ea43975b3f4298))


### Bug Fixes

* Run download progress when joining Wynn if the main menu was skipped somehow ([237dd73](https://github.com/Team-VoW/WynncraftVoiceProject/commit/237dd7322a8081be4dd5112eb7f0ceccdbb3abf7))


### Miscellaneous Chores

* **release:** v1.10.1 [skip ci] ([a7a4438](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a7a4438353d0354bdcc8c0a4f22f35756425d078))

