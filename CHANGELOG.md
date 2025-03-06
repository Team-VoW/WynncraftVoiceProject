## [1.10.6](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.10.5...v1.10.6) (2025-03-06)


### New Features

* Added new Corrupted Betrayal ([8cdaa16](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8cdaa1641b89a237c16865690b9c40ee2e784bd6))
* Added new Grave Digger quest ([521daeb](https://github.com/Team-VoW/WynncraftVoiceProject/commit/521daeb7c9803c77e1d0ec3f11bb0f72041b6694))
* Added new undergrowth ruins dialogue ([7e414b6](https://github.com/Team-VoW/WynncraftVoiceProject/commit/7e414b69db3ab6eb89169c25008bd44395807c97))
* Allow lines to be registered with no sound line. This will not play anything or cause errors but lead to no line report being made. ([6721029](https://github.com/Team-VoW/WynncraftVoiceProject/commit/672102990a4477e428600fa3a3ddbbbbc5cbef16))


### Bug Fixes

* Added missing kingdom of sand lines from bandit1 and bandit2 ([24b2fd3](https://github.com/Team-VoW/WynncraftVoiceProject/commit/24b2fd391ebe234e9c426672c2462b0e0e6d95a1))
* Change sounds.json to being fetched from azure instead of git and fixed an error that can happen on first start if config was not present. ([45cee19](https://github.com/Team-VoW/WynncraftVoiceProject/commit/45cee19809dae17ae9ba4dd2684c5249c363f281))
* Fixed 16 not working lines in kingdom of sand ([ad3c1dc](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ad3c1dcdb178c8709e070d77b0286fc2d53295e2))
* Fixed a bunch of not playing lines over many quests ([a663490](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a6634907136700e4f7c81253fbc1ebc46688a45f))
* Fixed a few Talking mushroom lines that where not playing ([1bd8aee](https://github.com/Team-VoW/WynncraftVoiceProject/commit/1bd8aee99b71d525d4b913a53deef490ada0e128))
* Fixed a line not playing in redbeards booty ([32d8833](https://github.com/Team-VoW/WynncraftVoiceProject/commit/32d88335b02b3247f5cef44d6c7fd47d7c1d899d))
* Fixed not playing lines in Elemental Exercise, Maltic's well, Grave Digger, Pit of the dead and taking the tower ([c4518e9](https://github.com/Team-VoW/WynncraftVoiceProject/commit/c4518e9144186de1fe8d8a1a111a83098b31b385))
* Fixed some lines not playing from the Alchemist Recover the Past ([efbd094](https://github.com/Team-VoW/WynncraftVoiceProject/commit/efbd0940724620d29b54372a0296bfc0a0f8ea8f))
* Fixed some lines not playing in Green Gloop and added info about some combined lines for Ice barrows ([a0c535a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a0c535a39f30d525847eb74977b6f53f00d2f35c))
* Fixed some lines not playing in stable story ([b12318b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b12318b4c956c59501dfb4245a4bdc3730b1ecdc))
* Fixed some lines not playing in wrath of the mummy ([5bd4e2e](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5bd4e2ed9bca6395cac0840e51f582291a2405d3))


### Miscellaneous Chores

* Added different sounds.json which is used for the version on azure. Contains ALL seaskipper lines we are aware of. ([c1823c3](https://github.com/Team-VoW/WynncraftVoiceProject/commit/c1823c3176e5a09f5001c99634327aa3527530b3))
* Correctly added all new sound info to the sounds.json that goes on azure ([ca7186d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ca7186d0334addcd065f5f4f054beccca5b91dfb))
* Moved the sounds.json file into the sounds folder ([d2cdbdd](https://github.com/Team-VoW/WynncraftVoiceProject/commit/d2cdbdd6bae1b257378fc48cb0b72c82b0513ed4))
* Removed some accidental added stopSounds lines with no file which where added to the normal sounds.json and not the new one ([ba4bc45](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ba4bc457481cd3c3ddac86b5231969ea646d4ac3))

## [1.10.5](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.10.4...v1.10.5) (2025-03-02)


### New Features

* Added a stopSounds settings to define if a sound should stop playing audio when encountered ([95b4695](https://github.com/Team-VoW/WynncraftVoiceProject/commit/95b4695ae9e98a973880d23097efd10be267556e))
* Allow multiple sound lines to be played at the same time ([683662f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/683662f18d75a24a52fc3a9cd4748570959fd840))


### Bug Fixes

* Fixed a few hundred lines that where not playing because of special characters ([b354e70](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b354e702526865f7bad905c706b297d395d2c963))
* Fixed an issue where the bundled sounds.json is not read correctly if download failed leading to no sounds at all playing for some people the first time they use the mod ([f229576](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f2295761ca77e221a11243f2ee77ce8453fde914))
* Fixed some lines not playing in Ragnis ultimate discovery and made some of the lines be able to overlap ([b6d22b6](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b6d22b6734cc9db0266513ea00e040a8ccaf9ebf))


### Miscellaneous Chores

* **release:** v1.10.5 [skip ci] ([026b45d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/026b45da78f90045268694a38b0d264a199832fc))

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

