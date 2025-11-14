## [1.12.0](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.11.3...v1.12.0) (2025-11-14)


### ⚠ BREAKING CHANGES

* fixed the Sounds updater to properly update

### New Features

* **OceanCitizens:** Added voice acting for most Ocean citizen ([07a71d8](https://github.com/Team-VoW/WynncraftVoiceProject/commit/07a71d8242cbae70b2d8696c18f6f608cc3689d4))


### Bug Fixes

* fixed the Sounds updater to properly update ([4892bef](https://github.com/Team-VoW/WynncraftVoiceProject/commit/4892bef317b148e79a805fc7e9cee5038177ae63))

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

## [1.11.0](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.10.7...v1.11.0) (2025-06-28)


### ⚠ BREAKING CHANGES

* Added broadcast message for when auditions are open

### New Features

* Added broadcast message for when auditions are open ([eb6d237](https://github.com/Team-VoW/WynncraftVoiceProject/commit/eb6d237e8580247fb46dd39aa2efb94e9938f85b))
* added setting for playback speed ([#51](https://github.com/Team-VoW/WynncraftVoiceProject/issues/51)) ([81ed7d0](https://github.com/Team-VoW/WynncraftVoiceProject/commit/81ed7d0be3ccfb514a2f8a2b6e85cec144359083)), closes [#19](https://github.com/Team-VoW/WynncraftVoiceProject/issues/19)
* **AudioPlayer:** notify user when Voice/Speech volume is off ([3d0f3a6](https://github.com/Team-VoW/WynncraftVoiceProject/commit/3d0f3a6affc5ff279a4bd678e2fa2be696edcc11))
* **Dark Descent:** added new Charon lines ([99bb0ed](https://github.com/Team-VoW/WynncraftVoiceProject/commit/99bb0edc95106077335b45be1c3eae6bf6b2b8cc))
* **Dark Descent:** Added new General Graken lines ([9455e88](https://github.com/Team-VoW/WynncraftVoiceProject/commit/9455e888984860c64ff2614e8dc91fd2d9a56c6b))
* **Elemental Exercise:** added 5 new Tasim lines ([dd31ff3](https://github.com/Team-VoW/WynncraftVoiceProject/commit/dd31ff3c6a8891fe70305f796cd0aacdfe366b71))
* **Infested Plants:** added missing Ope lines ([cfd4251](https://github.com/Team-VoW/WynncraftVoiceProject/commit/cfd4251193f447966ec792555e71b819205d867e))
* **Infested Plants:** added new Tasim lines ([1f5b299](https://github.com/Team-VoW/WynncraftVoiceProject/commit/1f5b299f79026984eca1a88115e114cfd5cb3f21))
* **Jungle Fever:** added new lines from Worid ([1484644](https://github.com/Team-VoW/WynncraftVoiceProject/commit/148464428b6e704b645c8b5d93b497a9594f2ee2))
* **Lost Soles:** added 16 new Ferndor lines ([b9a31e2](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b9a31e2d5b9f24b18af96a4d6bffe7eec5803edb))
* **Misadventure on the Sea:** added many missing Honip lines ([861479b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/861479b10d2a2fb419a35394096b065f10278ccf))
* **Mushroom Man:** added 17 new Tasim lines ([856dabd](https://github.com/Team-VoW/WynncraftVoiceProject/commit/856dabde9f3db8a2ec02161f1b731deedc0706a4))
* **Wrath of the mummy:** Added 6 missing lines from the bandit ([cfadbd1](https://github.com/Team-VoW/WynncraftVoiceProject/commit/cfadbd1a85bc50e5d78318ca9918edf7a903281b))


### Bug Fixes

* **Aldorei's Secret Part 1:** Fixed two lines from Korben not playing ([f8fdbc5](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f8fdbc57c09343ea724e954e355cfb07de1b4dad))
* **All Roads to Peace:** Fixed many not playing lines and mixed up lines ([b318af9](https://github.com/Team-VoW/WynncraftVoiceProject/commit/b318af90a11fc2556ab825b2bf3f32704304f278))
* **An Iron Heart part 2:** Fixed two lines not playing from Daxe ([aac4f6d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/aac4f6d5c36302f3555a5fca9e12c537ab3a8e8c))
* **Arachnids Ascent:** Fixed a line from Tasim not playing ([31cc460](https://github.com/Team-VoW/WynncraftVoiceProject/commit/31cc46078e65ac897b82f4c132908b72fc248735))
* **At the Edge of Decay:** Fixed Laris lines not playing ([40b792b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/40b792b7b0be17a08f18782040358757ced79d23))
* **AudioPlayer:** stop sounds from playing for a millisecond if Voice/Speech is at 0% ([3913e56](https://github.com/Team-VoW/WynncraftVoiceProject/commit/3913e56c1404ddf6fd9d57a33203033833ad7645))
* **Bobs lost soul:** Fixed the blacksmiths lines playing all lines in first line ([bf7e407](https://github.com/Team-VoW/WynncraftVoiceProject/commit/bf7e4078f8c7599526152e27be565bb4058ac853))
* changed default url for the cdn to use jsdeliver ([89c3723](https://github.com/Team-VoW/WynncraftVoiceProject/commit/89c37238bf301457c343506bd08466677cc05092))
* **Dark Descent:** fixed a few General Graken lines not playing ([312ff4f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/312ff4fbd394132fac46eb78104fb46fc90721aa))
* **Elemental Exercise:** Fixed a line from elemental exercise not playing ([44de975](https://github.com/Team-VoW/WynncraftVoiceProject/commit/44de9753147d1720a76fb086e1694802829f0cc9))
* **Fate of the Fallen:** fixed one line from Telvu not playing ([67807e0](https://github.com/Team-VoW/WynncraftVoiceProject/commit/67807e0b0d7ddcf5f33a1695d063ee9806dcee09))
* **Fate of the Fallen:** Fixed Telvus last dialogue not playing ([826879a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/826879aa340a2ddf7c8ef4a8f46ecbc480c1086d))
* Fixed a talking mushroom line not playing ([aa03193](https://github.com/Team-VoW/WynncraftVoiceProject/commit/aa0319346f5d18228a885c2079357eb3f69250c5))
* fixed many Tasim lines not playing ([2a2c3bb](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2a2c3bbc2f59c3ee998114a476b263342395bfd1))
* Fixed two lines not playing in the mercenary ([03a04c5](https://github.com/Team-VoW/WynncraftVoiceProject/commit/03a04c57878415cf32033e2c3ccb443780594e8e))
* Fixed withereheads first line in sewers not playing ([6f7357f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/6f7357f71b4351693d7c0861934c58c0994c579f))
* **Frost Bite:** Fixed a line from Eppo not playing ([1d5e880](https://github.com/Team-VoW/WynncraftVoiceProject/commit/1d5e880f097b0ff42554388d3178d4fe63c69f43))
* **Grave Digger:** Fixed Sayleros'es post quest line not playing ([d414308](https://github.com/Team-VoW/WynncraftVoiceProject/commit/d4143083228c2c4c556c431e921b59c64d148970))
* **Hollow Serenity:** Fixed two lines not playing ([1c87f2b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/1c87f2b9ff48f053d06adc67ec5142aa9e503e45))
* **Jofash Docks:** Fixed the Bread Man's dialogue being offset by one line from around line 60 ([eb07ecd](https://github.com/Team-VoW/WynncraftVoiceProject/commit/eb07ecd8185a1d32727326174f6446d7134f1a5a))
* **Jungle Fever:** Fixed a few of Worids lines not playing ([2b7ad6a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2b7ad6a5967e0fbb77d645bfbf28343f8495b705))
* **Kings Recruit:** fix one of Aledars lines not playing ([7ddc20a](https://github.com/Team-VoW/WynncraftVoiceProject/commit/7ddc20a53980cfb9b242e45c48bd4dcbfb64ddbf))
* **Kings Recruit:** Fixed two lines from Aledar & Tasim not playing ([f9c52f4](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f9c52f4ca3f3131398dba77a18d7cd66548e57a0))
* **Memory Paranoia:** cut Atisun's lines ([867c507](https://github.com/Team-VoW/WynncraftVoiceProject/commit/867c507204ffeb2b253982279bdc88c99f237993))
* **Memory Paranoia:** Fixed two not playing lines ([795ac0c](https://github.com/Team-VoW/WynncraftVoiceProject/commit/795ac0cde5c7c98c8f5675770f3a4a4c5ab4522b))
* **Murder Mystery:** cut Valimares lines ([5b7c9bb](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5b7c9bb7b4d97b042cdb4be1508b99c88638aafc))
* **Order of the Grook:** Fixed one of Headmasters lines not playing ([e3a1467](https://github.com/Team-VoW/WynncraftVoiceProject/commit/e3a1467e88bd3a49aec45e82e326163ed81a3687))
* **Rise of the Quartron:** Fixed one of Naseas lines not playing ([54bd42b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/54bd42b3ece9607280fc8b3936f3cf597116c8c2))
* specify branch for sound sync workflow ([a203c4e](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a203c4ed767bd5acb2ea10cfdefde70518319022))
* **Stable Story:** Fixed Enksers two last lines not playing ([282a08d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/282a08dac070a79f7c7b77d65edf8cafd341dd2a))
* **Stable Story:** fixed two lines from Enkser not playing ([8455c26](https://github.com/Team-VoW/WynncraftVoiceProject/commit/8455c26c8c97c718e1443817b6182f2f5c37105c))
* **Taking the tower:** Fixed a line from Aledar not playing ([98c5150](https://github.com/Team-VoW/WynncraftVoiceProject/commit/98c51501c4e4aa2f600bcc6300e26679e9183ede))
* **Temple of Legends:** Fixed 3 lines from Kelight not playing ([31158c2](https://github.com/Team-VoW/WynncraftVoiceProject/commit/31158c2446e321abc6262d4b680dc856f06f872d))
* **The Passage:** Fixed one of Ildans lines not playing ([d0e247f](https://github.com/Team-VoW/WynncraftVoiceProject/commit/d0e247f8f66558a2994fbbb81e13a90c2a104c2e))
* **The Ultimate Weapon:** Fixed a line from Gogedar not playing ([4ebc9f2](https://github.com/Team-VoW/WynncraftVoiceProject/commit/4ebc9f28ed07ec6e3a62e3445d0404c386915892))
* **Wrath of the Mummy:** Fixed bandits first 4 lines not playing. ([5df73cb](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5df73cb0518422be019e207239f6dbbf027e1666))


### Miscellaneous Chores

* **release:** v1.11.0 [skip ci] ([89cac3d](https://github.com/Team-VoW/WynncraftVoiceProject/commit/89cac3de81141fd85193da1d31a201c6ca4407ef))
* Removed a no longer used audio file ([f2139bd](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f2139bded55e3bacd33726e0b61c4949289c9d2b))
* Removed no longer used audio files ([f54b16c](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f54b16ca2e2d7794d481c47f3a606bcbd8fd099a))

