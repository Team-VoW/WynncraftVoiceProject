package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.HashMap;

public class SoundsHandler {
    // public static SoundEvent TALKING_MUSHROOM_RETURNINGARDFD;
    public HashMap<String, CustomSoundClass> sounds;

    public SoundsHandler() {
        sounds = new HashMap<>();
        registerSounds();
    }

    public void registerSounds() {
        //Talking Mushroom
        addSound("[1/1] Talking Mushroom: PRICK.", "talkingmushroomprick", true);
        addSound("[1/2] Talking Mushroom: IF I HAD A MOVABLE NECK, I'D BE TWISTING IT AROUND TO FACE YOU WITH UTTER CONFUSION.", "talkingmushroomragnioutskirts", true);
        addSound("[1/1] Talking Mushroom: WHAT IN THE NAME OF ALL THAT IS HOLY IS GOING THROUGH YOUR HEAD?! WE'RE MILES FROM THE SKY ISLANDS!! WHERE IS THIS PLACE?!", "talkingmushroomragni", true);
        addSound("[1/2] Talking Mushroom: ... STAIRWAY TO HEAVEN, HUH? I DON'T KNOW ABOUT YOU, BUT I DON'T GET THE APPEAL.", "talkingmushroomtherck", true);
        addSound("[1/3] Talking Mushroom: OH, HEY, THIS PLACE IS NICE! IT HAS EVERYTHING A PLACE COULD NEED! MUSHROOMS, COZY HOUSES, BEINGS OF GREAT POWER...", "talkingmushroomelkurn", true);
        addSound("[1/1] Talking Mushroom: SO, WHAT IS THIS PLACE? IS IT A BRIDGE, OR IS IT A TOWN? MAKE UP YOUR DAMN MIND, VILLAGERS!", "talkingmushroombucie", true);
        addSound("[1/1] Talking Mushroom: WOW, REAL GRAND CITY HERE, HUH? THIS PLACE IS HUGE! AND PROBABLY A MESS TO NAVIGATE, REALLY POORLY DESIGNED IF YOU THINK ABOUT IT.", "talkingmushroomllevingar", true);
        addSound("[1/3] Talking Mushroom: ... WOW. I'M... I'M ACTUALLY SHOCKED.", "talkingmushroomforbiddenprison", true);
        addSound("[1/3] Talking Mushroom: HEY, THIS PLACE HAS GOT SOME NICE LOOKING CAPS! DROP ME OFF HERE!", "talkingmushroomyahya", false);
        addSound("[1/2] Talking Mushroom: WAIT, I'VE HEARD OF THIS PLACE. TIME GOES SLOWER HERE, DOESN'T IT?", "talkingmushroomdooroftime", true);
        addSound("[1/1] Talking Mushroom: WHY IS IT SO HOT IN HERE? OH, YOU HAD BETTER NOT BE THINKING WHAT I THINK YOU'RE THINKING! I'M NOT GONNA BE A ROASTED MUSHROOM TODAY!", "talkingmushroommountwynn", true);
        addSound("[1/2] Talking Mushroom: WOAH, THAT THING LOOKS REALLY WEIRD. IS THIS IMPORTANT OR SOMETHING? YEAH, THIS HAS TO BE IMPORTANT!", "talkingmushroomendportal", true);
        addSound("[1/1] Talking Mushroom: I KNOW THESE CANYON EGGS GET LARGE BUT I HAVE TO ASK, HOW DID THE BIRD FIT IN HERE TO MANAGE THIS?!", "talkingmushroommesacanyoneggs", true);
        addSound("[1/8] Talking Mushroom: Y-YOU CAN'T BE SERIOUS. YOU... YOU SHOULDN'T BE HERE. I LOST THAT KEY A LONG TIME AGO, BUT SOMEHOW... YOU FOUND IT AND MADE YOUR WAY HERE. RIGHT, LET ME EXPLAIN.", "talkingmushroombackstorry", true);
        addSound("[1/2] Talking Mushroom: HAHA, LOOK! YOU'RE PRACTICALLY ONE OF THEM, YOU FIT RIGHT IN! NOW STAY HERE LIKE A GOOD ANIMAL AND LET ME GO MY OWN WAY.", "talkingmushroomoutofmymind", true);
        addSound("[1/1] Talking Mushroom: A TREE GUARDIAN? SERIOUSLY?! THAT'S ABOUT AS BELIEVABLE AS A TALKING MUSH...OH WAIT. HUH. CARRY ON.", "talkingmushroomderneltreeguardien", true);
        addSound("[1/1] Talking Mushroom: WAIT, YOU'RE ACTUALLY FALLING FOR THIS STUFF? SERIOUSLY?! IT'S CLEARLY JUST A BUNCH OF SEEDS PAINTED GOLD, ARE YOU QUITE LITERALLY BLIND?", "talkingmushroomzhight", true);
        addSound("[1/4] Talking Mushroom: ...AND I HAD THOUGHT SOME OF YOUR PREVIOUS LOONEY ADVENTURES HAD INDICATED YOU WERE A BUNBLING IDIOT, BUT YOU'VE REALLY OUTDONE YOURSELF... WHERE EVEN ARE WE?!", "talkingmushroombaabsabondedmines", true);
        addSound("[1/1] Talking Mushroom: HEY HEY HEY, WHERE DO YOU THINK YOU'RE GOING? THERE'S A BOUNTY ON MY HEAD HERE SINCE... THE INCIDENT... CAN YOU JUST AVOID THIS PLACE LIKE YOU AVOID MY ADVICE?", "talkingmushroomfrumagate", true);
        addSound("[1/2] Talking Mushroom: OH NO. WHAT DID THAT BUTTON DO? WHAT DID THE BUTTON DO? DID YOU BREAK IT? I BET YOU BROKE IT!", "talkingmushroomheartoflevingar", true);
        addSound("[1/2] Talking Mushroom: I'M GONNA BE STUCK WITH YOU FOREVER, AREN'T I? MAY AS WELL THROW ME IN ONE OF THOSE GRAVES RIGHT NOW!", "talkingmushroomgeilboardcemetary", true);
        addSound("[1/2] Talking Mushroom: OI, WHY'RE YOU TAKING ME ALL THE WAY OUT HERE? MY COUSIN LIVES HERE, I HATE HIM!", "talkingmushroomtrippyforest", true);
        addSound("[1/1] Talking Mushroom: *GASP* *PANT* IS THERE ZERO AIR UP HERE? GET ME DOWN THIS INSTANT! OR IS YOUR BRAIN SO TINY IT DOESN'T NEED OXYGEN TO SURVIVE?!", "talkingmushroomflightindistress", true);
        addSound("[1/2] Talking Mushroom: HEY NUMBSKULL, LEAVE ME HERE, ALRIGHT? I SAW SOME NICE CAPS OVER THERE.", "talkingmushroommushroomcave", true);
        addSound("[1/2] Talking Mushroom: BRILLIANT. ABSOLUTELY BRILLIANT! HOW BRAINDEAD ARE YOU?! THERE'S NOTHING HERE!", "talkingmushroompsiloshouse", true);
        addSound("[1/1] Talking Mushroom: WHO KNEW BEING EATEN ALIVE BY A FIRE-BREATHING DRAGON IS BETTER THAN BEING STUCK WITH YOU?", "talkingmushroomozothgut", true);
        addSound("[1/1] Talking Mushroom: URGH, WHAT IS THAT AWFUL STENCH?! IT'S ALMOST OVERPOWERING YOUR OWN RANCIDNESS! I MEAN, DO YOU EVER BATHE?!", "talkingmushroomcanarycallscave", true);
        addSound("[1/3] Talking Mushroom: ARGH, I'M CURSED, I SWEAR IT! EVEN THE SWEET RELEASE OF DEATH HASN'T GOTTEN ME AWAY FROM YOU! YOU'RE LIKE A COCKROACH!", "talkingmushroomdeathrealm", true);
        addSound("[1/2] Talking Mushroom: YOU KNOW WHAT, TO HECK WITH THIS! I'M BAILING, SEE YA LATER! HAVE A TERRIBLE LIFE, YOU INGRATE!", "talkingmushroommoltenheights", true);
        addSound("[1/1] Talking Mushroom: HOW DID YOU MANAGE TO GET ME TO SOMEPLACE DARKER THAN THE INSIDE OF YOUR SATCHEL? WHAT, DID YOU CRAWL UP SOMETHING'S-", "talkingmushroomfantasticvoyage", true);
        addSound("[1/1] Talking Mushroom: OH GREAT, ANOTHER ONE. JUST FANTASTIC. WHAT'S WITH THE FACE, HUH? NEVER SEEN A TALKING MUSHROOM BEFORE? GO AWAY!", "talkingmushroombeforerecipefordisaster", false);
        addSound("[1/2] Talking Mushroom: OH MY, LOOK WHO'S BACK. HERE I THOUGHT I WAS FREE OF YOUR GRASP AND THEN YOU COME BACK HERE.", "talkingmushroomafterrecipefordistaster", false);
        addSound("[1/2] Talking Mushroom: HEY DWEEB, LOOK OVER THERE! I THINK IT'S... PEOPLE?", "talkingmushroomonethousandmetersunder", true);
        addSound("[1/1] Talking Mushroom: HEY MEATBAG, DON'T YOU THINK YOU'RE A LITTLE FAT FOR THAT THING? WAIT, YOU'RE SERIOUS? STO-AAAHHH!!!", "talkingmushroomcorkuscity", true);
        addSound("[1/1] Talking Mushroom: HUH, THIS VIEW IS GOOD. PSST... I DON'T WANT TO ACKNOWLEDGE THE FACT THAT WE'RE STANDING ON A CLOUD IN FEAR OF IT FALLING APART LIKE SOME CLICHÉ... GOOOOOD VIEW.", "talkingmushroomcorkuscloud", true);
        addSound("[1/1] Talking Mushroom: I BET THE PERSON WHO LIVES HERE DIDN'T EVEN COME UP WITH THIS ROBOT. IT'S TOO GOOD TO BE ORIGINAL.", "talkingmushroomcorkusbastion", true);
        addSound("[1/3] Talking Mushroom: HEY IDIOT, WHERE ARE WE THIS TIME! IT FEELS ALL... BRIGHT, ALMOST EXCESSIVELY SO-", "talkingmushroomnexusoflight", true);
        addSound("[1/3] Talking Mushroom: YOU KNOW, HAVE I EVER TOLD YOU JUST HOW MUCH I HA-", "talkingmushroomrolportal", true);
        addSound("[1/3] Talking Mushroom: SATISFIED YET? CAN YOU TRY UNHINGING HIS JAW AND PUTTING YOUR ARM IN THERE?", "talkingmushroomfeathersflydog", true);
        addSound("[1/1] Talking Mushroom: HEY IDIOT, WHERE ARE WE?? THIS PLACE SMELLS LIKE-", "talkingmushroommonumenttosifried", true);
        addSound("[1/3] Talking Mushroom: WOW. NOW THAT IS A BIG GUY. IT LOOKS ANCIENT! I WONDER WHO COULD'VE BUILT SOMETHING LIKE THIS...", "talkingmushroomtcc", true);
        addSound("[1/4] Talking Mushroom: ... WOW. I'M SPEECHLESS. HUMAN, ARE YOU SEEING THIS PLACE? WITH THE NUMBER OF EMERALDS HERE... WE'RE GONNA BE RICH!!", "talkingmushroomabondedmines", true);
        addSound("[1/2] Talking Mushroom: ... ALRIGHT. I'VE ACCEPTED THAT YOU GO TO WEIRD PLACES BUT THIS CROSSES THE LINE. WHERE EVEN ARE WE?", "talkingmushroomeyeballforest", true);
        addSound("[2/4] Talking Mushroom: GREAT JOB! YOU SOMEHOW MANAGED TO FALL OUT OF REALITY BY DOING ABSOLUTELY NOTHING!", "talkingmushroomotherworldyoccurence", true);
        addSound("[1/5] Talking Mushroom: ALRIGHT, HOLD UP, LET ME GET SOMETHING STRAIGHT HERE. YOU ACTUALLY THINK IT'S A GOOD IDEA TO GO INSIDE THAT OMINOUS DOME THAT'S LITERALLY ERUPTING WITH THIS HORRIBLE DARK SENSATION FOR... WHAT REASON??", "talkingmushroombeforeeyefight", true);
        addSound("[1/3] Talking Mushroom: OH. OH, I DON'T- I DON'T LIKE THIS PLACE. AT ALL. WH-WHERE ARE WE??", "talkingmushroombotomlesspit", true);
        addSound("[1/1] Talking Mushroom: WOAH, HEY, WH-WHAT ARE WE DOING HERE, HUMAN?? IT'S... IT'S PROBABLY DANGEROUS IN THERE! Y-YOU WOULDN'T WANT TO DIE, WOULD YOU???", "talkingmushroomsecretariodopassage", true);
        addSound("[1/3] Talking Mushroom: WOW, LOTS OF POWERFUL MAGIC HERE, HUH?? MUCH STRONGER THAN ANYTHING YOU COULD EVER DO, THAT'S FOR SURE!", "talkingmushroomhouseoftwain", true);
        addSound("[1/2] Talking Mushroom: OH, LOOK AT THESE THINGS. THEY'RE KIND OF CUTE. A LITTLE BIT TOO CUTE... IT'S A CONSPIRACY, I TELL YOU!", "talkingmushroomwybelisland", true);
        addSound("[1/3] Talking Mushroom: I KNEW IT! IT'S ALL A CONSPIRACY!", "talkingmushroomorangewybelactor", true);
        addSound("[1/1] Talking Mushroom: THIS BRIDGE SEEMS DIFFERENT SINCE YOU LAST CROSSED IT, OR IS IT JUST ME? IT MIGHT JUST BE YOUR 247TH TIME CROSSING IT THAT'S DISORIENTATING ME… OR DID I LOSE COUNT AGAIN?", "talkingmushroomnivadetlasbridge", true);
        addSound("[1/2] Talking Mushroom: WOAH, WAIT. WHERE ARE WE? WHAT ARE YOU DOING HE-", "talkingmushroomhiddencamp", true);
        addSound("[1/3] Talking Mushroom: ...I DON'T KNOW WHAT MANIC IDEA YOU'RE ABOUT TO HAVE NOW, BUT I'M GOING TO PUT AN END TO IT RIGHT NOW. YOU DO NOT WANT TO GO IN THERE.", "talkingmushroomoutsideeo", true);
        addSound("[1/3] Talking Mushroom: REALLY COOL CITY, HUH? BUILT UP ON THE CLIFFS, VERY STYLISH, IS THAT WHAT YOU'RE THINKING?", "talkingmushroomkandon", true);

        //Creeper Infiltration
        addSound("[1/7] Thomas: Oh, finally, someone has come to this hidden village!", "thomas_creeperinfl_1", false);
        addSound("[1/10] Thomas: I didn't expect you to fall out of the sky like that. Are you okay, or should I go get the potion merchant to heal you?", "thomas_creeperinfl_2", false);
        addSound("[1/6] Thomas: I see you have the hide. This skin will work perfectly for my plan.", "thomas_creeperinfl_3", false);
        addSound("[1/7] Thomas: So, what was in the cave? Were there really creepers in there?", "thomas_creeperinfl_4", false);

        //Maltics Well
        addSound("[1/4] Rynend: Please help us! We called for a Ragni guard days ago, but Humans tend to forget this little village.", "malticwellrysend1", false);
        addSound("[1/1] Child: Help me! Someone! The witch is being mean!!", "malticwellchild", true);
        addSound("[1/2] Witch: I don't know why they call me a witch. Magic is everywhere, you know. This one's father made fun of my looks, so I'll turn his son into a Grook!", "malticwellwitch1", false);
        addSound("[1/2] Witch: Oh...drat. I never could get that spell right. Well, now you'll have to guess the way, um, you fool! Turn back now, for, uh...", "malticwellwitch2", false);
        addSound("[1/4] Witch: Oh...didn't think that'd fool you. You know, I'm really not all that good at magic. I just like wearing pointy hats, and have warts.", "malticwellwitch3", false);
        addSound("[1/3] Rynend: Oh my son! What happened down there? Erh, not a witch, you say?", "malticwellrysend2", false);

        //Grave Digger
        addSound("[1/8] Sayleros: You sure look like a strong adventurer who could help me!", "gravediggersayleros1", false);
        addSound("[1/2] Drucksh: Mmh. g... uh! oh, why hello there.", "gravediggerdrucksch1", false);
        addSound("[1/4] Drucksh: Ohh mmhhhgg... g-g-good old Nemract whiskey!", "gravediggerdrucksch2", false);
        addSound("[1/4] The Priest: Are you trying to go down into the crypt? Oh, that is a very bad idea.", "gravediggerpriest1", false);
        addSound("[1/2] The Priest: You got the flesh. Good.", "gravediggerpriest2", false);
        addSound("[1/1] Sayleros: Brilliant! It is a little bit dusty, but it will do the trick! Here, take these as proof of my gratitude.", "gravediggersayleros2", false);

        //Wrath of the Mummy
        addSound("[1/6] Ormrod: Uh, you shouldn't have come here!", "wrathofthemummyormrod1", false);
        addSound("[1/6] Achper: If I know about mummies? Of course! I've spent my entire life studying them!", "wrathofthemummyachper", false);
        addSound("[1/3] Bandit: Hey, what are you doing? If you're trying to loot the tomb, me and the other Creden Tibus already beat you to it.", "wrathofthemummybandit1", false);
        addSound("[1/4] Bandit: Well, whaddya know? You got some Pink Wool! Okay, hand it here and I'll look the other way and let you into the tomb.", "wrathofthemummybandit2", false);
        addSound("[1/4] Ormrod: Uh, hello again... What are those bandages?", "wrathofthemummyormrod2", false);

        //Corrupted village
        //FIX TYPO WYNNN!!
        addSound("[1/17] Roy: Well, that did the trick. Let’s take a look.", "corruptedviallgeroy", false);
        addSound("[1/7] Orikal: Go away! I know you're angry but there's nothing to gain her-", "corruptedviallgeorikal1", false);
        addSound("[1/6] Orikal: That was quick... did you find anything?", "corruptedviallgeorikal2", false);
        addSound("[1/5] Orikal: Okay. I happen to know a recipe for creating an extremely lethal magical explosive.", "corruptedviallgeorikal3", false);
        addSound("[1/3] Orikal: You got 'em all! Excellent, this is exactly what we need.", "corruptedviallgeorikal4", false);
        addSound("[1/4] Orikal: Oh, do you feel that? It's like a buzzing in my ears I never knew about has just vanished.", "corruptedviallgeorikal5", false);
        addSound("[1/3] Alfonse: This is insane, why are we here?! We're looking for corruption you know that right?", "corruptedviallgealfonse", true);

        //Ice nations
        addSound("[1/6] Adigard: Welcome traveler, to our humble island...", "icenationsadigard1", false);
        addSound("[1/7] Adigard: I should have expected that. Hallfred was always very greedy...", "icenationsadigard2", false);
        addSound("[1/5] Hallfred: Welcome traveler! What brings you to our island?", "icenationshallfred1", false);
        addSound("[1/4] Hallfred: Incredible! He actually delivered the treasure!", "icenationshallfred2", false);

        //Lost Royalty
        addSound("[1/5] Yavlis: Oh, hello there! I had hoped not to bother you, adventurer, but this is urgent.", "lostroyaltyyalvis1", false);
        addSound("[1/5] Yavlis: Ah, finally! You're back. Did they let you bring him back? Those mercenaries have an awful habit of failing to honour their deals.", "lostroyaltyyalvis2", false);
        addSound("[1/7] Mercenary Leader: Excuse me?! What are you doing here, how did you find our secret hideout?", "lostroyaltymercenary1", false);
        addSound("[1/3] Mercenary Leader: You've gotten the talisman! I can't believe you actually got through that puzzle!", "lostroyaltymercenary2", false);
        addSound("[1/4] Prince of Troms: Uh... Hi? Err, why did you free me? I'm trying to run away, I hate being the son of the king.", "lostroyaltykingsson", false);

        //Wynn Excavation site A
        addSound("[1/4] Vade: Oh, hey kid. Wanna make some money?", "wynnexcvationavade1", false);
        addSound("[1/8] Tesha: Hello, welcome to WynnExcavation Labs!", "wynnexcvationatesha", false);
        addSound("[1/6] Vade: Who are you?", "wynnexcvationavade2", false);
        addSound("[1/4] Vade: Hey kid, did you just come from the tomb?", "wynnexcvationavade3", false);

        //Wynn Excavation site B
        addSound("[1/1] Flendar: We know what you stole from us in the desert! Take THIS! You won't be escaping this dead end any time soon!", "wynnexcvationbflendar", true);
        addSound("[1/6] Excavator Lykron: Good day! You're the person that helped us out in the desert aren't you? Excellent! Just the person I need! Can you help me out with a problem I have?", "wynnexcvationblykron1", false);
        addSound("[1/5] Excavator Lykron: I have to admit, I'm impressed. I genuinely thought our plan would be successful. Who knew that dead end had a secret exit.", "wynnexcvationblykron2", false);
        addSound("[1/7] WynnExcavation Archaeologist: Baffling... What a puzzling situation I am in...", "wynnexcvationbarchaeologist", false);

        //Wynn Excavation site C
        addSound("[1/8] Excavator Placardus: All operations functional… Transportation on schedule… Oh. It's you. We've been looking for you.", "wynnexcavationcplacardus1", false);
        addSound("[1/7] Excavator Placardus: You couldn't just take my polite word and leave it at that, could you?", "wynnexcavationcplacardus2", false);
        addSound("[1/3] Guard Klerodor: I'm sorry. This is a private meeting.", "wynnexcavationcguard", false);
        addSound("[1/4] Chief Excavator Dranfor: Hello and welcome, senior members of Wynn Excavation Site C. May the dark's gaze forever watch over us. I want to update you on our progress thus far. The fire relic crystal has been largely intact in the heart of the volcano for many years.", "wynnexcavationcdranfor1", true);
        addSound("[1/1] Chief Excavator Dranfor: The alarm has been set off! There is an unauthorized guest! Guards, seize them!", "wynnexcavationcdranfor2", true);
        addSound("[1/10] ???: You. You are the one they are worried about.", "wynnexcavationcamadel", false);

        //Wynn Excavation Site D
        addSound("[1/8] Royal Advisor Carlos: His majesty Lord King of Troms is indisposed at this moment.", "wynnexcavationcarlos1", false);
        addSound("[1/1] Royal Advisor Carlos: Oh my, a secret passage has opened up beneath the throne! Quickly, head inside!", "wynnexcavationcarlos2", false);
        addSound("[1/9] King of Troms: I am impressed you managed to find me. Someone of my intellect is not normally found when he hasn't the intention of being so.", "wynnexcavationkingoftroms", false);
        addSound("[1/6] Troms Guard: Time is short, so I'll have to spare you the details, but let's just say I'm on your side.", "wynnexcavationtromsguard", false);
        addSound("[1/4] ???: Hey, psst. Come here!", "wynnexcavationquestionmark", false);
        addSound("[1/4] Traitor Thomas: I'm sorry, I can't let you in there.", "wynnexcavationthomas1", false);
        addSound("[1/4] Traitor Thomas: Is that… The map fragments? You found them!", "wynnexcavationthomas2", false);
        addSound("[1/8] Traitor Amadel: Welcome, friend. I knew you would figure it all out.", "wynnexcavationamadel1", false);
        addSound("[1/1] Traitor Buice: WynnExcavation have done really horrible things, and I used to help...", "wynnexcavationbucie", false);
        addSound("[1/1] Traitor Jaynar: WynnExcavation will be destroyed, no matter what!", "wynnexcavationjaynar", false);
        addSound("[1/8] Excavator Admin Uci: Oh my. It's you. I can't say I'm surprised to see you, we thought you might be stupid enough to come here.", "wynnexcavationadminuc", false);
        addSound("[1/1] Excavator Sean: My boss Uci is a bit of a mean guy, but hey the perks of minionship aren't bad!", "wynnexcavationsean", false);
        addSound("[1/1] WynnExcavation Scientist Azure: This leads deeper into this dungeon, but it seems like it has to be activated from the other side", "wynnexcavationazure", false);
        addSound("[1/8] WynnExcavation Leader Amadel: Well, well. Looks like you've finally made it.", "wynnexcavationamadel2", false);
        addSound("[1/1] WynnExcavation Leader Amadel: Step into this hole, and accept your fate. It's over, you've lost!", "wynnexcavationamadel3", false);
        addSound("[1/2] Amadel's Assistant: I see you have finally shown up.", "wynnexcavationassistant1", false);
        addSound("[1/2] Amadel's Assistant: That was a mere fluke..", "wynnexcavationassistant2", false);
        addSound("[1/2] Amadel's Assistant: He's simply warming up...", "wynnexcavationassistant3", false);
        addSound("[1/2] Amadel's Assistant: I, I can't believe you beat him...", "wynnexcavationassistant4", false);
        addSound("[1/18] Ragni's King: You... hmm... Ahh yes! I remember you. You're one of the recruits I accepted long ago..", "wynnexcavationragnisking", false);
        addSound("[1/1] Corrupted Amadel: The crystals combine the power of the stars themselves.. an ultimate power... the power to reign over the entire WORLD!", "wynnexcavationamadel4", true);
        addSound("[1/1] Shadow Amadel: I- I- I feel... unstopable.. this power... will not allow me to lose..!!!", "wynnexcavationamadel5", true);
        addSound("[1/1] Amadel: DID YOU REALLY THINK YOU COULD WIN?!?! YOU ARE NOTHING COMPARED TO ME! NOTHING!! I WILL EXTERMINATE ALL LIFE, STARTING WITH YOU!! NOW, PERISH!!!", "wynnexcavationamadel6", true);

        //Mushroom man
        addSound("[1/2] Yahya: G-... Hi! What a coincidence, I… I actually need you, yes.", "mushroomman-yahya-1", false);
        addSound("[1/5] Yahya: YOU DRANK ALL M-MY STUOP, STEW, SOUP!! WHERE IS IT!", "mushroomman-yahya-2", false);
        addSound("[1/5] Yahya: You, again? Oh, you have the… mushrooms.", "mushroomman-yahya-3", false);
        addSound("[1/6] Yahya: H-... Hey! I didn’t expect you to find it.. Haha… um..", "mushroomman-yahya-4", false);
        addSound("[1/2] Yahya: Oh boy I love bowls! Bowls, bowls, b-bowls! Perfect for mushrooms! W-wait... I thought I- was at my home?", "mushroomman-yahya-5", false);

        //Cluck Cluck
        addSound("[1/6] Nohno: Hey, you aren't a chicken! Wait, you're a human! You can help me! Maybe you are the guy who helped my brother Yahya, but who knows, you humans all look the same to me.", "cluckclucknohno1", false);
        addSound("[1/2] Nohno: What is this?! His feather? What did you do to Cluckles?! I wanted you to take care of him, not kill him!", "cluckclucknohno2", false);

        //Infested Plants
        addSound("[1/5] Ope: Hello! Young traveler.", "infplants1", false);
        addSound("[1/3] Ope: I've literally been sitting here for hours, maybe not hours, at least five minutes!", "infplants2", false);

        //Enzans Brother
        addSound("[1/6] Therck: Excuse me? Can't you see I'm busy?", "enzansbrothertherck1", false);
        addSound("[1/7] Therck: Ah, you're back! Do you have the mushroom?", "enzansbrothertherck2", false);
        addSound("[1/5] Enzan: I see you want to venture into the wilderness!", "enzansbrotherenzan", false);

        //Reincarnation
        addSound("[2/9] Bob: I am Bob, master of the arts of Wynn.", "reincarnationbob", true);
        addSound("[1/13] Batelu: Hello adventurer! I see you want to solve the mystery of Bob's tomb also? Glad to meet you.", "reincarnationbatleu1", false);
        addSound("[1/2] Batelu: Wonderful! We can now open the passage to the true tomb of Bob...", "reincarnationbatleu2", false);
        addSound("[1/5] Batelu: Bob's Seal of Approval? Oh my goodness!", "reincarnationbatleu3", false);

        //Bobs lost soul
        addSound("[1/10] Wedyf: Welcome traveler. Are you looking for something?", "bobslostsoulwedyf1", false);
        addSound("[1/6] Wedyf: Great, you got everything.", "bobslostsoulwedyf2", false);
        addSound("[1/6] Wedyf: I'm glad to see you again.", "bobslostsoulwedyf3", false);
        addSound("[1/6] The Blacksmith: What can I do for ya?", "bobslostsoulblacksmith1", false);
        addSound("[1/4] The Blacksmith: ... Hi again. I see you got the weapons.", "bobslostsoulblacksmith2", false);
        addSound("[1/9] Tarod: You're one of those soldier types, right? My name is Tarod, and I could really use your assistance here.", "bobslostsouldtarod1", false);
        addSound("[1/6] Tarod: Welcome back. Did you find anything worthwhile?", "bobslostsouldtarod2", false);
        addSound("[1/3] Tarod: You're back... Has he... has he finally been put to rest?", "bobslostsouldtarod3", false);
        addSound("[1/6] Apprentice: Weapons of the depressed kind? Oh, of course, of course, I know of the set! Around Nesaak, I hear they are kept.", "bobslostsoulapprentice1-1", false);
        addSound("[2/6] Apprentice: An army of Linx you must clear, in a cave you shall find this spear!", "bobslostsoulapprentice1-2", false);
        addSound("[3/6] Apprentice: To a skilled archer you must go, best her in a lumberyard and you will have her bow!", "bobslostsoulapprentice1-3", false);
        addSound("[4/6] Apprentice: On an altar, effervescence leers, complete the sacrifice and you will gain your shears...", "bobslostsoulapprentice1-4", false);
        addSound("[5/6] Apprentice: In the forest, a house frozen in spikes thick, outsmart the contraption and be gifted a stick!", "bobslostsoulapprentice1-5", false);
        addSound("[6/6] Apprentice: But beware, beware! Danger lies ahead, and will try to halt you in your stead!", "bobslostsoulapprentice1-6", false);

        //Temple of Legends
        addSound("[1/10] Garull: GWUH! Oh god, don't scare me like that! Who... Who even are you?!", "templeoflegendsgarull1", false);
        addSound("[1/4] Garull: Thank you for listening to me about not being so quiet coming over here. I'm guessing you've got everything?", "templeoflegendsgarull2", false);
        addSound("[1/6] Jorkin: You may want to get out of here, traveller. The Nether is very dangerous!", "templeoflegendsjorkin1", false);
        addSound("[1/7] Jorkin: You have the Light Dust, then? Hand it here, I want to see something.", "templeoflegendsjorkin2", false);
        addSound("[1/5] Kelight: I see you want to enter this mighty temple! Do not deny it, I see it in your eyes.", "templeoflegendskelight1", false);
        addSound("[1/6] Kelight: Mhm, mhm. Yeah, that was a bit fast. Fork them over, I need to see if these are the real deal.", "templeoflegendskelight2", false);
        addSound("[1/10] Kelight: Back so soon? A little bit suspicious. Allow me to examine your trophies once more.", "templeoflegendskelight3", false);
        addSound("[1/6] Kelight: Hah. Took you long enough. This is the light dust, I presume?", "templeoflegendskelight4", false);
        addSound("[1/4] Kelight: Oh, so you actually managed to find the blighter. I was getting tired of waiting for you.", "templeoflegendskelight5", false);
        addSound("[1/4] Kelight: .. You... you did it? H-how?!", "templeoflegendskelight6", false);
        addSound("[1/6] Rayshyroth: Oh? Well, greetings. What brings you to our quiet lonely island, let alone all the way up here?", "templeoflegendsrayshyroth1", false);
        addSound("[1/9] Rayshyroth: Aha, you're back with the Sky Vapor! Sorry about the climb, even I find it rather inconvenient at times.", "templeoflegendsrayshyroth2", false);

        //Cook Assistant
        addSound("[1/4] The Cook: I can't believe what is happening to me!", "cookassistant1", false);
        addSound("[1/3] The Cook: Great! You got all the ingredients!", "cookassistant2", false);

        //Clearing the Camps
        addSound("[1/6] Captain Kymer: Aha, another Ragni soldier, eh? Well, a warm welcome to you.", "clearingthecampskymer1", false);
        addSound("[1/5] Captain Kymer: Ah, the soldier from earlier! You're back, I see. Any luck defeating those- ...yeesh. Are those their heads?", "clearingthecampskymer2", false);

        //The Sewers Of Ragni
        addSound("[1/4] Jenprest: Soldier! Good timing. We've been requesting help for ages.", "thesewersofragnijenprest1", false);
        addSound("[1/3] Jenprest: Ah you're already here. Coming up behind you soldier!", "thesewersofragnijenprest2", false);
        addSound("[1/3] Jenprest: Do you feel like there's something lurking that doesn't want us here?", "thesewersofragnijenprest3", false);
        //FIX TYPO WYNNN!! NOT WORKING
        addSound("[1/3] Jenprest: Ah, you're alive! Looks like we underestimated the amount of sewage.", "thesewersofragnijenprest4", false);
        addSound("[1/1] Jenprest: Oh no, oh no no no! Did you just open that? Come here immediately.", "thesewersofragnijenprest5", false);
        addSound("[1/6] Jenprest: Oh no...", "thesewersofragnijenprest6", false);
        addSound("[1/1] ???: Release me...", "thesewersofragniquestionmark", false);

        //Decrepit Sewers
        addSound("[1/1] ???: You dare enter these sewers, human? These pipes once hid the civilians of Ragni during raids... Now it just holds corpses.", "decriptsewerswitherehead1", true);
        addSound("[1/1] ???: The forces of the Humans could not save me when I hid here, and they can't save you either.", "decriptsewerswitherehead2", true);
        addSound("[1/1] Witherhead: You persist through the sludge, now you will join me like the rest!", "decriptsewerswitherehead3", true);

        //Star Thief
        addSound("[1/1] ???: It's... mine.", "starthiefthief1", true);
        addSound("[1/1] Thief: .... I can't control myself...fjfjfj This rock...", "starthiefthief2", true);
        //NOT WORKING
        addSound("[1/2] Thief: What is... this feeling… No I won't listen..", "starthiefthief3", false);
        addSound("[1/5] Agent: Hey you! Human. What are you doing here?", "starthiefagent1", false);
        addSound("[1/4] Agent: Hello again. Did you find the meteor down there? Why on earth are you holding some?!", "starthiefagent2", false);

        //Arachnids Ascent
        addSound("[1/8] Enduyn: At ease, soldier! Did Cob send you?", "arachnidsascent-edyun-1", false);
        addSound("[1/5] Enduyn: Ah perfect! Now give me that bucket.", "arachnidsascent-edyun-2", false);
        addSound("[1/7] Private Cob: ‘ey there my good chap! I require some assistance!", "arachnidsascent-cob-1", false);

        //The house of twain
        addSound("[1/10] Twendle: Oh, hey you there! Are you heading down this road? You know it's a dead end, right?", "thehouseoftwaintwendle1", false);
        addSound("[1/2] Twendle: I guess I put my faith in the right person. My history lies within these pages, and I don't intend to keep them secret. I shall make them public.", "thehouseoftwaintwendle2", false);

        //Tower of Ascension
        addSound("[1/11] Ankou: Hehehe... Hello there.", "towerofascension-ankou-1", false);
        addSound("[1/6] Ankou: You have completed my tower, I see...", "towerofascension-ankou-2", false);

        //One Thousand Meters Under
        addSound("[1/3] The Secretary: Hello, welcome to Ynnos' facility.", "onethousandmetersundersecretary", false);
        addSound("[1/11] Scientist Ynnos: *Ahem* Welcome, everyone.", "onethousandmetersunderscientistyvnoss1", false);
        addSound("[1/10] Scientist Ynnos: You survived! Where is everyone else?", "onethousandmetersunderscientistyvnoss2", false);
        addSound("[1/2] Arlene: I wonder what the mechanic is doing?", "onethousandmetersunderarlene1", false);
        addSound("[1/2] Kantor: Attention crew members.", "onethousandmetersunderkantor1", true);
        addSound("[1/2] Kantor: Thank you so much! I thought we would all die before the mission even started!", "onethousandmetersunderkantor2", false);
        addSound("[1/1] Kantor: Ah! finally! Line up the white rope with the blue blocks. I need to control things from here. Push those blocks into line so we won't just fall down!", "onethousandmetersunderkantor3", false);
        addSound("[1/1] Captain Olof: We have encountered our first island, 100m down. Crew members, climb up the ladder and search the island for the crystal.", "onethousandmetersunderolof1", true);
        addSound("[1/3] Captain Olof: We've arrived at the next void island.", "onethousandmetersunderolof2", false);
        addSound("[1/3] Captain Olof What are these creatures!", "onethousandmetersunderolof3", true);
        addSound("[1/1] Captain Olof: Don't let the creatures break through glass! Press against it and scare them off!", "onethousandmetersunderolof4", false);
        addSound("[1/3] Captain Olof: This is not good, the creatures destroyed part of the windshield.", "onethousandmetersunderolof5", false);
        addSound("[1/2] Captain: Attention crew. We have arrived at the second island, 300m down. We cannot progress without making some emergency repairs.", "onethousandmetersunderolof6", true);
        addSound("[1/5] Captain Olof: We need to repair the submarine.", "onethousandmetersunderolof7", false);
        addSound("[1/3] Captain Olof: Ech! What is this stuff? Well, this should do the trick.", "onethousandmetersunderolof8", false);
        addSound("[1/6] Jesp: Oh, blast! Do you smell that?", "onethousandmetersunderjesp1", true);
        addSound("[1/3] Captain Olof: Attention crew. We have arrived at the next island 700m down.", "onethousandmetersunderolof9", true);
        addSound("[1/5] Rex: We got this, just take care of the monsters while we build the bridge!", "onethousandmetersunderrex1", true);
        addSound("[2/5] Manny: Dont die! We're soon done.", "onethousandmetersundermanny1", true);
        addSound("[3/5] Jesp: This is hard, but we'll make it!", "onethousandmetersunderjesp2", true);
        addSound("[4/5] Arlene: Be careful, it seems to be getting tougher. The bridge is soon ready to use.", "onethousandmetersunderarlene2", true);
        addSound("[5/5] Manny: Hey, the bridge is ready!", "onethousandmetersundermanny2", true);
        addSound("[1/4] Rex: We didn't find the crystal here either. Maybe it just fell in the void?", "onethousandmetersunderrex2", true);
        addSound("[1/13] Celuuse: Whoooooooo are you!?", "onethousandmetersunderceluuse", true);
        addSound("[1/8] Rontaid: WHAT...WHO...Humans in the void!?", "onethousandmetersunderrontaid", true);

        //Recipe for disaster
        addSound("[1/4] Frank: How are ya? Hamsey said you're an old buddy of his, helped him while he was still in Ragni.", "recipefordisastefrank1", false);
        addSound("[1/9] Frank: So you decided to work with me, huh? I promise I won't dissapoint.", "recipefordisastefrank2", false);
        addSound("[1/2] Frank: Well done on getting them items, some of 'em are pretty tough to find!", "recipefordisastefrank3", false);
        addSound("[1/5] Brie: Heya! You must be the adventurer that Hamsey told me about. You know, he was almost ecstatic, but don't tell him I said that!", "recipefordisasterbrie1", false);
        addSound("[1/8] Brie: Heya again! So you decided to make the Zork Stew with me? Hurray!", "recipefordisasterbrie2", false);
        addSound("[1/2] Brie: Oh my gosh, you came back! You were gone for quite a while and I started getting worried about you!", "recipefordisasterbrie3", false);
        addSound("[1/5] Kale: When Hamsey told me about a good friend of his, I didn't expect... this.", "recipefordiasterkale1", false);
        addSound("[1/7] Kale: When Hamsey told me about a good friend of his, I didn't expect... this.", "recipefordiasterkale2", false);
        addSound("[1/3] Kale: Oh, it's you again. You got the ingredients... it's about time!", "recipefordiasterkale3", false);
        addSound("[1/8] Chef Hamsey: Bonjour, let me take you to your seat, monsieu..mada-this way!", "recipefordisasterhamsey1", false);
        addSound("[1/7] Chef Hamsey: Welcome to ma cuisine! C'est beau, non?", "recipefordisasterhamsey2", false);
        addSound("[1/4] Chef Hamsey: Welcome back, mon ami! I see you've return with a dish in hand!", "recipefordisasterhamsey3", false);
        addSound("[1/4] Chef Hamsey: I cannot help but notice that your dish is ready!", "recipefordisasterhamsey4", false);
        addSound("[1/4] Chef Hamsey: I see you've returned, and not empty handed. Please, show me what you've created!", "recipefordisasterhamsey5", false);
        addSound("[1/9] Talking Mushroom: ISN'T THIS JUST ABSOLUTELY FANTASTIC. A HUMAN?! WHO WOULD HAVE GUESSED.", "recipefordisastertalkingmushroom", false);

        //Beyond the Grave
        addSound("[1/4] Death: IT SEEMS I HAVE A NEW GUEST. WELCOME.", "beyondthegravedeath1", true);
        addSound("[1/3] Death: I SUGGEST YOU DON'T EAT ANYTHING THAT YOU ARE SERVED HERE, I HEARD IT IS TO DIE FOR.", "beyondthegravedeath2", true);
        addSound("[1/2] Death: THIS IS AN EMPTY ROOM.", "beyondthegravedeath3", true);
        addSound("[1/1] Death: FEET OFF THE TABLE PLEASE.", "beyondthegravedeath4", false);
        addSound("[1/3] Death: THIS IS THE LIBRARY, WHERE MY GUESTS COME TO SPEND THEIR TIME.", "beyondthegravedeath5", true);
        addSound("[1/4] Death: IT SEEMS YOU REALLY WANTED TO KNOW WHAT WAS BEHIND THIS DOOR.", "beyondthegravedeath6", true);
        addSound("[1/2] Death: ANOTHER DOOR.", "beyondthegravedeath7", true);
        addSound("[1/4] Death: THESE HOURGLASSES SHOW THE LIFESPAN OF HUMANS. WHEN ALL THE SAND HITS THE BOTTOM, I GET A NEW GUEST.", "beyondthegravedeath8", true);
        addSound("[1/1] Death: IF ANYONE ASKS, YES, I AM MARRIED. JUST BECAUSE I DON'T HAVE A HEART DOESN'T MEAN THAT I CAN'T LOVE.", "beyondthegravedeath9", true);
        addSound("[1/4] Death: THIS IS MY OFFICE. GUESS WHAT I DO HERE. HUH?", "beyondthegravedeath10", true);
        addSound("[1/14] Death: I KNOW THIS SEEMS LIKE AN ODD PLACE TO BE. SITTING IN FRONT OF DEATH.", "beyondthegravedeath11", false);
        addSound("[1/2] Death: SOMETHING I'M FINDING INCREASINGLY ANNOYING IS HOW MANY PEOPLE ARE FINDING WAYS TO BECOME IMMORTAL NOWADAYS. INTELLIGENT COMPANY IS STARTING TO GET A BIT SCARCER.", "beyondthegravedeath12", true);
        addSound("[1/5] Death: WELL, I SEE YOU ARE LEAVING.", "beyondthegravedeath13", true);
        addSound("[1/1] Death: STAY AWAY FROM THAT ROOM. IT'S NOT GOOD FOR EITHER OF US.", "beyondthegravedeath14", true);
        addSound("[1/7] Irlok: Welcome to Bantisu Temple. We guide adventuring souls onwards.", "beyondthegraveirlok1", false);
        addSound("[1/5] Irlok: Oh, you've returned. I must admit, I'm very impressed.", "beyondthegraveirlok2", false);

        //Blazing retribution
        addSound("[1/10] Detective Jackson: How can I go about this...? I can't get involved myself, and they won't let me near... Wait, a human?", "blazingretributiondetectivejackson1", false);
        addSound("[1/9] Detective Jackson: I see no axe in your hands, so I should assume neither Sablestone or Loamsprout had it.", "blazingretributiondetectivejackson2", false);
        addSound("[1/4] Detective Jackson: That looks like the axe of Llevigar to me! Blue finish, hot to the touch- well done! What Orc boss had stolen it?", "blazingretributiondetectivejackson3", false);
        addSound("[1/1] ???: GRRRH!! What 'Uthniiazek' mean?!", "blazingretributiongreggr1", true);
        addSound("[1/1] Gregg'r: Uthniiazek! Uthniiazek!! WHY NO LISTEN TO GREGG'R?", "blazingretributiongreggr2", true);
        addSound("[1/3] Gregg'r: Stupid Villager magic thing! Why fire no listen?", "blazingretributiongreggr3", true);
        addSound("[1/7] Piere: Oh sweet baby bovine, my house, what am I going to do? Wait, who are you?", "blazingretributionpiere", false);

        //Potion Making
        addSound("[1/4] Dr. Essren: Ah, you must be the one my assistant told me about.", "potionmakingdressren1", true);
        addSound("[2/4] Dr. Essren: If you are trying to stop me, it's too late. I already drank the pot-...", "potionmakingdressren2", true);
        addSound("[3/4] Dr. Essren: What is this taste? Mushrooms? This can't b-", "potionmakingdressren3", true);
        addSound("[4/4] Dr Essren: Aaaaaarrrggg!", "potionmakingdressren4", true);
        addSound("[1/1] Transformed Essren: What have you done to this potion, human? You will pay for it!", "potionmakingdressren5", true);
        addSound("[1/5] The Assistant: Heeh...stranger. I...may need your assistance...", "potionmakingassistant1", false);
        addSound("[1/5] The Assistant: Good...good. You have the mushrooms.", "potionmakingassistant2", false);
        addSound("[1/3] The Assistant: I...must thank you, stranger...", "potionmakingassistant3", false);

        //Underwater
        addSound("[1/6] Sayrr: Why ya looking at me like that? Is it cause I'm small, eh? I'll have you know that I caught fish that were twice your size!", "underwatersayrr1", false);
        addSound("[1/2] Sayrr: Ah! Welcome back! Any luck with the fish?", "underwatersayrr2", false);
        addSound("[1/9] Omango: Hi, my name is Omango. I am a resident of the Maltic village, on top of the hill.", "underwateromango1", false);
        addSound("[1/2] Omango: You got it! You found the treasure!", "underwateromango2", false);

        //Elemental Exercise
        addSound("[1/7] Ragon: Ah! Finally! I've been waiting for a recruit like yourself to turn up.", "elementalexercise-ragon-1", false);
        addSound("[1/4] Ragon: Interesting, it completely destroyed the beast. It seems our theories might be correct.", "elementalexercise-ragon-2", false);
        addSound("[1/1] Ragon: Hey, wake up.", "elementalexercise-ragon-3", true);
        addSound("[1/3] Ragon: Is everything alright? We felt the cave collapse! We came down as soon as possible!", "elementalexercise-ragon-4", true);
        addSound("[1/1] Ragon: Good to see you made it out, would you mind giving your fire weapon back to Suri and then bring me his study results?", "elementalexercise-ragon-5", false);
        addSound("[1/6] Ragon: Thank you, I guess you can use this, it's not a lot but we are not the richest scientists around, sorry for all the trouble we put you through.", "elementalexercise-ragon-6", false);

        //Dwelling Walls
        addSound("[1/10] Leucsaa: Let's see here... mother's old vase… check!", "dwellingwalls-leucsaa-1", false);
        addSound("[1/3] Leucsaa: The journal! You outsmarted the mansion!", "dwellingwalls-leucsaa-2", false);

        //Pit of the dead
        addSound("[1/8] Merloni: Would you like to hear the tale of the pit of the dead?", "pitofthedead-merloni-1", false);
        addSound("[1/1] Merloni: Very impressive! Here's your reward. I found it a while ago while walking around Nemract.", "pitofthedead-merloni-2", false);

        //Deja Vu
        addSound("[1/6] Asher: Well well well, look who decided to come back with their tail between their legs.", "dejavu-asher-1", false);
        addSound("[2/6] Asher: Two weeks now and you still haven't found it? Ugh, I can't believe I ever trusted you with such a simple task.", "dejavu-asher-2", false);
        addSound("[3/6] Asher: Who am I? WHAT DO YOU MEAN WHO AM I? You offered to help find my shovel, and I've been waiting two weeks!", "dejavu-asher-3", false);
        addSound("[4/6] Asher: Forget about your payment. Unless you either pay me back or get my shovel right now!", "dejavu-asher-4", false);
        addSound("[5/6] Asher: My shovel? That's why I sent you out in the first place! It's somewhere in Time Valley. I lost it while following a trail of flowers.", "dejavu-asher-5", false);
        addSound("[6/6] Asher: I think it was south west of the large ruined gate. Now go get it!", "dejavu-asher-6", false);
        addSound("[1/7] Asher: Well hello there, stranger! Welcome to Time Valley! Always nice to see a new face around here.", "dejavu-asher-7", false);
        addSound("[2/7] Asher: Mad at you? Why would I be mad at you? We've never met.", "dejavu-asher-8", false);
        addSound("[3/7] Asher: I know this a bit forward, but I need to ask for your help with-", "dejavu-asher-9", false);
        addSound("[4/7] Asher: Wait, is that-? It is! You've found my shovel! I recognize those markings anywhere.", "dejavu-asher-10", false);
        addSound("[5/7] Asher: But why is it so rusty? Ah, no matter. Give that to me and I'll head inside to get you a nice reward.", "dejavu-asher-11", false);
        addSound("[6/7] Asher: Thank you. Now, about that reward-", "dejavu-asher-12", false);
        addSound("[7/7] Asher: Wha- What's happening?", "dejavu-asher-13", false);
        addSound("[1/18] Asher: Huh? Where is everyone?", "dejavu-asher-14", true);
        addSound("[2/18] Asher: Usually this place is filled with workers.", "dejavu-asher-15", true);
        addSound("[3/18] Asher: But no one's here. And this place is clearly not done.", "dejavu-asher-16", true);
        addSound("[4/18] Asher: Whatever the case, I should probably find my shovel.", "dejavu-asher-17", true);
        addSound("[5/18] Asher: If I were a shovel, where would I be?", "dejavu-asher-50", true);
        addSound("[6/18] Asher: Hmmm. Not around here.", "dejavu-asher-18", true);
        addSound("[1/1] Asher: Wait, what's that-?", "dejavu-asher-19", true);
        addSound("[7/18] Asher: This place... There's so much going on but no one here.", "dejavu-asher-20", true);
        addSound("[8/18] Asher: What I don't understand, though, is why this place looks brand new.", "dejavu-asher-21", true);
        addSound("[9/18] Asher: This place looks like it's just being built!", "dejavu-asher-22", true);
        addSound("[10/18] Asher: This must be something to do with the valley's magic. Wouldn't be the first time.", "dejavu-asher-23", true);
        addSound("[11/18] Asher: Heh, time.", "dejavu-asher-25", true);
        addSound("[1/1] Asher: Woah! Where did-?", "dejavu-asher-26", true);
        addSound("[12/18] Asher: In fact, whenever I hold that shovel, this kind of stuff seems to happen.", "dejavu-asher-27", true);
        addSound("[13/18] Asher: Maybe it's sent me way back. Back to the construction of this place.", "dejavu-asher-28", true);
        addSound("[14/18] Asher: Then why is no one here?", "dejavu-asher-29", true);
        addSound("[15/18] Asher: Woah, that tree looks like it might fall over!", "dejavu-asher-30", true);
        addSound("[16/18] Asher: That was a close one!", "dejavu-asher-31", true);
        addSound("[17/18] Asher: Uhf! How do they have a cannon? These guys must be super advanced.", "dejavu-asher-32", true);
        addSound("[18/18] Asher: I think I'm close now. I can just feel that shovel close by.", "dejavu-asher-33", true);
        addSound("[1/11] Asher: Oh there it is! My shovel!", "dejavu-asher-34", true);
        addSound("[2/11] Asher: I'm surprised it took me back this far this time.", "dejavu-asher-35", true);
        addSound("[3/11] Asher: Well, time to head on back-", "dejavu-asher-36", true);
        addSound("[4/11] Asher: Wait, Martyn?", "dejavu-asher-37", true);
        addSound("[5/11] Asher: Ow! What was that for?", "dejavu-asher-38", true);
        addSound("[7/11] Asher: I... Might recall it.", "dejavu-asher-39", true);
        addSound("[9/11] Asher: Oh... So that's where I am. I am so sorry Martyn. Please don't kick me out of the valley.", "dejavu-asher-40", true);
        addSound("[1/14] Asher: Holy Bovine!", "dejavu-asher-41", true);
        addSound("[3/14] Asher: How did you get these powers? Are you even human?", "dejavu-asher-42", false);
        addSound("[6/14] Asher: I probably could have gotten myself out...", "dejavu-asher-43", false);
        addSound("[8/14] Asher: I did?", "dejavu-asher-44", false);
        addSound("[12/14] Asher: Uh, you're still here.", "dejavu-asher-45", false);
        addSound("[14/14] Asher: I don't understand Martyn. He seems to know everything about the past. Here, come closer, I want to whisper something.", "dejavu-asher-46", false);
        addSound("[1/3] Asher: I think he might have known Bob. I saw some letters in his house.", "dejavu-asher-47", false);
        addSound("[2/3] Asher: Weird, right? Bob was a hard guy to know.", "dejavu-asher-48", false);
        addSound("[3/3] Asher: Anyway, take this. It's the least I can do to pay you for saving me.", "dejavu-asher-49", false);
        addSound("[1/3] Old Man Martyn: Oh dear, there he goes again. Poor fool ignored my warnings and touched that shovel.", "dejavu-oldmanmartyn-1", false);
        addSound("[2/3] Old Man Martyn: He's gone and got himself stuck in a loop", "dejavu-oldmanmartyn-2", false);
        addSound("[3/3] Old Man Martyn: Head inside, soldier, and I'll explain what's happening here.", "dejavu-oldmanmartyn-3", false);
        addSound("[1/8] Old Man Martyn: Let me explain why Asher disappeared...", "dejavu-oldmanmartyn-4", false);
        addSound("[2/8] Old Man Martyn: Time Valley was built many years ago by a long-lost civilization.", "dejavu-oldmanmartyn-5", false);
        addSound("[3/8] Old Man Martyn: They had odd powers. They ended up distorting time itself.", "dejavu-oldmanmartyn-6", false);
        addSound("[4/8] Old Man Martyn: The tools they used to make these structures are imbued with their power.", "dejavu-oldmanmartyn-7", false);
        addSound("[5/8] Old Man Martyn: Asher found one of these tools: the shovel.", "dejavu-oldmanmartyn-8", false);
        addSound("[6/8] Old Man Martyn: The shovel is tethering its time of influence to today. It keeps sending Asher back.", "dejavu-oldmanmartyn-9", false);
        addSound("[7/8] Old Man Martyn: The issue is, Asher died in the past. This creates a paradox.", "dejavu-oldmanmartyn-10", false);
        addSound("[8/8] Old Man Martyn: We can save him if I send you back to prevent his death. Go through this rift to join him in the past.", "dejavu-oldmanmartyn-11", false);
        addSound("[6/11] Old Man Martyn: Asher, you remember when I allowed you to live in the valley, I told you not to touch any odd objects.", "dejavu-oldmanmartyn-12", false);
        addSound("[8/11] Old Man Martyn: You got yourself stuck hundreds of years ago. If it wasn't for this guard, you'd be removed from existence.", "dejavu-oldmanmartyn-13", false);
        addSound("[10/11] Old Man Martyn: I know time travel can be tempting. But it's far more dangerous than you can ever understand.", "dejavu-oldmanmartyn-14", false);
        addSound("[11/11] Old Man Martyn: The valley is banned for most citizens. I watch over this timeline because of poor souls like Asher", "dejavu-oldmanmartyn-15", false);
        addSound("[2/14] Old Man Martyn: Ah, we're back.", "dejavu-oldmanmartyn-16", false);
        addSound("[4/14] Old Man Martyn: Oh you know. I've read a book or two in my time.", "dejavu-oldmanmartyn-17", false);
        addSound("[5/14] Old Man Martyn: Soldier, I'm sorry I got you dragged into this. I wish I could have done it myself.", "dejavu-oldmanmartyn-18", false);
        addSound("[7/14] Old Man Martyn: You died in the past. Multiple times.", "dejavu-oldmanmartyn-19", false);
        addSound("[9/14] Old Man Martyn: Yes. In some surprising ways too.", "dejavu-oldmanmartyn-20", false);
        addSound("[10/14] Old Man Martyn: Soldier, I'm sure we will meet again... in time.", "dejavu-oldmanmartyn-21", false);
        addSound("[11/14] Old Man Martyn: See you around.", "dejavu-oldmanmartyn-22", false);
        addSound("[13/14] Old Man Martyn: Am I?", "dejavu-oldmanmartyn-23", false);
        addSound("[1/1] Old Man Martyn: Without Asher, his house has vanished from time.", "dejavu-oldmanmartyn-24", true);
        addSound("[1/2] Old Man Martyn: My my, a crushing defeat to be sure. You'll have to find a way to stop that.", "dejavu-oldmanmartyn-25", false);
        addSound("[2/2] Old Man Martyn: Go back through the rift and make sure Asher doesn't become a pancake this time.", "dejavu-oldmanmartyn-26", false);
        addSound("[1/2] Old Man Martyn: Well that was quite the explosive end to our friend there. There must be a way to prevent that.", "dejavu-oldmanmartyn-27", false);
        addSound("[2/2] Old Man Martyn: Head back in there. This time with less explosions", "dejavu-oldmanmartyn-28", false);
        addSound("[1/2] Old Man Martyn: Oh, so close on that try. Who could've guessed that was how our friend met his end?", "dejavu-oldmanmartyn-29", false);
        addSound("[2/2] Old Man Martyn: I'm sure you'll nail it this time. Not sure how you will stop a cosmic event, though.", "dejavu-oldmanmartyn-30", false);

        //Macabre Masquerade Hallowynn 2014
        addSound("[1/5] Mask Salesman: Hmm... Let's see, wha- Ah! Oh, pardon me, I was distracted. Luckily you're here, because I require some assistance.", "macabremasquerade-masksalesman-1", false);
        addSound("[2/5] Mask Salesman: You see, I was just on my way back from that large mansion near Nesaak, where I went to acquire some antiques from an old business partner.", "macabremasquerade-masksalesman-2", false);
        addSound("[3/5] Mask Salesman: However, along the way, a valuable cursed mask escaped from my caravan and somehow made its way into the dark mine over there!", "macabremasquerade-masksalesman-3", false);
        addSound("[4/5] Mask Salesman: I would've gone after it, but the things I have in this caravan are too important to leave by themselves. Not to mention that the cave seemed to corrupt from the inside out...", "macabremasquerade-masksalesman-4", false);
        addSound("[5/5] Mask Salesman: You must help me retrieve the mask, for I must depart in 3 days! Could you brave the shadowy cave and bring it back?", "macabremasquerade-masksalesman-5", false);
        addSound("[1/4] Mask Salesman: You've met a terrible fate, haven't you? I was just about to run the whole way to Nemract to get help.", "macabremasquerade-masksalesman-6", false);
        addSound("[2/4] Mask Salesman: I would really like to hear what you encountered in that cave, but I must prepare to leave, for I have many masks to sell on a far away continent.", "macabremasquerade-masksalesman-7", false);
        addSound("[3/4] Mask Salesman: Maybe you can visit me at some point, if our paths were to ever cross again. Then we could talk about your journey.", "macabremasquerade-masksalesman-8", false);
        addSound("[4/4] Mask Salesman: To thank you for your help, I wanted to give you a special mask, but I'm out of it. Maybe next year?", "macabremasquerade-masksalesman-9", false);

        //Generals Orders
        addSound("[1/6] Tylas: ZZZZZZZZzzzzzzZZZZZzzzzz...", "generalsorders-tylas-1", false);
        addSound("[2/6] Tylas: AAAHHHHH! You startled me! Sleeping? No, you must be mistaken. I have sworn to guard this fort at all costs, I would never even close my eyes!", "generalsorders-tylas-2", false);
        addSound("[3/6] Tylas: The barracks is not currently operating, I'm afraid. General Lecade has all of the men working on all sorts of absolutely ridiculous tasks.", "generalsorders-tylas-3", false);
        addSound("[4/6] Tylas: You wish to help out? That is very kind of you to offer, but I'm not supposed to let anyone through aside from fellow soldiers.", "generalsorders-tylas-4", false);
        addSound("[5/6] Tylas: Then again, General Lecade hasn't been himself lately. It probably won't even bother him if it means getting his work done.", "generalsorders-tylas-5", false);
        addSound("[6/6] Tylas: I'll let you through, if you wish to help. Speak with General Lecade to find out what insane task he wants you to do, his office lies in the central building, just follow this path.", "generalsorders-tylas-6", false);
        addSound("[1/6] Lecade: Who let you in here? Probably that private who I let guard this place, I'll bet that he was sleeping again. So what brings you here, anyways?", "generalsorders-lecade-1-1-6", false);
        addSound("[2/6] Lecade: So, you wanna help me with the work I need done? Sure, why not? I could use an extra hand around here!", "generalsorders-lecade-1-2-6", false);
        addSound("[3/6] Lecade: All right, I need you to get to work straight away! You see... um... I've heard rumors that some of our equipment isn't working well.", "generalsorders-lecade-1-3-6", false);
        addSound("[4/6] Lecade: Some of our cannons and ballistae, actually. You know, quite dangerous, and whatnot.", "generalsorders-lecade-1-4-6", false);
        addSound("[5/6] Lecade: You can find this equipment outside of our main fort. Just follow the path, they're to the right of our blacksmith.", "generalsorders-lecade-1-5-6", false);
        addSound("[6/6] Lecade: Now what are you waiting for? Get to work!", "generalsorders-lecade-1-6-6", false);
        addSound("[1/4] Lecade: You're back already? You're telling me you reached the equipment and came back already? Impossible!", "generalsorders-lecade-2-1-4", false);
        addSound("[2/4] Lecade: I mean, good work, soldier! While you were gone, some more work freed up. You see, I... accidentally dropped my good-luck charm into an aqueduct, it's in the sewers now.", "generalsorders-lecade-2-2-4", false);
        addSound("[3/4] Lecade: That charm brings me luck, you're going to have to dive into the sewers to retrieve it. I do know of a way you can enter them.", "generalsorders-lecade-2-3-4", false);
        addSound("[4/4] Lecade: There's a crack in a pipe on the other side of the fort that you should be able to use to enter the sewers. Now hurry up, standing around won't get me that charm!", "generalsorders-lecade-2-4-4", false);
        addSound("[1/3] Lecade: What's with that gold thing? Oh, yes, my good-luck charm! Good work, soldier! There's yet another task for you, and I think you're fit for the job.", "generalsorders-lecade-3-1-3", false);
        addSound("[2/3] Lecade: There's a mountain near the town that's always looked rather dull to me. I want you to paint it a different color, make it look nice.", "generalsorders-lecade-3-2-3", false);
        addSound("[3/3] Lecade: Yes, how about you paint it purple? I wrote the coordinates down in your quest book, so get to it!", "generalsorders-lecade-3-3-3", false);
        addSound("[1/3] Lecade: You painted the mountain already? I can't believe this! How are you doing everything I order you to so quickly? You should be exhausted!", "generalsorders-lecade-4-1-3", false);
        addSound("[2/3] Lecade: I mean, you have shown you are an extremely capable soldier! In fact, so capable, that I think you're ready for this next task.", "generalsorders-lecade-4-2-3", false);
        addSound("[3/3] Lecade: I want you to walk across a tightrope to amuse your fellow soldiers. The coordinates are in your quest book, so go put on a show!", "generalsorders-lecade-4-3-3", false);
        addSound("[1/1] Private: I'll bet you five emeralds they don't make it across.", "generalsorders-private-1-1", false);
        addSound("[1/3] Lecade: Wait, you actually went and did it? I didn't think anyone would do something so completely ridiculous!", "generalsorders-lecade-5-1-3", false);
        addSound("[2/3] Lecade: Fine, if you're still so eager to help after all of this, then I'll give you one last task to do.", "generalsorders-lecade-5-2-3", false);
        addSound("[3/3] Lecade: I need you to destroy a wall in the fort so we can  add another house. Lieutenant Gren is there to give you the details.", "generalsorders-lecade-5-3-3", false);
        addSound("[1/3] Gren: Stay away from this area! It's marked for demolition, we're just waiting for a soldier General Lecade sent in to do the job.", "generalsorders-gren-1-3", false);
        addSound("[2/3] Gren: Wait, you're supposed to be the one handling explosives? Well, better than me risking my own neck!", "generalsorders-gren-2-3", false);
        addSound("[3/3] Gren: In that case, just go light the explosives, and get back before you get blown to bits!", "generalsorders-gren-3-3", false);
        addSound("[1/7] ???: You were sent here under the orders of General Lecade? Seems that fool forgot he locked me up in here. Listen, that man can't be who he says he is, because...", "generalsorders-lecade-6-1-7", false);
        addSound("[2/7] ???: I am General Lecade.", "generalsorders-lecade-6-2-7", false);
        addSound("[3/7] Lecade: I know that sounds preposterous. The man you spoke to looks exactly like me, doesn't he? Well, let me explain everything I know about that impostor.", "generalsorders-lecade-6-3-7", false);
        addSound("[4/7] Lecade: It is actually a rare Gavel creature called \"Corpus Accipientis\" or in layman's terms, a shape-shifter. It took my identity and locked me up in this cell.", "generalsorders-lecade-6-4-7", false);
        addSound("[5/7] Lecade: That thing loves to create mischief, it lives to make others' lives harder. It must be doing horrible things to my loyal men, that beast!", "generalsorders-lecade-6-5-7", false);
        addSound("[6/7] Lecade: I want you to kill it. I know a way into my office, or its office now, from here. After all, I'm the one who had this place built.", "generalsorders-lecade-6-6-7", false);
        addSound("[7/7] Lecade: Hold down both wooden plates on the desks over there and a secret door should open. I'd follow you, but I am unable to leave this cell.", "generalsorders-lecade-6-7-7", false);
        addSound("[1/3] Corpus Accipientis: So, looks like you've found me out. You know, all I wanted to do was control an army, I wasn't even going to kill anyone!", "generalsorders-corpusaccipientis-1-3", true);
        addSound("[2/3] Corpus Accipientis: But you have to come in and ruin this, don't you? Such a shame, I was going to ask if I could borrow your form for a while.", "generalsorders-corpusaccipientis-2-3", true);
        addSound("[3/3] Corpus Accipientis: You know, defeating you will allow me to take your form and continue posing as the General. Yes, I'll use my true form to take you out!", "generalsorders-corpusaccipientis-3-3", true);
        addSound("[1/1] Lecade: Fear not, soldier! I am no slimy impostor, now bring me that shape-shifter's remains so I can lock them up!", "generalsorders-lecade-7-1-1", true);
        addSound("[1/5] Lecade: Well, looks like you destroyed a big blob of slime and came back without a scratch. Now that's what I like to see.", "generalsorders-lecade-8-1-5", false);
        addSound("[2/5] Lecade: I'm surprised you pulled that off, though. When I hunted that thing down, it managed to take my form. Guess I can learn a few tricks from you", "generalsorders-lecade-8-2-5", false);
        addSound("[3/5] Lecade: Well, at least that shape-shifter won't be taking anyone's body anymore. That thing already caused enough trouble when it only took mine, imagine if it took more.", "generalsorders-lecade-8-3-5", false);
        addSound("[4/5] Lecade: The soldiers understood the situation, luckily. Can't believe it got that far. I'll make sure nothing like that ever happens again!", "generalsorders-lecade-8-4-5", false);
        addSound("[5/5] Lecade: But enough of that, you helped out a tremendous deal. And for that you deserve a reward.", "generalsorders-lecade-8-5-5", false);

        //Tunnel trouble
        addSound("[1/1] Bylvis: Ah, good to see my cows are all in tip-top shape! Welp, time to feed the townsfolk again!", "tunneltrouble-bylvis-1", true);
        addSound("[1/2] Bylvis: Huh? Hey!", "tunneltrouble-bylvis-2", true);
        addSound("[1/5] Captain Fenor: Ah! You must be a new recruit! Well, I've got a mission for you.", "tunneltrouble-captainfenor-1", false);
        addSound("[4/5] ???: Oh, uh... Hi... Well, this is a little awkward... I'll just... Go.", "tunneltrouble-drale-1", false);
        addSound("[1/4] Drale: Oh... Didn't expect you to follow me... Well, um... Hi! This will sound mad but...", "tunneltrouble-drale-2", false);
        addSound("[1/2] Drale: Thank you so much, but we're not done yet. We need to get these cows out the province.", "tunneltrouble-drale-3", false);
        addSound("[1/1] Drale: There's the raft! Help me push it for the cows!", "tunneltrouble-drale-4", false);
        addSound("[1/2] Drale: And, that should do it! Be free, my friends! A new life awaits!", "tunneltrouble-drale-5", false);
        addSound("[5/11] Drale: Hello again, friend! Did I hear you needed help? Well, have no fear! Cows, to me!", "tunneltrouble-drale-6", false);
        addSound("[7/11] Drale: Since you helped me with my problem, I thought I should repay that favor by helping you!", "tunneltrouble-drale-7", false);
        addSound("[8/11] Drale: Welp, see ya! Oh, and nice to meet you, miner! Now, let's get going my cows!", "tunneltrouble-drale-8", false);
        addSound("[1/3] Miner Linton: Ah, recruit! You must be the one Cap'n Fenor sent!", "tunneltrouble-minerlinton-1", false);
        addSound("[1/1] Miner Linton: Nice job! Let's move cautiously, We don't really know what's down here...", "tunneltrouble-minerlinton-2", false);
        addSound("[1/1] Miner Linton: Wait... Do you hear that? Ah, monsters! Do your job soldier!", "tunneltrouble-minerlinton-3", false);
        addSound("[1/2] Miner Linton: Wha?!", "tunneltrouble-minerlinton-4", false);
        addSound("[2/2] Miner Linton: Ah, that cave-in just blocked the path! Defend me while I try and make a hole!", "tunneltrouble-minerlinton-5", false);
        addSound("[1/1] Miner Linton: Keep it up! I think I'm half way there!", "tunneltrouble-minerlinton-6", false);
        addSound("[1/1] Miner Linton: Aha! I can see the other side! Just a little more...", "tunneltrouble-minerlinton-7", false);
        addSound("[1/1] Miner Linton: Got it, I made a hole! Quickly, let's go before we're overrun!", "tunneltrouble-minerlinton-8", false);
        addSound("[1/5] Miner Linton: This cave barely seems worth the trouble to me...", "tunneltrouble-minerlinton-9", false);
        addSound("[2/5] Miner Linton: This wall of debris is much too big for us to be able to move. What do we do now?", "tunneltrouble-minerlinton-10", false);
        addSound("[3/5] Miner Linton: hmm... Maybe if we- wait, what's happening to the wall to the right?", "tunneltrouble-minerlinton-11", false);
        addSound("[5/5] Miner Linton: ...Who in the world was that? You can go check that hole out if you want, but I'll stay here and try to think of a solution to this debris.", "tunneltrouble-minerlinton-12", false);
        addSound("[1/11] Miner Linton: Recruit, what took you so long? I've been waiting for an hour! What were you doing?", "tunneltrouble-minerlinton-13", false);
        addSound("[2/11] Miner Linton: ...You helped some crazy cow-obsessed lunatic steal cows from the local farm? Recruit, that's against the law you know...", "tunneltrouble-minerlinton-14", false);
        addSound("[3/11] Miner Linton: Oh, what do I care? Silly Bovemists.", "tunneltrouble-minerlinton-15", false);
        addSound("[4/11] Miner Linton: Either way, we still don't have any means to get rid of this giant pile of rubble...", "tunneltrouble-minerlinton-16", false);
        addSound("[6/11] Miner Linton: What in the world?", "tunneltrouble-minerlinton-17", false);
        addSound("[9/11] Miner Linton: ...What just happened? Am I seeing things? You know what, I don't want to know.", "tunneltrouble-minerlinton-18", false);
        addSound("[10/11] Miner Linton: Anyway, why don't you go on ahead? I'll be here guarding this passage so nothing else bad happens to it.", "tunneltrouble-minerlinton-19", false);
        addSound("[11/11] Miner Linton: It was an honor to adventure with you, recruit!", "tunneltrouble-minerlinton-20", false);
        addSound("[1/2] Sergeant Klafson: Recruit, Captain Fenor told me to meet you here, said if you came out on this side of that tunnel then you completed your mission!", "tunneltrouble-sergantklafson-1", false);

        //Dark Descent
        addSound("[1/1] ???: Keheh... Come further, little human... Step into the shadows...", "darkdescent-charon-1", true);
        addSound("[1/1] ???: Welcome...", "darkdescent-charon-2", true);
        addSound("[2/6] ???: Ah, but if you had only said so sooner! They have already left this mortal coil...oh, what a shame.", "darkdescent-charon-3", true);
        addSound("[4/6] ???: Hush now. I will ensure that you and your family are reunited. You will even be able to partake in...keheh...family outings, under my watchful eye...", "darkdescent-charon-4", true);
        addSound("[6/6] ???: Aha, naive girl. How cute, you believe you have a choice in the matter. What you want, is not what I need.", "darkdescent-charon-5", true);
        addSound("[1/6] ???: Heheh...do you see now?", "darkdescent-charon-6", true);
        addSound("[2/6] ???: Do you see how easily this place fell? How quickly I became its master? And how I could simply dispose of you without a second thought?", "darkdescent-charon-7", true);
        addSound("[3/6] ???: Such fragile beings, humans. So very easily influenced. Heartbroken, made hopeless. Your ties only serve to weaken you.", "darkdescent-charon-8", true);
        addSound("[4/6] ???: So I invite you to continue. I encourage it, even. For if you should foray into my domain...", "darkdescent-charon-9", true);
        addSound("[5/6] ???: There would be simply no chance for you to survive. And then, I will be free to string you up like a lifeless little puppet.", "darkdescent-charon-10", true);
        addSound("[6/6] ???: So take the gem, my little soldier. I eagerly await our next meeting, keheheh...", "darkdescent-charon-11", true);
        addSound("[1/3] ???: I will allow you to return now... and I will ensure you remember what you have seen here.", "darkdescent-charon-12", true);
        addSound("[2/3] ???: Good luck on your little quest...keheheh...", "darkdescent-charon-13", true);
        addSound("[1/11] General Graken: Ah, hello there chap! I must say, I didn't expect to see a part of the old Ragni regiment over 'ere.", "darkdescent-generalgraken-1", false);
        addSound("[1/5] General Graken: Oh, sod it, I knew I was forgetting something! Steady there, chum! I'm on my way!", "darkdescent-generalgraken-2", true);
        addSound("[2/5] General Graken: Drat! Too late, it seems. Yes, I forgot to mention the floor was in poor condition. Up here! You took quite the tumble, are you alright?", "darkdescent-generalgraken-3", true);
        addSound("[3/5] General Graken: Well, there's some good news, I suppose. Still though, seems I sent you a ticket to a bit of a sticky wicket! Do you see a way out anywhere down there?", "darkdescent-generalgraken-4", true);
        addSound("[4/5] General Graken: There's a tunnel? Aha, spiffing! I suggest you take it immediately. I'll have to return to my post now, post-haste!", "darkdescent-generalgraken-5", true);
        addSound("[5/5] General Graken: Good luck, chum! Now, back, you cads! Back I say!", "darkdescent-generalgraken-6", true);
        addSound("[3/3] General Graken: Hey! Come on, come over here! Quit milling about with the zombies, chap!", "darkdescent-generalgraken-7", true);
        addSound("[1/10] General Graken: Oi! Hallo?! Chum? Why're you wandering round all bog-eyed? Come back to me now!", "darkdescent-generalgraken-8", false);
        addSound("[1/6] Lost Soul: Please, stop this! Whoever you are, leave me and my family in peace! What did we ever do to deserve this?!", "darkdescent-lostsoul-1", true);
        addSound("[3/6] Lost Soul: Y-you...I thought they...oh, god, no...", "darkdescent-lostsoul-2", true);
        addSound("[5/6] Lost Soul: Wha...you mean... N-no, please! I don't want to hurt anyone! I don't want to die!!", "darkdescent-lostsoul-3", true);
        addSound("[2/5] Scout: I...there...too many...th-thousands, at...at least...", "darkdescent-scout-1", true);
        addSound("[4/5] Scout: No point...we're...doomed...", "darkdescent-scout-2", true);
        addSound("[1/5] Soldier: Scout! Report your findings, immediately! How many corrupteds are coming?", "darkdescent-soldier-1", true);
        addSound("[3/5] Soldier: Thousands...? This is far worse than I expected. Men! Prepare for-", "darkdescent-soldier-2", true);
        addSound("[5/5] Soldier: Wait, w-what's going on? What's happening to you?!", "darkdescent-soldier-3", true);
        addSound("[1/1] ???: How foolish. You might think you are strong, adventurer, but you have no idea what you are about to face...", "darkdescentdungeon-charon-1", true);
        addSound("[1/1] Charon: I have an entire undead army at my service. Do you really think you stand a chance?", "darkdescentdungeon-charon-2", true);
        addSound("[1/1] Charon: I see you've brought some help with you, you aren't as foolish as I thought. However, how do you plan to defeat an army that cannot die?", "darkdescentdungeon-charon-3", true);
        addSound("[1/6] General Graken: Aha, chap! Wonderful, the Ragni regiment's gotten its representative down 'ere. All rested up, I suppose, so let me give you an earful.", "darkdescent-generalgraken-9", false);
        addSound("[1/4] General Graken: Simply marvellous work, chap! You've made it this far, so no time to fill your boots. Be prepared, we are about to fight Charon's undead army.", "darkdescent-generalgraken-10", false);

        //Zhight island
        addSound("[1/12] Czytash: Ah, yes, I did find plenty o' supplies on that ship.", "zhightisland-czytash-1", false);
        addSound("[1/9] Phief: Hello there. I see Zhight has sent you, correct?", "zhightisland-phief-1", false);
        addSound("[1/9] Tirt: He told you what?! That fumbleknuckle...", "zhightisland-tirt-1", false);
        addSound("[1/8] Zhight: Hello, esteemed tourist sir or madame! I see you have found my illustrious resort, Zhight Island!", "zhightisland-zhight-1", false);
        addSound("[1/7] Zhight: Ahaaa, esteemed sir! Or madame! I can't actually tell under all that armour...", "zhightisland-zhight-2", false);

        //Fallen delivery
        addSound("[1/3] Bricot: Are you here to help? Did you hear the explosion, or my girlish scream? Either way, you have to help!", "fallendelivery-bricot-1", false);
        addSound("[1/8] Gawrick: I'm working on spells hitherto unexplored and unknown to villager kind, please leave!", "fallendelivery-gawrick-1", false);
        addSound("[1/7] Gawrick: Oh, you're back. Were you successful? While you were gone I created a spell that turns cheese into wine.", "fallendelivery-gawrick-2", false);
        addSound("[1/4] Office Manager: Greetings, do you require assistance?", "fallendelivery-officemanager-1", false);
        addSound("[1/1] Office Manager: Hey! You are not allowed in there!", "fallendelivery-officemanager-2", true);
        addSound("[1/1] Office Manager: Hey! you are not permitted to enter the office!", "fallendelivery-officemanager-3", true);
        addSound("[1/2] Representative Ernold: Heh... HEH... HEHE! Everytime I teleport, I leave a little more of my sanity behind! But I get stronger!", "fallendelivery-representativeernold-1", true);
        addSound("[1/6] Marden: How did you get up here, human? What are they doing down there if not defending the fort!?", "fallendelivery-marden-1", false);

        //The Passage
        addSound("[1/10] Ildan: Oh! So you want to become a member of this town?", "thepassage-ildan-1", false);
        addSound("[1/8] Ildan: Impressive! You got the bead!", "thepassage-ildan-2", false);
        addSound("[1/3] Wirt: Not so fast, you!", "thepassage-wirt-1", false);

        //Lava Springs
        addSound("[1/6] Colonel Gailard: Oh, good good! I bet'cha you're a Ragni soldier, aren't ya? Listen up, soldier! I've got a task here that 'ye can help out with.", "lavasprings-colonelgailard-1", false);
        addSound("[1/8] Colonel Gailard: Alright, good! Ya found some evidence, let me take a look at that!", "lavasprings-colonelgailard-2", false);
        addSound("[1/6] Colonel Gailard: Report! Ya got the potion yet, soldier? Good, drink it if ya haven’t already!", "lavasprings-colonelgailard-3", false);
        addSound("[1/1] Colonel Gailard: Hah! I knew ya’d give me results! Ye’re pretty damn reliable, soldier! Here, take this for your trouble!", "lavasprings-colonelgailard-4", false);
        addSound("[1/4] Gregor: Hey, hey hey hey! Darling, what do you think you’re doing? You can’t just waltz in here without telling me the password!", "lavasprings-gregor-1", false);
        addSound("[1/8] Nami the Healer: G’day to you, friend. How can I help you?", "lavasprings-namithehealer-1", false);

        //Desperate Metal
        addSound("[1/7] Nettik: What are these? I have nothing to do with them!", "desperatemetal-nettik-1", false);
        addSound("[1/1] Nettik: I'm sorry it ended up like this, you should've just ignored that old man.", "desperatemetal-nettik-2", true);
        addSound("[1/7] Phinas: Oh my...", "desperatemetal-phinas-1", false);
        addSound("[1/6] Phinas: You Wynn folk sure know your fights.", "desperatemetal-phinas-2", false);
        addSound("[1/6] Phinas: Did you find the source of those odd handmade mechs?", "desperatemetal-phinas-3", false);
        addSound("[1/5] ???: Stranger detected... Security system activated.", "desperatemetal-security-1", true);
        addSound("[2/5] ???: Security drop-chute engaging. 5... 4...", "desperatemetal-security-2", true);
        addSound("[3/5] ???: 3...", "desperatemetal-security-3", true);
        addSound("[4/5] ???: 2...", "desperatemetal-security-4", true);
        addSound("[5/5] ???: 1...", "desperatemetal-security-5", true);

        //Cowfusion
        addSound("[1/4] Cow Guard: Want to consult the Holy Spirit of Moo'in, huh? You can't without the Drale's permission. Have you got it?", "cowfusion-cowguard-1", false);
        addSound("[1/1] Cow Guard: Halt! You may not talk to the Holy Spirit of Moo'in without the permission of Drale!", "cowfusion-cowguard-2", false);
        addSound("[1/5] Ranol: A Human? All the way here at the end of the Canyon? Incredible resilience.", "cowfusion-ranol-1", false);
        addSound("[1/1] Ranol: Zs! Nv xld'h yzxp! Tvg rm gsv kvm mld", "cowfusion-ranol-2", false);
        addSound("[1/3] Ranol: Where have you been? I saw my cow run off into the barn over there a while ago. You seen it?", "cowfusion-ranol-3", false);
        addSound("[1/5] Drale: Oh, a new cow, welcome! Let me introduce myself.", "cowfusion-drale-1", false);
        addSound("[1/6] Drale: I guess you are a real cow. We will aid you in your venture to become human again, at least, that's why I assume you are here.", "cowfusion-drale-2", false);
        addSound("[1/10] Ibele: Oh, another cow. You poor soul. I saw you talking to the farmer, but obviously you didn't speak moo'n", "cowfusion-ibele-1", false);
        addSound("[1/2] Human: Texq exmmbkba? Tebob xj F? Xeee! X zlt! Qexw'p jb! Fp qefp x jfoolo? Txfq! F'j x yfiixdbo klt!", "cowfusion-human-1", false);
        addSound("[1/3] Veekhat: Who on earth are you? You're not a client! What do you want?", "cowfusion-veekhat-1", false);
        addSound("[1/2] Veekhat: Wrw blf qfhg gfim rmgl z... Xld? R-rg dlipvw! Gsv znzarmt Evvpszg hgirpvh ztzrm! Sz sz sz!", "cowfusion-veekhat-2", false);
        addSound("[1/6] Veekhat: Ah, I see the Human has returned. Or should I say Humoon.", "cowfusion-veekhat-3", true);
        addSound("[2/6] Veekhat: Have you come to look for a cure? Of course I have one.", "cowfusion-veekhat-4", true);
        addSound("[3/6] Veekhat: I need it for things go wrong. But not anymore. I have perfected it.", "cowfusion-veekhat-5", true);
        addSound("[4/6] Veekhat: It turns out all I needed was a little biomagical information on Humans.", "cowfusion-veekhat-6", true);
        addSound("[5/6] Veekhat: Now I am able to fully merge to become the most powerful Minotaur that ever lived!", "cowfusion-veekhat-7", true);
        addSound("[6/6] Veekhat: You are fortunate, as no one is here to witness it, except you. Come forth, and witness your destruction!", "cowfusion-veekhat-8", true);
        addSound("[1/1] Veekhat: Look at me now! I am so beautiful!", "cowfusion-veekhat-9", true);

        //The Maiden Tower
        addSound("[1/10] Sherk: Ah! What are you doing in my swamp?! Don't you know that ogres live alone for a reason?", "themaidentower-sherk-1", false);
        addSound("[1/9] Sherk: This is the place. She's up at the top... Remember, I won't stop 'til I meet her. Coming here means you're in for the long haul.", "themaidentower-sherk-11", false);
        addSound("[2/9] Maiden: I hear a voice outside! Oh, who might it be, I wonder~", "themaidentower-maiden-1", false);
        addSound("[3/9] Sherk: Here goes... Hello, fair lady of the tower! I am Sherk! I heard your voice on the road- and I must meet you myself! ...that's formal enough, right?", "themaidentower-sherk-12", false);
        addSound("[4/9] Maiden: Ohoho! An admirer? Such a lovely, rugged accent! However...if you wish to meet me, I'd like to see your devotion, yes?", "themaidentower-maiden-2", false);
        addSound("[5/9] Sherk: Woman, you have no idea what you're sayin' when you ask for devotion.", "themaidentower-sherk-13", false);
        addSound("[6/9] Maiden: There is a lovely urn my father had before. He took it with him to the grave, and I do miss my dear father so.", "themaidentower-maiden-3", false);
        addSound("[7/9] Maiden: Retrieve the urn for me, so I can have a memento of him. Brave the crypts of Olux, behind the bank... And prove your devotion, dear suitor~", "themaidentower-maiden-4", false);
        addSound("[8/9] Sherk: ...I'll return! You'll get your urn, no doubt! ... ...and a lovely fine boot up my backside. I don't fancy a massacre for a piece of pottery, Win person.", "themaidentower-sherk-14", false);
        addSound("[9/9] Sherk: I go into Olux, I'll get attacked. I get attacked, I attack back. They'll come after us...wouldn't want to present her with that. You get that urn for me, alright?", "themaidentower-sherk-15", false);
        addSound("[1/4] Sherk: And this is why I asked your help. As nice as blood spreads on toast, I have to reserve my sensibilities for the miss.", "themaidentower-sherk-16", false);
        addSound("[2/4] Sherk: I've got you some compensation already, so don't worry about gettin' snubbed for credit. Thank you kindly for the help.", "themaidentower-sherk-17", false);
        addSound("[3/4] Sherk: Just one last thing. I doubt she'll be expecting an ogre of all people to climb up her tower. I don't want her to get frightened away.", "themaidentower-sherk-18", false);
        addSound("[4/4] Sherk: So, Win person. Care to temper her expectations before I arrive? Head up before me.", "themaidentower-sherk-19", false);
        addSound("[1/9] Maiden: Ohoho...you're one of those Humans I've read about, aren't you? Did you truly intend to be my suitor, dear?~", "themaidentower-maiden-5", false);
        addSound("[2/9] Sherk: Not exactly, milady! I'm the one seeking your affections- Coming up the ladder. I know I may not be a fairytale husband, but-", "themaidentower-sherk-20", false);
        addSound("[3/9] Maiden: Oh...my...goodness! A big, strong, strapping ogre!! Not a fairytale, my foot! And so polite, too... Oh, you'll make a perfect husband, heehee~", "themaidentower-maiden-6", false);
        addSound("[4/9] Sherk: ...ehm. I. Ah.", "themaidentower-sherk-21", false);
        addSound("[5/9] Sherk: Aren't your type supposed to be less... Ehm... Clefted? Grizzled? Pale?", "themaidentower-sherk-22", false);
        addSound("[6/9] Maiden: Oh, come here, Sherky-pie, give me a big kiss! And no need to worry, there's plenty more in store for my big hubby after that~", "themaidentower-maiden-7", false);
        addSound("[7/9] Sherk: Cor, lady! Even for an ogre you're layin' it on a bit thick! Aah, you see, ogres, and people like you, they have layers, right? And your first one is... Eh... ...rotten.", "themaidentower-sherk-23", false);
        addSound("[8/9] Sherk: Now I'm sure you'll find a nice lad somewhere who'll take you for who you are, but until you find someone that has no standards, shove your creepy lips where I can't see 'em!!", "themaidentower-sherk-24", false);
        addSound("[9/9] Maiden: ...I...I g-guess he was rotten to the core, despite his...l-luscious exterior...but, you'll make me feel better, right, you s-studly human? Right?! RIGHT?!!", "themaidentower-maiden-8", false);

        //Tribal Aggression
        addSound("[1/7] Caras: My tribe worries our own business, stranger.", "tribalaggression-caras-1", false);
        addSound("[1/3] Caras: I-Is that, the totem? My, it has been a long time since the owl has been with me. Though I wonder if the Eagle and Owl totem possess the same mystic powers.", "tribalaggression-caras-2", false);
        addSound("[1/5] Favian: Greetings, what brings you to the noble Eagle tribe?", "tribalaggression-favian-1", false);
        addSound("[1/5] Favian: Wonderful! You may now enter the cave to retrieve the blasted Owl totem.", "tribalaggression-favian-2", false);

        //Studying the corrupt
        addSound("[1/6] Viraex: Garoth? Sorry that name doesn't ring a bell. He could have lived here, but...", "studyingthecorrupt-viraex-1", false);
        addSound("[1/10] Garoth's Journal: Day 4: The year is 623 AP. My base of operations has been completed, along with a safety bunker.", "studyingthecorrupt-garothsjournal-1", false);
        addSound("[2/10] Garoth's Journal: Day 18: It's become extremely dangerous to continue my studies. With the Twains gone, the corrupt run wild.", "studyingthecorrupt-garothsjournal-2", false);
        addSound("[3/10] Garoth's Journal: Day 53: My studies have yielded poor results. I know only what we already knew.", "studyingthecorrupt-garothsjournal-3", false);
        addSound("[4/10] Garoth's Journal: That simple creatures become enraged and corrupt, the fallen both old and new will rise near corruption.", "studyingthecorrupt-garothsjournal-4", false);
        addSound("[5/10] Garoth's Journal: Day 91: My studies have hit a wall. I know from my extended stay that humans will not corrupt through proximity or touch.", "studyingthecorrupt-garothsjournal-5", false);
        addSound("[6/10] Garoth's Journal: The corruption seems to affect everything differently. Land, animal, human, mind. It's incomprehensible.", "studyingthecorrupt-garothsjournal-6", false);
        addSound("[7/10] Garoth's Journal: Day 147: I fear there is nothing else to do but study the portal entrance for myself.", "studyingthecorrupt-garothsjournal-7", false);
        addSound("[8/10] Garoth's Journal: If there is a \"cure\" I'm sure it will have something to do with understanding the portal.", "studyingthecorrupt-garothsjournal-8", false);
        addSound("[9/10] Garoth's Journal: I leave this journal in the hopes that it will be found and help those understand this plight better than I. Though I admit, my information is not helpful.", "studyingthecorrupt-garothsjournal-9", false);
        addSound("[10/10] Garoth's Journal: The corruption acts independently and without intelligence. It does not think, it simply does. It's like an idea, a concept. An idea is something that can not be destroyed.", "studyingthecorrupt-garothsjournal-10", false);
        addSound("[1/3] Adventuring Mage: What business does a Ragni soldier have here? These are restricted grounds!", "studyingthecorrupt-adventuringmage-1", false);
        addSound("[2/3] Adventuring Mage: Finding Garoth's old camp? Yes, it's been said to be around here, but it's old, and the entrance has probably fallen apart.", "studyingthecorrupt-adventuringmage-2", false);
        addSound("[3/3] Adventuring Mage: How about you look around here, it should be very near, and when you have found it, I'll help you open it!", "studyingthecorrupt-adventuringmage-3", false);
        addSound("[1/8] Adventuring Mage: *Mumbling* ... Hey what are you doing here! This is off limits!", "studyingthecorrupt-adventuringmage-4", false);
        addSound("[2/8] Adventuring Mage: Right, finding Garoth's old camp...", "studyingthecorrupt-adventuringmage-5", false);
        addSound("[3/8] Adventuring Mage: You say the blocked off entrance is over there?", "studyingthecorrupt-adventuringmage-6", false);
        addSound("[4/8] Adventuring Mage: I doubt you'd be able to move it with any spell you know.", "studyingthecorrupt-adventuringmage-7", false);
        addSound("[5/8] Adventuring Mage: Well, allow me to assist.", "studyingthecorrupt-adventuringmage-8", false);
        addSound("[6/8] Adventuring Mage: The spells around here have to be a bit stronger to deal with the... Well, you know.", "studyingthecorrupt-adventuringmage-9", false);
        addSound("[7/8] Adventuring Mage: Wait for it...", "studyingthecorrupt-adventuringmage-10", false);
        addSound("[8/8] Adventuring Mage: And there you go. I hope you find what you’re looking for. Get out of here as soon as you’re done.", "studyingthecorrupt-adventuringmage-11", false);
        addSound("[1/4] Garoth's Note: This... was a mistake. Beyond the portal holds no answers.", "studyingthecorrupt-garothsnote-1", true);
        addSound("[2/4] Garoth's Note: I can feel my mind slipping through my very mindscape, I fear this may be my very last entry.", "studyingthecorrupt-garothsnote-2", true);
        addSound("[3/4] Garoth's Note: As I studied the portal closely, I found myself staring for hours, longing to enter. Just as I forced myself to leave.. I was pushed.", "studyingthecorrupt-garothsnote-3", true);
        addSound("[1/6] Pottur: Stop, Stop, Stop!", "studyingthecorrupt-pottur-1", false);
        addSound("[2/6] Pottur: This isn't working. Great, now a Ragni soldier is here for a progress report.", "studyingthecorrupt-pottur-2", false);
        addSound("[3/6] Pottur: Soldier, we haven't made any progress getting to Garoth.", "studyingthecorrupt-pottur-3", false);
        addSound("[4/6] Pottur: Truth is, we simply don't know enough about him to know how to deal with this.", "studyingthecorrupt-pottur-4", false);
        addSound("[5/6] Pottur: Maybe if you could help us find out more we could figure this out.", "studyingthecorrupt-pottur-5", false);
        addSound("[6/6] Pottur: Garoth, many years ago lived and studied in Elkurn. It might be a good place to start.", "studyingthecorrupt-pottur-6", false);
        addSound("[1/5] Pottur: Welcome back soldier. Still no luck here I'm afraid. Did you find out anything about Garoth's past?", "studyingthecorrupt-pottur-7", false);
        addSound("[2/5] Pottur: ... I see. We had no idea he was corrupted. We didn't even consider that for a second!", "studyingthecorrupt-pottur-8", false);
        addSound("[3/5] Pottur: Well if that's the case it might just be a simple case of...", "studyingthecorrupt-pottur-9", false);
        addSound("[4/5] Pottur: Success!", "studyingthecorrupt-pottur-10", false);
        addSound("[5/5] Pottur: I can't believe it was that simple. Magic can be like forcing a square into a circle, you just got to change your tact. Thanks for your help soldier.", "studyingthecorrupt-pottur-11", false);

        //A grave Mistake
        addSound("[1/3] Alem: You might want to leave.", "agravemistake-alem-1", false);
        addSound("[2/3] Alem: There is nothing in this graveyard.", "agravemistake-alem-2", false);
        addSound("[3/3] Alem: This place is private.", "agravemistake-alem-3", false);
        addSound("[1/1] Alem: I have warned you...", "agravemistake-alem-4", false);
        addSound("[1/2] ???: You were warned not to enter this forsaken place.", "agravemistake-mael-1", true);
        addSound("[2/2] ???: But since you have made it thusfar, I suppose I shall show you the truth...", "agravemistake-mael-2", true);
        addSound("[1/3] Mael: Do you understand now?", "agravemistake-mael-3", true);
        addSound("[2/3] Mael: Then leave, and forget the horrors you have witnessed here today...", "agravemistake-mael-4", true);
        addSound("[1/1] Alem: I have nothing more to say. Take these.", "agravemistake-alem-5", true);
        addSound("[3/3] Mael: They are safer here.", "agravemistake-mael-5", true);
        addSound("1/12kxxxxxxx2aohmyarmitsba", "agravemistake-skeleton-1", false);

        //Lost in the jungle
        addSound("[1/10] Gracen: Excuse me, traveller... I am dreadfully sorry to ask this of you, but I need your help.", "lostinthejungle-gracen-1", false);
        addSound("[1/7] Gracen: Oh my... Aryn was possessed by a spirit residing within the gemstone?", "lostinthejungle-gracen-2", false);
        addSound("[1/4] Aryn: P-please, I beg of you spirit, let me go free! The tribesmen must be so worried-", "lostinthejungle-aryn-1", false);
        addSound("[2/4] Aryn?: DO NOT RESIST, BOY! YOUR BODY IS MINE. I WAS TRAPPED IN THAT STONE FOR MILLENIA, AND NOW I AM FREE.", "lostinthejungle-aryn-2", true);
        addSound("[3/4] Aryn: Aha, I hear someone coming! Surely a tribesman here to rescue me!", "lostinthejungle-aryn-3", true);
        addSound("[4/4] Aryn?: Oh! If so, the body of this warrior will be far better suited to my needs! Grant me your power, boy! I need to thrash them first to possess them!", "lostinthejungle-aryn-4", true);
        addSound("[1/6] Aryn: Hahah, take that, evil wraith! You'll never match the power of our tribe-", "lostinthejungle-aryn-5", true);

        //The Mercenary
        addSound("[1/10] Amerigo: I have no time for you, stranger! Almuj's army is in panic!", "themercenary-amerigo-1", false);
        addSound("[1/9] Amerigo: Stranger, report!", "themercenary-amerigo-2", false);
        addSound("[1/8] Mylo: These are dark times, weary traveler! Nemract is at its weakest!", "themercenary-mylo-1", false);
        addSound("[1/3] Takan: The commanding officers are aristocrats, all of them! They deserve to suffer for their selfishness!", "themercenary-takan-1", true);
        addSound("[2/3] Takan: Now that we have taken over their barracks, we can take over all of Wynn and make them suffer!", "themercenary-takan-2", true);
        addSound("[3/3] Takan: Today is the dawn of a new era... GAAHHH!", "themercenary-takan-3", true);

        //An iron heart part 1
        addSound("[1/3] ???: ...I am... What^s my...name...? I had one...", "anironheartpart1-guardgolem-1", true);
        addSound("[2/3] ???: Re...remember!! WHAT IS MY NAME? My life?", "anironheartpart1-guardgolem-2", true);
        addSound("[3/3] ???: REMEMBER!! No...no orders! No directives!", "anironheartpart1-guardgolem-3", true);
        addSound("[1/3] Guard Golem: My name!! My life! I won't hurt them! The pain helps me reme...who's there?! LEAVE! PLEASE!", "anironheartpart1-guardgolem-4", true);
        addSound("[2/3] Guard Golem: No directives!! Who did this?! I can't...s-stop giving me ORDERS!!", "anironheartpart1-guardgolem-5", true);
        addSound("[3/3] Guard Golem: NO MORE COLDNESS! NO MORE VOICES THAT AREN'T MINE! MY NAME IS [ERROR]!! [ERROR]!! NOOO!!", "anironheartpart1-guardgolem-6", true);
        addSound("[1/1] Guard Golem: Falling... apart...this isn't who I I I was... My... My... My my my my my name is. Was. Is. Was. Is. IS...! [ERROR]... Ma[ERROine... Madeleine!! I'm Madeleine... I... I-I-IIIIIIIIII-", "anironheartpart1-guardgolem-7", true);
        addSound("[1/6] Duvale: Oh, you there! I wasn't expecting a human soldier to be here, but you've come at a perfect time. I could use some help, see.", "anironheartpart1-duvale-1", false);
        addSound("[1/7] Duvale: Were you able to subdue the golem? You certainly look like you're in one piece.", "anironheartpart1-duvale-2", false);

        //An iron heart part 2
        addSound("[1/17] Daxe: Oh, so a human wishes to wander into the home of the great Daxe?", "anironheartpart2-daxe-1", false);
        addSound("[2/17] Daxe: Unfortunately for you, I'm not fond of visitors. I've left my criminal past behind...mh?", "anironheartpart2-daxe-2", false);
        addSound("[3/17] Daxe: What's that device you have there? So you already know about this, then...?", "anironheartpart2-daxe-3", false);
        addSound("[4/17] Daxe: Hm! If you already know, what do you need the information for?", "anironheartpart2-daxe-4", false);
        addSound("[7/17] Daxe: Wait, what in the world is going on?! What did you do to my golem?!", "anironheartpart2-daxe-5", false);
        addSound("[9/17] Daxe: Wait, your daughter?! M-Madeleine...? I...h-how...what...?", "anironheartpart2-daxe-6", false);
        addSound("[10/17] Daxe: You...you aren't...dead steel? You remember your name...?!", "anironheartpart2-daxe-7", false);
        addSound("[13/17] Daxe: I... I can't believe it. They didn't...they're still...", "anironheartpart2-daxe-8", false);
        addSound("[14/17] Daxe: Dear god, that...that must...I'll talk. I will tell you what I know.", "anironheartpart2-daxe-9", false);
        addSound("[15/17] Daxe: I broke into the golem factory for petty thievery...and found them. They gave me this Golem... Theodore...later to keep me quiet.", "anironheartpart2-daxe-10", false);
        addSound("[16/17] Daxe: They assured me that they were gone, that every last bit of them was erased. They...those monsters lied to me...", "anironheartpart2-daxe-11", false);
        addSound("[17/17] Daxe: Follow this road west and turn right. Look for Urelix. The factory is hidden in a cabin, it won't look like a factory.", "anironheartpart2-daxe-12", false);
        addSound("[1/8] Detective Hart: Hotor, Athomo, Arperi, Etyir, Taiyroth, On'omi, Danin, Nesys, Onton. All missing...", "anironheartpart2-detectivehart-1", false);
        addSound("[1/5] Detective Hart: You've returned, I see. Any news on the situation?", "anironheartpart2-detectivehart-2", false);
        addSound("[1/5] Urelix: And what do you think you are doing, barging into my humble home like this? Do they not teach humans manners in Wynn?", "anironheartpart2-drurelix-1", false);
        addSound("[1/10] Dr. Urelix: Well well, look at you, here at the end of my factory. I wasn't expecting you to survive the production line. I thought you'd be one of ours by now.", "anironheartpart2-drurelix-2", false);
        addSound("[1/3] Dr. Urelix: Ahh, a wise choice. Production will continue then...and you've earned yourself a pocket full of emeralds at the same time.", "anironheartpart2-drurelix-3", false);
        addSound("[5/17] Guard Golem: BZZT. [ERROR]...? Madeleine...?", "anironheartpart2-guardgolem-1", false);
        addSound("[6/17] Guard Golem: MADELEINE! Where's...wait! What's happening, where am I? WHO'S VOICE IS THIS?!", "anironheartpart2-guardgolem-2", false);
        addSound("[8/17] Guard Golem?: They... my daughter... SHUT UP! QUIET! Who's giving me orders- NO ORDERS! Cold! COLD!", "anironheartpart2-guardgolem-3", false);
        addSound("[11/17] Guard Golem?: STOP GIVING ME ORDERS!! I'M...I'M THEODORE! I'M NOT A SERVANT! I...where has my life gone...? Where is...", "anironheartpart2-guardgolem-4", false);
        addSound("[12/17] Guard Golem?: Madeleine... Where are you- RE-AFFIRMING DIRECTIVES. BZZT. ... ... ...", "anironheartpart2-guardgolem-5", false);
        addSound("[1/1] ???: Initiating Chip Insertion Machine - All personell in machine be advised, avoid the spikes.", "anironheartpart2-securityrobot-1", true);
        addSound("[1/1] ???: Intruder Detected - Emergency meltdown initiated. Please evacuate immediately via the emergency exit.", "anironheartpart2-securityrobot-2", true);

        //Realm of lights 1 - The worm holes
        addSound("[1/6] Malo: Arrighty fellers, let's get this wrapped up now! 'ere's plenty more holes where this one came from! ", "rol1wormholes-malo-1", false);
        addSound("[2/6] Malo: ...what in...aw no!", "rol1wormholes-malo-2", false);
        addSound("[3/6] Malo: The scaffolds are crackin'! Everyone, get offa there, now!!", "rol1wormholes-malo-3", false);
        addSound("[4/6] Worker: GwaaAAAAAH!!", "rol1wormholes-worker-1", false);
        addSound("[5/6] Malo: Aw, hell... I knew this project was a bust from the start...there goes Willam and Clint cause a' this!", "rol1wormholes-malo-4", false);
        addSound("[6/6] Malo: I gotta get someone tough enough to get down there...hm? Hey, a human! Git on over here!", "rol1wormholes-malo-5", false);
        addSound("[1/5] Malo: I gotta ask, how much 'a that didja see, Wynn feller? Wasn't pretty, and I'm in charge a' them folks, so I gotta take responsibility fer that.", "rol1wormholes-malo-6", false);
        addSound("[2/5] Malo: Th' mayor of Olux there saw these holes poppin' up, and hired us out to cover 'em up. Thing is, the ground's too unstable to build.", "rol1wormholes-malo-7", false);
        addSound("[3/5] Malo: It's gettin' real worrying round here. They only started openin' up recently, and there've been quakes too. Feels like the place is gonna collapse!", "rol1wormholes-malo-8", false);
        addSound("[4/5] Malo: I think we oughta head down there and figure out what 'n heck's goin' on, and you're the best armed outta any of us, being human 'n all.", "rol1wormholes-malo-9", false);
        addSound("[5/5] Malo: Think y'all could head down 'n see if you can rustle up any leads? There's a path down just to the left of the hole.", "rol1wormholes-malo-10", false);
        addSound("[1/5] Malo: What in tarnation...? The heck did you find down there, soldier?", "rol1wormholes-malo-11", false);
        addSound("[2/5] Malo: Oh, it was just the skin, huh? Dang...was hopin' you'd've seen what it was, cause I got no clue 'bout this. ", "rol1wormholes-malo-12", false);
        addSound("[3/5] Malo: Urgh. Only gal I know who might have an idea 'bout this is that durn elf lady...", "rol1wormholes-malo-13", false);
        addSound("[4/5] Malo: She's got purple hair and's been wanderin' round like she knows everything. Yeah, she's a bright penny but she ain't gotta be so uppity ta all of us 'bout it.", "rol1wormholes-malo-14", false);
        addSound("[5/5] Malo: She put up a camp just east of here. She'll prob'ly react better to you than me, so I'll go to the authorities and see if they have any leads.", "rol1wormholes-malo-15", false);
        addSound("[1/8] Lari: ...Haven’t we met? I have witnessed your presence in Gavel more than once.", "rol1wormholes-lari-1", true);
        addSound("[2/8] Lari: I am Lari. I might have greeted you properly before if I were not so busy, which reminds me. You have a reason to be here, yes?", "rol1wormholes-lari-2", true);
        addSound("[3/8] Lari: Hm...I believe I know what you have found, by your description. That dead skin too, is proof enough, I would say.", "rol1wormholes-lari-3", true);
        addSound("[4/8] Lari: The creature which shed that skin is known as a Grootslang. Though large, they are rather peaceful. Gentle giants, I believe the saying is?", "rol1wormholes-lari-4", true);
        addSound("[5/8] Lari: This horrid Decay...it affects their habitats underground. They are seeking shelter closer to the surface. I have been trying to scrub the Decay without success...", "rol1wormholes-lari-5", true);
        addSound("[6/8] Lari: Wait... More holes appeared just today? There...there were deaths?! W-where, when?!", "rol1wormholes-lari-6", true);
        addSound("[7/8] Lari: No, no...! I refuse to be responsible for any more deaths... Human, tell me your name, please. ", "rol1wormholes-lari-7", true);
        addSound("[8/8] Lari: soldier, then... Let us head to the site. . I need to fix this... Please, help me get to the root of this problem so I may soothe the land's pain.", "rol1wormholes-lari-8", false);
        addSound("[1/3] Lari: So this is the site...", "rol1wormholes-lari-9", false);
        addSound("[2/3] Lari: Here. Allow me to provide at least one person safe passage into the pit.", "rol1wormholes-lari-10", false);
        addSound("[3/3] Lari: Have faith- Jump in. I will carry you down slowly.", "rol1wormholes-lari-11", false);
        addSound("[1/1] Lari: Take the lead. If we can find any further evidence of the Grootslangs around here, it will aid in my understanding.", "rol1wormholes-lari-12", false);
        addSound("[1/3] Lari: Hm? This wall looks like it's been disturbed recently...", "rol1wormholes-lari-13", false);
        addSound("[2/3] Lari: I can hear it...but where is it crawling...?", "rol1wormholes-lari-14", false);
        addSound("[3/3] Lari: That's the mother Wyrm! She must be what is making the holes. We need to follow her!", "rol1wormholes-lari-15", false);
        addSound("[1/1] Lari: Take the lead.", "rol1wormholes-lari-16", false);
        addSound("[1/7] Lari: Ah...ehm, soldier? I've... Um, this is...a bit difficult to say, but...", "rol1wormholes-lari-17", false);
        addSound("[2/7] Lari: I...do not believe we need to fight, or to kill, here? The creatures are native to here, we are invading their space.", "rol1wormholes-lari-18", false);
        addSound("[3/7] Lari: Why should they be attacked for merely existing in their territory? Even if they are animals, I believe that you can solve these problems without killing.", "rol1wormholes-lari-19", false);
        addSound("[4/7] Lari: I...understand you may not have the same powers as myself... And I, ah...am not free of guilt, either. But I think you could stand to take a peaceful stance.", "rol1wormholes-lari-20", false);
        addSound("[5/7] Lari: Even if I am alone on that front...though I understand you are taught differently where you come from. I do not envy you, though I still thank you for your aid thusfar.", "rol1wormholes-lari-21", false);
        addSound("[6/7] Lari: Your methods are...disagreeable, but you still have a sense of right to you, I feel... Well, the tunnel diverges here. We'll need to split apart.", "rol1wormholes-lari-22", false);
        addSound("[7/7] Lari: I'll go to the right, so you ought to continue to the left here. I will meet up with you again soon, hopefully.", "rol1wormholes-lari-23", false);
        addSound("[1/12] Lari: Aah! soldier! Don't...don't make any sudden movements.", "rol1wormholes-lari-24", false);
        addSound("[2/12] Lari: This Grootslang is uncharacteristically aggressive... She's scared, being so close to the surface. Stay behind me, please.", "rol1wormholes-lari-25", false);
        addSound("[3/12] Lari: AH!!", "rol1wormholes-lari-26", false);
        addSound("[4/12] Lari: Do not approach it! The power of the Wyrm is far too much for you!", "rol1wormholes-lari-27", false);
        addSound("[5/12] Lari: W-wait, again? Aah, MOVE!", "rol1wormholes-lari-28", false);
        addSound("[6/12] Lari: soldier!! You're unharmed, yes? She's scared and in pain!", "rol1wormholes-lari-29", false);
        addSound("[7/12] Lari: I have to get it right this time... I'm going to talk her down, stay there!", "rol1wormholes-lari-30", false);
        addSound("[8/12] Lari: You must be calm, I beg you. Retreat to the earth- You harm those above!", "rol1wormholes-lari-31", false);
        addSound("[9/12] Lari: The earth rotting... I take full responsibility, but does the grass above not rot also?", "rol1wormholes-lari-32", false);
        addSound("[10/12] Lari: I wish to soothe your pain, but you must allow me to aid you- You must listen!", "rol1wormholes-lari-33", false);
        addSound("[11/12] Lari: What does this violence solve? Be calm, please!", "rol1wormholes-lari-34", false);
        addSound("[12/12] Lari: soldier, it won't budge... There's something in its mind rejecting me. Distract it- but don't approach! It will kill you if you get close. Just...be loud!", "rol1wormholes-lari-35", false);
        addSound("[1/16] Lari: AIYEE! soldier! Hold on in there!", "rol1wormholes-lari-36", false);
        addSound("[2/16] Lari: Can you hear me?! I'll get her to...to...s-something! Spit you up, I don't know! Just...please, d-don't die!", "rol1wormholes-lari-37", false);
        addSound("[3/16] Lari: W-We have... We have not laid a blow against you! It is not right for you to do the same!", "rol1wormholes-lari-38", false);
        addSound("[4/16] Lari: You would wound and consume everything in your rage? Even knowing the consequences?", "rol1wormholes-lari-39", false);
        addSound("[5/16] Lari: Your hunger cannot be sated with this fruitless destruction! I beg of you, let them out!", "rol1wormholes-lari-40", false);
        addSound("[6/16] Lari: You know th- W-wait...where did you... AAAH!", "rol1wormholes-lari-41", false);
        addSound("[7/16] Lari: ...y-you... soldier... I...y-you're still alright? I...*hic* I...", "rol1wormholes-lari-42", false);
        addSound("[8/16] Lari: I tried...w-why does no one listen to me? Why won't a-anything change for me?", "rol1wormholes-lari-43", false);
        addSound("[9/16] Lari: She...she's been eating dark parasites. They're wracking her insides and h-hurting her head...", "rol1wormholes-lari-44", false);
        addSound("[10/16] Lari: I...i-it's so d-dark... I c-can't breathe... This...th-this can't be the end...", "rol1wormholes-lari-45", false);
        addSound("[11/16] Lari: I...n-no, I can't die in here... I-I'm needed still... You're n-needed still! I... I...", "rol1wormholes-lari-46", false);
        addSound("[12/16] Lari: I n-need... Or... O-Or... C-Concentrate, Lari... You can do this...", "rol1wormholes-lari-47", false);
        addSound("[13/16] Lari: !!", "rol1wormholes-lari-48", false);
        addSound("[14/16] Lari: LET US OUT! LET US OUT OF HERE! DON'T LET US DIE LIKE THIS! I'M BEGGING YOU, PLEASE!!", "rol1wormholes-lari-49", false);
        addSound("[15/16] Lari: ...I...oh no... It's...d-d... I... I j-just... I just w-wanted out... Ohhh...n-no...", "rol1wormholes-lari-50", false);
        addSound("[16/16] Lari: ...w-we're...leaving. I...I c-can't look at this any more...", "rol1wormholes-lari-51", false);
        addSound("[1/5] Lari: ...I... That's...m-my fault...", "rol1wormholes-lari-52", false);
        addSound("[2/5] Lari: I'm...I'm so sorry, soldier. If I'd...I don't... I don't even know what else I could have d-done...", "rol1wormholes-lari-53", false);
        addSound("[3/5] Lari: There...it didn’t feel like there was...anything left to it... But, if I’d just been quicker, then... Would it...would it even have made a difference?", "rol1wormholes-lari-54", false);
        addSound("[4/5] Lari: ...some savior I am... I need to be better, but what else even is there that...ugh, I'm going in circles...", "rol1wormholes-lari-55", false);
        addSound("[5/5] Lari: Let's... Let's just leave...", "rol1wormholes-lari-56", false);
        addSound("[1/6] Lari: ...I...I can't believe this. This is...does destiny just hate me? What have I done to deserve this?!", "rol1wormholes-lari-57", false);
        addSound("[2/6] Lari: The Mother Wyrm laid eggs... Eggs infested with parasites! Every single one is going to be born berserk!!", "rol1wormholes-lari-58", false);
        addSound("[3/6] Lari: You saw how strong the mother was...an infestation like this could cause the entire region of Gavel to collapse in a quake...", "rol1wormholes-lari-59", false);
        addSound("[4/6] Lari: They...they need to be stopped, but...I can't do it. I c-can't kill... I'm sorry, I just can't!", "rol1wormholes-lari-60", false);
        addSound("[5/6] Lari: Bring a group... Even Whelps can be a terror if they're enraged. Just...p-please, take care of this quickly.", "rol1wormholes-lari-61", false);
        addSound("[6/6] Lari: You deserve the best... But that's more than I can give...so here. Take these. I'll move along.", "rol1wormholes-lari-62", false);

        //Realm of lights 2 - Taproot
        addSound("[1/5] Lari: ...oh. Soldier, you're back. Ehm...I have to say, I'm...rather busy at the moment. ", "rol2taproot-lari-1", false);
        addSound("[2/5] Lari: I have...important things to do. This accursed Decay...I have to end it. This all needs to stop. ", "rol2taproot-lari-2", false);
        addSound("[3/5] Lari: I am thankful for your aid with the...the Grootslang, but I do not believe you will be able to help with this- Simply a matter of scale. ", "rol2taproot-lari-3", false);
        addSound("[4/5] Lari: I apologize, but I need to focus on my task here. If, perhaps, you could come back another time? And, I do mean that sincerely...", "rol2taproot-lari-4", false);
        addSound("[5/5] Lari: I can't even recall how many attempts this will make...but all my knowledge has gone into this. This must work. ", "rol2taproot-lari-5", false);
        addSound("[1/10] Lari: ...I suppose I can't fault you for being curious. You may have seen a large, dark-looking patch of ground on your way here- I am trying to tend to it once more. ", "rol2taproot-lari-6", true);
        addSound("[2/10] Lari: It is...the most obvious stain of the decay, and very intense...if I can scrub this out, the rest should follow. ", "rol2taproot-lari-7", true);
        addSound("[3/10] Lari: This concoction of mine should...do the trick! ", "rol2taproot-lari-8", true);
        addSound("[4/10] Lari: It...it's working!! I can feel it, the land is reacting positively! ", "rol2taproot-lari-9", true);
        addSound("[5/10] Lari: I...I... It was...it was working, a-and... Agh, why can't I get this RIGHT?! ", "rol2taproot-lari-10", true);
        addSound("[6/10] Lari: How many decades have I been doing this? How much time have I wasted, just...failing like this?!", "rol2taproot-lari-11", true);
        addSound("[7/10] Lari: Look, Soldier, I...I don't know what you expect to do against this. The Wyrm was one thing, but...this is beyond your comprehension. ", "rol2taproot-lari-12", true);
        addSound("[8/10] Lari: I think you sh... Should...w-what is... Now, of all times? As I fail?!", "rol2taproot-lari-13", true);
        addSound("[9/10] Lari: The road, look! He's contacting me! A-are you finally giving me the answers I've asked for? ", "rol2taproot-lari-14", true);
        addSound("[10/10] Lari: I have to follow the trail! I...I have to know what he's telling me!", "rol2taproot-lari-54", true);
        addSound("[1/12] Lari: ...I followed you seeking answers...and here, I am presented with more questions. Why did he bring us here? ", "rol2taproot-lari-15", true);
        addSound("[2/12] Lari: This gate has appeared, immovable and impassable, since the moment that...that everything started. ", "rol2taproot-lari-16", true);
        addSound("[3/12] Lari: I don't...is he trying to remind me of what's at stake? Did he want to show y- No, that's...he wouldn't know you...but then, what IS this about?! ", "rol2taproot-lari-17", true);
        addSound("[4/12] Lari: Rrgh... ANSWER ME! PLEASE! I've begged you for so long to answer! ", "rol2taproot-lari-18", true);
        addSound("[5/12] Lari: You gave me my task and your light but never told me what to DO! I've tried everything I can think of!! ", "rol2taproot-lari-19", true);
        addSound("[6/12] Lari: Tell me! Please, just t-tell me already!! I don't know how much longer I can stand this!! ", "rol2taproot-lari-20", true);
        addSound("[7/12] Lari: I...a-another one? Where are you leading me now? ", "rol2taproot-lari-21", true);
        addSound("[8/12] Lari: It...the gateway...? Are you...I can't understand! Why this, again? ", "rol2taproot-lari-22", true);
        addSound("[9/12] Lari: You showed me this before... At first, when you first contacted me. I made the choice... ", "rol2taproot-lari-23", true);
        addSound("[10/12] Lari: Must I make it again? Is that it, do I need to prove myself to you once more? ", "rol2taproot-lari-24", true);
        addSound("[11/12] Lari: I...what? You...you present me with this, but refuse to let me do what you want? ", "rol2taproot-lari-25", true);
        addSound("[12/12] Lari: Soldier...please, can you investigate the doorway? There must be something wrong that I don't understand.", "rol2taproot-lari-26", true);
        addSound("[1/14] ???: My pulse... Human. I must inquire to you. I present you a test. Guide your attention to those you see. ", "rol2taproot-orphion-1", true);
        addSound("[2/14] ???: I must see the rhythm your heart beats. Listen well. ", "rol2taproot-orphion-2", true);
        addSound("[3/14] Mayor: This has gone far enough. I can't do this anymore! I can't give you any more and I refuse to apologize for it! ", "rol2taproot-mayor-1", true);
        addSound("[4/14] Dr Urelix: You know our deal. Remember what good you are doing by following the plans I've laid out for you. ", "rol2taproot-drurelix-1", true);
        addSound("[5/14] Dr Urelix: Are you going to let your emotions cloud your judgment? You're a smarter man than that, Mr. Mayor. ", "rol2taproot-drurelix-2", true);
        addSound("[6/14] Mayor: You can't possibly believe that throwing away the lives of innocent people is right! I can't go on with this on my conscience! ", "rol2taproot-mayor-2", true);
        addSound("[7/14] Mayor: Detective Hart WILL be hearing from me, and he'll be hearing everything. We can't keep sacrificing our own like this! ", "rol2taproot-mayor-3", true);
        addSound("[8/14] Dr Urelix: Our golems have single-handedly turned the tide of the Humans' corruption war. You would save ten here and sentence a thousand to die overseas?", "rol2taproot-drurelix-3", true);
        addSound("[9/14] Mayor: You're a sick, twisted man, Urelix. I will not abide by this a moment longer. ", "rol2taproot-mayor-4", true);
        addSound("[10/14] ???: My pulse, you must decide. Three paths diverge upon your road.", "rol2taproot-orphion-3", true);
        addSound("[11/14] ???: You may leave. The rays of fate will see to destiny unfolding.", "rol2taproot-orphion-4", true);
        addSound("[12/14] ???: You may end this ''Mayor's'' life. Blood upon your hands in many ways.", "rol2taproot-orphion-5", true);
        addSound("[13/14] ???: You may end the life of Urelix. Heartbeats ceased by a bleeding heart.", "rol2taproot-orphion-6", true);
        addSound("[14/14] ???: Which way does your heart beat? How will you cast your light? Can you see?", "rol2taproot-orphion-7", true);
        addSound("[1/1] ???: My pulse... The blood of a bleeding heart has poisoned me. Even if you believe the sun should shine elsewhere, you must direct it yourself.", "rol2taproot-orphion-8", true);
        addSound("[1/5] ???: You must realize what is needed. You must see light through my lens.", "rol2taproot-orphion-9", true);
        addSound("[2/5] ???: To leave... As destiny unfolds, the sun shall set.", "rol2taproot-orphion-10", true);
        addSound("[3/5] ???: To end this ''Mayor's'' life... To snuff out one whose heart aches is painful.", "rol2taproot-orphion-11", true);
        addSound("[4/5] ???: To end the life of Urelix... The shadows of war shall engulf all.", "rol2taproot-orphion-12", true);
        addSound("[5/5] ???: My pulse, there is only one choice. You must do what is required, I beg of you.", "rol2taproot-orphion-13", true);
        addSound("[1/1] Dr. Urelix: Ah, you recognize necessity. That couldn't have been pleasant...but what's needed isn't pleasant, in the end.", "rol2taproot-drurelix-4", true);
        addSound("[1/1] ???: My pulse, you are worthy! ", "rol2taproot-orphion-14", true);
        addSound("[1/8] Lari: ...I didn't think you would be able to help, but I had to ask. So...was this for nothing?", "rol2taproot-lari-27", true);
        addSound("[2/8] Lari: Wait...y-you...had a choice? He gave you the choice too?! A-Already?! ", "rol2taproot-lari-28", true);
        addSound("[3/8] Lari: Wh- I don't understand! You...you just arrived to Gavel! You haven't seen its decline! Felt its pain! ", "rol2taproot-lari-29", true);
        addSound("[4/8] Lari: What did you do?! The gate is opening? YOU did this?! I know your heart is in the right place, but you're a stranger to this land! ", "rol2taproot-lari-30", true);
        addSound("[5/8] Lari: I...I...rrrgh... AAAAGH!! This isn't fair!! I've worked for decades and the light just...throws me to the wayside?! ", "rol2taproot-lari-31", true);
        addSound("[6/8] Lari: PLEASE! Just let me in!", "rol2taproot-lari-32", true);
        addSound("[7/9] ???: My pulse, you must leave her be. Her heart's emotions have overpowered her mind's judgement. She must learn to separate them as you have. ", "rol2taproot-orphion-15", true);
        addSound("[7/8] Lari: Please... You can't DO this to me!! ", "rol2taproot-lari-33", true);
        addSound("[8/8] Lari: Why do you want this human to enter the Taproot?! What's special about them, what's their connection? What have they done that I haven't?! Tell me, please! Let me be useful!! ", "rol2taproot-lari-34", true);
        addSound("[1/6] ???: Your heartbeat beats in time with mine. Your mind is unclouded, despite the fury you have been steeped in. My pulse, you are needed. ", "rol2taproot-orphion-16", true);
        addSound("[2/6] ???: Your eyes cannot see the scope of the realms. You must allow me to shed light upon them- You must see the connections. ", "rol2taproot-orphion-17", true);
        addSound("[3/6] ???: Light... Dark... We oppose. I am light. It is dark. Our places reflect this land...and it is plain- The sun is setting. Light is fading ", "rol2taproot-orphion-18", true);
        addSound("[4/6] ???: The land you visit warps with the slow siphoning of my light, my blood. It becomes outlandish and strange-Oppressive and alien. ", "rol2taproot-orphion-19", true);
        addSound("[5/6] ???: As dusk approaches- As darkness spreads- My heart slows and stops. My pulse, you are needed. You shall be as one with me, and you shall see ", "rol2taproot-orphion-20", true);
        addSound("[6/6] ???: The Realm of Light needs life anew- A new pulse. It needs you.", "rol2taproot-orphion-21", true);
        addSound("[1/3] ???: Our light is synchronous! You may see what I must have you see... ", "rol2taproot-orphion-22", true);
        addSound("[2/3] ???: Follow the road. You must witness the reason behind my trial.", "rol2taproot-orphion-23", true);
        addSound("[3/3] ???: See my memories. The memories of the realm. See...and be enlightened. ", "rol2taproot-orphion-24", true);
        addSound("[1/1] ???: Witness it... The Equinox.  Learn why you are needed, my pulse.", "rol2taproot-orphion-25", true);
        addSound("[1/2] ???: Witness it. The vile emergence... ", "rol2taproot-orphion-26", true);
        addSound("[2/2] ???: Witness the beginning of decay. ", "rol2taproot-orphion-27", true);
        addSound("[1/17] Lari: My light...I won't let you down!", "rol2taproot-lari-35", true);
        addSound("[2/17] Lari: You! You don't belong here! ", "rol2taproot-lari-36", true);
        addSound("[3/17] Lari: Wah! Back away, I don't want to hurt you! ", "rol2taproot-lari-37", true);
        addSound("[4/17] Lari: I can do this... Orphion, lend me your voice! ", "rol2taproot-lari-38", true);
        addSound("[5/17] Lari: Return to your realm in peace! ", "rol2taproot-lari-39", true);
        addSound("[6/17] Lari: Your power is blinded by light. You cannot win! ", "rol2taproot-lari-40", true);
        addSound("[7/17] Lari: Peace, strange creature! You must be peaceful! ", "rol2taproot-lari-41", true);
        addSound("[8/17] Lari: Why isn't this working? Even Orphion's voice... ", "rol2taproot-lari-42", true);
        addSound("[9/17] Lari: It's scarring the land... I need to end this, quickly... ", "rol2taproot-lari-43", true);
        addSound("[10/17] Lari: His light... This power... I can do this! ", "rol2taproot-lari-44", true);
        addSound("[11/17] Lari: The embodiment of light supports me! ", "rol2taproot-lari-45", true);
        addSound("[12/17] Lari: You cannot fight the focus of pure light! ", "rol2taproot-lari-46", true);
        addSound("[13/17] Lari: Be done! I bind you with Orphion's light! ", "rol2taproot-lari-47", true);
        addSound("[14/17] Lari: Wait...it...it's n-not holding?! ", "rol2taproot-lari-48", true);
        addSound("[15/17] ???: Lari... A conflicted heart failed to channel my light. She refused to battle. ", "rol2taproot-orphion-28", true);
        addSound("[16/17] ???: The vile intruder was weak... But every opportunity to strike, exchanged for words to the deaf.", "rol2taproot-orphion-29", true);
        addSound("[17/17] ???: My pulse... it pains me. Her light simply fails to shine where it is needed, and so we are here. ", "rol2taproot-orphion-30", true);
        addSound("[1/5] Lari: I... I...hope you'll forgive me, <Soldier>. My anger wasn't with you. I...j-just... ", "rol2taproot-lari-49", true);
        addSound("[2/5] Lari: I work for untold years, trying to save this province, to save a Realm... All to so little avail. To the sound of silence from the one I'm trying to help. ", "rol2taproot-lari-50", true);
        addSound("[3/5] Lari: And then, you appear, and... I still haven't ever been in there. I'd...I'd ask what it was like, but it's...probably private, right?", "rol2taproot-lari-51", true);
        addSound("[4/5] Lari: At...at least I have an ally now, r-right? But...I'm afraid that... N-no, it's silly, you don't need to hear me fretting.", "rol2taproot-lari-52", true);
        addSound("[5/5] Lari: I... I promise I can still be useful. I'll... I'll find a way. I promise you. I'll...get out of your way, for now.", "rol2taproot-lari-53", true);

        //Realm of lights 3 - A headless history
        addSound("[1/6] Hans: Oi, you there! With the small nose and whatnot! I got somethin' to ask you. Got a minute? ", "rol3aheadlesshistory-hans-1", true);
        addSound("[2/6] Hans: Good, you're good people. It's about our neighbor, Referick. He's a bit of a conspiracy nut. You know the type, villager illuminati, Wybels are evil, all that?", "rol3aheadlesshistory-hans-2", true);
        addSound("[3/6] Hans: He's acting proper strange though, the past few days. Thinkin' a lord of time's draggin' creatures from other dimensions doesn't make ya stop eatin' or drinkin'.", "rol3aheadlesshistory-hans-3", true);
        addSound("[4/6] Hans: He'll talk your ear off about it if you give him half a chance, but he ain't sayin' a word to me, even when I tried askin' about his theories.", "rol3aheadlesshistory-hans-4", true);
        addSound("[5/6] Hans: Somethin's definitely wrong about him, that much's for sure. Could ya take a look for me, see whatcha can figure out and all?", "rol3aheadlesshistory-hans-5", true);
        addSound("[6/6] Hans: He lives in the house next to the pile of rocks. He's got screws loose but I don't want 'im dyin' on me, yeah?", "rol3aheadlesshistory-hans-6", true);
        addSound("[2/4] Hans: Oi! Any luck with ol' Rick there? ", "rol3aheadlesshistory-hans-7", true);
        addSound("[3/4] Hans: ...hm. Dang it...y'know, maybe there's somethin' here that'll tell us what in heck happened.", "rol3aheadlesshistory-hans-8", true);
        addSound("[4/4] Hans: Let's look around the house for anythin' he mighta been workin' on. Maybe he finally fiddled with somethin' he shouldn'ta fiddled with? ", "rol3aheadlesshistory-hans-9", true);
        addSound("[1/6] Hans: Whassat you're lookin' at there? The castle? Don't see why he'd be pointin' this ol' telescope that way.", "rol3aheadlesshistory-hans-10", true);
        addSound("[2/6] Hans: You're human, so you wouldn't know, but somethin' like two hundred years ago, an elf of all people lived in that castle. Called 'imself Dullahan, wore a helmet a lot, I think.", "rol3aheadlesshistory-hans-11", true);
        addSound("[3/6] Hans: People thought he was the reason for the Decay- can't imagine why, personally, and obviously they weren't right.", "rol3aheadlesshistory-hans-12", true);
        addSound("[4/6] Hans: ...hm. Y'know, there's some ghost stories about Dullahan still livin' in that castle... Come to think of it, I think Referick mentioned a theory about it.", "rol3aheadlesshistory-hans-13", true);
        addSound("[5/6] Hans: Barely remember what it was about cause of how many crackpots he's stuck his head into, but it's somethin' about a ghost. 's the only lead I can think of, anyways...", "rol3aheadlesshistory-hans-14", true);
        addSound("[6/6] Hans: The castle door's locked, but you can access the basement. Think you could try lookin' over there? That's all I got.", "rol3aheadlesshistory-hans-15", true);
        addSound("[1/2] ???: ...an unfamiliar soul? Strange. I have not brought you here. I do not know of you.", "rol3aheadlesshistory-dullahan-1", true);
        addSound("[2/2] ???: What an odd feeling... I haven't felt confusion in a long time. How refreshing...still, I shall ask you to leave.", "rol3aheadlesshistory-dullahan-2", true);
        addSound("[1/3] ???: Excuse you. I asked you to leave, strange person. You would ignore my request? ", "rol3aheadlesshistory-dullahan-3", true);
        addSound("[2/3] ???: Such an action is rather insolent, not to mention...interesting. Still, interfering with my business is inexcusable. ", "rol3aheadlesshistory-dullahan-4", true);
        addSound("[3/3] ???: Demons of the Prison Guard, I compel you to execute this interloper. Trap their soul for my perusal later. ", "rol3aheadlesshistory-dullahan-5", true);
        addSound("[1/16] Hans: OI! Referick!! Wait for me! Ain'tcha need some food and drink first?!", "rol3aheadlesshistory-hans-16", true);
        addSound("[2/16] Hans: The heck did you do?! Referick just bolted out the door like he forgot his wallet or somethin'!", "rol3aheadlesshistory-hans-17", true);
        addSound("[4/16] Hans: C'mon mate, you got up and moved for the first time in days and now you're clammin' up again? Speak to me!", "rol3aheadlesshistory-hans-18", true);
        addSound("[5/16] Referick: WROOOOOAAAAAARRR!!! I'M ALIIIIIVE!! ", "rol3aheadlesshistory-referick-1", true);
        addSound("[6/16] Hans: YIPE! Never heard 'im scream like that before! Never heard anyone scream like that before! I'd like to never hear anyone scream like that again!", "rol3aheadlesshistory-hans-19", true);
        addSound("[7/16] Referick: Oh, shut UP Hans! I nearly had my soul devoured, don't DARE give me any chaff when I was RIGHT all along!!", "rol3aheadlesshistory-referick-2", true);
        addSound("[8/16] Hans: Agh, alright, alright! Quiet, my ears are ringin'! I'll listen, I'll listen, just quiet!", "rol3aheadlesshistory-hans-20", true);
        addSound("[9/16] Referick: I knew the disappearances in Gelibord weren't just because of the risen bodies. People were being left catatonic!! That doesn't happen randomly!", "rol3aheadlesshistory-referick-3", true);
        addSound("[10/16] Referick: It's Dullahan, I swear on my life! I figured him out and he came after me! He's devouring peoples' souls and siphoning their energy for himself!", "rol3aheadlesshistory-referick-4", true);
        addSound("[11/16] Referick: He's acting from within the castle somehow- Ghostly hands and servants, I'll bet! And that means there's a chance to stop him, if he has to act indirectly!", "rol3aheadlesshistory-referick-5", true);
        addSound("[12/16] Referick: We need to round up the townsfolk, the guards, the military, EVERYONE! We have to storm the castle and stop him before he's able to escape!", "rol3aheadlesshistory-referick-6", true);
        addSound("[13/16] Hans: ...criminy, you really bit the big one, didn'tcha...look, if he's tryin' to eat people's souls, wouldn't it be smarter to NOT have a huge mob?", "rol3aheadlesshistory-hans-21", true);
        addSound("[14/16] Referick: Wait, wh-what do you mean?! Shouldn't everyone be on high alert? Won't we have a better chance of swarming him all at once?", "rol3aheadlesshistory-referick-7", true);
        addSound("[15/16] Hans: He'd have a whole mob of people ready to take from in that case. Sending in one, heavily-armed person, or a small group'd be better.", "rol3aheadlesshistory-hans-22", true);
        addSound("[16/16] Hans: And, well...a heavily-armed human just rescued ya, Ricky. I think we leave this to 'em. How about it, human? Head up to the castle and look into this for us?", "rol3aheadlesshistory-hans-23", true);
        addSound("[1/3] ???: The interesting soul arrives once more. Why is it you valued the life of that villager? You risked yourself for his soul.", "rol3aheadlesshistory-dullahan-6", true);
        addSound("[2/3] ???: It will not make a difference one way or the other, and so I am doubly confused. The feeling has already grown tiresome. ", "rol3aheadlesshistory-dullahan-7", true);
        addSound("[3/3] ???: What purpose have you in this place? Turn over your soul so I may examine it and find out. I am genuinely curious to know... ", "rol3aheadlesshistory-dullahan-8", true);
        addSound("[1/13] Lari: Be done! I bind you with Orphion's light!", "rol3aheadlesshistory-lari-1", true);
        addSound("[2/13] Lari: Wait...it...it's n-not holding?! ", "rol3aheadlesshistory-lari-2", true);
        addSound("[3/13] Dullahan: LARIII! Where are you?! I can help!! ", "rol3aheadlesshistory-dullahan-9", true);
        addSound("[4/13] Dullahan: Ugh, I saw her with that...THING, but... ", "rol3aheadlesshistory-dullahan-10", true);
        addSound("[5/13] Dullahan: My visions didn't show an exact enough spot... LARIII? ", "rol3aheadlesshistory-dullahan-11", true);
        addSound("[6/13] Dullahan: I...what is... What left the land scarred like this?! ", "rol3aheadlesshistory-dullahan-12", true);
        addSound("[7/13] Dullahan: Was...was this their clash...? L-LARI! LARIIII! WHERE ARE YOU?!", "rol3aheadlesshistory-dullahan-13", true);
        addSound("[8/13] Lari: ...hh...D-Du...lla...?", "rol3aheadlesshistory-lari-3", true);
        addSound("[9/13] Dullahan: You are NOT dying on me! I won't allow it! You will LIVE, you hear me?!", "rol3aheadlesshistory-dullahan-14", true);
        addSound("[10/13] Dullahan: Sit as still as you can, I have healing spells prepared. ", "rol3aheadlesshistory-dullahan-15", true);
        addSound("[11/13] Dullahan: I ask the land for its life to save another. Grant me this!", "rol3aheadlesshistory-dullahan-16", true);
        addSound("[12/13] Lari: Dullahan!", "rol3aheadlesshistory-lari-4", true);
        addSound("[13/13] Dullahan: Oh, what am I going to do with you...", "rol3aheadlesshistory-dullahan-17", true);
        addSound("[1/2] Dullahan: Once again you ignore my demands...? And not only that, you invade the memories I have locked away? ", "rol3aheadlesshistory-dullahan-18", true);
        addSound("[2/2] Dullahan: I must ask again, what is your goal? Why have you come here, and why do you seek my most private thoughts, that not even I wish to think?", "rol3aheadlesshistory-dullahan-19", true);
        addSound("[1/12] Lari: Look, Dulla, I really do appreciate your help, but you know I can't do what you're asking of me! There's another way, I just need to find it! ", "rol3aheadlesshistory-lari-5", true);
        addSound("[2/12] Dullahan: Lari, you remember that I have my visions, right? I've seen what will happen if you don't step up- and I can help stop it!", "rol3aheadlesshistory-dullahan-20", true);
        addSound("[3/12] Dullahan: You've been trying to find ''another way'' for two centuries. Don't you think it's high-time we took my route, before things get worse?", "rol3aheadlesshistory-dullahan-21", true);
        addSound("[4/12] Lari: NO! I am not going to kill it! YOU are not going to kill it! I'll find a way! I will, I have these powers for a reason!", "rol3aheadlesshistory-lari-6", true);
        addSound("[5/12] Dullahan: You talk a big game, and you show one too, but the creature wasn't fazed. I'm not fazed. Drop the intimidating act, please. ", "rol3aheadlesshistory-dullahan-22", true);
        addSound("[6/12] Lari: I...there...there has to be a way. I...I-I'm terrified that I've...already ruined everything, just by following my heart, like I was told... ", "rol3aheadlesshistory-lari-7", true);
        addSound("[7/12] Dullahan: I know you're frustrated, but I am too. I left a lot behind to try and help you. I hate to say these things, I wish it could be your way.", "rol3aheadlesshistory-dullahan-23", true);
        addSound("[8/12] Dullahan: I'm terrified too, that if we don't take more drastic action, things will get worse. Can't you see that, too?", "rol3aheadlesshistory-dullahan-24", true);
        addSound("[9/12] Lari: I...I just... I don't know what to do! ", "rol3aheadlesshistory-lari-8", true);
        addSound("[10/12] Lari: He showed me all these things and gave me all these choices and asked how my heart beat!", "rol3aheadlesshistory-lari-9", true);
        addSound("[11/12] Lari: He gave me these powers but they don't work or I don't know HOW they work!", "rol3aheadlesshistory-lari-10", true);
        addSound("[12/12] Lari: I want to do the right thing but I don't know what it is and I don't know how to ask what it is and...a-and... ", "rol3aheadlesshistory-lari-11", true);
        addSound("[1/2] Dullahan: You have become infuriating instead of interesting. Why do you violate my memories? Why do you force me to relive them? ", "rol3aheadlesshistory-dullahan-25", true);
        addSound("[2/2] Dullahan: Spirit of the Keeper of Keys, rid me of this thing that befouls my place. It incites a rage akin to HER. ", "rol3aheadlesshistory-dullahan-26", true);
        addSound("[1/2] Dullahan: You...are a fool. More than I was. You knowingly walk to your death.", "rol3aheadlesshistory-dullahan-27", true);
        addSound("[2/2] Dullahan: It is convenient for me, certainly, but I admit that it is equal parts enraging and bewildering.", "rol3aheadlesshistory-dullahan-28", true);
        addSound("[1/10] Dullahan: I told you, Lari! I saw it in my visions, this is our last chance! Hurry! ", "rol3aheadlesshistory-dullahan-29", true);
        addSound("[2/10] Dullahan: Look at this spot, Lari. It has to be here. The land is being scarred, just like after your fight. The light is being drained!", "rol3aheadlesshistory-dullahan-30", true);
        addSound("[3/10] Lari: This must be where the parasite burrowed down... You're... You're right. We have to...to do something.", "rol3aheadlesshistory-lari-12", true);
        addSound("[4/10] Lari: I... I'm not sure if I'm ready, but you're right. We need to do this... ", "rol3aheadlesshistory-lari-13", true);
        addSound("[5/10] Dullahan: ...Remember what I said. You HAVE to take action. There's only two outcomes here. ", "rol3aheadlesshistory-dullahan-31", true);
        addSound("[6/10] Lari: Right... ", "rol3aheadlesshistory-lari-14", true);
        addSound("[7/10] Lari: We'll need to dig... Do you think you could help with that, Dulla?", "rol3aheadlesshistory-lari-15", true);
        addSound("[8/10] Dullahan: Of course, Lari. We'll need everything we can get if we're going to kill that horrendous thing.", "rol3aheadlesshistory-dullahan-32", true);
        addSound("[9/10] Lari: ...let's get this over with.", "rol3aheadlesshistory-lari-16", true);
        addSound("[10/10] Dullahan: I see it! Lari, it's time!", "rol3aheadlesshistory-dullahan-33", true);
        addSound("[1/1] Dullahan: ...I will show you an exit, interloper. It would be...wise of you to take it.", "rol3aheadlesshistory-dullahan-34", true);
        addSound("[1/5] Dullahan: You are a damned fool, interloper. Dredging my memories as you have... I have never felt such rage, not in a long time. ", "rol3aheadlesshistory-dullahan-35", true);
        addSound("[2/5] Dullahan: As such, it shall end now. The exit I spoke of will not free you from the castle, as you can see. ", "rol3aheadlesshistory-dullahan-36", true);
        addSound("[3/5] Dullahan: Instead, it will free you from your very life. I have had enough of your meddling. ", "rol3aheadlesshistory-dullahan-37", true);
        addSound("[4/5] Dullahan: Warden of Wisdom! I compel you- Rid me of this miscreant! Erase every trace of it from this existence... ", "rol3aheadlesshistory-dullahan-38", true);
        addSound("[5/5] Dullahan: ...save for a corpse, for me to toss at Lari's feet.", "rol3aheadlesshistory-dullahan-39", true);
        addSound("[1/24] Lari: GAH! It was never able to do this before...! W-What happened to it?! ", "rol3aheadlesshistory-lari-17", true);
        addSound("[2/24] Dullahan: AWAY! You keep away from us!! ", "rol3aheadlesshistory-dullahan-40", true);
        addSound("[3/24] Dullahan: Lari, you need to get up, now! We can't waste this chance! ", "rol3aheadlesshistory-dullahan-41", true);
        addSound("[4/24] Lari: It...it threw me aside like nothing! How is it overpowering Orphion's light...?", "rol3aheadlesshistory-lari-18", true);
        addSound("[5/24] Dullahan: It's overpowering you because you're not a ttacking! Remember what I said? You have to-", "rol3aheadlesshistory-dullahan-42", true);
        addSound("[6/24] Dullahan: ...wait, where did it...did we lose it already?!", "rol3aheadlesshistory-dullahan-43", true);
        addSound("[7/24] Lari: It's under you! MOVE!!", "rol3aheadlesshistory-lari-19", true);
        addSound("[8/24] Dullahan: It's shrugging my attacks off, Lari! Come on, do SOMETHING! ", "rol3aheadlesshistory-dullahan-44", true);
        addSound("9/24lari!!", "rol3aheadlesshistory-lari-20", true);
        addSound("[10/24] Dullahan: I...No! It's happening just like I saw! She's... ", "rol3aheadlesshistory-dullahan-45", true);
        addSound("[11/24] Dullahan: I need to get to her, push her to the killing blow...", "rol3aheadlesshistory-dullahan-46", true);
        addSound("[12/24] Lari: Be still, creature! I compel you with Orphion's light!", "rol3aheadlesshistory-lari-21", true);
        addSound("[13/24] Dullahan: LARIIII! THIS IS THE LAST CHANCE YOU HAVE! DON'T TRAP IT, KILL IT! ", "rol3aheadlesshistory-dullahan-47", true);
        addSound("[14/24] Lari: I-I'm...Dulla, I'm- I can't! I know I can keep it held this time!! ", "rol3aheadlesshistory-lari-22", true);
        addSound("[15/24] Lari: I...wha...N-no!", "rol3aheadlesshistory-lari-23", true);
        addSound("[16/24] Lari: I...I-I...th-the light is...I c-can't hold onto it...i-is it...it's t-taking...g-guh...", "rol3aheadlesshistory-lari-24", true);
        addSound("[17/24] Dullahan: Lari! You can't reason with it! You can't hold it! You have to put Gavel first! US first!! ", "rol3aheadlesshistory-dullahan-48", true);
        addSound("[18/24] Lari: I won't let what you predicted happen! I know I can change fate!!", "rol3aheadlesshistory-lari-25", true);
        addSound("[19/24] Lari: Please work, please work! D-Don't break, I can't- ", "rol3aheadlesshistory-lari-26", true);
        addSound("[20/24] Dullahan: Lari, no! If it bites you we're doomed! GET UP! ", "rol3aheadlesshistory-dullahan-83", true);
        addSound("[21/24] Dullahan: AAAH, GET AWAY FROM HER! ", "rol3aheadlesshistory-dullahan-49", true);
        addSound("[22/24] Lari: D-Dulla! ", "rol3aheadlesshistory-lari-27", true);
        addSound("[23/24] Dullahan: L-Lari...we...e-everything happened like I s-said...you...you didn't...l-listen...", "rol3aheadlesshistory-dullahan-50", true);
        addSound("[24/24] Lari: No! No it didn't happen like you said! I-I can fix this! Y-You'll be alright! You'll be alright, you hear me?! I'll make sure of it!", "rol3aheadlesshistory-lari-28", true);
        addSound("[1/1] Dullahan: ...so I am cursed to bear witness to my most painful memories due to your idiocy. It is almost a fitting penance... Almost, but not quite.", "rol3aheadlesshistory-dullahan-51", true);
        addSound("[1/2] Dullahan: My rage... My memories... Your presence stirs them in kind. You must disappear, I no longer care to see your soul.", "rol3aheadlesshistory-dullahan-52", true);
        addSound("[2/2] Dullahan: Sit still and allow yourself to be engulfed. It will save me further pain.", "rol3aheadlesshistory-dullahan-53", true);
        addSound("[1/25] Dullahan: Lari...? Where are you? I...I need you... Why are you gone? Why do you ignore me?", "rol3aheadlesshistory-dullahan-54", true);
        addSound("[2/25] Dullahan: My magic is fading, I can't see my visions... They're clearer around you... Please, come back...", "rol3aheadlesshistory-dullahan-55", true);
        addSound("[3/25] Gelibord Citizen: That elf girl's off in the forest. Don't know what she's doing but this is our shot!", "rol3aheadlesshistory-gelibordcitizen-1", true);
        addSound("[4/25] Gelibord Citizen: We can finally execute the person causing the decay in the forest! Gelibord will be saved!", "rol3aheadlesshistory-gelibordcitizen-2", true);
        addSound("[5/25] Gelibord Citizen: You prepared the sleeping spell, right? Who knows if we'll get a chance like this again.", "rol3aheadlesshistory-gelibordcitizen-3", true);
        addSound("[6/25] Gelibord Citizen: Shhh... Quiet. He's in there.", "rol3aheadlesshistory-gelibordcitizen-4", true);
        addSound("[7/25] Gelibord Citizen: Okay, go! Knock him out.", "rol3aheadlesshistory-gelibordcitizen-5", true);
        addSound("[8/25] Dullahan: L-Lari? Is...is that you? Did you finally come back...? ", "rol3aheadlesshistory-dullahan-56", true);
        addSound("[9/25] Gelibord Citizen: Nah, not quite. Say, why don't you go to sleep 'til she comes back? Dream sweet dreams... ", "rol3aheadlesshistory-gelibordcitizen-6", true);
        addSound("[10/25] Gelibord Citizen: ...they'll be the last ones you ever have, grimy demon. To the guillotine with you... Everyone! I got him!", "rol3aheadlesshistory-gelibordcitizen-7", true);
        addSound("[11/25] Gelibord Citizen: All of you, welcome! Today will be the day we save our fair land. Today's the day of light!", "rol3aheadlesshistory-gelibordcitizen-8", true);
        addSound("[12/25] Gelibord Citizen: Dullahan has been captured! We have the cursed progenitor of the decay in our grip- to be executed, once and for all!", "rol3aheadlesshistory-gelibordcitizen-9", true);
        addSound("[13/25] Gelibord Citizen: Haha, yeah! Shoulda thought twice before cursing OUR homes! Three cheers for our wizards!!", "rol3aheadlesshistory-gelibordcitizen-10", true);
        addSound("[14/25] Gelibord Citizen: The decay will clear up- We'll spill his blood, chop off his head, and the curse will be lifted!!", "rol3aheadlesshistory-gelibordcitizen-11", true);
        addSound("[15/25] Gelibord Citizen: Ladies and gentlemen, folks of all shapes and sizes, it's time to take our lives back!", "rol3aheadlesshistory-gelibordcitizen-12", true);
        addSound("[16/25] Gelibord Citizens: 3!", "rol3aheadlesshistory-gelibordcitizens-1", true);
        addSound("[17/25] Gelibord Citizens: 2!", "rol3aheadlesshistory-gelibordcitizens-2", true);
        addSound("[18/25] Gelibord Citizens: 1!", "rol3aheadlesshistory-gelibordcitizens-3", true);
        addSound("[19/25] Lari: Dullahan? I'm...I'm back. I didn't leave you for long, I just was trying to find the cu-", "rol3aheadlesshistory-lari-29", true);
        addSound("[20/25] Lari: ...uh? Dullahan? He's...he's been right here to greet me almost every day for the past twenty years...", "rol3aheadlesshistory-lari-30", true);
        addSound("[21/25] Lari: Footprints...? Dullaaaa? Where'd you go? Is everything alright, did something happen? ", "rol3aheadlesshistory-lari-31", true);
        addSound("[22/25] Lari: D-Dulla...ha...I... Wh...", "rol3aheadlesshistory-lari-32", true);
        addSound("[23/25] Lari: I... H-how could they...d-do this...?", "rol3aheadlesshistory-lari-33", true);
        addSound("[24/25] Lari: I...I l-left for...an hour, a-and...and they... Why?", "rol3aheadlesshistory-lari-34", true);
        addSound("[25/25] Lari: Why did they do this to you...? I...h-how can I possibly fix this?!", "rol3aheadlesshistory-lari-35", true);
        addSound("[1/1] Dullahan: I cannot understand why you continue to defile me with these horrid memories. What do you hope to find?", "rol3aheadlesshistory-dullahan-57", true);
        addSound("[1/1] Dullahan: I am once more curious. If only to know what I could possibly do or say to cease this horrid quest you've given yourself. ", "rol3aheadlesshistory-dullahan-58", true);
        addSound("[1/9] Lari: Th-this has to work. It WILL work! I know it will! I...I d-don't know if it will... I have to fix this, I have to... ", "rol3aheadlesshistory-lari-36", true);
        addSound("[2/9] Lari: The waters from the Lazarus Pit are fabled for bringing back the dead... If this doesn't work, I... ", "rol3aheadlesshistory-lari-37", true);
        addSound("[3/9] Lari: I can't think about that. This...this WILL work. I promise. I found a way to fix it... It has to. ", "rol3aheadlesshistory-lari-38", true);
        addSound("[4/9] Lari: P-Please...w-whatever gods are out there, whoever is listening, hear me...bring Dullahan back to me, I'm begging you... ", "rol3aheadlesshistory-lari-39", true);
        addSound("[5/9] Lari: It's... I-Is it reacting? It... It looks like it is, but...h-he's not mending... ", "rol3aheadlesshistory-lari-40", true);
        addSound("[6/9] Lari: N-No, this...i-it isn't...NO! It... It HAS to work! I...I can m-make this work! ", "rol3aheadlesshistory-lari-41", true);
        addSound("7/9lari!!", "rol3aheadlesshistory-lari-42", true);
        addSound("[8/9] Lari: D-Dullahan! It...it worked! You're back! ", "rol3aheadlesshistory-lari-43", true);
        addSound("[9/9] Lari: I...I t-told you... I knew I could change fate... I'm so glad you're here... ", "rol3aheadlesshistory-lari-44", true);
        addSound("[1/1] Dullahan: It's odd. If it were not for her, I would not be here. I would not have my power. Yet even thinking her name enrages me.", "rol3aheadlesshistory-dullahan-59", true);
        addSound("[1/15] Lari: Dullahan? I... ", "rol3aheadlesshistory-lari-45", true);
        addSound("[2/15] Lari: H...h-hrk...g-gah, what is...wh-what... You...you didn't really...? ", "rol3aheadlesshistory-lari-46", true);
        addSound("[3/15] Lari: Th-there's so much death... The...the air smells like b-blood... ", "rol3aheadlesshistory-lari-47", true);
        addSound("[4/15] Lari: This isn't... There has to be s-some other reason, I... I can't have b-brought back a...a...", "rol3aheadlesshistory-lari-48", true);
        addSound("[5/15] Gelibord Citizen: P-Please, we can't afford to lose more people! There's hardly any farmers in the fields anymore, I-I'm needed elsewhere!", "rol3aheadlesshistory-gelibordcitizen-13", true);
        addSound("[6/15] Dullahan: The last few said similar things. Someone else I knew said similar things. Frankly, I do not care. Present your soul to me, now.", "rol3aheadlesshistory-dullahan-60", true);
        addSound("[7/15] Lari: DULLAHAN! What...w-what are you doing?! I... I can't believe my eyes...", "rol3aheadlesshistory-lari-49", true);
        addSound("[8/15] Lari: I... I tried to help you! I t-tried so hard at everything we did together... How could you do this to them?! To...to me?", "rol3aheadlesshistory-lari-50", true);
        addSound("[9/15] Dullahan: Odd. I recall you explicitly denying the only option that would have prevented this. I warned you quite thoroughly.", "rol3aheadlesshistory-dullahan-61", true);
        addSound("[10/15] Dullahan: You have had many chances to end this, Lari. Do you expect me to feel sympathy, when your own actions have made me feel nothing?", "rol3aheadlesshistory-dullahan-62", true);
        addSound("[11/15] Lari: This...this isn't like you... You would never have done this before! You dedicated your life to trying to help...so w-why are you...m...m-m... I-I'm leaving!!", "rol3aheadlesshistory-lari-51", true);
        addSound("[12/15] Lari: What have I done...? This...he's... He's not th-the same anymore... What changed? Is this my fault...? I c-can't understand it...", "rol3aheadlesshistory-lari-52", true);
        addSound("[13/15] Lari: ...but...I'll... I'll protect them. I'll protect him... I...there has to be a way. But...until I find it...", "rol3aheadlesshistory-lari-53", true);
        addSound("[14/15] Lari: ...goodbye. Goodbye until the day comes when I can fill you with light once more. Goodbye until you learn... Goodbye, until I can fix you...", "rol3aheadlesshistory-lari-54", true);
        addSound("[15/15] Lari: Goodbye, Dullahan... I'll miss you.", "rol3aheadlesshistory-lari-55", true);
        addSound("At this moment...I believed that I could protect her. I believed that I could aid her.", "rol3aheadlesshistory-dullahan-63", true);
        addSound("I left Aldorei behind, to aid in her ''battle'' against the darkness. What a fool I was.", "rol3aheadlesshistory-dullahan-64", true);
        addSound("As the darkness slowly seeped into the land, her stubbornness halted any progress we might have made.", "rol3aheadlesshistory-dullahan-65", true);
        addSound("Years of searching to no avail- A strong enough attack would have ended it, but her foolish pacifism doomed us.", "rol3aheadlesshistory-dullahan-66", true);
        addSound("Six hundred years of searching finally brought us to what we so foolishly sought... And we were unprepared.", "rol3aheadlesshistory-dullahan-67", true);
        addSound("The creature was found hibernating underground... It had been storing power, and we found ourselves utterly outmatched.", "rol3aheadlesshistory-dullahan-68", true);
        addSound("She had the power and the opportunity to kill it ten times over, but clung to her morals. She could not recognize necessity, and refused to heed my offers and warnings.", "rol3aheadlesshistory-dullahan-69", true);
        addSound("And so, my dark agony began. The light began to siphon away from me. I found myself paranoid and addled, fearful that my disfigurement would be seen.", "rol3aheadlesshistory-dullahan-70", true);
        addSound("Even still I held some misplaced faith in her. With that dark seed in my mind... It grew to an obsession, almost- I had to hide it. I had to hide my curse. But it was found...", "rol3aheadlesshistory-dullahan-71", true);
        addSound("The people of Gelibord saw my cursed visage... They saw me near the dark scars. They assumed without thought, and turned me into a perfect scapegoat.", "rol3aheadlesshistory-dullahan-72", true);
        addSound("Not even death saw the end of my misfortunes. Not even a final release such as this could save me from Lari's stubbornness and endless mistakes.", "rol3aheadlesshistory-dullahan-73", true);
        addSound("She brought me back...but the dark infection had eroded away my mind, my soul, my magic, and it continued to ravage my body. Every part of me, the dark touched.", "rol3aheadlesshistory-dullahan-74", true);
        addSound("Her soft light burned in me, as the darkness quashed it and filled in the emptiness that it had created. I was there...but you could not say I was the same person.", "rol3aheadlesshistory-dullahan-75", true);
        addSound("I could feel nothing. She still made me feel...but only annoyance. Rage. Sorrow. I could not bear to be with her a moment longer.", "rol3aheadlesshistory-dullahan-76", true);
        addSound("There is no fixing this. Her actions are doomed to failure, just as I saw. She has not returned since this time... And so she did not realize that her actions have done little.", "rol3aheadlesshistory-dullahan-77", true);
        addSound("The very powers she bestowed me with, knowingly or not...allow me to command spirits as I wish. To have those who doomed me to this purgatory feel the same aches as I have.", "rol3aheadlesshistory-dullahan-78", true);
        addSound("To have their minds, bodies, and souls wracked. To have their magic outlawed and wither in disuse.", "rol3aheadlesshistory-dullahan-79", true);
        addSound("Each soul I consume fills me with new strength. And none can reach me- Lari herself saw to that. Would she have taken action, had she known?", "rol3aheadlesshistory-dullahan-80", true);
        addSound("There is truly, finally, nothing more for you here. The spell can not be broken, as of now. You have seen my life laid bare in front of you. There is nothing more for you to see.", "rol3aheadlesshistory-dullahan-81", true);
        addSound("Leave. And do not return.", "rol3aheadlesshistory-dullahan-82", true);
    }


    private static SoundEvent registerSound(String name) {
        ResourceLocation location = new ResourceLocation(ModCore.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }

    private void addSound(String message, String name, boolean movingSound) {
        message = formatToSound(message);
        sounds.put(message, new CustomSoundClass(registerSound(name), movingSound));
    }

    public static String formatToSound(String message) {
        message = message.replaceAll(" ", "");
        //  message = message.replaceAll("’", "");
        // message = message.replaceAll("\\.", "");
        //  message = message.replaceAll(",", "");
        message = message.toLowerCase();
     //   message = getTextInbetween(message, "\\n\\n");
        message = getTextAfterSplit(message, "clearedallpotioneffects");
        message = getTextAfterSplit(message, "yourquestswillnotauto-trackanymore");
        message = getTextBeforeSplit(message, "pressshifttocontinue");
        message = getTextBeforeSplit(message, "presssneaktocontinue");
        message = getTextAfterSplit(message, "\\[");
        message = getTextAfterSplit(message, "/queststartbeacon");
        message = "[" + message;
        message = message.replace("\n", "");
        message = message.replaceAll("[^abcdefghijklmnopqrstuvwxyz?!0123456789/]", "");

        return message;
    }

    public static String getTextAfterSplit(String message, String split) {
        String[] splitMessage = message.split(split);
        message = splitMessage[splitMessage.length - 1];
        return message;
    }

    public static String getTextBeforeSplit(String message, String split) {
        String[] splitMessage = message.split(split);
        message = splitMessage[0];
        return message;
    }

    private static String getTextInbetween(String message, String split) {
        String[] splitMessage = message.split(split);
        if (splitMessage.length > 1) {
            message = splitMessage[1];
        }
        return message;
    }


}
