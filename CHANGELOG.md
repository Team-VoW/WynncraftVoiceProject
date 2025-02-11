## [1.10.3](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.10.2...v1.10.3) (2025-02-11)


### New Features

* Added missing Desperate Metal voice acting ([95c5907](https://github.com/Team-VoW/WynncraftVoiceProject/commit/95c5907c4649d6f5783d19f19978f535be3141c4))
* Added new kingdom of sand ([ab31fa1](https://github.com/Team-VoW/WynncraftVoiceProject/commit/ab31fa1d68670fa2a28565b790b7623883accd7a))


### Bug Fixes

* Fixed a very rare crash that can happen if you get a tick before your sound engine was started ([5259168](https://github.com/Team-VoW/WynncraftVoiceProject/commit/5259168aba6af3301a772e9078afba42cce38fa7))


### Miscellaneous Chores

* Added log messages that say which files failed to download ([9f8af19](https://github.com/Team-VoW/WynncraftVoiceProject/commit/9f8af1915f02fbcac4d005ec4ed059a6fd2de33f))
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

## [1.10.0](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.9.4...v1.10.0) (2025-01-19)


### Bug Fixes

* Disabled auto progress by default ([806d4cc](https://github.com/Team-VoW/WynncraftVoiceProject/commit/806d4cc6c2d0d2dfd5def71d8344285cd4e474f0))


### Miscellaneous Chores

* Added initial audio manifest ([35b4cc9](https://github.com/Team-VoW/WynncraftVoiceProject/commit/35b4cc91a8657ef96606521696b9154cab1ee8d7))
* Changed the update audio manifest workflow to use the correct audio directory ([323db79](https://github.com/Team-VoW/WynncraftVoiceProject/commit/323db79c5799d511d6caf3e92d26f77d27f5765e))
* Deleted generate-sounds.yml workflow ([24772d1](https://github.com/Team-VoW/WynncraftVoiceProject/commit/24772d14e38ce1a72aec16f324a8250700cb627a))
* Deleted old sounds.json file ([4193353](https://github.com/Team-VoW/WynncraftVoiceProject/commit/419335315869aa23783bbef51dcf50485bb9ecd3))
* Dynamically generate an audio manifest file ([bd9dbdd](https://github.com/Team-VoW/WynncraftVoiceProject/commit/bd9dbdd12f283e89789238d7db9ddbe26b7a9fe7))
* **release:** v1.10.0 [skip ci] ([9de7d76](https://github.com/Team-VoW/WynncraftVoiceProject/commit/9de7d761abe569f057953c690c54d5e591ae3036))

## [1.9.4](https://github.com/Team-VoW/WynncraftVoiceProject/compare/v1.9.3...v1.9.4) (2024-12-26)


### New Features

* Added auto progress to automatically shift after dialogues. ([f4f1438](https://github.com/Team-VoW/WynncraftVoiceProject/commit/f4f14387e5e8b54d2d87fff1f94a52346d7973f0))
* Added new sewers of ragni ([a272912](https://github.com/Team-VoW/WynncraftVoiceProject/commit/a2729122485c0cede15739cb6eb1282b982eb24f))


### Bug Fixes

* Fixed lines with player name not working if player is nicked ([2e34a7b](https://github.com/Team-VoW/WynncraftVoiceProject/commit/2e34a7b7bc93013922c7922b1c88ad02bee79300))


### Miscellaneous Chores

* **release:** v1.9.4 [skip ci] ([6c38cf4](https://github.com/Team-VoW/WynncraftVoiceProject/commit/6c38cf45d4c663baf8f0e9d2d614ad69023de21e))
* Removed LibGui from build.gradle ([617eb26](https://github.com/Team-VoW/WynncraftVoiceProject/commit/617eb2668fa8bff1fd2fd9dea21173a51a82effa))
* Upgraded required fabric_loader_version to 0.16.5 ([cee84e3](https://github.com/Team-VoW/WynncraftVoiceProject/commit/cee84e3409298a293c47a39be5ec0f5129dc68d4))


### Code Refactoring

* replace hardcoded dialogues with JSON file ([#39](https://github.com/Team-VoW/WynncraftVoiceProject/issues/39)) ([13bd1c3](https://github.com/Team-VoW/WynncraftVoiceProject/commit/13bd1c3227b62e316ada78e85bcd3f1d3a19caeb))

