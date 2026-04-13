## [1.14.2](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.14.1...v1.14.2) (2026-04-13)


### ⚠ BREAKING CHANGES

* **Audio Downloader:** Fix the Audio Downloader not working

### Bug Fixes

* **Audio Downloader:** Fix the Audio Downloader not working ([9176c8d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/9176c8d1fc89cff69ccd09402332192fbc703c18))
* **Line Detection:** Widely decreased the chance of lines starting to play, stopping and then playing again when Wynncraft Server Lags ([#83](https://github.com/Team-VoW/WynncraftVoiceProject/issues/83)) ([71cf060](https://github.com/Team-VoW/WynncraftVoiceProject/commit/71cf060392ae478fd2ad392a26792a94dac14b52))
* **Version Checker:** Fix the Version checker not working properly sometimes because of a race condition ([cb7ddcc](https://github.com/Team-VoW/WynncraftVoiceProject/commit/cb7ddcc962118632ede4357dca7bd098bd3fd531))

## [1.14.1](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.14.0...v1.14.1) (2026-04-09)


### New Features

* **A Journey Home:** Added voice acting ([388df4d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/388df4d6a93332c79521581bff8aa053c7d0d95d))
* Add setting to enter Nickname to allow dialogues to play while having a nick ([5cd128d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5cd128da2428277ae5451ce56706464740ce8f55))
* If a wrong audio starts playing, stop it when we detect it was wrong. ([2d883e5](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2d883e58dc8975e6f5797cdeb8065a3f8d7139fc))
* **Queens Recruit:** Add voice acting for the Queens Recruit quest ([7238057](https://github.com/Team-VoW/WynncraftVoiceProject/commit/723805795ddf87a25b0a293f40eb537b426ddd5f))
* **Queens Recruit:** added two missing lines (One from Reynauld and one from the King of Ragni) ([cb6aadc](https://github.com/Team-VoW/WynncraftVoiceProject/commit/cb6aadcb9983e5b17f66b709b8ea91dc45ff32f8))
* **Queens Recruit:** Added two missing lines & fixed one not playing line from Dr. Picard ([59eaa78](https://github.com/Team-VoW/WynncraftVoiceProject/commit/59eaa78fd802cfc991020570a21576b2fe43ea86))
* remove dialogue lines from dialogues to work with the Fruma update ([7677c47](https://github.com/Team-VoW/WynncraftVoiceProject/commit/7677c47252712efa0340a26f1d0b71c4888af34b))


### Bug Fixes

* Fix Cryptic languages not playing (Wynnic, etc.) ([f3c335b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f3c335b6dfb592a3bdfed81f523c7ad5455d03ce))
* Make the LineReporter work again with the new dialogue system to allow us to get information on what lines need Voice acting ([#81](https://github.com/Team-VoW/WynncraftVoiceProject/issues/81)) ([1e1c9c4](https://github.com/Team-VoW/WynncraftVoiceProject/commit/1e1c9c4c35a9812db019aa4963666d1abcaad715))
* **Queens Recruit:** Fixed a few not playing lines/lines that got cut off mid-way through ([79e65a1](https://github.com/Team-VoW/WynncraftVoiceProject/commit/79e65a19972dc1095a139fe56d21744c2e3f4cf1))
* release being tagged with the wrong minecraft version ([#72](https://github.com/Team-VoW/WynncraftVoiceProject/issues/72)) ([162479f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/162479f0b7dc4b4196aed50ff27ca5dccc55874b))
* Stop villager sounds playing through the voice lines ([#73](https://github.com/Team-VoW/WynncraftVoiceProject/issues/73)) ([d58228e](https://github.com/Team-VoW/WynncraftVoiceProject/commit/d58228ebf0271f3b52a7bcbf12e16ebe752c9c6e))


### Miscellaneous Chores

* **release:** v1.14.1 [skip ci] ([20c7bec](https://github.com/Team-VoW/WynncraftVoiceProject/commit/20c7bec8c4a19c6c4a063002c89b226a14cea95b))
* workflow plugin updates ([#74](https://github.com/Team-VoW/WynncraftVoiceProject/issues/74)) ([e432255](https://github.com/Team-VoW/WynncraftVoiceProject/commit/e4322550589e1d9f963e47b1cdfe642b21eb8267))

## [1.14.0](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.9.3...v1.14.0) (2026-04-03)


### ⚠ BREAKING CHANGES

* upgrade to Minecraft version 1.21.11 (#58)
* fixed the Sounds updater to properly update
* Added broadcast message for when auditions are open

### New Features

* **A Marauders Dues:** Added all lines (all had been changed) ([fde7949](https://github.com/Team-VoW/WynncraftVoiceProject/commit/fde7949e6e4ec40811f35985f2446bfefa7fa760))
* Add a configurable setting to decide how many characters we should try matching to detect the dialogue early ([c18e047](https://github.com/Team-VoW/WynncraftVoiceProject/commit/c18e047ec677568052d994440923ff2e2a2cc32d))
* add a configurable setting to decide if we should try matching the dialogue early ([1822390](https://github.com/Team-VoW/WynncraftVoiceProject/commit/182239082d1dfdeea0c5610c98de3376fb43dc63))
* Add configuration option to enable or disable sound downloads. Default setting is now false meaning all files are streamed. ([4328e4b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/4328e4b81dfcf51bdb8694681c4d115772ec576b))
* Add fallback mechanism for fetching audio from multiple servers if the first fails. ([d671450](https://github.com/Team-VoW/WynncraftVoiceProject/commit/d6714506004c1b09960a5e48bc7429b9377419f0))
* Add reverb functionality ([6c94803](https://github.com/Team-VoW/WynncraftVoiceProject/commit/6c94803d6cb38f68fea94b2969d3a0020bf91e97))
* Add support for new Fruma dialogue system.  ([#64](https://github.com/Team-VoW/WynncraftVoiceProject/issues/64)) ([0c17634](https://github.com/Team-VoW/WynncraftVoiceProject/commit/0c17634e6df5585d98a5a21d2291502b031f37f1))
* Added 144 lines related to the Qira Hive, including many from the Qira and Gale battles, as well as numerous lines form NPCs across the world that mention the Qira Hive. ([bd68923](https://github.com/Team-VoW/WynncraftVoiceProject/commit/bd68923cc5e6da94c04015e72dfc027cc9341120))
* Added a debug setting to change the Path of where the audio is fetched from ([f3160a5](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f3160a5cec26df550872eee0ac50f956c7daf3d4))
* Added a stopSounds settings to define if a sound should stop playing audio when encountered ([95b4695](https://github.com/Team-VoW/WynncraftVoiceProject/commit/95b4695ae9e98a973880d23097efd10be267556e))
* Added Aledar lines for Kings Recruit ([4fbfc52](https://github.com/Team-VoW/WynncraftVoiceProject/commit/4fbfc52542b5376ffccdc8c09ad13fd89508aad1))
* Added Alekin Village citizens ([3fb6226](https://github.com/Team-VoW/WynncraftVoiceProject/commit/3fb622614836c7e66cc5bb24558eb8ac2bf2c98a))
* Added Almuj citizens ([93a5fba](https://github.com/Team-VoW/WynncraftVoiceProject/commit/93a5fba5f8a71d74e3732430c21d1291ddaea800))
* Added auto progress to automatically shift after dialogues. ([f4f1438](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f4f14387e5e8b54d2d87fff1f94a52346d7973f0))
* Added broadcast message for when auditions are open ([eb6d237](https://github.com/Team-VoW/WynncraftVoiceProject/commit/eb6d237e8580247fb46dd39aa2efb94e9938f85b))
* Added Corrupted Village citizens ([9308459](https://github.com/Team-VoW/WynncraftVoiceProject/commit/9308459f7aca8d4d9d5480bb0e28d8c7543a64cb))
* Added Detlas citizens ([7458db1](https://github.com/Team-VoW/WynncraftVoiceProject/commit/7458db19aaa77e32758a76095f4d451c7cc2c7c4))
* Added Iboju Village citizens ([adc4c55](https://github.com/Team-VoW/WynncraftVoiceProject/commit/adc4c554baa0ba911a8e52f2b05d183df6ce84b4))
* Added lines for the juggler ([4bf8d51](https://github.com/Team-VoW/WynncraftVoiceProject/commit/4bf8d5101111477bb86ee0566bc55fed253c617a))
* Added Lusuco citizens ([a0c5fd3](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a0c5fd3033b110c0141b6cfdf3d0a39d7e5d01b6))
* Added Maltic citizens ([8595e03](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8595e034a4b98243c164e15e3ff11b593a23bf1d))
* Added Mesa citizens and Lift mechanic ([0297880](https://github.com/Team-VoW/WynncraftVoiceProject/commit/0297880e1e92e29fdac4db034f495d3d03db81d1))
* Added missing Desperate Metal voice acting ([95c5907](https://github.com/Team-VoW/WynncraftVoiceProject/commit/95c5907c4649d6f5783d19f19978f535be3141c4))
* Added missing lines from the tutorial ([a8c6536](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a8c6536bf01f6f1bb57fbd529a8efd5f84eeaa47))
* Added missing Tasim lines for multiple quests ([68c7c77](https://github.com/Team-VoW/WynncraftVoiceProject/commit/68c7c77c0a162829d6b01f87f94668e2cb3c9ed4))
* Added Nemract citizens ([757f962](https://github.com/Team-VoW/WynncraftVoiceProject/commit/757f96265a7fecea6dfe014eb9cb33f5a9062d07))
* Added Nesaak citizens and Sauna Enjoyer ([d021fb2](https://github.com/Team-VoW/WynncraftVoiceProject/commit/d021fb22f46228fdc68bad3b0f92664dfeefb8e0))
* Added new "A Sandy Scandal" quest ([0cd5fc3](https://github.com/Team-VoW/WynncraftVoiceProject/commit/0cd5fc39ffe8b997507f5b6f6ffb48e1c31f591b))
* Added new Cook Assistant quest ([d71e85a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/d71e85acb7d9a09fceddb57e6a74f5c7081715a0))
* Added new Corrupted Betrayal ([8cdaa16](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8cdaa1641b89a237c16865690b9c40ee2e784bd6))
* Added new Fate of the fallen Telvu ([2029ba5](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2029ba504f867562f319c32a6e926e081eadc6ac))
* Added new Grave Digger quest ([521daeb](https://github.com/Team-VoW/WynncraftVoiceProject/commit/521daeb7c9803c77e1d0ec3f11bb0f72041b6694))
* Added new kingdom of sand ([ab31fa1](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ab31fa1d68670fa2a28565b790b7623883accd7a))
* Added new Kings Recruit (Still missing new Aledar voice acting) ([4c65f7b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/4c65f7b3eda0ef388b9f3ed6253ec27d15730041))
* Added new lines from Private Cob in arachnids ascent ([87a7135](https://github.com/Team-VoW/WynncraftVoiceProject/commit/87a7135d8e7f1ec3c8f1241744561c7cab4ae90f))
* Added new sewers of ragni ([a272912](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a2729122485c0cede15739cb6eb1282b982eb24f))
* Added new undergrowth ruins dialogue ([7e414b6](https://github.com/Team-VoW/WynncraftVoiceProject/commit/7e414b69db3ab6eb89169c25008bd44395807c97))
* Added new wrath of the mummy ([a276ff2](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a276ff261003cb564ba5a88c55b6c00d78068a5e))
* Added Ravine Village villager ([da1b502](https://github.com/Team-VoW/WynncraftVoiceProject/commit/da1b5027422efa67a59edebf8bd16a4791228e92))
* added setting for playback speed ([#51](https://github.com/Team-VoW/WynncraftVoiceProject/issues/51)) ([81ed7d0](https://github.com/Team-VoW/WynncraftVoiceProject/commit/81ed7d0be3ccfb514a2f8a2b6e85cec144359083)), closes [#19](https://github.com/Team-VoW/WynncraftVoiceProject/issues/19)
* Added the new "The Mercenary" quest ([fdf6084](https://github.com/Team-VoW/WynncraftVoiceProject/commit/fdf608454971cf7ffe0829718a798e14c8c8d5b8))
* Added Troms citizens ([ae8872b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ae8872b3dec0308cb4ce01c3cb60a82ecc943899))
* Allow lines to be registered with no sound line. This will not play anything or cause errors but lead to no line report being made. ([6721029](https://github.com/Team-VoW/WynncraftVoiceProject/commit/672102990a4477e428600fa3a3ddbbbbc5cbef16))
* Allow multiple sound lines to be played at the same time ([683662f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/683662f18d75a24a52fc3a9cd4748570959fd840))
* **AudioPlayer:** notify user when Voice/Speech volume is off ([3d0f3a6](https://github.com/Team-VoW/WynncraftVoiceProject/commit/3d0f3a6affc5ff279a4bd678e2fa2be696edcc11))
* Client now fetches active audio streaming servers. ([240141d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/240141d58dabaa46119476b6377b320a52ac1989))
* **Dark Descent:** added new Charon lines ([99bb0ed](https://github.com/Team-VoW/WynncraftVoiceProject/commit/99bb0edc95106077335b45be1c3eae6bf6b2b8cc))
* **Dark Descent:** Added new General Graken lines ([9455e88](https://github.com/Team-VoW/WynncraftVoiceProject/commit/9455e888984860c64ff2614e8dc91fd2d9a56c6b))
* **Dark Descent:** Added new lines from Lost Soul ([104d97e](https://github.com/Team-VoW/WynncraftVoiceProject/commit/104d97e3f508d3eb07b98283cafd5d9a05a2742a))
* **Elemental Exercise:** added 5 new Tasim lines ([dd31ff3](https://github.com/Team-VoW/WynncraftVoiceProject/commit/dd31ff3c6a8891fe70305f796cd0aacdfe366b71))
* If any audio is not downloaded yet and it's trying to be played it will be streamed. ([0607ea7](https://github.com/Team-VoW/WynncraftVoiceProject/commit/0607ea7b89883ea18f86a0724097cba1e876b370))
* Implemented new tunnel trouble lines from miner linton ([770c488](https://github.com/Team-VoW/WynncraftVoiceProject/commit/770c488df47bee083dd65181c2a51b5a5a037e39))
* **Infested Plants:** added missing Ope lines ([cfd4251](https://github.com/Team-VoW/WynncraftVoiceProject/commit/cfd4251193f447966ec792555e71b819205d867e))
* **Infested Plants:** added new Tasim lines ([1f5b299](https://github.com/Team-VoW/WynncraftVoiceProject/commit/1f5b299f79026984eca1a88115e114cfd5cb3f21))
* **Jungle Fever:** added new lines from Worid ([1484644](https://github.com/Team-VoW/WynncraftVoiceProject/commit/148464428b6e704b645c8b5d93b497a9594f2ee2))
* **Lost Soles:** added 16 new Ferndor lines ([b9a31e2](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b9a31e2d5b9f24b18af96a4d6bffe7eec5803edb))
* **Misadventure on the Sea:** added many missing Honip lines ([861479b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/861479b10d2a2fb419a35394096b065f10278ccf))
* **Mushroom Man:** added 17 new Tasim lines ([856dabd](https://github.com/Team-VoW/WynncraftVoiceProject/commit/856dabde9f3db8a2ec02161f1b731deedc0706a4))
* **OceanCitizens:** Added voice acting for most Ocean citizen ([07a71d8](https://github.com/Team-VoW/WynncraftVoiceProject/commit/07a71d8242cbae70b2d8696c18f6f608cc3689d4))
* play dialogues early ([0240f49](https://github.com/Team-VoW/WynncraftVoiceProject/commit/0240f49ecd412edb4d5f8fe55d3f0144a4a9dbbb))
* replace player name correctly in new dialogue system ([3585fa6](https://github.com/Team-VoW/WynncraftVoiceProject/commit/3585fa6f88135b3a1287841bd91d6b3b83e9b0fa))
* Replace recordings from Natedog with newer recordings for kid & officer in Acquiring credentials & missing child in Flight in Distress ([7e43f2f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/7e43f2fa87604ba37a8c92c2974f2ca273d1c823))
* The client now detects the closest server to stream the audio from to minimize latency. ([5de2cea](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5de2cea4b12bbe7095a2dac820b61c8dd228f5c2))
* Update sounds.json file dynamically to automatically support new updates ([84500f8](https://github.com/Team-VoW/WynncraftVoiceProject/commit/84500f8a36efae936ec71ea409ea43975b3f4298))
* upgrade to Minecraft version 1.21.11 ([#58](https://github.com/Team-VoW/WynncraftVoiceProject/issues/58)) ([8043f3f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8043f3f7c71c806b7f5eab5b57990f25fbed6b24))
* Upgraded to Minecraft version 1.21.4 ([0513b93](https://github.com/Team-VoW/WynncraftVoiceProject/commit/0513b93b831187a92606c9df67f423da07721d6b))
* **volume control:** Added voice volume control ([#63](https://github.com/Team-VoW/WynncraftVoiceProject/issues/63)) ([43de72c](https://github.com/Team-VoW/WynncraftVoiceProject/commit/43de72c1a505f3cfbdfab1b9da514e4518fdd8cf))
* **Wrath of the mummy:** Added 6 missing lines from the bandit ([cfadbd1](https://github.com/Team-VoW/WynncraftVoiceProject/commit/cfadbd1a85bc50e5d78318ca9918edf7a903281b))


### Bug Fixes

* **A journey further:** made most of Aledars lines in the last scene play correctly ([03375d4](https://github.com/Team-VoW/WynncraftVoiceProject/commit/03375d425ebae7fbb1b858c02ec8a3f15ae5db12))
* **Acquiring Credentials:** Cut the black market members lines and one uncut dialogue from Doan ([f3ff4ac](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f3ff4acdff44bd882dc1a53351e3f81f1228dc79))
* Added missing kingdom of sand lines from bandit1 and bandit2 ([24b2fd3](https://github.com/Team-VoW/WynncraftVoiceProject/commit/24b2fd391ebe234e9c426672c2462b0e0e6d95a1))
* **Aldorei's Secret Part 1:** Fixed two lines from Korben not playing ([f8fdbc5](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f8fdbc57c09343ea724e954e355cfb07de1b4dad))
* **All Roads to Peace:** Fixed many not playing lines and mixed up lines ([b318af9](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b318af90a11fc2556ab825b2bf3f32704304f278))
* **An Iron Heart part 2:** Fixed two lines not playing from Daxe ([aac4f6d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/aac4f6d5c36302f3555a5fca9e12c537ab3a8e8c))
* **Arachnids Ascent:** Fixed a line from Tasim not playing ([31cc460](https://github.com/Team-VoW/WynncraftVoiceProject/commit/31cc46078e65ac897b82f4c132908b72fc248735))
* **Arachnids Ascent:** fixed one line from Aledar not playing ([45fee9b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/45fee9b5ed519b69107291032f51c747d9c4038e))
* **At the Edge of Decay:** Fixed Laris lines not playing ([40b792b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/40b792b7b0be17a08f18782040358757ced79d23))
* **Audio Downloader:** fix audio downloader being broken & get the audio manifest from a blob instead ([95fac15](https://github.com/Team-VoW/WynncraftVoiceProject/commit/95fac15dcde2bfa2d87e481a5bdb530afff1467c))
* **AudioPlayer:** stop sounds from playing for a millisecond if Voice/Speech is at 0% ([3913e56](https://github.com/Team-VoW/WynncraftVoiceProject/commit/3913e56c1404ddf6fd9d57a33203033833ad7645))
* **Bobs lost soul:** Fixed the blacksmiths lines playing all lines in first line ([bf7e407](https://github.com/Team-VoW/WynncraftVoiceProject/commit/bf7e4078f8c7599526152e27be565bb4058ac853))
* **Canyon Conder:** fixed one linefrom Jankan not playing ([1c4b4d2](https://github.com/Team-VoW/WynncraftVoiceProject/commit/1c4b4d2977ff65568dec90cc6657777dd1eb9ee7))
* Change sounds.json to being fetched from azure instead of git and fixed an error that can happen on first start if config was not present. ([45cee19](https://github.com/Team-VoW/WynncraftVoiceProject/commit/45cee19809dae17ae9ba4dd2684c5249c363f281))
* changed default url for the cdn to use jsdeliver ([89c3723](https://github.com/Team-VoW/WynncraftVoiceProject/commit/89c37238bf301457c343506bd08466677cc05092))
* Changed to using http for audio streaming to possible decrease delay ([cd1c66a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/cd1c66a16f9031cdb502b6b860f1d617cb9102fa))
* Changed update notification message to not include any info about the installer ([4debd86](https://github.com/Team-VoW/WynncraftVoiceProject/commit/4debd864176394c131930e0fdbe584569203a118))
* Cleaned up the mods configuration screen and added more tooltips ([1aed2dd](https://github.com/Team-VoW/WynncraftVoiceProject/commit/1aed2dd8d2519eec5bc9bb693ee3968e43d5de52))
* **Dark Descent:** fixed a few General Graken lines not playing ([312ff4f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/312ff4fbd394132fac46eb78104fb46fc90721aa))
* **Dark Descent:** fixed one of the Scouts lines not playing ([f7c2fd2](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f7c2fd2c95af9c175cd12e036fe1c304679ac60e))
* **Developer:** enable DevAuth in loom runs and add Tarsos DSP dependencies for developer runs ([bc46fad](https://github.com/Team-VoW/WynncraftVoiceProject/commit/bc46fad8bd8ab55597bea6d4878505dd285ecd79))
* Disabled auto progress by default ([806d4cc](https://github.com/Team-VoW/WynncraftVoiceProject/commit/806d4cc6c2d0d2dfd5def71d8344285cd4e474f0))
* **Elemental Exercise:** Fixed a line from elemental exercise not playing ([44de975](https://github.com/Team-VoW/WynncraftVoiceProject/commit/44de9753147d1720a76fb086e1694802829f0cc9))
* **Fate of the Fallen:** fixed one line from Telvu not playing ([67807e0](https://github.com/Team-VoW/WynncraftVoiceProject/commit/67807e0b0d7ddcf5f33a1695d063ee9806dcee09))
* **Fate of the Fallen:** Fixed Telvus last dialogue not playing ([826879a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/826879aa340a2ddf7c8ef4a8f46ecbc480c1086d))
* Fix Debug sounds.json working with a remote sounds file ([2813bee](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2813beec096768b20488e5307bbf4441eeb0b0b3))
* Fix lines that have no NPC name not playing ([f92c40b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f92c40b71321c132b34216ab9f88d426fb8a8ada))
* fix villager sounds not being blocked ([#62](https://github.com/Team-VoW/WynncraftVoiceProject/issues/62)) ([b335c67](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b335c678a6aee8dd483a540f413aa470ed3298e8))
* Fixed 16 not working lines in kingdom of sand ([ad3c1dc](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ad3c1dcdb178c8709e070d77b0286fc2d53295e2))
* Fixed 4 not playing lines in "The Mercenary" and fixed Lintons lines being offset by one ([e267d55](https://github.com/Team-VoW/WynncraftVoiceProject/commit/e267d55d5ae506540746dac23733063bd5598b04))
* fixed 6 lines not playing (spanning multiple quests) ([ffb9779](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ffb97790f94ee7792293a13c5ea6426b79b70bf7))
* Fixed a bunch of not playing lines over many quests ([a663490](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a6634907136700e4f7c81253fbc1ebc46688a45f))
* Fixed a few hundred lines that where not playing because of special characters ([b354e70](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b354e702526865f7bad905c706b297d395d2c963))
* Fixed a few lines not playing in a Sandy Scandal ([2616a66](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2616a66277d45fb8e69f3a246a5bbc4f911b60dd))
* Fixed a few Talking mushroom lines that where not playing ([1bd8aee](https://github.com/Team-VoW/WynncraftVoiceProject/commit/1bd8aee99b71d525d4b913a53deef490ada0e128))
* Fixed a line not playing in redbeards booty ([32d8833](https://github.com/Team-VoW/WynncraftVoiceProject/commit/32d88335b02b3247f5cef44d6c7fd47d7c1d899d))
* Fixed a talking mushroom line not playing ([aa03193](https://github.com/Team-VoW/WynncraftVoiceProject/commit/aa0319346f5d18228a885c2079357eb3f69250c5))
* Fixed a very rare crash that can happen if you get a tick before your sound engine was started ([5259168](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5259168aba6af3301a772e9078afba42cce38fa7))
* Fixed an issue where the bundled sounds.json is not read correctly if download failed leading to no sounds at all playing for some people the first time they use the mod ([f229576](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f2295761ca77e221a11243f2ee77ce8453fde914))
* Fixed lines with player name not working if player is nicked ([2e34a7b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2e34a7b7bc93013922c7922b1c88ad02bee79300))
* fixed many Tasim lines not playing ([2a2c3bb](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2a2c3bbc2f59c3ee998114a476b263342395bfd1))
* Fixed not playing lines in Elemental Exercise, Maltic's well, Grave Digger, Pit of the dead and taking the tower ([c4518e9](https://github.com/Team-VoW/WynncraftVoiceProject/commit/c4518e9144186de1fe8d8a1a111a83098b31b385))
* Fixed some lines not playing from the Alchemist Recover the Past ([efbd094](https://github.com/Team-VoW/WynncraftVoiceProject/commit/efbd0940724620d29b54372a0296bfc0a0f8ea8f))
* Fixed some lines not playing in Green Gloop and added info about some combined lines for Ice barrows ([a0c535a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a0c535a39f30d525847eb74977b6f53f00d2f35c))
* Fixed some lines not playing in Ragnis ultimate discovery and made some of the lines be able to overlap ([b6d22b6](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b6d22b6734cc9db0266513ea00e040a8ccaf9ebf))
* Fixed some lines not playing in stable story ([b12318b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b12318b4c956c59501dfb4245a4bdc3730b1ecdc))
* Fixed some lines not playing in wrath of the mummy ([5bd4e2e](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5bd4e2ed9bca6395cac0840e51f582291a2405d3))
* fixed the Sounds updater to properly update ([4892bef](https://github.com/Team-VoW/WynncraftVoiceProject/commit/4892bef317b148e79a805fc7e9cee5038177ae63))
* Fixed two lines not playing in the mercenary ([03a04c5](https://github.com/Team-VoW/WynncraftVoiceProject/commit/03a04c57878415cf32033e2c3ccb443780594e8e))
* Fixed two lines playing wrong audio and 3 lines not playing in Kings Recruit ([c230008](https://github.com/Team-VoW/WynncraftVoiceProject/commit/c230008e84d4742f25067a1b3b1482f5a586190d))
* Fixed withereheads first line in sewers not playing ([6f7357f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/6f7357f71b4351693d7c0861934c58c0994c579f))
* **From the Bottom:** fix volume being low and audio being in wrong order ([c44bd46](https://github.com/Team-VoW/WynncraftVoiceProject/commit/c44bd46c9ff4e7c854e44488cf43549f940596e4))
* **Frost Bite:** Cut up two of Eppo's dialogues which where not cut ([eea5f67](https://github.com/Team-VoW/WynncraftVoiceProject/commit/eea5f6741b1fd2679ce1c37738ab36967c40d7bf))
* **Frost Bite:** Fixed a line from Eppo not playing ([1d5e880](https://github.com/Team-VoW/WynncraftVoiceProject/commit/1d5e880f097b0ff42554388d3178d4fe63c69f43))
* **Grave Digger:** Fixed Sayleros'es post quest line not playing ([d414308](https://github.com/Team-VoW/WynncraftVoiceProject/commit/d4143083228c2c4c556c431e921b59c64d148970))
* **Hollow Serenity:** Fixed two lines not playing ([1c87f2b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/1c87f2b9ff48f053d06adc67ec5142aa9e503e45))
* Improve error message for when an audio fails to fetch ([a7c1b42](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a7c1b4211d13a63224af5bba70349a5ba6fbcba4))
* **Jofash Docks:** Fixed the Bread Man's dialogue being offset by one line from around line 60 ([eb07ecd](https://github.com/Team-VoW/WynncraftVoiceProject/commit/eb07ecd8185a1d32727326174f6446d7134f1a5a))
* **Jungle Fever:** Fixed a few of Worids lines not playing ([2b7ad6a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2b7ad6a5967e0fbb77d645bfbf28343f8495b705))
* **Kings Recruit:** fix one of Aledars lines not playing ([7ddc20a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/7ddc20a53980cfb9b242e45c48bd4dcbfb64ddbf))
* **Kings Recruit:** Fixed two lines from Aledar & Tasim not playing ([f9c52f4](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f9c52f4ca3f3131398dba77a18d7cd66548e57a0))
* **Lazarus Pit:** Cut the lines from the undead ([443e3ba](https://github.com/Team-VoW/WynncraftVoiceProject/commit/443e3ba8b6f5cbf35853e5dfbe0bfaba1e41f3d1))
* Made the "Play all sounds on player" setting work correctly to disable 3d audio playback. ([9893286](https://github.com/Team-VoW/WynncraftVoiceProject/commit/9893286fdabcecce642d35a650e299e280322d06))
* **Maltic's Well:** Fix Rynend's last line not playing ([f4b4a58](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f4b4a586fb168220287c1e6de294bbd3bcadc070))
* **Maltic's Well:** fixed one not playing line from the Witch and cut up audio in the cutscene ([552c7de](https://github.com/Team-VoW/WynncraftVoiceProject/commit/552c7ded321d127fef2c10272085d7abe5b019e4))
* **Memory Paranoia:** cut Atisun's lines ([867c507](https://github.com/Team-VoW/WynncraftVoiceProject/commit/867c507204ffeb2b253982279bdc88c99f237993))
* **Memory Paranoia:** Fixed two not playing lines ([795ac0c](https://github.com/Team-VoW/WynncraftVoiceProject/commit/795ac0cde5c7c98c8f5675770f3a4a4c5ab4522b))
* **Misadventure on the Sea:** Cut the Seaskipper captains lines ([e36903d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/e36903d19673d22d24a077867536f193683c3272))
* **Murder Mystery:** cut Valimares lines ([5b7c9bb](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5b7c9bb7b4d97b042cdb4be1508b99c88638aafc))
* **Mushroom Man:** fixed 5 not playing lines ([e7aa66f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/e7aa66f156aad064f3b4b38f5b3b82af228c4e0b))
* Only try playing local audio file if download sound setting is enabled ([86b1f92](https://github.com/Team-VoW/WynncraftVoiceProject/commit/86b1f9286a20cc5e7d021db2a18ac2402453cde5))
* **Order of the Grook:** Fixed one of Headmasters lines not playing ([e3a1467](https://github.com/Team-VoW/WynncraftVoiceProject/commit/e3a1467e88bd3a49aec45e82e326163ed81a3687))
* Re-fetch the sounds.json file in case it was deleted. ([8d2ab49](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8d2ab499bd7369e327382fda2afdf337b1de959d))
* **Recover the past:** Fixed 3 Tasim lines not playing ([5563c47](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5563c47874f0971a9d97b383298d0501a9a18058))
* redownload audio file if it gets corrupted. ([b6a2586](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b6a25861e8a24daa1ef53fc6ec9a844d30194cb2))
* Removed old unused Ragon voice acting from old Elemental Exercise ([943d372](https://github.com/Team-VoW/WynncraftVoiceProject/commit/943d37262ed2a2f98f55a41b6c7cba332f33a577))
* **Rise of the Quartron:** Fixed one of Naseas lines not playing ([54bd42b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/54bd42b3ece9607280fc8b3936f3cf597116c8c2))
* Run download progress when joining Wynn if the main menu was skipped somehow ([237dd73](https://github.com/Team-VoW/WynncraftVoiceProject/commit/237dd7322a8081be4dd5112eb7f0ceccdbb3abf7))
* sendMessage thread safety ([#60](https://github.com/Team-VoW/WynncraftVoiceProject/issues/60)) ([2ad80e6](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2ad80e61189822b0d5102c8df8bc4d0853c872ac))
* Set default minimum characters to 15 ([698700d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/698700d6eb8ae5ba7bb7ada359c94c3c57db3c88))
* specify branch for sound sync workflow ([a203c4e](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a203c4ed767bd5acb2ea10cfdefde70518319022))
* **Stable Story:** Fixed Enksers two last lines not playing ([282a08d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/282a08dac070a79f7c7b77d65edf8cafd341dd2a))
* **Stable Story:** fixed two lines from Enkser not playing ([8455c26](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8455c26c8c97c718e1443817b6182f2f5c37105c))
* **Taking the tower:** Fixed a line from Aledar not playing ([98c5150](https://github.com/Team-VoW/WynncraftVoiceProject/commit/98c51501c4e4aa2f600bcc6300e26679e9183ede))
* **Temple of Legends:** Cut Garull, Jorkin & Rayshyroths lines ([b8606c0](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b8606c0042338dcdb919a5c6a2c0fddc00aa560c))
* **Temple of Legends:** Fixed 3 lines from Kelight not playing ([31158c2](https://github.com/Team-VoW/WynncraftVoiceProject/commit/31158c2446e321abc6262d4b680dc856f06f872d))
* **The Envoy part 1:** Cut the lines from Olivin and the C.S.S Wavebreaker Captain ([a85d5ec](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a85d5ecb661bbfb4a70aa4f5088b5caac814e7c9))
* **The Passage:** Fixed one of Ildans lines not playing ([d0e247f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/d0e247f8f66558a2994fbbb81e13a90c2a104c2e))
* **The Ultimate Weapon:** Fixed a line from Gogedar not playing ([4ebc9f2](https://github.com/Team-VoW/WynncraftVoiceProject/commit/4ebc9f28ed07ec6e3a62e3445d0404c386915892))
* **Wrath of the Mummy:** Fixed bandits first 4 lines not playing. ([5df73cb](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5df73cb0518422be019e207239f6dbbf027e1666))


### Miscellaneous Chores

* Added a file checker script to make sure all files are correctly present and registered ([6337944](https://github.com/Team-VoW/WynncraftVoiceProject/commit/633794426115185a900222e13d9b4abc1904b99b))
* Added different sounds.json which is used for the version on azure. Contains ALL seaskipper lines we are aware of. ([c1823c3](https://github.com/Team-VoW/WynncraftVoiceProject/commit/c1823c3176e5a09f5001c99634327aa3527530b3))
* Added initial audio manifest ([35b4cc9](https://github.com/Team-VoW/WynncraftVoiceProject/commit/35b4cc91a8657ef96606521696b9154cab1ee8d7))
* Added log messages that say which files failed to download ([9f8af19](https://github.com/Team-VoW/WynncraftVoiceProject/commit/9f8af1915f02fbcac4d005ec4ed059a6fd2de33f))
* Changed the update audio manifest workflow to use the correct audio directory ([323db79](https://github.com/Team-VoW/WynncraftVoiceProject/commit/323db79c5799d511d6caf3e92d26f77d27f5765e))
* Correctly added all new sound info to the sounds.json that goes on azure ([ca7186d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ca7186d0334addcd065f5f4f054beccca5b91dfb))
* Deleted generate-sounds.yml workflow ([24772d1](https://github.com/Team-VoW/WynncraftVoiceProject/commit/24772d14e38ce1a72aec16f324a8250700cb627a))
* Deleted old sounds.json file ([4193353](https://github.com/Team-VoW/WynncraftVoiceProject/commit/419335315869aa23783bbef51dcf50485bb9ecd3))
* Dynamically generate an audio manifest file ([bd9dbdd](https://github.com/Team-VoW/WynncraftVoiceProject/commit/bd9dbdd12f283e89789238d7db9ddbe26b7a9fe7))
* Moved the sounds.json file into the sounds folder ([d2cdbdd](https://github.com/Team-VoW/WynncraftVoiceProject/commit/d2cdbdd6bae1b257378fc48cb0b72c82b0513ed4))
* Removed a no longer used audio file ([f2139bd](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f2139bded55e3bacd33726e0b61c4949289c9d2b))
* Removed LibGui from build.gradle ([617eb26](https://github.com/Team-VoW/WynncraftVoiceProject/commit/617eb2668fa8bff1fd2fd9dea21173a51a82effa))
* Removed no longer used audio files ([f54b16c](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f54b16ca2e2d7794d481c47f3a606bcbd8fd099a))
* Removed some accidental added stopSounds lines with no file which where added to the normal sounds.json and not the new one ([ba4bc45](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ba4bc457481cd3c3ddac86b5231969ea646d4ac3))
* Synced bundled JSON ([22eb07e](https://github.com/Team-VoW/WynncraftVoiceProject/commit/22eb07eff5fddd32b6bd72582ffaf01edc7b5e9d))
* Update developer cloth config and mod menu version ([5a9f76b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5a9f76b51bb2f10c80a7c270628c1cfb22800875))
* Updated game version in release pipeline ([8de90cf](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8de90cfd65ad14799038f81424785fb53f561aa0))
* Upgraded required fabric_loader_version to 0.16.5 ([cee84e3](https://github.com/Team-VoW/WynncraftVoiceProject/commit/cee84e3409298a293c47a39be5ec0f5129dc68d4))


### Code Refactoring

* Refactored AudioPlayer to use HttpRequest and send the players a message about enable download sounds if their playback keeps on failing ([1fe7ad7](https://github.com/Team-VoW/WynncraftVoiceProject/commit/1fe7ad7bedc83bdc0f3b02ace7558a8820acd2d7))
* remove old line parsing that was needed for before fruma ([e0f7bc1](https://github.com/Team-VoW/WynncraftVoiceProject/commit/e0f7bc18505d6d4e8cd7b7df595eb55b33c33f42))
* replace hardcoded dialogues with JSON file ([#39](https://github.com/Team-VoW/WynncraftVoiceProject/issues/39)) ([13bd1c3](https://github.com/Team-VoW/WynncraftVoiceProject/commit/13bd1c3227b62e316ada78e85bcd3f1d3a19caeb))


### Build System

* Configure shadow plugin to relocate TarsosDSP dependencies ([8c54b19](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8c54b1954e86724d3ec703c772b56ddfe1fac878))
* Copy over the latest sounds.json to be bundled with the mod ([dc1c8fa](https://github.com/Team-VoW/WynncraftVoiceProject/commit/dc1c8fa197fa95852f8508f52fb191e30dfdd972))
* exclude PipeDecoder class due to CurseForge restrictions ([d462bcf](https://github.com/Team-VoW/WynncraftVoiceProject/commit/d462bcf262e920e533d274d10764f94b5585f479))

## [1.9.3](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.8.2...v1.9.3) (2024-09-24)


### ⚠ BREAKING CHANGES

* Allow VoW to be used with 1.21.1 (along with 1.21) (#33)

### New Features

* Add automated releases to Github Releases and CurseForge with Github Actions ([#30](https://github.com/Team-VoW/WynncraftVoiceProject/issues/30)) ([8fee8ff](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8fee8ffd35df299e708bf042f722b537ef6c485a))
* Add Spotless to make the code base have a standard format, fix up licenses in repository ([#21](https://github.com/Team-VoW/WynncraftVoiceProject/issues/21)) ([dd0a7ac](https://github.com/Team-VoW/WynncraftVoiceProject/commit/dd0a7ac9874dbb806c120bfcf7d7d728da9192df))
* Allow VoW to be used with 1.21.1 (along with 1.21) ([#33](https://github.com/Team-VoW/WynncraftVoiceProject/issues/33)) ([9fbee2b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/9fbee2b0545575b380772b3ab69c7b6fb31e6259))
* Introduce sound_info.json and add everything needed to load it ([#28](https://github.com/Team-VoW/WynncraftVoiceProject/issues/28)) ([b1d2653](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b1d26530b13c12534900643de03e5be6d4af495d))


### Bug Fixes

* Added custom audio playback system and fix crash with new Text displays ([#36](https://github.com/Team-VoW/WynncraftVoiceProject/issues/36)) ([c1373de](https://github.com/Team-VoW/WynncraftVoiceProject/commit/c1373decc39d6fbadce6675b5058a8ed6991b347))
* Don't upload build artifacts ([#25](https://github.com/Team-VoW/WynncraftVoiceProject/issues/25)) ([0458896](https://github.com/Team-VoW/WynncraftVoiceProject/commit/04588964a4160a88849750c5722200aa422c6240))
* Fixed a dialogue form Therck not playing in enzans brother ([39be62f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/39be62f98d0580fc01a6d1050b370a0bb7077011))
* Fixed sounds that where stereo and mono which would not play ([53f9f2f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/53f9f2f6653b8335bf3239fb910da620783177e0))
* Make format-build.yml use the correct token ([#24](https://github.com/Team-VoW/WynncraftVoiceProject/issues/24)) ([ce37cc6](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ce37cc6b23c13f23df2b60dc0fbe876524998610))
* Removed debugging ([378a9ed](https://github.com/Team-VoW/WynncraftVoiceProject/commit/378a9edd1b08f8c8c4c99a3b958923145e15859a))
* Removed mix to mono function which causes crashes ([a9c748d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a9c748d765fd2b35111b0738fd7e4654ca435bd4))
* Revert LICENSE (which accidentally got changed during the folder refactors) ([#26](https://github.com/Team-VoW/WynncraftVoiceProject/issues/26)) ([61df23a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/61df23a269fa9731ed0cb6dbcf59b1136fb1370c))


### Documentation

* Improve the project README file ([#29](https://github.com/Team-VoW/WynncraftVoiceProject/issues/29)) ([762c7d8](https://github.com/Team-VoW/WynncraftVoiceProject/commit/762c7d8ae1f30212781df76821d47cb2d220c325))


### Miscellaneous Chores

* Change some sound ids to better follow a format, remove color codes from sound lines ([#27](https://github.com/Team-VoW/WynncraftVoiceProject/issues/27)) ([e5b9af8](https://github.com/Team-VoW/WynncraftVoiceProject/commit/e5b9af8f71f9fd44fe1868208092a1b1b8c392a8))
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

