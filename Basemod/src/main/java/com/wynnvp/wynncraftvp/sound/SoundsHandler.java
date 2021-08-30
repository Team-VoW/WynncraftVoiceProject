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
        addSound("[1/1] Merloni: Very impressive! Here's your reward. I found it a while ago while walking around Nemract. It probably fell off a skeleton", "pitofthedead-merloni-2", false);

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

        //Tempo Town Trouble
        addSound("[1/6] Scout: Grr... I KNOW I saw that beast go in here, where the hell is it?!?!", "tempotowntrouble-dougthescout-1", false);
        addSound("[1/8] Homeless Mayor: Hello there. Welcome to the recently made Tempo Town, friend.", "tempotowntrouble-homelessmayor-1", false);
        addSound("[1/9] Homeless Mayor: Hello there, welcome to the recently- IS THE MONSTER DEAD?! DID YOU KILL IT???", "tempotowntrouble-homelessmayor-2", false);
        addSound("1/13oldmanmartynwhatwasgoingthroughyournogginsoldier?", "tempotowntrouble-oldmanmartyn-1", false);
        addSound("[1/8] Tyko: Hello??? EXCUSE ME, CAN ANYONE HERE HELP ME? ANYONE?!?!", "tempotowntrouble-tyko-1", false);

        //Green gloop
        addSound("[1/9] Eluzterp: Oh, hey! You, adventurer over there! Would you mind helping me?", "greengloop-eluzterp-1", false);
        addSound("[1/6] Eluzterp: Oh, you're back! Good to see you were able to get through there. Have you got a full Slime Scooper?", "greengloop-eluzterp-2", false);
        addSound("[1/6] Yodbon: Welcome to Yodbon's Glass Blowery, what can I do f-", "greengloop-yodbon-1", false);
        addSound("[1/3] Yodbon: You've returned, I see.", "greengloop-yodbon-2", false);

        //kings Recruit
        addSound("[1/2] Caravan Driver: Agh! ", "kingsrecruit-caravandriver-1", true);
        addSound("[2/2] Tasim: Hey, soldier! You alright in there? Looks like we hit something. ", "kingsrecruit-tasim-1", true);
        addSound("[1/4] Caravan Driver: I swear I hit this same dang boulder everytime I make this trip.", "kingsrecruit-caravandriver-2", true);
        addSound("[2/4] Tasim: Does this mean we have to walk all the way to Ragni from here?", "kingsrecruit-tasim-2", true);
        addSound("[3/4] Caravan Driver: It's not that far, it's just a straight path from here.", "kingsrecruit-caravandriver-3", true);
        addSound("[4/4] Aledar: Soldier, if you're ready, let's get moving.", "kingsrecruit-aledar-1", true);
        addSound("[1/4] Tasim: So, what do you guys know about Wynn exactly?", "kingsrecruit-tasim-3", true);
        addSound("[2/4] Aledar: Same as you. That the war's been getting worse, so the King of Ragni's been recruiting Fruma soldiers. ", "kingsrecruit-aledar-2", true);
        addSound("[3/4] Tasim: I've never seen the Wynn Province before. What do you think it's like? ", "kingsrecruit-tasim-4", true);
        addSound("[4/4] Aledar: I think we're about to find out.", "kingsrecruit-aledar-3", true);
        addSound("[1/4] Tasim: Must be the gate marking the border between Fruma and Wynn.", "kingsrecruit-tasim-5", true);
        addSound("[2/4] Aledar: Yeah, now we can see what Wynn is really like, and find out if the stories are true.", "kingsrecruit-aledar-4", true);
        addSound("[3/4] Aledar: Here we go.", "kingsrecruit-aledar-5", true);
        addSound("[4/4] Aledar: Well, what are you waiting for?", "kingsrecruit-aledar-6", true);
        addSound("[1/3] Tasim: So this is Wynn, huh?", "kingsrecruit-tasim-6", true);
        addSound("[2/3] Aledar: It looks nice. The war clearly hasn't made it this far.", "kingsrecruit-aledar-7", true);
        addSound("[3/3] Soldier: Hey, you three! Come over here.", "kingsrecruit-soldier1-1", true);
        addSound("[1/4] Tasim: A soldier! What are you doing here?", "kingsrecruit-tasim-7", true);
        addSound("[2/4] Soldier: There's a bit of trouble up ahead. You can't expect to get out of here alive if you aren't prepared.", "kingsrecruit-soldier1-2", true);
        addSound("[3/4] Soldier: There's a cave right there. Those often contain useful loot, I'd give it a try if I were you. Enter the cave and find some armour.", "kingsrecruit-soldier1-3", true);
        addSound("[4/4] Aledar: Let's go inside then.", "kingsrecruit-aledar-8", true);
        addSound("[1/1] Aledar: The soldier didn't tell us the cave path was collapsed! We'll have to jump through.", "kingsrecruit-aledar-9", true);
        addSound("[1/3] Tasim: I don't think we can make this jump...", "kingsrecruit-tasim-8", true);
        addSound("[2/3] Aledar: What if we break that rock on the ceiling? That could open up the path for us.", "kingsrecruit-aledar-10", true);
        addSound("[3/3] Tasim: That could work... soldier, left-click the rock to break it!", "kingsrecruit-tasim-9", true);
        addSound("[1/1] Aledar: Just left-click the rock to break it!", "kingsrecruit-aledar-11", true);
        addSound("[1/1] Aledar: It worked! Now we can reach that chest.", "kingsrecruit-aledar-12", true);
        addSound("[1/1] Aledar: I think that's all we needed, let's get out. ", "kingsrecruit-aledar-13", true);
        addSound("[1/5] Soldier: That looks like the stuff.", "kingsrecruit-soldier1-4", true);
        addSound("[2/5] Soldier: Normally, this place is pretty safe, but somehow a few corrupted slipped through.", "kingsrecruit-soldier1-5", true);
        addSound("[3/5] Soldier: We're pretty far from the portal so I don't know how they got here. They even broke the bridge!", "kingsrecruit-soldier1-6", true);
        addSound("[4/5] Soldier: ... Anyway, you may have noticed that your loot is currently unidentified, you can identify your item at the Item Identifier further down the path on your left!", "kingsrecruit-soldier1-7", true);
        addSound("[5/5] Aledar: Thanks for the warning. I guess we better get going then. Soldier, lead the way.", "kingsrecruit-aledar-14", true);
        addSound("[1/2] Aledar: That's the identifier the soldier was talking about.", "kingsrecruit-aledar-15", true);
        addSound("[2/2] Aledar: Just right-click him to identify your item and once you're done talk to us again.", "kingsrecruit-aledar-16", true);
        addSound("[1/2] Aledar: If you've identified your item let's head off. Lead the way.", "kingsrecruit-aledar-17", true);
        addSound("[2/2] Tasim: The soldier said something about a bridge being broken. Let's check it out.", "kingsrecruit-tasim-10", true);
        addSound("[1/6] Tasim: Wait, stop! Don't want you falling off the edge. ", "kingsrecruit-tasim-11", true);
        addSound("[2/6] Tasim: Yeah, there's no way we'll get across here, unless one of you happened to be a bridge repairman back in Fruma. ", "kingsrecruit-tasim-12", true);
        addSound("[3/6] Aledar: Come to think of it, I don't remember. I know this sounds crazy, but I honestly can't remember anything I did back home. ", "kingsrecruit-aledar-18", true);
        addSound("[4/6] Tasim: No, you're not crazy, it's the same for me. It looks like soldier is suffering the same amnesia, as well. ", "kingsrecruit-tasim-13", true);
        addSound("[5/6] Aledar: It's a mystery for another day, I suppose.", "kingsrecruit-aledar-19", true);
        addSound("[6/6] Aledar: For now, soldier, let's look for a way across. There's gotta be something we can use to get to the other side...", "kingsrecruit-aledar-20", true);
        addSound("[1/1] Tasim: I guess that's one way of getting across! You guys head over the ravine, I'll be right behind you.", "kingsrecruit-tasim-14", true);
        addSound("[1/1] Aledar: Just ahead is the entrance to the underpass. Once we get through there, we'll be at the castle!", "kingsrecruit-aledar-21", true);
        addSound("[1/5] Guard: Ah, the new recruits. You might want to wait before passing through.", "kingsrecruit-guard-1", true);
        addSound("[2/5] Aledar: What? Why?", "kingsrecruit-aledar-22", true);
        addSound("[3/5] Guard: Somehow a bunch of corrupt sieged the underpass. It's not safe to go in unarmed. You three need weapons first.", "kingsrecruit-guard-2", true);
        addSound("[4/5] Guard: Here, take these weapons, courtesy from the government of Ragni, to complete your journey.", "kingsrecruit-guard-3", true);
        addSound("[5/5] Guard: Looks like you are all in order now, I hope you know how to use it. You may pass, but be careful, it's still very dangerous.", "kingsrecruit-guard-4", false);
        addSound("[1/3] Tasim: What's going on?", "kingsrecruit-tasim-15", true);
        addSound("[2/3] Tasim: That must be one of those 'Corrupted' the guard mentioned. Don't worry, it's moving so slow that it can't hurt us.", "kingsrecruit-tasim-16", true);
        addSound("[3/3] Tasim: NEVERMIND! KILL IT!", "kingsrecruit-tasim-17", true);
        addSound("[1/1] Aledar: Well done, Soldier! Let's keep going.", "kingsrecruit-aledar-23", true);
        addSound("[1/1] Tasim: Anyone else have a bad feeling..?", "kingsrecruit-tasim-18", true);
        addSound("[1/2] Aledar: Oh no...", "kingsrecruit-aledar-24", true);
        addSound("[2/2] Aledar: Soldier, take the one in the front. We'll get the two in the back!", "kingsrecruit-aledar-25", true);
        addSound("[1/1] Tasim: Great work! We're almost to the castle, so let's keep going!", "kingsrecruit-tasim-19", true);
        addSound("[1/1] Tasim: Careful! Looks like there's more undead!", "kingsrecruit-tasim-20", true);
        addSound("[1/8] Guard: Finally, some backup! Help me out here, would ya?", "kingsrecruit-guard2-1", true);
        addSound("[2/8] Aledar: What happened, did you get attacked by the corrupt?", "kingsrecruit-aledar-26", true);
        addSound("[3/8] Guard: Not quite, recruit. I'm well-equipped for them. The issue is this cave-in.", "kingsrecruit-guard2-2", true);
        addSound("[4/8] Tasim: This isn't seriously the only way through, is it? What are we going to do?", "kingsrecruit-tasim-21", true);
        addSound("[5/8] Guard: If it wasn't for my injuries, I could just use my weapon to cast a spell on these boulders to get rid of them.", "kingsrecruit-guard2-3", true);
        addSound("[6/8] Guard: ...", "kingsrecruit-guard2-4", true);
        addSound("[7/8] Guard: Soldier, you're an Assassin, right? Walk up to the boulder for me.", "kingsrecruit-guard2-5", true);
        addSound("[8/8] Guard: Now, cast your Spin Attack spell by clicking Right-Left-Right!", "kingsrecruit-guard2-6", true);
        addSound("[7/8] Guard: Soldier, you're a Warrior, right? Walk up to the boulder for me.", "kingsrecruit-guard2-7", true);
        addSound("[8/8] Guard: Now, cast your Bash spell by clicking Right-Left-Right!", "kingsrecruit-guard2-8", true);
        addSound("[7/8] Guard: Soldier, you're a Mage, right? Gonna need you to stay close to me.", "kingsrecruit-guard2-9", true);
        addSound("[8/8] Guard: Now, cast your Heal spell by clicking Right-Left-Right!", "kingsrecruit-guard2-10", true);
        addSound("[7/8] Guard: Soldier, you're an Archer, right? Walk up to the boulder for me.", "kingsrecruit-guard2-11", true);
        addSound("[8/8] Guard: Now, cast your Arrow Storm spell by clicking Left-Right-Left!", "kingsrecruit-guard2-12", true);
        addSound("[7/8] Guard: In my current state, I'm too weak to destroy this boulder. But since you're a Shaman, recruit, you can help me.", "kingsrecruit-guard2-13", true);
        addSound("[8/8] Guard: All I need is for you to use your totem spell. To cast it, click Right-Left-Right!", "kingsrecruit-guard2-14", true);
        addSound("[1/1] Guard: Good work. Now, you three head on towards the castle.", "kingsrecruit-guard2-15", true);
        addSound("[1/2] Guard: That's done it! All healed up. Now let me take care of this.", "kingsrecruit-guard2-16", true);
        addSound("[2/2] Guard: Now, you three should head on towards the castle.", "kingsrecruit-guard2-17", true);
        addSound("[1/2] Guard: I feel stronger already! This should be enough for me to get us out of here.", "kingsrecruit-guard2-18", true);
        addSound("[2/2] Guard: Took all of the strength your Totem gave me to do it, but I managed to save us! You three head on to the castle, now.", "kingsrecruit-guard2-19", true);
        addSound("[7/8] Guard: Soldier, you're an Ninja, right? Walk up to the boulder for me", "kingsrecruit-guard2-23", false);
        addSound("[8/8] Guard: Now, cast your Whirlwind spell by clicking Right-Left-Right!", "kingsrecruit-guard2-24", false);
        addSound("[7/8] Guard: Soldier, you're a Knight, right? Walk up to the boulder for me.", "kingsrecruit-guard2-25", false);
        addSound("[8/8] Guard: Now, cast your Holy Blast spell by clicking Right-Left-Right!", "kingsrecruit-guard2-26", false);
        addSound("[7/8] Guard: Soldier, you're a Dark Wizard, right? Gonna need you to stay close to me.", "kingsrecruit-guard2-27", false);
        addSound("[8/8] Guard: Now, cast your Remedy spell by clicking Right-Left-Right!", "kingsrecruit-guard2-28", false);
        addSound("[7/8] Guard: Soldier, you're a Hunter, right? Walk up to the boulder for me.", "kingsrecruit-guard2-29", false);
        addSound("[8/8] Guard: Now, cast your Bolt Blizzard spell by clicking Left-Right-Left!", "kingsrecruit-guard2-30", false);
        addSound("[7/8] Guard: In my current state, I'm too weak to destroy this boulder. But since you're a Skyseer, Soldier, you can help me.", "kingsrecruit-guard2-31", false);
        addSound("[8/8] Guard: All I need is for you to use your sky emblem spell. To cast it, click Right-Left-Right!", "kingsrecruit-guard2-32", false);
        addSound("[1/2] Guard: Wait!", "kingsrecruit-guard2-20", true);
        addSound("[2/2] Guard: RUN!", "kingsrecruit-guard2-21", true);
        addSound("[1/3] Soldier: Launch the trebuchet!", "kingsrecruit-soldier-1", true);
        addSound("[2/3] Guard: Thanks for the save.", "kingsrecruit-guard2-22", true);
        addSound("[3/3] Soldier: Well, I'm just glad everyone made it out okay. All right, recruits, the castle is straight ahead!", "kingsrecruit-soldier-2", true);
        addSound("[1/1] Aledar: Well, we made it. Guess we better head inside and talk to the King.", "kingsrecruit-aledar-27", true);
        addSound("[1/1] Ragni's King: Ah, you must be the last recruit. Please come over here and speak to me.", "kingsrecruit-ragnisking-1", true);
        addSound("[1/7] Ragni's King: Hello! Your friends just left. They told me quite the story about what happened to you three on the way here.", "kingsrecruit-ragnisking-2", false);
        addSound("[1/1] Tasim: Just Right-Click the Loot chest to open it.", "kingsrecruit-tasim-22", false);

        //Recover the past
        addSound("[1/8] Dr. Picard: SUCCESS! I can see clearly now.. My memories from years ago!", "recoverthepast-dr.picard-1", false);
        addSound("[1/3] Scout Reynauld: Oh, good! Reinforcements! I've been left here alone to defend Elkurn.", "recoverthepast-scoutreynauld-1", false);
        addSound("[1/1] Scout Reynauld: Alright, here we are. Talk to me when you are ready.", "recoverthepast-scoutreynauld-2", true);
        addSound("[1/4] Scout Reynauld: Oh boy, here they come!", "recoverthepast-scoutreynauld-3", true);
        addSound("[2/4] Scout Reynauld: Wow! Erected spikes, too?!", "recoverthepast-scoutreynauld-4", true);
        addSound("[3/4] Scout Reynauld: There's a Corrupted summoner on the other side of this bridge! We gotta destroy it!", "recoverthepast-scoutreynauld-5", true);
        addSound("[4/4] Scout Reynauld: Defend me while I prepare my explosive arrows to bring down those towers blocking your path to it!", "recoverthepast-scoutreynauld-6", true);
        addSound("[1/1] Scout Reynauld: My arrow's almost ready...", "recoverthepast-scoutreynauld-7", true);
        addSound("[1/1] Scout Reynauld: Yes! One down, one to go! You're doing great!", "recoverthepast-scoutreynauld-8", true);
        addSound("[1/1] Scout Reynauld: Almost got this arrow nocked...", "recoverthepast-scoutreynauld-9", true);
        addSound("[1/1] Scout Reynauld: Bullseye! Now's your chance, go take out that summoner!", "recoverthepast-scoutreynauld-10", true);
        addSound("[1/1] Scout Reynauld: Great job, you did it! Now, come back and get these monsters off me!", "recoverthepast-scoutreynauld-11", true);
        addSound("[1/4] Scout Reynauld: Phew. Thanks for the help, not sure I could have done that alone.", "recoverthepast-scoutreynauld-12", true);
        addSound("[1/7] Dr. Picard: Ah, I see you have the soldier. ", "recoverthepast-drpicard-2", false);
        addSound("[2/7] Scout Reynauld: So how exactly were you planning on extracting my memory?", "recoverthepast-scoutreynauld-13", false);
        addSound("[3/7] Dr. Picard: In a non painful way, I promise you. Now, if you'll stand over here, please.", "recoverthepast-drpicard-3", false);
        addSound("[4/7] Scout Reynauld: You mean in front of that crystal thing? Alright...", "recoverthepast-scoutreynauld-14", false);
        addSound("[5/7] Scout Reynauld: Do I just look into it? ", "recoverthepast-scoutreynauld-15", false);
        addSound("[6/7] Dr. Picard: Just stare into its depths and let that depth reflect into your mind...", "recoverthepast-drpicard-4", false);
        addSound("[7/7] Scout Reynauld: I don't feel any- WOAH", "recoverthepast-scoutreynauld-16", false);
        addSound("[1/12] Reynauld: *Sigh*, what a day.", "recoverthepast-reynauld-1", true);
        addSound("[2/12] Reynauld: Is the water any better?", "recoverthepast-reynauld-2", true);
        addSound("[3/12] Heine: *Sigh*, there's barely enough for us and the horses.", "recoverthepast-heine-1", true);
        addSound("[4/12] Heine: I don't see why the Royalty has to irrigate from our rivers.", "recoverthepast-heine-2", true);
        addSound("[5/12] Imperial Guard: The Palace has ordered  an inquest into the productivity of this village. You are behind on your crop deliveries.", "recoverthepast-imperialguard-1", true);
        addSound("[6/12] Reynauld: Sir, we tried but the farms don't have enough clean water since the Palace dammed up the river!", "recoverthepast-reynauld-3", true);
        addSound("[7/12] Imperial Soldier: Are you insulting the work of our royal engineers?", "recoverthepast-imperialguard-2", true);
        addSound("[8/12] Reynauld: What? No! I'm just explaining- Please! I have a family I didn't speak out of turn.", "recoverthepast-reynauld-4", true);
        addSound("[9/12] Reynauld: Heine? Look after the kids. I'll be back soon!", "recoverthepast-reynauld-5", true);
        addSound("[10/12] Imperial Guard: The Palace does not tolerate insubordination. Reynauld you are to be tried for Lèse-majesté.", "recoverthepast-imperialguard-3", true);
        addSound("[11/12] Imperial Guard: This village would do well to remember who protects them from external forces.", "recoverthepast-imperialguard-4", true);
        addSound("[12/12] Dr. Picard: Hey. HEY! It's not real. Just a memory! Talk to me!", "recoverthepast-drpicard-5", true);
        addSound("[1/5] Dr. Picard: Well, that was unexpected. I didn't anticipate subjects would relive them that vividly.", "recoverthepast-drpicard-6", false);
        addSound("[1/5] Caid: Yep, I'm the sailor. Pretty sure I'm from Fruma, came here on the same cart as you.", "recoverthepast-caid-1", false);
        addSound("[1/6] Alchemist: Looking for potions?", "recoverthepast-alchemist-1", false);
        addSound("[1/1] Alchemist: If you have the items, mix them together in the brewing stand next to me.", "recoverthepast-alchemist-2", false);
        addSound("[1/5] Caid: You know, the more I look at this, the more I wonder how I did this. ", "recoverthepast-caid-2", false);
        addSound("[4/5] Caid: I mean... It certainly shrunk. Do I even fit now?", "recoverthepast-caid-3", false);
        addSound("[1/5] Dr. Picard: Ahhh, another Fruman. Let's see if his story is the same as Reynauld's.", "recoverthepast-drpicard-7", false);
        addSound("[2/5] Sailor Caid: I am still not sure about this, I'm kind of happy here.", "recoverthepast-sailorcaid-1", false);
        addSound("[3/5] Dr. Picard: Nothing to worry about, it won't change who you are now.", "recoverthepast-drpicard-8", false);
        addSound("[4/5] Dr. Picard: Wait for it...", "recoverthepast-drpicard-9", false);
        addSound("[5/5] Sailor Caid: Hold up. What is going on?", "recoverthepast-sailorcaid-2", false);
        addSound("[1/7] ???: AHHH!!!!!", "recoverthepast-cara-1", true);
        addSound("[2/7] Imperial Guard: Move", "recoverthepast-imperialguard-5", true);
        addSound("[3/7] Caid: CARA!", "recoverthepast-caid-4", true);
        addSound("[4/7] Imperial Guard: Cara is under arrest. She is coming with me.", "recoverthepast-imperialguard-6", true);
        addSound("[5/7] Caid: GET YOUR HANDS OFF OF HER!.", "recoverthepast-caid-5", true);
        addSound("[6/7] Imperial Guard: Perhaps you would like to go in her place?", "recoverthepast-imperialguard-7", true);
        addSound("[7/7] Dr. Picard: Hey. Earth to human.", "recoverthepast-drpicard-10", true);
        addSound("[1/4] Dr. Picard: Hmmm... This doesn't look too good.", "recoverthepast-drpicard-11", false);
        addSound("[1/5] Edwin: Fruma? Don't remember nothing. Don't want to neither.", "recoverthepast-edwin-1", false);
        addSound("[1/3] Edwin: You army folk sure keep your promises.", "recoverthepast-edwin-2", true);
        addSound("[1/5] Dr. Picard: Ah, I see you have the third subject. ", "recoverthepast-drpicard-12", false);
        addSound("[2/5] Edwin: Pfft! I'm not some subject!", "recoverthepast-edwin-3", false);
        addSound("[3/5] Dr. Picard: My apologies. Please, stand in front of the orb.", "recoverthepast-drpicard-13", false);
        addSound("[4/5] Dr. Picard: Wait for it...", "recoverthepast-drpicard-14", false);
        addSound("[5/5] Edwin: WAAAAO!!!!!!!", "recoverthepast-edwin-4", false);
        addSound("[1/23] Edwin: Why are you doing this!?", "recoverthepast-edwin-5", false);
        addSound("[2/23] Imperial Guard: Order of her majesty.", "recoverthepast-imperialguard-8", false);
        addSound("[3/23] Edwin: LET ME OUT! WHAT IS THIS!", "recoverthepast-edwin-6", false);
        addSound("[4/23] Caid: Hey you. Calm down. There's no point in yelling...", "recoverthepast-caid-6", false);
        addSound("[5/23] Edwin: What... what do you mean?", "recoverthepast-edwin-7", false);
        addSound("[6/23] Caid: There is no escape here. The guards come in once in a while and take people... people who are never seen again...", "recoverthepast-caid-7", false);
        addSound("[7/23] Edwin: Where are they being taken?", "recoverthepast-edwin-8", false);
        addSound("[8/23] Caid: No one knows... The last person they took was... Alen? Alida? Alegar?", "recoverthepast-caid-8", false);
        addSound("[9/23] Imperial Guard: Caid! You are being transferred.", "recoverthepast-imperialguard-9", false);
        addSound("[10/23] Caid: Wh-what. I... don't want to go!", "recoverthepast-caid-9", false);
        addSound("[11/23] Imperial Guard: Walk. Or you will be executed on the spot.", "recoverthepast-imperialguard-10", false);
        addSound("[12/23] Caid: What are you doing!?", "recoverthepast-caid-10", false);
        addSound("[13/23] Imperial Guard: Shut it. ", "recoverthepast-imperialguard-11", false);
        addSound("[14/23] Imperial Guard: There is no war inside the Fruman walls.", "recoverthepast-imperialguard-12", false);
        addSound("[22/23] Dr. Picard: Soldier! Get out of there! You are being sucked into the memory.", "recoverthepast-drpicard-15", false);
        addSound("[23/23] Dr. Picard: You need to find an exit or you will be sucked into the memory without chance of returning. HURRY!", "recoverthepast-drpicard-16", false);
        addSound("[1/5] Dr. Picard: Okay, three Frumans have seen the same thing. Why are they sending their people here?", "recoverthepast-drpicard-17", false);
        addSound("[4/5] Dr. Picard: Stand on this red carpet here and we'll get started.", "recoverthepast-drpicard-21", false);
        addSound("[5/5] Dr. Picard: Wait for it...", "recoverthepast-drpicard-18", false);
        addSound("[1/4] Dr. Picard: Are you okay?", "recoverthepast-drpicard-19", false);
        addSound("[2/4] Dr. Picard: The other subjects never experienced that powerful of a reaction, it's almost like we were accessing memories that never existed.", "recoverthepast-drpicard-20", false);

        //Out of my mind
        addSound("[1/10] Prentiss: Did ye two hear about some guy wandering around town? I've heard strange rumours about him...", "outofmymind-prentiss-1", false);
        addSound("[2/10] Todd: Of course I have, he's the talk of the town! I heard he only brushes his teeth once a day and that he enjoys books about gardening!", "outofmymind-todd-1", false);
        addSound("[3/10] Viola: I don't know, you guys, all these rumours seem a little far-fetched. What next? Will he suddenly turn out to be some mad scientist?", "outofmymind-viola-1", false);
        addSound("[4/10] Prentiss: Oh shush you, we're not living inside some storybook! Besides, if he *was* a mad scientist, wouldn't he be trying to take over the city like all mad scientists in stories do?", "outofmymind-prentiss-2", false);
        addSound("[5/10] Todd: To me that doesn't sound all that strange compared to these other things I've been hearing, anyway... Something about labs and a \"\"hermit\"\".", "outofmymind-todd-2", false);
        addSound("[6/10] Prentiss: I think that's a type of crab or something. Adults are right old weirdos sometimes.", "outofmymind-prentiss-3", false);
        addSound("[7/10] Viola: Hey, a stranger! How long have you been listening to us? Didn't your mom teach you any manners?", "outofmymind-viola-2", false);
        addSound("[8/10] Prentiss: Wait Viola, this is our chance! This stranger sure seems like they could tackle anything, even evil hermit crabs! We could just ask them to see if these rumours are true!", "outofmymind-prentiss-4", false);
        addSound("[9/10] Todd: Ignore my friends over there, they're always like that, but what Prentiss said was true. This might be our only chance to really know who this mystery man really is!", "outofmymind-todd-3", false);
        addSound("[10/10] Todd: It might be a good idea to look at that weird house next to those spooky ruins south-east of the city. We found this key you could try out, but if you lose it there are some spares in our base.", "outofmymind-todd-4", false);
        addSound("[1/4] Ariodo: Hello stranger! Welcome to my humble wooden abode. Comfy, isn't it? I bet you'd like a cup of this coff-", "outofmymind-ariodo-1", true);
        addSound("[1/3] Ariodo: This is the room you came from. I call it the \"\"Vivarium\"\", creative name I came up with, right? It keeps my pets that aren't quite ready.", "outofmymind-ariodo-2", true);
        addSound("[1/5] Ariodo: So you must be thinking, how have you never met a fine fellow such as myself until now? To be quite honest, you must have just been terribly unlucky, I used to travel the world, you know!", "outofmymind-ariodo-3", true);
        addSound("[1/2] Ariodo: Here we are, the library! I could spend years in here, but who has time for that? I could talk about books for hours too! Here's one right now.", "outofmymind-ariodo-4", true);
        addSound("[1/2] Ariodo: We're here now! Take the exit right in front of you, don't mind the corridor to the right, it's only decoration!", "outofmymind-ariodo-5", true);
        addSound("[1/1] Ariodo: The ground is shaking... I thought that was supposed to happen later! Come back friend, I can't do this alone!", "outofmymind-ariodo-6", true);
        addSound("[1/3] Ariodo: Help me keep this door shut, or else my surprise will be ruined!", "outofmymind-ariodo-7", true);
        addSound("[1/8] Ariodo: Oh my, a ghost? Wait, is this just you, my trusty friend, playing a prank on me? No? Well that certainly is a problem!", "outofmymind-ariodo-8", true);
        addSound("1/22ariodoryoureback!tobequitehonestididntthinkthatwouldworkbutimgladitdid!", "outofmymind-ariodo-9", true);
        addSound("1/32ariodorohnonothisisnotgood!ifihadknownthatmylittlepethasgrownsomuchiwouldhaverushedyououtsoonernowitiswilted", "outofmymind-ariodo-10", false);
        addSound("[1/11] Todd: You're back! You were gone for ages too, and what happened to your head? Who did the mystery man turn out to be?", "outofmymind-todd-5", false);
        addSound("[2/11] Viola: That sounds ridiculous! Who could ever belie-", "outofmymind-viola-3", false);
        addSound("[3/11] Prentiss: Wow that sounded just like in the stories, but it came from you, so it must be true!", "outofmymind-prentiss-5", false);
        addSound("[4/11] Prentiss: I don't know about you, Todd, but I feel we should make them our new honorary club member for finding such amazing things!", "outofmymind-prentiss-6", false);
        addSound("[5/11] Viola: Why do I even try... You two would believe anything.", "outofmymind-viola-4", false);
        addSound("[6/11] Prentiss: I'm pretty sure they mentioned something about being hit by coconut crabs, and just look at their head. One.. two.. two huge bumps!", "outofmymind-prentiss-7", false);
        addSound("[7/11] Viola: Oh fine... whatever you say, Prentiss. I'm sure they'll bring you on some wacky adventures next time around!", "outofmymind-viola-5", false);
        addSound("[8/11] Todd: Ignore Viola, she's acting up today, probably because you've got the spotlight this time. However, I agree with Prentiss.", "outofmymind-todd-6", false);
        addSound("[9/11] Todd: Thanks to you, we solved the case of the mystery man as well as the mystery house! For this I'd like to...", "outofmymind-todd-7", false);
        addSound("[10/11] Todd: Welcome you to the club, new member! Here is your shiny new badge that you can parade around town to show off how cool you are to others with style.", "outofmymind-todd-8", false);
        addSound("[11/11] Prentiss: Also since all the adults do it, we'll give you some emeralds that you rightly deserve for such an achievement. We all pitched in!", "outofmymind-prentiss-8", false);

        //The Shadow of the Beast
        addSound("[1/7] Dereg: Oh, you look tough. You must be one of those humans I keep hearing about, right? People talk about your kind all the time.", "theshadowofthebeast-dereg-1", false);
        addSound("[1/9] Tolem: Oh, this is such a mess! And my axe is busted, too...how am I going to get this fixed?!", "theshadowofthebeast-tolem-1", false);
        addSound("[1/11] Tolem: Oh, back already? I haven't even finished my stuff, where are the cows?", "theshadowofthebeast-tolem-2", false);
        addSound("[1/4] Tolem: Oh lovely. Idiot's back! Joy of joys. Willow planks, please.", "theshadowofthebeast-tolem-3", false);
        addSound("[1/1] Kroac: Oh, I'll never be able to fix this with my back in this state...", "theshadowofthebeast-kroac-1", false);
        addSound("[1/6] Kroac: Oh, look at this...all my years of carpentry and my bad back does my poor house in.", "theshadowofthebeast-kroac-2", false);
        addSound("[1/5] Kroac: Did you get enough cobblestone? I found a guy down the street selling this top-quality Willow...", "theshadowofthebeast-kroac-3", false);
        addSound("[1/3] Kroac: That'll do just fine, kind soldier! You're a lifesaver, really, you are.", "theshadowofthebeast-kroac-4", false);
        addSound("[1/2] Rileen: EVERYONE, MOVE! THE BEAST IS ON A RAMPAGE!", "theshadowofthebeast-rileen-1", false);
        addSound("[2/2] Rileen: Aah, no! There were people in that house!", "theshadowofthebeast-rileen-2", false);
        addSound("[1/7] Rileen: Wah! H-Human! Get away, there's nothing you can help with here!", "theshadowofthebeast-rileen-3", false);
        addSound("[1/7] Rileen: No, there's no time to make a fishing spear- AAH! You're still here? What do you WANT?!", "theshadowofthebeast-rileen-4", false);
        addSound("[1/7] Dereg: You're back! Any luck hunting the thing down?", "theshadowofthebeast-dereg-2", false);

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
        addSound("[1/12] Lari: Aah! soldier! Don't...don't make any sudden movements.", "rol1wormholes-lari-24", true);
        addSound("[2/12] Lari: This Grootslang is uncharacteristically aggressive... She's scared, being so close to the surface. Stay behind me, please.", "rol1wormholes-lari-25", true);
        addSound("[3/12] Lari: AH!!", "rol1wormholes-lari-26", true);
        addSound("[4/12] Lari: Do not approach it! The power of the Wyrm is far too much for you!", "rol1wormholes-lari-27", true);
        addSound("[5/12] Lari: W-wait, again? Aah, MOVE!", "rol1wormholes-lari-28", true);
        addSound("[6/12] Lari: soldier!! You're unharmed, yes? She's scared and in pain!", "rol1wormholes-lari-29", true);
        addSound("[7/12] Lari: I have to get it right this time... I'm going to talk her down, stay there!", "rol1wormholes-lari-30", true);
        addSound("[8/12] Lari: You must be calm, I beg you. Retreat to the earth- You harm those above!", "rol1wormholes-lari-31", true);
        addSound("[9/12] Lari: The earth rotting... I take full responsibility, but does the grass above not rot also?", "rol1wormholes-lari-32", true);
        addSound("[10/12] Lari: I wish to soothe your pain, but you must allow me to aid you- You must listen!", "rol1wormholes-lari-33", true);
        addSound("[11/12] Lari: What does this violence solve? Be calm, please!", "rol1wormholes-lari-34", true);
        addSound("[12/12] Lari: soldier, it won't budge... There's something in its mind rejecting me. Distract it- but don't approach! It will kill you if you get close. Just...be loud!", "rol1wormholes-lari-35", true);
        addSound("[1/16] Lari: AIYEE! soldier! Hold on in there!", "rol1wormholes-lari-36", true);
        addSound("[2/16] Lari: Can you hear me?! I'll get her to...to...s-something! Spit you up, I don't know! Just...please, d-don't die!", "rol1wormholes-lari-37", true);
        addSound("[3/16] Lari: W-We have... We have not laid a blow against you! It is not right for you to do the same!", "rol1wormholes-lari-38", true);
        addSound("[4/16] Lari: You would wound and consume everything in your rage? Even knowing the consequences?", "rol1wormholes-lari-39", true);
        addSound("[5/16] Lari: Your hunger cannot be sated with this fruitless destruction! I beg of you, let them out!", "rol1wormholes-lari-40", true);
        addSound("[6/16] Lari: You know th- W-wait...where did you... AAAH!", "rol1wormholes-lari-41", true);
        addSound("[7/16] Lari: ...y-you... soldier... I...y-you're still alright? I...*hic* I...", "rol1wormholes-lari-42", true);
        addSound("[8/16] Lari: I tried...w-why does no one listen to me? Why won't a-anything change for me?", "rol1wormholes-lari-43", true);
        addSound("[9/16] Lari: She...she's been eating dark parasites. They're wracking her insides and h-hurting her head...", "rol1wormholes-lari-44", true);
        addSound("[10/16] Lari: I...i-it's so d-dark... I c-can't breathe... This...th-this can't be the end...", "rol1wormholes-lari-45", true);
        addSound("[11/16] Lari: I...n-no, I can't die in here... I-I'm needed still... You're n-needed still! I... I...", "rol1wormholes-lari-46", true);
        addSound("[12/16] Lari: I n-need... Or... O-Or... C-Concentrate, Lari... You can do this...", "rol1wormholes-lari-47", true);
        addSound("[13/16] Lari: !!", "rol1wormholes-lari-48", true);
        addSound("[14/16] Lari: LET US OUT! LET US OUT OF HERE! DON'T LET US DIE LIKE THIS! I'M BEGGING YOU, PLEASE!!", "rol1wormholes-lari-49", true);
        addSound("[15/16] Lari: ...I...oh no... It's...d-d... I... I j-just... I just w-wanted out... Ohhh...n-no...", "rol1wormholes-lari-50", true);
        addSound("[16/16] Lari: ...w-we're...leaving. I...I c-can't look at this any more...", "rol1wormholes-lari-51", true);
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

        //Realm of light 4 - finding the light
        addSound("[1/7] Lari: Awaken! Your guidance is needed!", "realmoflight4findingthelight-lari-1", false);
        addSound("[2/7] Lari: ...ah. soldier. So you are needed here too, then?", "realmoflight4findingthelight-lari-2", false);
        addSound("[3/7] Lari: I am... TRYING... To awaken the Guardian of the Forest to receive its guidance, but it refuses to budge! Even the voice of Orphion provides no reaction.", "realmoflight4findingthelight-lari-3", false);
        addSound("[4/7] Lari: I beg once more of you, awaken! Us two... Us two seek your wisdom!", "realmoflight4findingthelight-lari-4", false);
        addSound("[5/7] Lari: I present you Orphion's barest light- Please, respond!", "realmoflight4findingthelight-lari-5", false);
        addSound("6/7lariurghsee?nothingishappeningimatalossofwhattodomaybeyoucaneffectsomechangeconsideringwhatyouvedonebefore", "realmoflight4findingthelight-lari-6", false);
        addSound("[7/7] ???: My pulse, it is time. I shall shine my light upon the roots of the Guardian. You and she must learn now.", "realmoflight4findingthelight-orphion-1", false);
        addSound("[1/4] Lari: An inscription! That wasn't there before! It was... That was completely instant. How did you even...?", "realmoflight4findingthelight-lari-7", false);
        addSound("[2/4] Lari: But, still! I knew I could be useful, let me translate this for you! As a stranger to Gavel you wouldn't know High Gavellian...", "realmoflight4findingthelight-lari-8", false);
        addSound("[3/4] Lari: It says... Secrets uncovered in the oldest of libraries in Cinfras.", "realmoflight4findingthelight-lari-9", false);
        addSound("[4/4] Lari: ...of course. A place that Elves aren't allowed, but humans are. soldier, you'll have to find this library on your own, it seems.", "realmoflight4findingthelight-lari-10", false);
        addSound("[1/7] Lari: Do you reject the light of Orphion? Waken, please!", "realmoflight4findingthelight-lari-11", false);
        addSound("[2/7] Lari: Urgh... soldier, please tell me you were able to make some progress. The Guardian still refuses... To... WAKE! ", "realmoflight4findingthelight-lari-12", false);
        addSound("[3/7] Lari: What is this? A spell scroll- Of the Waking Forest?! In Cinfras, of all places? Where it would have been... Impossible for me to go...", "realmoflight4findingthelight-lari-13", false);
        addSound("[4/7] Lari: So I am Orphion's chosen, yet I would not have been able to do what was asked of me. That... No, I can't dwell on that, let me see the scroll.", "realmoflight4findingthelight-lari-14", false);
        addSound("[5/7] Lari: ...hm. It says, The seeker must rest these words upon those of the Guardian.", "realmoflight4findingthelight-lari-15", false);
        addSound("[6/7] Lari: I suppose that means you need to place it by the inscription. So yet another task that you must finish...", "realmoflight4findingthelight-lari-16", false);
        addSound("[7/7] Lari: ...go on, then, do it. I'm just needed here to be a translator for you thusfar, it seems.", "realmoflight4findingthelight-lari-17", false);
        addSound("[1/7] Lari: Let me go ahead and read this, too. This will be tedious, I fear...", "realmoflight4findingthelight-lari-18", false);
        addSound("[2/7] Lari: It says... Awakening the Guardian of the Forest requires more than just magic. The home of the elves will house the answers and revelations...?", "realmoflight4findingthelight-lari-19", false);
        addSound("[3/7] Lari: That... This is finally my time! I still have a hand in this!! Finally, I can do something more than just holding stasis here!", "realmoflight4findingthelight-lari-20", false);
        addSound("[4/7] Lari: Listen, soldier. Achara... This is my time. I can finally help you more directly. I know the elders of Aldorei- They're sure to have answers!", "realmoflight4findingthelight-lari-21", false);
        addSound("[5/7] Lari: You truly have been helpful, even through my own worries. I must thank you, but you can relax now! I will be the one this time!", "realmoflight4findingthelight-lari-22", false);
        addSound("[6/7] Lari: You can continue doing...whatever else it is you need doing. I have seen you helping in various places- Concern yourself with those for now. I can handle this.", "realmoflight4findingthelight-lari-23", false);
        addSound("[7/7] ???: Pursue her, my pulse. She must take dire action- Alone, her light shall illuminate nothing. You must provide her direction.", "realmoflight4findingthelight-orphion-2", false);
        addSound("[1/2] Lari: W-What? You- Why did you follow me? I can handle this! I can finally be useful again! Do you...not trust me?", "realmoflight4findingthelight-lari-24", false);
        addSound("[2/2] Lari: Please, just respect my wishes! If I need your help, I'll go find you- For now, just leave! You don't need to worry about this! ", "realmoflight4findingthelight-lari-25", false);
        addSound("[1/1] Lari: Get out! Just...get out.", "realmoflight4findingthelight-lari-26", false);
        addSound("[1/16] Elder: ...if this human has truly done what you have said, then why do you spurn their aid? It is unlike you.", "realmoflight4findingthelight-elder-1", false);
        addSound("[2/16] Lari: I know, I know. I just am in disbelief. They've been contacted by Orphion himself, allowed into the Taproot, they have a symbol of Orphion's blessing!!", "realmoflight4findingthelight-lari-27", false);
        addSound("[3/16] Lari: They just...walked up, after a thousand years of my putting my entire life into trying to save this province, and do everything I couldn't, in the span of weeks at most!", "realmoflight4findingthelight-lari-28", false);
        addSound("[4/16] Lari: Do you understand how infuriating this is? Can you comprehend such a thing, not having been in my position yourself?", "realmoflight4findingthelight-lari-29", false);
        addSound("[5/16] Elder: No. I cannot understand. But I also cannot understand how you are not grateful for this aid.", "realmoflight4findingthelight-elder-2", false);
        addSound("[6/16] Lari: I know I should be! It's all I've been able to think about. I should be ecstatic! The light will be saved, it should be everything I've dreamed of...", "realmoflight4findingthelight-lari-30", false);
        addSound("[7/16] Lari: But it makes me feel like a complete failure. Like everything I have done has been for naught, that I have somehow been doing everything wrong!", "realmoflight4findingthelight-lari-31", false);
        addSound("[8/16] Lari: I know I have made mistakes. Dullahan would still be... Still be himself, if I had taken faster action, but I can't have done nothing right. I can't accept that.", "realmoflight4findingthelight-lari-32", false);
        addSound("[9/16] Lari: This is my chance to prove I can still be useful- that Orphion choosing me wasn't a fluke! Please, the inscription said you would know where to go!", "realmoflight4findingthelight-lari-33", false);
        addSound("[11/16] Elder: ...even after a millennium, there is so very much for us all to learn. Fine then.", "realmoflight4findingthelight-elder-3", false);
        addSound("[12/16] Elder: But promise me to accept the aid you receive. This opportunity cannot be wasted, Lari.", "realmoflight4findingthelight-elder-4", false);
        addSound("[13/16] Lari: ...I have to do at least one thing right. I can't just keep up this holding pattern. I'll do whatever is necessary!", "realmoflight4findingthelight-lari-34", false);
        addSound("[14/16] Elder: You must seek the Canned Abis plant, then. It rests within a cavern shaded by giant fern leaves.", "realmoflight4findingthelight-elder-5", false);
        addSound("[15/16] Lari: Thank you. I can finally do what I was meant to do! I won't let anything stop me this time!!", "realmoflight4findingthelight-lari-35", false);
        addSound("[16/16] Elder: ...Human, I can see you. Consider yourself lucky that she did not. Come talk to me.", "realmoflight4findingthelight-elder-6", false);
        addSound("[1/12] Elder: So you are Orphion's new chosen, then. It is both surprising and not- Humans have an interesting reputation.", "realmoflight4findingthelight-elder-7", false);
        addSound("[2/12] Elder: Often said as being able to get anything done, without limits, be it completing impossible tasks or breaking moral tenets.", "realmoflight4findingthelight-elder-8", false);
        addSound("[3/12] Elder: In this instance, however, you've both to do. Orphion has commanded it, has he not? And so you must, and for that I apologize.", "realmoflight4findingthelight-elder-9", false);
        addSound("[4/12] Elder: I have seen Lari's work- And she has done this land a priceless service. Alone, she has kept the Decay from creeping south, and thus the Guardian remains with us.", "realmoflight4findingthelight-elder-10", false);
        addSound("[5/12] Elder: However, her own morals prevent the situation from progressing. She cannot kill- And the source must be exterminated for light to once again fill the land.", "realmoflight4findingthelight-elder-11", false);
        addSound("[6/12] Elder: Orphion's circumstances are dire- And so you must come to understand something. He is above us, in terms of morals. Such concepts do not apply to him.", "realmoflight4findingthelight-elder-12", false);
        addSound("[7/12] Elder: As the embodiment of Light itself, he has no sense of morals- Merely survival and stasis. What he asks of us, many will balk at.", "realmoflight4findingthelight-elder-13", false);
        addSound("[8/12] Elder: It is a matter where morals must be set aside... And such a thing is one that Lari cannot abide by, for she has stubbornly clung to them for a thousand years.", "realmoflight4findingthelight-elder-14", false);
        addSound("[9/12] Elder: To break them... I will admit, I believe there to be some other way to solve these problems, but the fact of the matter is there simply is no time left.", "realmoflight4findingthelight-elder-15", false);
        addSound("[10/12] Elder: She must use the extent of her powers, and while she retains her pacifism that will not happen. I must ask you to follow her, as Orphion would wish.", "realmoflight4findingthelight-elder-16", false);
        addSound("[11/12] Elder: There is, unfortunately, no right to this. There is simply necessity- And there, you must push her to her limits. Seek the cave of the Canned Abis.", "realmoflight4findingthelight-elder-17", false);
        addSound("[12/12] Elder: It is north-east from here. The entrance is shaded by giant fern leaves. I only pray that this will be enough.", "realmoflight4findingthelight-elder-18", false);
        addSound("[1/13] Lari: ...I can't believe this. What am I doing wrong? What is it? There has to be some reason, what IS it?! ", "realmoflight4findingthelight-lari-36", false);
        addSound("[2/13] Lari: There's not another inscription...it's just the same words! What does it mean by \"\"dire actions?!\"\" I've done everything I can think of!", "realmoflight4findingthelight-lari-37", false);
        addSound("[3/13] Lari: Is- Is this not dire enough? Is poring over you and putting aside my role and pride to someone else not EXTREME enough?!", "realmoflight4findingthelight-lari-38", false);
        addSound("[4/13] Lari: ...soldier. I. I have to know. What did Orphion tell you, exactly? I can't stand not knowing any longer. Did he really replace me?", "realmoflight4findingthelight-lari-39", false);
        addSound("[5/13] Lari: I can't think of any other reason why everything for me has just...stopped working. Did he? Please, tell me-", "realmoflight4findingthelight-lari-40", false);
        addSound("[6/13] Lari: ...wait... You... You have Canned Abis on you!! Where did you get that?!", "realmoflight4findingthelight-lari-41", false);
        addSound("[7/13] Lari: You... There's no way you would've known that unless you... YOU! You eavesdropped on me!!", "realmoflight4findingthelight-lari-42", false);
        addSound("[8/13] Lari: You FOLLOWED ME into the cave even after I asked not to! You... After everything I said!! After you heard all those private thoughts!", "realmoflight4findingthelight-lari-43", false);
        addSound("[9/13] Lari: You STILL decided it was a good idea to come after me! What are you planning to accomplish like this?!", "realmoflight4findingthelight-lari-44", false);
        addSound("[10/13] Lari: What, are you thinking \"\"oh, let's follow the funny elf girl and see her FAIL, OVER and OVER AGAIN!?\"\" IS THAT IT?!", "realmoflight4findingthelight-lari-45", false);
        addSound("[11/13] Lari: You heard me then!! It's ALL out in the open! I can't succeed! You're there! You're doing everything I couldn't!", "realmoflight4findingthelight-lari-46", false);
        addSound("[12/13] Lari: I'm NOT a failure! I'm not! I won't be! I refuse to be! You don't GET to watch me flounder and flail!!", "realmoflight4findingthelight-lari-47", false);
        addSound("[13/13] Lari: I DON'T CARE ANYMORE! STOP STEALING MY LIFE LIKE THIS!!!", "realmoflight4findingthelight-lari-48", false);
        addSound("[1/52] Guardian of the Forest: YOU HAVE GONE FAR ENOUGH!", "realmoflight4findingthelight-guardianoftheforest-1", false);
        addSound("[2/52] Guardian of the Forest: This is enough, Lari. I waken. And thus, your task is complete, soldier. You have done as Orphion asked.", "realmoflight4findingthelight-guardianoftheforest-2", false);
        addSound("[3/52] Lari: Wha- I... What just... What did I...?", "realmoflight4findingthelight-lari-49", false);
        addSound("[4/52] Lari: I j-just... I nearly...", "realmoflight4findingthelight-lari-50", false);
        addSound("[6/52] Lari: ...GRAAAAAUGH!! TELL ME HOW THIS IS \"\"ENOUGH!\"\" TELL ME, NOW!", "realmoflight4findingthelight-lari-51", false);
        addSound("[7/52] Lari: I-I NEARLY KILLED THEM! I JUST BROKE AND DEFILED EVERYTHING I'VE EVER STOOD FOR!!", "realmoflight4findingthelight-lari-52", false);
        addSound("[8/52] Lari: TELL ME!! HOW COULD THIS POSSIBLY BE THE ANSWER?! HOW COULD THIS BE WHAT NEEDED TO HAPPEN?!", "realmoflight4findingthelight-lari-53", false);
        addSound("[9/52] Lari: WHY WOULD I BE NEEDED TO... To...", "realmoflight4findingthelight-lari-54", false);
        addSound("[10/52] Guardian of the Forest: To do something \"\"dark,\"\" you are about to say, and to that I say you have not.", "realmoflight4findingthelight-guardianoftheforest-3", false);
        addSound("[11/52] Lari: T-Trying to murder them because they did what I couldn't isn't dark? How can you possibly think that?!", "realmoflight4findingthelight-lari-55", false);
        addSound("[12/52] Lari: What kind of guardian are you, permissing this kind of violence? How is anything I just did alright?", "realmoflight4findingthelight-lari-56", false);
        addSound("[13/52] Guardian of the Forest: For a thousand years or more of your own thoughts, I understand that you are conflicted.", "realmoflight4findingthelight-guardianoftheforest-4", false);
        addSound("[14/52] Guardian of the Forest: However... You have, in that time, made a grave error. You have grown to realize an incorrect assumption.", "realmoflight4findingthelight-guardianoftheforest-5", false);
        addSound("[15/52] Guardian of the Forest: Dark and Light... They oppose. They clash. They create a cloying pox. But, allow me to ask.", "realmoflight4findingthelight-guardianoftheforest-6", false);
        addSound("[16/52] Guardian of the Forest: Is the axe evil for chopping the tree? Is the wood evil for resisting the metal?", "realmoflight4findingthelight-guardianoftheforest-7", false);
        addSound("[17/52] Guardian of the Forest: Are the flames evil for burning up the leaves? Is the water evil for dousing the fire?", "realmoflight4findingthelight-guardianoftheforest-8", false);
        addSound("[18/52] Guardian of the Forest: Is a victim evil for striking back against those that have struck them?", "realmoflight4findingthelight-guardianoftheforest-9", false);
        addSound("[19/52] Guardian of the Forest: And most importantly... Are any of these things light, or dark?", "realmoflight4findingthelight-guardianoftheforest-10", false);
        addSound("[20/52] Guardian of the Forest: What connection does a primordial force have with the moral leanings of mortals- or even immortals?", "realmoflight4findingthelight-guardianoftheforest-11", false);
        addSound("[21/52] Lari: ...you. You can't possibly be saying that what I did was the right thing to do. I refuse to accept that.", "realmoflight4findingthelight-lari-57", false);
        addSound("[22/52] Guardian of the Forest: I will not ask you to. You must understand that Orphion has no moral bearings- He simply needs action.", "realmoflight4findingthelight-guardianoftheforest-12", false);
        addSound("[23/52] Guardian of the Forest: What you have done may not have been the right thing, by anyone's account. However, it was necessary. You have thrown off your bonds, in a sense.", "realmoflight4findingthelight-guardianoftheforest-13", false);
        addSound("[25/52] Lari: ...then... Shouldn't I feel... I don't know. Shouldn't I feel less restrained? Less afraid? More... More free?", "realmoflight4findingthelight-lari-58", false);
        addSound("[26/52] Lari: If what I've done was what needed to happen, why do I feel like the scum of the earth? Why do I feel so terrible?!", "realmoflight4findingthelight-lari-59", false);
        addSound("[27/52] Guardian of the Forest: I admit, such a thing is a conundrum. I agree that it is a tragedy to kill, in many instances.", "realmoflight4findingthelight-guardianoftheforest-14", false);
        addSound("[28/52] Guardian of the Forest: Yet when such an action could save the lives of others, it becomes a conundrum with no answer. An impossible value to weigh.", "realmoflight4findingthelight-guardianoftheforest-15", false);
        addSound("[29/52] Guardian of the Forest: Despite the circumstances, you have done the best you could. I cannot fault you for your thoughts and morals.", "realmoflight4findingthelight-guardianoftheforest-16", false);
        addSound("[30/52] Guardian of the Forest: And so... As you are now proven, as I am awoken, the gateway to the Light shall reveal itself to you. Lari... You must prepare for your journey to the Realm of Light.", "realmoflight4findingthelight-guardianoftheforest-17", false);
        addSound("[31/52] Lari: ...I... I need time to process this. I... Do you understand how difficult this is to hear? To bear?", "realmoflight4findingthelight-lari-60", false);
        addSound("[32/52] Guardian of the Forest: I do not blame you for it. By the time you have composed yourself, your ally will be ready. Understand that they are as necessary as you are.", "realmoflight4findingthelight-guardianoftheforest-18", false);
        addSound("[33/52] Guardian of The Forest: For now, soldier must learn what you have learned, of the nature of realms. They must know what you know if they are to aid you fully.", "realmoflight4findingthelight-guardianoftheforest-19", false);
        addSound("[34/52] Guardian of the Forest: I must ask you to pay close attention. I shall present it simply- though the enormity of this cannot be understated.", "realmoflight4findingthelight-guardianoftheforest-20", false);
        addSound("[35/52] Guardian of the Forest: Allow me to show you our reality, soldier. It is difficult to properly present its scale... So here is an approximation.", "realmoflight4findingthelight-guardianoftheforest-21", false);
        addSound("[36/52] Guardian of the Forest: This is our world, the world we stand in right now. The physical plane of existence.", "realmoflight4findingthelight-guardianoftheforest-22", false);
        addSound("[37/52] Guardian of the Forest: Only a select few are aware that there is more of the world than this. Even the astronomers and those who view the enormity of the sky cannot understand.", "realmoflight4findingthelight-guardianoftheforest-23", false);
        addSound("[38/52] Guardian of the Forest: For, you see, this realm, this plane- It is mirrored upon a second plane, as the embodiments of Realms clash.", "realmoflight4findingthelight-guardianoftheforest-24", false);
        addSound("[39/52] Guardian of the Forest: This is the Plane of Influence, comprised of many realms. There are three you must know- But only one you must pay attention to.", "realmoflight4findingthelight-guardianoftheforest-25", false);
        addSound("[40/52] Guardian of the Forest: The Realms of Influence are connected to the physical plane, tied inexorably so to it. This is why even those gazing at the cosmos cannot grasp the enormity of it.", "realmoflight4findingthelight-guardianoftheforest-26", false);
        addSound("[41/52] Guardian of the Forest: These realms are in a constant push and pull- Battles that could not be seen by those of this world, waged constantly.", "realmoflight4findingthelight-guardianoftheforest-27", false);
        addSound("[42/52] Guardian of the Forest: However, they can be felt. Wide-scale changes to the balance of one realm or another are reflected on the physical plane.", "realmoflight4findingthelight-guardianoftheforest-28", false);
        addSound("[43/52] Guardian of the Forest: The Realms of Influence are, themselves, reflections of a Beast- They themselves, a physical manifestation of their realm, as their realm is a manifestation of them.", "realmoflight4findingthelight-guardianoftheforest-29", false);
        addSound("[44/52] Guardian of the Forest: So surely, you must know- As Gavel has decayed, it must mean that the Realm of Light is in peril, and thus that Orphion is, as well.", "realmoflight4findingthelight-guardianoftheforest-30", false);
        addSound("[45/52] Guardian of the Forest: The Light is infected- That abominable parasite has breached the Realm of Light, and sought Orphion to leech away his light.", "realmoflight4findingthelight-guardianoftheforest-31", false);
        addSound("[46/52] Guardian of the Forest: And so, as the sun is forced to set, the land of Gavel grows dark. As the dark parasites spread and devour the minds of those here, so too is Orphion subject to this torture.", "realmoflight4findingthelight-guardianoftheforest-32", false);
        addSound("[47/52] Guardian of the Forest: The balance shifts, and Orphion grows weary. Angered, desperate. He called to you as a last plea- and you have done admirably, for what he has asked of you.", "realmoflight4findingthelight-guardianoftheforest-33", false);
        addSound("[48/52] Guardian of the Forest: Now, you must enter the Realm of Light, and aid Lari in purging the parasite, before its work is finished and Orphion's mind is consumed.", "realmoflight4findingthelight-guardianoftheforest-34", false);
        addSound("[49/52] Guardian of the Forest: You must ready yourself before this, of course. To venture to another realm is a lofty task.", "realmoflight4findingthelight-guardianoftheforest-35", false);
        addSound("[50/52] Guardian of the Forest: I will bestow upon you two things that will surely aid you in this, however. First, I shall provide you the way to the Gateway to Light.", "realmoflight4findingthelight-guardianoftheforest-36", false);
        addSound("[51/52] Guardian of the Forest: Once you have reached it, I shall bestow upon you a personal blessing of mine, in the hopes that your path will become clear.", "realmoflight4findingthelight-guardianoftheforest-37", false);
        addSound("[52/52] Guardian of the Forest: Enter the portal, soldier. I shall show you the Gateway to Light... And I shall wish you luck.", "realmoflight4findingthelight-guardianoftheforest-38", false);
        addSound("[1/2] ???: My pulse, your light is brilliant... It has shone upon Lari, and soon her light will shine my way...", "realmoflight4findingthelight-orphion-3", false);
        addSound("[2/2] ???: Enter the roots, and witness the path to my realm. And then, prepare yourself. Muster your most blinding light... ", "realmoflight4findingthelight-orphion-4", false);

        //Realm of light 4 - the realm of light
        addSound("[2/8] Lari: soldier... Achara... I do not believe I can ever atone for what I have done to you. An apology simply isn't enough.", "rol5therealmoflight-lari-1", true);
        addSound("[3/8] Lari: As infuriating as what you did was, you were following orders... I had every right to be angry, but to try and take your life was ten steps too far.", "rol5therealmoflight-lari-2", true);
        addSound("[4/8] Lari: Even though it is not enough, I will say I am sorry for the pain I caused you. The Guardian was right, in the end.", "rol5therealmoflight-lari-3", true);
        addSound("[5/8] Lari: Without you carrying out Orphion's will, we would not be here. You truly are necessary in this, and I believe I have finally made peace with that.", "rol5therealmoflight-lari-4", true);
        addSound("[6/8] Lari: It has been a long time since I had a partner willing to help me in this way. I was unused to it, I suppose... Still.", "rol5therealmoflight-lari-5", true);
        addSound("[7/8] Lari: If you'll be willing to aid me, it would be a great relief. It has been a thousand years since I entered the Realm of Light...", "rol5therealmoflight-lari-6", true);
        addSound("[8/8] Lari: I can only hope things have not changed much. I remember how it was, right down to the last petal...", "rol5therealmoflight-lari-7", true);
        addSound("[1/4] Lari: Wait a moment, soldier. I have one more thing I would like to say before we enter.", "rol5therealmoflight-lari-8", true);
        addSound("[2/4] Lari: I... I need to thank you. For helping, as you have. For staying by me as you have. There was one other before you like that...", "rol5therealmoflight-lari-9", true);
        addSound("[3/4] Lari: I promise that you will not meet the same fate as him. I will fight with you to the bitter end, achara.", "rol5therealmoflight-lari-10", true);
        addSound("[4/4] Lari: It's time, then. To visit Orphion.", "rol5therealmoflight-lari-11", true);
        addSound("[1/1] Lari: Welcome to the Realm of Light then, soldier. Once you've re-oriented yourself, come over here.", "rol5therealmoflight-lari-12", true);
        addSound("[1/5] Lari: It's so much smaller than it used to be! There used to be many more landmasses, all connected to each other...", "rol5therealmoflight-lari-13", true);
        addSound("[2/5] Lari: I feel... I feel everything in here. With Orphion's light, I can tap into the very core of the realm.", "rol5therealmoflight-lari-14", true);
        addSound("[3/5] Lari: And I can feel disturbances. There are dark creatures here, where there should be none!", "rol5therealmoflight-lari-15", true);
        addSound("[4/5] Lari: I mean, even look at this creature. The light emanating from it is-", "rol5therealmoflight-lari-16", true);
        addSound("[5/5] Lari: Wait. Wh... Why is it attacking you!?", "rol5therealmoflight-lari-17", true);
        addSound("[1/4] Lari: Its mind was addled by darkness. Poor thing was terrified... Nothing for it though. ", "rol5therealmoflight-lari-18", true);
        addSound("[2/4] Lari: I suppose your more ruthless nature is a good thing, in this case. We each will need to fight, if this is as widespread as it feels.", "rol5therealmoflight-lari-19", true);
        addSound("[3/4] Lari: I'll have to prepare myself for that... Being willing to take action will not make it any easier on my conscience.", "rol5therealmoflight-lari-20", true);
        addSound("[4/4] Lari: But I will set that aside, until the day this all becomes a memory. We'll both need to seek the Nexus of Light, Orphion's home. Follow me!", "rol5therealmoflight-lari-21", true);
        addSound("[1/16] Lari: I should have expected this. With the connections severed, of course the bridge to the Nexus would be gone.", "rol5therealmoflight-lari-22", true);
        addSound("[2/16] Lari: This must be because of that horrid parasite... The Guardian of the Forest informed me of the situation afterwards.", "rol5therealmoflight-lari-23", true);
        addSound("[3/16] Lari: The gap may be too far for me to carry you over... Is there a way I can bypass this, I wonder?", "rol5therealmoflight-lari-24", true);
        addSound("[4/16] Lari: Perhaps I can try to bridge the gap myself. Let's see if this will work.", "rol5therealmoflight-lari-25", true);
        addSound("5/16lari!", "rol5therealmoflight-lari-26", true);
        addSound("[6/16] Lari: ...hm. That ought to have worked! Is the Nexus too far to reach?", "rol5therealmoflight-lari-27", true);
        addSound("[7/16] Lari: There has to be some way over... Maybe the direct method is- Hm? Another creature?", "rol5therealmoflight-lari-28", true);
        addSound("[8/16] Lari: This one doesn't seem hostile, soldier... I'll try and talk with it.", "rol5therealmoflight-lari-29", true);
        addSound("[9/16] Lari: Its mind is whole, but it's wary of your presence. You should stay back, just in case.", "rol5therealmoflight-lari-30", true);
        addSound("[10/16] Lari: So tell me, achara. What is it you sought me for?", "rol5therealmoflight-lari-31", true);
        addSound("[11/16] Lari: Ahh, I see. You wish to see your master once more as well...", "rol5therealmoflight-lari-32", true);
        addSound("[12/16] Lari: The monoliths? Are they what's severed the connections?", "rol5therealmoflight-lari-33", true);
        addSound("[13/16] Lari: Ahh, I understand. I assure you, we will fix this!", "rol5therealmoflight-lari-34", true);
        addSound("[14/16] Lari: It said that the connections to the rest of the realm were severed intentionally by Orphion, in order to protect them.", "rol5therealmoflight-lari-35", true);
        addSound("[15/16] Lari: Two monoliths on this landmass controlled the connections, and they are being drained of their light- As such, they closed the bridges.", "rol5therealmoflight-lari-36", true);
        addSound("[16/16] Lari: The light is still there- But it must be drawn back out. We have our task ahead of us now, achara. We need to go to the first monolith. Follow me!", "rol5therealmoflight-lari-37", true);
        addSound("[1/8] Lari: I never knew the monoliths kept the realm together as they did. You can see that it is inactive, yes?", "rol5therealmoflight-lari-38", true);
        addSound("[2/8] Lari: There should be bright bands of color going along it, and light pouring from it. This is where we play our roles, Soldier!", "rol5therealmoflight-lari-39", true);
        addSound("[3/8] Lari: It may be getting drained, but there is certainly still light within it, and if that parasite is battling Orphion we should be able to recharge it.", "rol5therealmoflight-lari-40", true);
        addSound("[4/8] Lari: I'll tap into the monolith... You'll need to enter it, and find its core. I'll send a part of Orphion's light with you to activate it once you do.", "rol5therealmoflight-lari-41", true);
        addSound("5/8lari", "rol5therealmoflight-lari-42", true);
        addSound("[6/8] Lari: The damage here is minor- Nothing that can't be fixed, thankfully.", "rol5therealmoflight-lari-43", true);
        addSound("[7/8] Lari: Its aura is lowered- Now your soul must enter. I will keep you safe, soldier.", "rol5therealmoflight-lari-44", true);
        addSound("[8/8] Lari: Allow my magic to open your senses... Good luck, achara.", "rol5therealmoflight-lari-45", true);
        addSound("[1/4] Lari: Welcome back, soldier. I had few troubles dealing with the creatures trying to get close. Did you have issues inside?", "rol5therealmoflight-lari-46", true);
        addSound("[2/4] Lari: The light is starting to pulse out from the monolith again, so I would say you've succeeded. I suppose we do make a decent team?", "rol5therealmoflight-lari-47", true);
        addSound("[3/4] Lari: Look! The colours are returning to the monolith! It's definitely reactivated. I'll run ahead and get to the other monolith.", "rol5therealmoflight-lari-48", true);
        addSound("[4/4] Lari: Follow the path! The way there is up the big cliff where we first entered the Realm.", "rol5therealmoflight-lari-49", true);
        addSound("[1/12] Lari: I'm unsure if you can feel the light returning from the monolith, but I can. I almost feel reborn, with all this energy!", "rol5therealmoflight-lari-50", true);
        addSound("[2/12] Lari: The rift ahead ought to take us to the second monolith, but it doesn't seem to be working, and I'm unsure why.", "rol5therealmoflight-lari-51", true);
        addSound("[3/12] Lari: See? It's inert, which is... Odd. These aren't connected to the energy of the monolith.", "rol5therealmoflight-lari-52", true);
        addSound("[4/12] Lari: It shouldn't be inactive as it is. There's some dark influence around here, but I can't seem to place it.", "rol5therealmoflight-lari-53", true);
        addSound("[5/12] Lari: My only guess is that it's interfering with the rift's energy. I've already scoured the hill though- No dark patches to be seen.", "rol5therealmoflight-lari-54", true);
        addSound("[6/12] Lari: Do you think you could take a second look-", "rol5therealmoflight-lari-55", true);
        addSound("[7/12] Lari: ...maybe you won't need to, after all. That was...", "rol5therealmoflight-lari-56", true);
        addSound("[8/12] Lari: The gliders!", "rol5therealmoflight-lari-57", true);
        addSound("[9/12] Lari: Back up! I'll hold them.", "rol5therealmoflight-lari-58", true);
        addSound("[10/12] Lari: We've found the disturbance... Even beyond this, I'll need to charge the rift with energy to activate it.", "rol5therealmoflight-lari-59", true);
        addSound("[11/12] Lari: Go in peace, tortured ones! Be at one with the light!", "rol5therealmoflight-lari-60", true);
        addSound("[12/12] Lari: Get the rest of them, Soldier! I'll charge the rift!", "rol5therealmoflight-lari-61", true);
        addSound("[1/1] Lari: Thank you, achara. The rift is charged again- Just walk into it!", "rol5therealmoflight-lari-62", true);
        addSound("[1/5] Lari: The process here should be roughly the same. I bring down the barrier, you enter and charge it with the light I provide.", "rol5therealmoflight-lari-63", true);
        addSound("[2/5] Lari: I'll need to focus the energies of the monoliths to reconnect the Nexus of Light to the mainland here, so you'll be alone.", "rol5therealmoflight-lari-64", true);
        addSound("[3/5] Lari: I'll leave a barrier around you. Few creatures live on these cliffs, but best to be certain, of course.", "rol5therealmoflight-lari-65", true);
        addSound("[4/5] Lari: Once you're done, meet me at the cliff near the Nexus. If we're to free Orphion from the parasite, having you along would be a great help.", "rol5therealmoflight-lari-66", true);
        addSound("[5/5] Lari: Good luck, achara. I will see you soon.", "rol5therealmoflight-lari-67", true);
        addSound("1/3laricarefulsoldier!stepbackamoment!", "rol5therealmoflight-lari-68", true);
        addSound("2/3laritheretheconnectiontothenexusisestablishedandsowellcross", "rol5therealmoflight-lari-69", true);
        addSound("[1/9] Lari: Wait! Something... Something's not quite right...", "rol5therealmoflight-lari-70", true);
        addSound("[2/9] Lari: Something's emerging from the gate!", "rol5therealmoflight-lari-71", true);
        addSound("[3/9] Bak'al: Weaklings. You will not interfere.", "rol5therealmoflight-bakal-1", true);
        addSound("[4/9] Lari: Interfere?! You're one of the dark!", "rol5therealmoflight-lari-72", true);
        addSound("[5/9] Bak'al: You. Elf girl. You are to be eliminated.", "rol5therealmoflight-bakal-2", true);
        addSound("[6/9] Lari: You're the one trespassing in realms not your own!", "rol5therealmoflight-lari-73", true);
        addSound("[7/9] Bak'al: Fool. The Beast quakes with our influence. This land is ours.", "rol5therealmoflight-bakal-3", true);
        addSound("[8/9] Lari: soldier. I think. I found someone. Who deserves to be killed. Help me out?", "rol5therealmoflight-lari-74", true);
        addSound("[9/9] Bak'al: I will not waste my time with that specimen. Now. Face death.", "rol5therealmoflight-bakal-4", true);
        addSound("[1/1] Lari: soldier!! Stay back! I'm keeping up but he'll destroy you if you get close!!", "rol5therealmoflight-lari-75", true);
        addSound("[1/7] Lari: AGH, ENOUGH OF THIS! You aren't allowed here, foul thing!", "rol5therealmoflight-lari-76", true);
        addSound("2/7lari!!", "rol5therealmoflight-lari-77", true);
        addSound("[3/7] Lari: No! You won't run- I'll kill you before that!", "rol5therealmoflight-lari-78", true);
        addSound("[4/7] Bak'al: You shall not. Surely your god will smile seeing me leave.", "rol5therealmoflight-bakal-5", true);
        addSound("[5/7] Lari: YOU DON'T GET TO TALK ABOUT ORPHION LIKE THAT!", "rol5therealmoflight-lari-79", true);
        addSound("[6/7] Bak'al: We have already won. Your peon is outmatched.", "rol5therealmoflight-bakal-6", true);
        addSound("There's a faint buzzing in your ears...", "rol5therealmoflight-buzzing-1", true);
        addSound("soldier... I apologize for leaving you like that. I felt like this was my only chance at defeating this plague - by attacking its core.", "rol5therealmoflight-buzzing-2", true);
        addSound("I have a slight idea of where I'm headed, but... If I'm able to handle him, then maybe I'm powerful enough for this.", "rol5therealmoflight-buzzing-3", true);
        addSound("I've never been one to say goodbyes, but thank you for everything. I know you'll find people just as strong as you are to rescue Orphion in my place.", "rol5therealmoflight-buzzing-4", true);
        addSound("I- I see the end of this... Tunnel. There's- Wait...", "rol5therealmoflight-buzzing-5", true);
        addSound("By the light what is that thing?! S-Stay away f-", "rol5therealmoflight-buzzing-6", true);

        //The Qira Hive
        addSound("[1/13] Yansur: I see another challenger has found their way to The Qira Hive!", "theqirahive-yansur-1", false);
        addSound("[1/1] Qira: Begin, the Thunder Division! Strike quickly and move on, for those within will warp your very mind should you stray for too long!", "theqirahive-qira-1", true);
        addSound("[1/1] ????: Heeheehahaaaah! I see you! You humans have such easily influenced minds, but I'll settle for beating you within an inch of your life! Come through, come through!!!", "theqirahive-psychomancer-1", true);
        addSound("[1/2] Psychomancer: Feh, you're no fun, you little cheater! I was supposed to beat you, not the other way around!", "theqirahive-psychomancer-2", false);
        addSound("[1/8] Yansur: You have completed the Thunder floor? How surprising. They were some of Mistress Qira's earliest successes.", "theqirahive-yansur-2", false);
        addSound("[1/1] Qira: Begin, the Air Division! Fight freely with the wind, for the coming storm shall claim you for my own if you cannot spread your wings here!", "theqirahive-qira-2", true);
        addSound("[1/1] ????: Well, nice work getting this far! Don't tell anyone, but I'm gonna pull my punches here, okay? I'm rooting for you, so let's have fun with this! Come on through!", "theqirahive-gale-1", true);
        addSound("[1/2] Gale: Heehee, maybe I didn't need to hold back after all? You did really, really well!", "theqirahive-gale-2", false);
        addSound("[1/6] Yansur: Hrm, perhaps we have underestimated you. The creations of Air have defeated thousands, particularly-", "theqirahive-yansur-3", false);
        addSound("[1/1] Qira: Begin, the Earth Division! You must withstand both sheer force and deadly toxins! Be quick on your feet, or you will return to the dust of the earth!", "theqirahive-qira-3", true);
        addSound("[1/6] Yansur: Creatures of the earth were not enough for you... Mistress's most rawly powerful... Destroyed.", "theqirahive-yansur-4", false);
        addSound("[1/1] Qira: Begin, the Water Division! If you will not withstand the crashing waves, the creatures that lurk within shall ensure you never emerge from the abyss!", "theqirahive-qira-4", true);
        addSound("[1/1] ????: You have been judged worthy enough to face me in combat. However, I know your strength, and I do not believe it enough to defeat me. Come forth now. Make your attempt to prove me wrong.", "theqirahive-oceanicjudge-1", true);
        addSound("[1/2] Oceanic Judge: Seems my judgment was incorrect. In this case, however, it is refreshing to see.", "theqirahive-oceanicjudge-2", false);
        addSound("[1/7] Yansur: Impossible. Not even the most tactically advanced monsters could defeat you.", "theqirahive-yansur-5", false);
        addSound("[1/1] Qira: Begin, the Fire Division! Summon your courage, and brave the heat of the volcanoes and stars for my amusement, else burn to cinders and blow away in the breeze!", "theqirahive-qira-5", true);
        addSound("[1/1] ????: Impressive, making it this far. I am your final test. Let us fight fairly, then. An honourable end to your challenge is only fitting, whether that be triumph or defeat. Come forward and face me.", "theqirahive-solarvanguard-1", true);
        addSound("[1/2] Solar Vanguard: Defeat feels strange, after so long. You fought valiantly, and with great zeal, however. It would be a lie to say I am disappointed.", "theqirahive-solarvanguard-2", false);
        addSound("[1/11] Yansur: This is unprecedented. You have defeated some of the most recent and powerful creations.", "theqirahive-yansur-6", false);
        addSound("[1/1] Qira: So, you've arrived. You have proven yourself very capable... I am looking forward to this bout! Let's dance, mortal!!", "theqirahive-qira-6", true);
        addSound("[1/1] Qira: ...you. You...ahaha...I like you. You're capable enough... Perhaps we could be allies in the future...", "theqirahive-qira-7", true);
        addSound("[1/14] Yansur: How cowardly! After defeating so many in the Hive, and receiving such praise, you've the gall to upstand Mistress's request?  ", "theqirahive-yansur-7", false);

        //A Sandy Scandal
        addSound("[1/7] Almuj Bank Guard: You look like a hardy one, Ragni army is it? Would you be willing to help Almuj a bit?", "asandyscandal-almujbankguard-1", false);
        addSound("[1/9] Shopkeeper Gibbs: Hello gentlemen! What can I interest you in today? Some industrial torches? Pickaxes? ", "asandyscandal-shopkeepergibbs-1", false);
        addSound("[2/9] Bandit: Shut yer' trap, old man. We came for a different kind of business.", "asandyscandal-bandit-1", false);
        addSound("[3/9] Shopkeeper Gibbs: Wha-? Look, fellows. Just step out of my store, and nobody gets hurt. I don't want any trouble.", "asandyscandal-shopkeepergibbs-2", false);
        addSound("[4/9] Bandit: Listen up old man, tell us the code to the TNT vault in the basement, or I decorate my necklace with your fingers!", "asandyscandal-bandit-2", false);
        addSound("[5/9] Shopkeeper Gibbs: Certainly not! That TNT is for licensed mining companies only, not your average amateur miner.", "asandyscandal-shopkeepergibbs-3", false);
        addSound("[6/9] Bandit: Well then! I guess you'll still be able to grasp a pick with four fingers, eh?", "asandyscandal-bandit-3", false);
        addSound("[7/9] Shopkeeper Gibbs: Gaaah! Alright, alright! No more violence, please!", "asandyscandal-shopkeepergibbs-4", false);
        addSound("[8/9] Shopkeeper Gibbs: The code is 7-8-1-2... please don't hurt me! ", "asandyscandal-shopkeepergibbs-5", false);
        addSound("Business was slow that day, until two rugged, oddly-dressed men stormed into my shop, with a lot of nerve.", "asandyscandal-shopkeepergibbs-6", false);
        addSound("Before I could close any deal with them, the man with a dagger started threatening me!", "asandyscandal-shopkeepergibbs-7", false);
        addSound("I tried to make peace with the crooks.", "asandyscandal-shopkeepergibbs-8", false);
        addSound("The bandit put the knife closer to me and started demanding the code to the TNT vault downstairs!", "asandyscandal-shopkeepergibbs-9", false);
        addSound("The bandits grew agitated and resorted to violence to obtain the combination.", "asandyscandal-shopkeepergibbs-10", false);
        addSound("They swiped their knife at me, leaving a sizeable wound on my arm.", "asandyscandal-shopkeepergibbs-11", false);
        addSound("I had no choice but to give up the combination, which was 7812.", "asandyscandal-shopkeepergibbs-12", false);
        addSound("After I gave up the combination, they knocked me out!", "asandyscandal-shopkeepergibbs-13", false);
        addSound("I had no idea what the criminals did after that, I never went back into my shop after the incident.", "asandyscandal-shopkeepergibbs-14", false);
        addSound("[9/9] Bandit: 7-8-1-2... thanks! We won't be needing you anymore... now go to sleep!", "asandyscandal-bandit-4", false);
        addSound("[1/6] Almuj Bank Guard: You're back, having read the report I assume. Did you find anything in particular?", "asandyscandal-almujbankguard-2", false);
        addSound("[1/3] Scroll Merchant: Ey', what do you two blighters think you're doing with those dangerous explosives in the city?", "asandyscandal-scrollmerchant-1", true);
        addSound("[2/3] Scroll Merchant: Guards! Guards! Robber! Come quick! Urgently!", "asandyscandal-scrollmerchant-2", true);
        addSound("[3/3] Scroll Merchant: Gaaah!", "asandyscandal-scrollmerchant-3", true);
        addSound("I was having a fine time tending to my rooftop garden after a lengthy day of selling scrolls.", "asandyscandal-scrollmerchant-4", true);
        addSound("While looking down on the street, I saw two unsavory men, one with TNT in hand, marching down the path.", "asandyscandal-scrollmerchant-5", true);
        addSound("I was suspicious, so I tried to get their attention, but they ignored me.", "asandyscandal-scrollmerchant-6", true);
        addSound("I then realized that had no innocent itinerary, they were heading straight for my wealthy neighbor's house!", "asandyscandal-scrollmerchant-7", true);
        addSound("I tried to yell for the attention of a guard or official, but nobody could hear me.", "asandyscandal-scrollmerchant-8", true);
        addSound("Before anybody could hear me, a large explosion occurred inside of the house the bandits intruded.", "asandyscandal-scrollmerchant-9", true);
        addSound("The bandits came sprinting out of my wealthy neighbor's house with two of his most prized possessions: two of his weapons.", "asandyscandal-scrollmerchant-10", true);
        addSound("I wasn't sure what to do at this point... I didn't try to confront them, that'd have been way too risky!", "asandyscandal-scrollmerchant-11", true);
        addSound("I have no idea where they went off to afterwards.", "asandyscandal-scrollmerchant-12", true);
        addSound("[1/8] Almuj Bank Guard: Were you able to uncover any new information regarding the status of the criminals? ", "asandyscandal-almujbankguard-3", false);
        addSound("[2/8] Almuj Bank Guard: Oh dear, this isn't good. The criminals plundered two very powerful weapons.", "asandyscandal-almujbankguard-4", false);
        addSound("[3/8] Almuj Bank Guard: It won't be easy facing those criminals if they decide to raid the bank with those weapons.", "asandyscandal-almujbankguard-5", false);
        addSound("[4/8] Almuj Bank Guard: I'm not certain how to proceed from here, we didn't receive any more witness reports, and-", "asandyscandal-almujbankguard-6", false);
        addSound("[5/8] Almuj Sentry: Boss, I just received urgent reports of a robbery underway in the left residential area of Almuj!", "asandyscandal-almujsentry-1", false);
        addSound("[6/8] Almuj Bank Guard: Oh my... you! Soldier! Urgently! The robbery is taking place across the bridge, next to the first house you visited today!", "asandyscandal-almujbankguard-7", false);
        addSound("[7/8] Almuj Bank Guard: There will be somebody waiting for you outside of the house once you get to the residential area!", "asandyscandal-almujbankguard-8", false);
        addSound("[8/8] Almuj Bank Guard: If you still can't find it, I've written the coordinates in your quest book. Now quickly, get to the house!", "asandyscandal-almujbankguard-9", false);
        addSound("[1/2] Almuj Citizen: Hello? Are you with the guards? Good! Hurry! Some bandits just ransacked my house, and left with lots of emeralds!", "asandyscandal-almujcitizen-1", true);
        addSound("[2/2] Almuj Citizen: Follow me to my house, hopefully you can find the bandits! They stole lots of my precious gold!", "asandyscandal-almujcitizen-2", true);
        addSound("[1/4] Bandit Leader: Here-- maybe you'll help us pull the heist if I give you some of this gold we just stole.", "asandyscandal-banditleader-1", true);
        addSound("[2/4] Bandit Leader: Ahah. I had a feeling we had a guard on our tail.", "asandyscandal-banditleader-2", true);
        addSound("[3/4] Bandit Leader: I wouldn't want to wear out my new-stolen weapons killing you... I'll just trap you in here with all of this TNT!", "asandyscandal-banditleader-3", true);
        addSound("[4/4] Bandit Leader: This'll blow up in a few seconds. Good luck getting out of here! ", "asandyscandal-banditleader-4", true);
        addSound("[1/1] Bandit Leader: It's too late to stop us now! We're already about to bust out of here with a grand prize sure to gain us province-wide respect!", "asandyscandal-banditleader-5", true);
        addSound("[1/3] Bandit Leader: Look here, the soldier decided they wanted to follow us. Dumb move.", "asandyscandal-banditleader-6", true);
        addSound("[2/3] Bandit Leader: I guess we can kill two birds with one stone: escaping the bank, and killing this pest.", "asandyscandal-banditleader-7", true);
        addSound("[3/3] Bandit Leader: Block the entrance, bandits!", "asandyscandal-banditleader-8", true);
        addSound("[1/1] Bandit Leader: Oh no... we've run out of TNT! We were in over our heads... We can't defeat them, just run for your life! Forget the emeralds, let's get outta' here!", "asandyscandal-banditleader-9", true);
        addSound("[1/4] Almuj Bank Guard: What happened down there underneath the bank? I heard the rumble.", "asandyscandal-almujbankguard-10", false);

        //Redbeards Booty
        addSound("[1/6] Marston: Ah, ahoy matey!", "redbeardsbooty-marston-1", false);
        addSound("[1/5] Argus: Cor lad...ye don' look like a pirate ta me, and I be but a young lass!", "redbeardsbooty-argus-1", false);
        addSound("[2/5] Martim: 's right, bucko. Who be ye, bargin' into our home in yer fancy armour 'n whatnot?", "redbeardsbooty-martim-1", false);
        addSound("[3/5] Argus: Ah, ye be accompanyin' Marston, eh? Well if ye insist on that, where be the man 'himself?", "redbeardsbooty-argus-2", false);
        addSound("[4/5] Karkun: Comin' later? Hm. Well, more hands always be helpful. Let us go down to me ship, the Black Ring. ", "redbeardsbooty-karkun-1", false);
        addSound("[5/5] Martim: So he told ye about the plan? We'll bathe in doubloons when we get back! Ah, me fair lass will be so happy.", "redbeardsbooty-martim-2", false);
        addSound("[1/5] Karkun: Aha, quick on yer feet, lad! Ye got 'ere before us!", "redbeardsbooty-karkun-2", false);
        addSound("[2/5] Argus: No trace o' Marston yet?", "redbeardsbooty-argus-3", false);
        addSound("[3/5] Martim: How's about we wait up fer him? He's the one who's givin us the opportunity fer this hunt, anyways.", "redbeardsbooty-martim-3", false);
        addSound("[4/5] Argus: I be getting impatient... The whole crew is 'ere, 'cept fer him!", "redbeardsbooty-argus-4", false);
        addSound("[5/5] Karkun: Argh! Where be our mate? It be getting dark out! We will have to leave Marston at land.", "redbeardsbooty-karkun-3", false);
        addSound("[1/3] Karkun: Do ya have the map Marston bought?", "redbeardsbooty-karkun-4", false);
        addSound("[2/3] Martim: Let us take a looksie at the map and see where to go.", "redbeardsbooty-martim-4", false);
        addSound("[3/3] Argus: It seems like one of these islands have the treasure... A'ight, yer in charge, landlubber! Set us on a course fer an island! ", "redbeardsbooty-argus-5", true);
        addSound("[1/4] Karkun: Ahoy, this must be the booty. Fret not, Argus is keepin' the ship prepared so we can leave.", "redbeardsbooty-karkun-5", false);
        addSound("[2/4] Martim: A'ight, let's us dig it up, jacks!", "redbeardsbooty-martim-5", false);
        addSound("[3/4] Karkun: Aha, I see somethin' gleaming in there! Methinks we hit gold, lads.", "redbeardsbooty-karkun-6", false);
        addSound("[4/4] Karkun: Now Martim, knock the lubber out.", "redbeardsbooty-karkun-7", false);
        addSound("[1/4] Karkun: What? Another clue! This be just a wild grook hunt, where be the real booty?! No one ever got rich off a heap o^ keys!!", "redbeardsbooty-karkun-8", false);
        addSound("[2/4] Martim: Wait, there be a clue on the key; When leaving the hidden island, the real treasure will be found if you can look closely enough.", "redbeardsbooty-martim-6", false);
        addSound("[3/4] Martim: Garh...I be losin' my patience. Argus arrived, let's ditch the lubber before they awake, hm? This island be hidden by magic, ain't no way they're gettin' found.", "redbeardsbooty-martim-7", false);
        addSound("[4/4] Karkun: Arright! We might be able to bring Redbeard 'imself back to life... He will strike terror in the seas again, and we will be his crew!", "redbeardsbooty-karkun-9", false);
        addSound("[1/8] Marston: What in the... Cor! Ye jest fell outta the sky, bucko! What'n the name o' Davy Jones' Locket happened?!", "redbeardsbooty-marston-2", false);

        //Craftmas Chaos
        addSound("[1/9] Tom: Finally, you have arrived! I have been waiting for you.", "craftmaschaos-tom-1", false);
        addSound("[1/9] Santa: Ho ho ho! Merry Craftmas! Well not exactly, but its only a couple of days left!", "craftmaschaos-santa-1", false);
        addSound("[1/8] Tommy: Wendy, where are you? Heheheheh! Oh, hi! You seem new to this place.", "craftmaschaos-tommy-1", false);
        addSound("[1/1] Summoner: So a little rat finally finds out about our secret, huh. Doesn't matter, with this relicstone, I am going to kill you right here and right now!", "craftmaschaos-summoner-1", true);
        addSound("[1/4] Summoner: Uaaaggh! How dare you do this to me!", "craftmaschaos-summoner-2", false);
        addSound("[1/13] Old Tom: Oh no, please! Don't kill me! Wait a minute, it's you! I can't believe it, it's you!", "craftmaschaos-oldtom-1", false);
        addSound("[1/8] Old Tom: Did you do it? Santa is dead? Oh no, I can't believe it. He is gone, just like that. After nearly 20 or so years of torment...", "craftmaschaos-oldtom-2", false);
        addSound("[1/9] Santa: Ho ho ho! Well If it isn't my friend who just took a journey through time!", "craftmaschaos-santa-2", false);

        //Meaningful Holiday
        addSound("[1/4] Haily: Hello, dear. My name is Haily. Nice to meet you.", "meaningfulholiday-haily-1", false);
        addSound("[1/7] Nick: Oh. Oh... Oh dear. There's more people here than ever.", "meaningfulholiday-nick-1", false);
        addSound("[1/5] Flodur: 'ello young lad.", "meaningfulholiday-flodur-1", false);
        addSound("[1/3] Flodur: Hm... This apple has seen betta days... ", "meaningfulholiday-flodur-2", false);
        addSound("[1/8] Nick: Any news?", "meaningfulholiday-nick-2", false);
        addSound("[1/3] Margaret: Oh, please don't move me along. I have no where else to go!", "meaningfulholiday-margaret-1", false);
        addSound("[1/4] Reshad: Welcome to my farm! Looking to buy some of my fresh crops?", "meaningfulholiday-reshad-1", false);
        addSound("[1/4] Nick: Hey, soldier! Any luck?", "meaningfulholiday-nick-3", false);
        addSound("[1/9] Santa: Nick... You know the deal.", "meaningfulholiday-santa-1", false);
        addSound("[1/3] Nick: Wow. The people here live in such grandure. I wonder if they know people are starving to the east.", "meaningfulholiday-nick-4", false);
        addSound("[1/7] Server: What are you doing here?", "meaningfulholiday-server-1", false);
        addSound("[1/6] Nick: Were you able to get any food?", "meaningfulholiday-nick-5", false);
        addSound("[1/13] Nick: Listen up everyone!", "meaningfulholiday-nick-6", true);
        addSound("[2/13] Nick: I know you are hungry. Because I am too.", "meaningfulholiday-nick-7", true);
        addSound("[3/13] Nick: But your dinner will not be served tonight. ", "meaningfulholiday-nick-8", true);
        addSound("[4/13] Nick: There will be no Craftmas feast this year!", "meaningfulholiday-nick-9", true);
        addSound("[5/13] Nick: This one night, this one missed meal.", "meaningfulholiday-nick-10", true);
        addSound("[6/13] Nick: This is how thousands of people live every day.", "meaningfulholiday-nick-11", true);
        addSound("[7/13] Nick: Hungry, wondering when the food will be available.", "meaningfulholiday-nick-12", true);
        addSound("[8/13] Nick: And while you sit there with empty stomachs be thankful for what you have. Spare a thought for those who have not.", "meaningfulholiday-nick-13", true);
        addSound("[9/13] Nick: Hundreds of men, women and children, just like you, will not eat tonight, at all. ", "meaningfulholiday-nick-14", true);
        addSound("[10/13] Nick: All because this enormous feast was not enough. Detlas bought all the food, cheap or expensive.", "meaningfulholiday-nick-15", true);
        addSound("[11/13] Nick: I'm here to remind you the real meaning of this day. To think of others..", "meaningfulholiday-nick-16", true);
        addSound("[12/13] Nick: I will return your meal tonight, but while you eat it... I want you to remember every empty mouth it could have fed.", "meaningfulholiday-nick-17", true);
        addSound("[13/13] Nick: Share what you have, be thankful for what you are given.", "meaningfulholiday-nick-18", false);
        addSound("[1/4] ???: Well, looks like you came through yet again Nick.", "meaningfulholiday-santa-2", false);
        addSound("[2/4] Santa: I came to see if I could help out. But it looks like you've done it.", "meaningfulholiday-santa-3", false);
        addSound("[3/4] Santa: Well, I guess there is only one thing left to do to complete this scene.", "meaningfulholiday-santa-4", false);
        addSound("[4/4] Santa: Hope you all have a merry craftmas. Ho-ho!", "meaningfulholiday-santa-5", false);
        addSound("[1/7] Nick: It looks like my little speech brought the rich back down to earth.", "meaningfulholiday-nick-19", false);
        addSound("[1/1] Guest: You can't do that!", "meaningfulholiday-guest-1", true);
        addSound("[1/1] Guest: The food on the table isn't enough to feed everyone here!", "meaningfulholiday-guest-2", true);

        //Kingdom of sand
        addSound("[1/3] ???: Hmm.. No dice. Let me try another.", "kingdomofsand-lanu-1", false);
        addSound("2/3???", "kingdomofsand-lanu-2", false);
        addSound("[3/3] ???: Ugh. I guess the legend is true about the sceptre. ", "kingdomofsand-lanu-3", false);
        addSound("[1/7] Lanu: Ah, a Ragni Soldier. I didn't think you had much to do out here in the desert?", "kingdomofsand-lanu-4", false);
        addSound("[1/4] Vault Guard: What, wanna enter the hideout?", "kingdomofsand-vaultguard-1", false);
        addSound("[1/4] Tarek: Now would you look at that... Even soldiers think they can waltz in ere.", "kingdomofsand-tarek-1", false);
        addSound("[2/4] Geo: Having fun thinking of a password? Sorry to spoil your lunch, but there isn't one!", "kingdomofsand-geo-1", false);
        addSound("[3/4] Geo: We could use a few more officials on the roster.", "kingdomofsand-geo-2", false);
        addSound("[4/4] Tarek: If yer are on the liberal side of the law, talk to us outside.", "kingdomofsand-tarek-2", false);
        addSound("[1/7] Tarek: Looks like yer in. Look, there ain't a password cause we're meant to be the best thieves in the land, right?", "kingdomofsand-tarek-3", false);
        addSound("[2/7] Tarek: So what good are words? Yer gotta give the guard somethin' stolen, aintcha!", "kingdomofsand-tarek-4", false);
        addSound("[3/7] Geo: And we just so happen to have the opportunity of a lifetime for you. Rymek has gone soft see, a new Mayor is cleaning things up.", "kingdomofsand-geo-3", false);
        addSound("[4/7] Tarek: Well, we can't have government not under our control. Rymek will always be bandit capital.", "kingdomofsand-tarek-5", false);
        addSound("[5/7] Geo: So how about we give him a taste of his own medicine, and get hold of that journal he keeps? I'm sure that'll dig up some dirt on him.", "kingdomofsand-geo-4", false);
        addSound("[6/7] Tarek: And by \"we\", we mean you. You'll have to sneak through the mansion and avoid guards.", "kingdomofsand-tarek-6", false);
        addSound("[7/7] Tarek: He keeps his journal in the highest room. Might need to distract some guards too. It's the biggest mansion in Rymek, can't miss it.", "kingdomofsand-tarek-7", false);
        addSound("[1/3] Tarek: Yer know you could get kicked out of the army fer this.", "kingdomofsand-tarek-8", false);
        addSound("[2/3] Tarek: Heh, guess yer the real thing. Hop in, lets get out of 'ere.", "kingdomofsand-tarek-9", false);
        addSound("[3/3] Tarek: That journal is more valuable than any gem, you'll be able to get in fer sure.", "kingdomofsand-tarek-10", false);
        addSound("[1/1] Tarek: Off we go!", "kingdomofsand-tarek-11", false);
        addSound("[1/2] Vault Guard: Password?", "kingdomofsand-vaultguard-2", false);
        addSound("[1/7] Bandit Boss: There was another mage at the ancient tomb today boys.", "kingdomofsand-banditboss-1", false);
        addSound("[2/7] Bandit: Well she ain't got the sceptre does she? She can't do much harm. Wait why dun we use the sceptre boss?", "kingdomofsand-bandit-1", false);
        addSound("[3/7] Bandit Boss: Because the Creden Tibus is more than just a group of criminals. We are honour bound to protect these sands.", "kingdomofsand-banditboss-2", false);
        addSound("[4/7] Bandit Boss: The empire that fell beneath these sands could be revived if that sceptre made it back to the emperor.", "kingdomofsand-banditboss-3", false);
        addSound("[5/7] Bandit: Good job no one will think of searching that broken cart near the river ey boss?", "kingdomofsand-bandit-2", false);
        addSound("[6/7] Bandit Boss: Silence you fool. Didn't I just say it was imperative no one found the sceptre?", "kingdomofsand-banditboss-4", false);
        addSound("[7/7] Bandit Boss: Right, go steal some things or something, I have real work to do.", "kingdomofsand-banditboss-5", false);
        addSound("[1/3] Bandit Leader: I knew someone was listening.", "kingdomofsand-banditleader-1", false);
        addSound("[2/3] Bandit Leader: We might be bandits, but we're not stupid. That sceptre is left better off alone.", "kingdomofsand-banditleader-2", false);
        addSound("[3/3] Desert Raider: Seize 'em!", "kingdomofsand-desertraider-1", false);
        addSound("[1/5] Lanu: You actually got it! I knew I could count on you. Lets try this!", "kingdomofsand-lanu-5", false);
        addSound("[2/5] Lanu: Huh? We got no time for superstition, we need to break that seal.", "kingdomofsand-lanu-6", false);
        addSound("[3/5] Lanu: Seems like something's happening!", "kingdomofsand-lanu-7", false);
        addSound("[4/5] Lanu: We did it! Who would've thought that it was that ea-", "kingdomofsand-lanu-8", false);
        addSound("[5/5] Lanu: What the hell was that...!", "kingdomofsand-lanu-9", false);
        addSound("[1/6] Lanu: That shouldn't have happened, I... I don't understand.", "kingdomofsand-lanu-10", false);

        //Reclaiming the house
        addSound("[1/9] Elphaba: Oh, a Wynnic soldier out here in the boondocks? Honestly, you're just what we need- I could use your help, if you're able?", "reclaimingthehouse-elphaba-1", false);
        addSound("[2/9] Elphaba: Good, thank you. Let me lock down the camp before I give you the briefing, though.", "reclaimingthehouse-elphaba-2", false);
        addSound("[3/9] Elphaba: My name is Elphaba. I've been working with the Gavellian army for a good few years now.", "reclaimingthehouse-elphaba-3", false);
        addSound("[4/9] Elphaba: I'm the commander of the Olux Outpost down ahead. It's a strategic fortress, placed to monitor the Orcish capital.", "reclaimingthehouse-elphaba-4", false);
        addSound("[5/9] Elphaba: It was safe for a long while, but especially in the past while the Orcs have gotten far bolder, more aggressive.", "reclaimingthehouse-elphaba-5", false);
        addSound("[6/9] Elphaba: They overwhelmed the outpost- we had to retreat and set up this camp to watch over the place we used to watch over them with!", "reclaimingthehouse-elphaba-6", false);
        addSound("[7/9] Elphaba: I need more firepower if I'm going...to get...", "reclaimingthehouse-elphaba-7", true);
        addSound("[8/9] Elphaba: ...oh no. See, this is what I mean! The orcs are coming!", "reclaimingthehouse-elphaba-8", true);
        addSound("[9/9] Elphaba: Brace yourself, soldier! Protect the camp!", "reclaimingthehouse-elphaba-9", true);
        addSound("[1/2] Elphaba: This is the third attack in three days! How many reinforcements have they got?!", "reclaimingthehouse-elphaba-10", true);
        addSound("[2/2] Elphaba: Gah! Their casters are strong, careful!", "reclaimingthehouse-elphaba-11", true);
        addSound("[1/1] Elphaba: Ugh, I think that was all of them... Come here now, I'll start up where we left off.", "reclaimingthehouse-elphaba-12", true);
        addSound("[1/5] Elphaba: Not sure I could have done that myself. I'm lucky you came around when you did, soldier.", "reclaimingthehouse-elphaba-13", true);
        addSound("[2/5] Elphaba: Anyway, it's clear that I'm going to need more firepower if I'm going to have a shot at reclaiming that outpost.", "reclaimingthehouse-elphaba-14", true);
        addSound("[3/5] Elphaba: You've certainly got the fighting chops, so I'd appreciate the aid, but we've got to come up with a plan, fast.", "reclaimingthehouse-elphaba-15", true);
        addSound("[4/5] Elphaba: There's an old tower near the outpost- some old storage silo. It was built taller than the fort, so we might be able to spy on them from there.", "reclaimingthehouse-elphaba-16", true);
        addSound("[5/5] Elphaba: It's a risk though, since it's so close by, but we have no other options. Meet me at the tower southeast of here so we can survey the situation.", "reclaimingthehouse-elphaba-17", true);
        addSound("[1/5] Elphaba: Soldier! I'm right behind you. You can see our outpost- and the tower I mentioned.", "reclaimingthehouse-elphaba-18", true);
        addSound("[2/5] Elphaba: We need to find a way in. A lot of soldiers were caught unaware during the Orcs' assault, too.", "reclaimingthehouse-elphaba-19", true);
        addSound("[3/5] Elphaba: If there's any survivors in there, we should make rescuing them a priority.", "reclaimingthehouse-elphaba-20", true);
        addSound("[4/5] Elphaba: After all, it means more manpower for us. We can't count on that being the case, though...", "reclaimingthehouse-elphaba-21", true);
        addSound("[5/5] Elphaba: Come on, hurry inside before one of the orcs spots us!", "reclaimingthehouse-elphaba-22", true);
        addSound("[1/9] Elphaba: Alright, this is a pretty clear view. Let's see what's going on in there...", "reclaimingthehouse-elphaba-23", true);
        addSound("[2/9] Elphaba: Wh- I'm shocked! In the middle there- they just caged the soldiers instead of killing them? That's...kind of a relief.", "reclaimingthehouse-elphaba-24", true);
        addSound("[3/9] Elphaba: There's definitely too many orcs in the plaza there for us to take down on our own...", "reclaimingthehouse-elphaba-25", true);
        addSound("[4/9] Elphaba: ...but that cage is held up by those ropes. If we can cut them, the other soldiers should be able to help!", "reclaimingthehouse-elphaba-26", true);
        addSound("[5/9] Elphaba: Alright, now I just need to figure out how we can both get in... There's sentries on the perimeter, so going over may not be an option...", "reclaimingthehouse-elphaba-27", true);
        addSound("[6/9] Elphaba: Maybe we can keep an eye out on their sentry patterns and look for an opening-", "reclaimingthehouse-elphaba-28", true);
        addSound("[7/9] Orc: Humans on old tower! Junhur will shoot! This our place now, get out!!", "reclaimingthehouse-orc-1", true);
        addSound("[8/9] Elphaba: Aw, not now! I knew this was a risk, no time to get out... Brace for impact!!", "reclaimingthehouse-elphaba-29", true);
        addSound("[9/9] Orc: Old tower fall! Junhur is best cannon shooter!!", "reclaimingthehouse-orc-2", true);
        addSound("[1/7] Elphaba: Agh, soldier!! Are you alright, where'd you fall?!", "reclaimingthehouse-elphaba-30", true);
        addSound("[2/7] Elphaba: Ah... Wow. You barely look scratched, you must've gotten really lucky.", "reclaimingthehouse-elphaba-31", true);
        addSound("[3/7] Elphaba: Not so much here... I'm pretty battered after that. At least one of us is in good condition...", "reclaimingthehouse-elphaba-32", true);
        addSound("[4/7] Elphaba: Unfortunately, that means figuring a way in is up to you. We can't examine their sentry patterns from down here.", "reclaimingthehouse-elphaba-33", true);
        addSound("[5/7] Elphaba: I'll survive, sure, but I won't be able to walk around and defend myself for a while.", "reclaimingthehouse-elphaba-34", true);
        addSound("[6/7] Elphaba: I hate leaving you on your own like this, but if it's any consolation, you should be tough enough, or lucky enough, to get through.", "reclaimingthehouse-elphaba-35", true);
        addSound("[7/7] Elphaba: Explore the perimeter of the outpost. Try to find a way in, but be careful, okay?", "reclaimingthehouse-elphaba-36", false);
        addSound("[1/2] Orc Guard: Someone outside door? Check!", "reclaimingthehouse-orcguard-1", false);
        addSound("[2/2] Orc Guard: Oh, orc from mud bath. In, now!", "reclaimingthehouse-orcguard-2", false);
        addSound("[1/1] Orc Guard: More muddy orcs? In, now!", "reclaimingthehouse-orcguard-3", true);
        addSound("[1/2] Soldier: Good thinking with the mud disguise, soldier! They never suspected a thing, now come help us out over here!", "reclaimingthehouse-soldier-1", true);
        addSound("[2/2] Soldier: Kill the Orcish Overtakers, we'll help keep the other ones off you! ", "reclaimingthehouse-soldier-2", true);
        addSound("[1/1] Soldier: The Overtakers are down, the other Orcs are flailing! Open the outpost's entrance with a spell now, we've got this under control!", "reclaimingthehouse-soldier-3", true);
        addSound("[1/5] Elphaba: Phew... That's some fine work there, soldier! The outpost is back under our control!", "reclaimingthehouse-elphaba-37", true);
        addSound("[2/5] Elphaba: Glad to see some more capable Wynnic hands over here. Though, I didn't catch your name. Would you mind?", "reclaimingthehouse-elphaba-38", true);
        addSound("[3/5] Elphaba: Soldier, huh? I'll remember that. You barely even needed my help, heh... Wish I hadn't been such a burden, but at least it worked out.", "reclaimingthehouse-elphaba-39", true);
        addSound("[4/5] Elphaba: Here- it's some of my own personal spending money, as a thank-you gift.", "reclaimingthehouse-elphaba-40", true);
        addSound("[5/5] Elphaba: Alright everyone, let's get this place ship-shape! Everything needs to be repaired in case of another attack!!", "reclaimingthehouse-elphaba-41", false);

        //The Envoy Part 1
        addSound("[1/5] Olivin: Mo'in! My name is Olivin.", "theenvoypart1-olivin-1", false);
        addSound("[1/3] C.S.S Wavebreaker Captain: Welcome welcome to C.S.S Wavebreaker!", "theenvoypart1-c.s.swavebreakercaptain-1", false);
        addSound("[1/5] C.S.S Wavebreaker Captain: Welcam aboard me ship, I'll bring ye directly to Corkus.", "theenvoypart1-c.s.swavebreakercaptain-2", false);
        addSound("[1/20] Corkus Delegate: It's such an honour to have a Wynn soldier visit our island.", "theenvoypart1-corkusdelegate-1", false);
        addSound("[2/20] Corkus Delegate: We were hoping for a true envoy, but you will do.", "theenvoypart1-corkusdelegate-2", false);
        addSound("[3/20] Corkus Delegate: If you follow me, I'll give you a tour of the city.", "theenvoypart1-corkusdelegate-3", false);
        addSound("[4/20] Corkus Delegate: We first arrived on Corkus 350 years ago.", "theenvoypart1-corkusdelegate-4", false);
        addSound("[5/20] Corkus Delegate: The Corkians, like you, are from Fruma originally...", "theenvoypart1-corkusdelegate-5", false);
        addSound("[6/20] Corkus Delegate: ...But let's not talk about that.", "theenvoypart1-corkusdelegate-6", false);
        addSound("[7/20] Corkus Delegate: Coming from Fruma, we of course knew no magic at all.", "theenvoypart1-corkusdelegate-7", false);
        addSound("[8/20] Corkus Delegate: We soon developed our own. The rare power of Electromagic.", "theenvoypart1-corkusdelegate-8", false);
        addSound("[9/20] Corkus Delegate: It's a difficult modern branch of magic that allows us to create machines.", "theenvoypart1-corkusdelegate-9", false);
        addSound("[10/20] Corkus Delegate: Ah, here already. Welcome to Corkus City, Capital of our island.", "theenvoypart1-corkusdelegate-10", false);
        addSound("[11/20] Corkus Delegate: We've come a long way in a short time.", "theenvoypart1-corkusdelegate-11", false);
        addSound("[12/20] Corkus Delegate: Let me point out where the shops are.", "theenvoypart1-corkusdelegate-12", false);
        addSound("[13/20] Corkus Delegate: North, you can buy weapons and potions. If you continue through the gate you will find the native Avos.", "theenvoypart1-corkusdelegate-13", false);
        addSound("[14/20] Corkus Delegate: West, you can buy armor, and there is another entrance to the potion shop.", "theenvoypart1-corkusdelegate-14", false);
        addSound("[15/20] Corkus Delegate: To the south is our bank, powder shop, scrolls, and our exclusive powder exchange!", "theenvoypart1-corkusdelegate-15", false);
        addSound("[16/20] Corkus Delegate: Oh! And speaking of the powder exchange, I'll start you off with some of these.", "theenvoypart1-corkusdelegate-16", false);
        addSound("[17/20] Corkus Delegate: Moving on... The south gate leads to Relos. On your way there, you might pass by the-", "theenvoypart1-corkusdelegate-17", false);
        addSound("[18/20] Corkus Delegate: Uhm, nevermind. In fact, we have a gift for you.", "theenvoypart1-corkusdelegate-18", false);
        addSound("[19/20] Corkus Delegate: The Five Gears Diner is hosting any guests of the island.", "theenvoypart1-corkusdelegate-19", false);
        addSound("[20/20] Corkus Delegate: Go through the western gate and head to the diner for your free feast. Enjoy!", "theenvoypart1-corkusdelegate-20", false);
        addSound("[1/2] Corkus Citizen: RUN!", "theenvoypart1-corkuscitizen-1", false);
        addSound("[2/2] Corkus Citizen: Mechs at the Diner! Run!", "theenvoypart1-corkuscitizen-2", false);
        addSound("[1/4] Maxie: Psst! You! Over here, behind the logs!", "theenvoypart1-maxie-1", false);
        addSound("[2/4] Maxie: Mo'in, Wynnian. Thank goodness I stopped you!", "theenvoypart1-maxie-2", false);
        addSound("[3/4] Maxie: The diner is being raided by mechs...", "theenvoypart1-maxie-3", false);
        addSound("[4/4] Maxie: You don't know, huh? Of course you don't. Let me explain.", "theenvoypart1-maxie-4", false);
        addSound("[1/1] Maxie: The name's Maxie by the way. Nice to meet you.", "theenvoypart1-maxie-5", false);
        addSound("[1/8] Maxie: This town is usually full of life...", "theenvoypart1-maxie-6", false);
        addSound("[2/8] Maxie: But Corkus has a secret...", "theenvoypart1-maxie-7", false);
        addSound("[3/8] Maxie: Our own creations have rebelled against us.", "theenvoypart1-maxie-8", false);
        addSound("[4/8] Maxie: They now raid towns for parts.", "theenvoypart1-maxie-9", false);
        addSound("[5/8] Maxie: Mechs are getting increasingly more desperate.", "theenvoypart1-maxie-10", false);
        addSound("[6/8] Maxie: Even in rust, they are still powerful.", "theenvoypart1-maxie-11", false);
        addSound("[7/8] Maxie: We have tried to remove them but the factory always makes more.", "theenvoypart1-maxie-12", false);
        addSound("[8/8] Maxie: It is Corkus' greatest failure. It must be stopped.", "theenvoypart1-maxie-13", false);
        addSound("[1/3] Maxie: We need to act now.", "theenvoypart1-maxie-14", false);
        addSound("[2/3] Maxie: I can try to open the vent to sneak in.", "theenvoypart1-maxie-15", false);
        addSound("[3/3] Maxie: Look... I know you're a stranger, but... If you want to help, talk to me.", "theenvoypart1-maxie-16", false);
        addSound("[1/9] Maxie: Follow me, soldier.", "theenvoypart1-maxie-17", false);
        addSound("[2/9] Maxie: Alright, I'll open the door with electromagic...", "theenvoypart1-maxie-18", false);
        addSound("[4/9] Maxie: Gah! I can't even do the most basic spell!", "theenvoypart1-maxie-19", false);
        addSound("[5/9] Maxie: No... L-Let me try again..!", "theenvoypart1-maxie-20", false);
        addSound("[6/9] Maxie: Yes!! It worked!", "theenvoypart1-maxie-21", false);
        addSound("[7/9] Maxie: Look, I-I'm sorry for slowing you down. It's just...", "theenvoypart1-maxie-22", false);
        addSound("[8/9] Maxie: Electromagic is different from your magic... And I'm not very good at it.", "theenvoypart1-maxie-23", false);
        addSound("[9/9] Maxie: Oh, two paths. Well, I'm not sure which way it is, so let's split up. I'll go to the right.", "theenvoypart1-maxie-24", false);
        addSound("[1/2] Collector Mech: RETRIEVE SCRAP...", "theenvoypart1-collectormech-1", false);
        addSound("[2/2] Collector Mech: RETURN WITH HAUL FOR CONSTRUCTION.", "theenvoypart1-collectormech-2", false);
        addSound("[1/10] Maxie: Ouch.", "theenvoypart1-maxie-25", false);
        addSound("[2/10] Maxie: Mo'in. Sorry, had a problem with the-", "theenvoypart1-maxie-26", false);
        addSound("[3/10] Maxie: ...What in the world is that mech on top of the pile of scrap over there?!", "theenvoypart1-maxie-27", false);
        addSound("[4/10] Maxie: That is definitely not a regular despermech... It looks brand new!", "theenvoypart1-maxie-28", false);
        addSound("[5/10] Maxie: One of those hasn't been seen since the factory was running...", "theenvoypart1-maxie-29", false);
        addSound("[6/10] Maxie: We need to do something! I'll clear the path for you. That's the only thing I can do, I... hurt my... leg.", "theenvoypart1-maxie-30", false);
        addSound("[7/10] Maxie: Come on...", "theenvoypart1-maxie-31", false);
        addSound("[8/10] Maxie: There! Go, defeat it!", "theenvoypart1-maxie-32", false);
        addSound("[9/10] Mech X: PARTS ACQUIRED... RETURN TO FACTORY.", "theenvoypart1-mechx-1", false);
        addSound("[10/10] Maxie: Are you okay? Good. Meet me at the entrance where we met. We need to report to Corkus City!", "theenvoypart1-maxie-33", false);
        addSound("[1/2] Maxie: Don't worry about the guards, they're disabled for now. Follow me!", "theenvoypart1-maxie-34", false);
        addSound("[2/2] Maxie: I know a shortcut, we'll be in Corkus City in no time.", "theenvoypart1-maxie-35", false);
        addSound("[1/9] Corkus Delegate: Mo'in! Welcome back to Corkus City.", "theenvoypart1-corkusdelegate-21", false);
        addSound("[2/9] Maxie: Beltrude, we have a problem! The factory is running!", "theenvoypart1-maxie-36", false);
        addSound("[3/9] Maxie: We saw a mech... A new mech! It looked different like it was made in-", "theenvoypart1-maxie-37", false);
        addSound("[4/9] Corkus Delegate: What a load of scrap! You shouldn't talk about that in front of our guest!", "theenvoypart1-corkusdelegate-22", false);
        addSound("[5/9] Maxie: Our guest... just killed several despermechs by himself.", "theenvoypart1-maxie-38", false);
        addSound("[6/9] Corkus Delegate: And why would we take the word of someone who can't do basic electromagic?", "theenvoypart1-corkusdelegate-23", false);
        addSound("[7/9] Corkus Delegate: Don't listen to Maxie. Enjoy your stay.", "theenvoypart1-corkusdelegate-24", false);
        addSound("[8/9] Maxie: They won't admit anything with you around. They want to become allies with Wynn.", "theenvoypart1-maxie-39", false);
        addSound("[9/9] Maxie: Come to my house when you reach level 89, here's a key so you get in. We must prepare to break into the factory.", "theenvoypart1-maxie-40", false);

        //Canyon Condor
        addSound("[1/9] Svin: I am one of the Keepers in the great Temple of Legends. There we hoard secrets about the world.", "canyoncondor-svin-1", false);
        addSound("[1/6] Jankan: Hello there, youngster! Ye must be the stranger folks 'been talkin' 'bout.", "canyoncondor-jankan-1", false);
        addSound("[1/4] Jankan: Finally! It took ye long enough, hand it over! Oh right.. yer want'n somethin'.", "canyoncondor-jankan-2", false);
        addSound("[1/7] Hermit: What do you think you're doing here, kid? Think you can waltz in my humble abode and get across my bridge?", "canyoncondor-hermit-1", false);
        addSound("[1/5] Hermit: A ha! This will do perfectly. My chickling will love it! I suppose you can cross the bridge now.", "canyoncondor-hermit-2", false);
        addSound("[1/5] Svin: Traveler! You have returned! Tell me, what have you learned?", "canyoncondor-svin-2", false);

        //Poisoning the pest
        addSound("[1/5] Farmer Cevalus: Ah, looks like a fresh recruit!", "poisoningthepest-farmercevalus-1", false);
        addSound("[1/1] Farmer Cevalus: Have you at least attempted to get rid of that pest yet? Remember, the water reservoir is on the other end of this here field.", "poisoningthepest-farmercevalus-2", true);
        addSound("[1/2] Farmer Cevalus: Look at the body!", "poisoningthepest-farmercevalus-3", true);
        addSound("[1/3] Farmer Cevalus: Great job getting rid of that corrupt varmint, I can't thank you enough!", "poisoningthepest-farmercevalus-4", false);
        addSound("[1/1] Farmer Cevalus: Hurry and enable the sprinklers with the button near the farm's entrance before it can do any more damage!", "poisoningthepest-farmercevalus-5", true);
        addSound("[1/2] Farmer Cevalus: Just one more to kill.", "poisoningthepest-farmercevalus-6", true);
        addSound("[2/2] Farmer Cevalus: Well done! Come back to me for a reward.", "poisoningthepest-farmercevalus-7", true);

        //Stable Story
        addSound("[1/9] Enkser: Looking for able-bodied adventurers! Preferably with no friends or family, but I'm not picky!", "stablestory-enkser-1", false);
        addSound("[1/1] Etus The Blind: MOO! ALBERT! SOMEONE IS HERE... I CAN SMELL IT. MOO.", "stablestory-etustheblind-1", true);
        addSound("[1/5] Enkser: Looking for able-bodied adventurers, preferably with no friends or-", "stablestory-enkser-2", false);

        //Rise of the Quartron
        addSound("[1/9] Nasea: Shipment 3A and 4B, fully accounted for... shipments 5C and 6A...missing 50% of quartz content?!", "riseofthequartron-nasea-1", false);
        addSound("[1/5] Dado: Okay, these go to division P, and...look, I'm busy allocating these deliveries to Llevigar. Go pester someone else, okay?", "riseofthequartron-dado-1", false);
        addSound("[1/16] Nasea: I'm just going to take a wild guess and say that asking him nicely whether he committed theft didn't bring up any answers.", "riseofthequartron-nasea-2", false);
        addSound("[2/16] Nasea: What, he brushed you off like THAT? Jeez, I knew he was a jerk, but really? That's just uncalled for.", "riseofthequartron-nasea-3", false);
        addSound("[3/16] Nasea: We have to make him talk...if shipments keep going missing, my contractors are going to look elsewhere!", "riseofthequartron-nasea-4", false);
        addSound("[4/16] Nasea: We need to prove that he's guilty...wait, you came to ME for ideas? This is why I asked you for help! Am I really back to square one?", "riseofthequartron-nasea-5", false);
        addSound("[5/16] Lari: Aah, Nasea!", "riseofthequartron-lari-1", false);
        addSound("[6/16] Nasea: I...what are you doing here, uh...Larry? Were you missing quartz too??", "riseofthequartron-nasea-6", false);
        addSound("[7/16] Lari: Ah, it's...Lari. No, the quartz shipment found its way fine...", "riseofthequartron-lari-2", false);
        addSound("[8/16] Lari: But, there was hardly an effect. Your idea was, unfortunately, a dead end.", "riseofthequartron-lari-3", false);
        addSound("[9/16] Nasea: Well, I did warn you that it was only an idea. I manage the mine, I don't practice purification magic. Don't pin anything on me for that.", "riseofthequartron-nasea-7", false);
        addSound("[10/16] Lari: I did not intend to imply that. Your help was appreciated, fate simply had opposing plans...", "riseofthequartron-lari-4", false);
        addSound("[11/16] Lari: ...is this human a part of your operations, miss?", "riseofthequartron-lari-5", false);
        addSound("[12/16] Nasea: Well, in a sense. Someone's siphoning quartz, but our suspect wasn't intimidated by the ten tons of armor, so we're at a loss. Any ideas?", "riseofthequartron-nasea-8", false);
        addSound("[13/16] Lari: ...as a matter of fact, I do have a solution. Elven botany describes a flower that grows decently close by, the \"\"Tattytale\"\" flower.", "riseofthequartron-lari-6", false);
        addSound("[14/16] Lari: It weakens the will of those who catch a whiff of its petals, making them pliable to questions. It's...unpleasant, but if it is needed, I suppose.", "riseofthequartron-lari-7", false);
        addSound("[15/16] Lari: There is a cave infested with spiders, and covered with webs in the western mountain. It is the only place it grows outside of Aldorei, unfortunately.", "riseofthequartron-lari-8", false);
        addSound("[16/16] Lari: Nasea, since it didn't help, I've returned the quartz. I thank you for your help...and I shall bid you both adieu.", "riseofthequartron-lari-9", false);
        addSound("[1/8] Dado: Hey, everyone's least-favorite attack dog! Here boy! You want the emerald? Go kill all the flies in my house! Come on boy!", "riseofthequartron-dado-2", false);
        addSound("[1/5] Popo: Ah, you must- Wait, never mind. I was expecting a visit from someone else.", "riseofthequartron-popo-1", false);
        addSound("[1/10] Harnort: Interesting, a human's been swept up in our plans. Hadn't expected the work force to diversify so fast.", "riseofthequartron-harnort-1", false);
        addSound("[2/10] Harnort: Time for the rundown, oh I love explaining this! I think you'll find what I have to say quite interesting, yes yes!", "riseofthequartron-harnort-2", false);
        addSound("[3/10] Harnort: You see, quartz is a powerful magical material- moreso than many know! It can absorb and harness magical energy, but what more? I had to find out!", "riseofthequartron-harnort-3", false);
        addSound("[4/10] Harnort: The rubes in my research department got me kicked out for suggesting we further our research. What kind of scientist is scared to make advancements?!", "riseofthequartron-harnort-4", false);
        addSound("[5/10] Harnort: But without a board of directors breathing down my neck, my research has led me exactly where I'd hoped, and even more, yes yes!", "riseofthequartron-harnort-5", false);
        addSound("[6/10] Guard: Oi. Got a guest taking the secret passage.", "riseofthequartron-guard-1", false);
        addSound("[8/10] Dado: Harnort! I'M Dado! This sicko mugged me for info! They're gonna ruin us both!", "riseofthequartron-dado-3", false);
        addSound("[9/10] Harnort: Guards!! GUARDS!! Capture them! Stop them! Tie them up, do something!! Our research can't go to waste, not at this vital point!!", "riseofthequartron-harnort-6", false);
        addSound("[10/10] Dado: HA! That's what you get for showing me that amazi- HORRIBLE flower! Outta here!", "riseofthequartron-dado-4", false);
        addSound("[1/1] Guard: Knock 'em down, boys!", "riseofthequartron-guard-2", true);
        addSound("[1/1] Guard: Aah, they're really tough!! Don't let them take down more of my buddies!", "riseofthequartron-guard-3", true);
        addSound("[1/1] Guard: Why are we fighting face-to-face?! They're destroying us!!", "riseofthequartron-guard-4", true);
        addSound("[1/1] Guard: What the hell are humans made of?! Overwhelm them!! We hardly have any guys left!", "riseofthequartron-guard-5", true);
        addSound("[1/5] Harnort: ...so it's come to this then. I knew the names of all those guards. I didn't hurt anyone to meet my goal.", "riseofthequartron-harnort-7", false);
        addSound("[2/5] Harnort: You...I won't let their sacrifices be in vain! Llevigar needs to see what quartz is truly capable of! I MUST show them.", "riseofthequartron-harnort-8", false);
        addSound("[3/5] Harnort: My Quartrons are the future! They need to see it! It will have been worth the scheming, and the thieving, and the burnt bridges!", "riseofthequartron-harnort-9", false);
        addSound("[4/5] Harnort: But you- you couldn't leave well enough alone. You stepped in, and callously threw away a hundred lives to stop the inevitable!", "riseofthequartron-harnort-10", false);
        addSound("[5/5] Harnort: One life for a hundred is a fair trade!!", "riseofthequartron-harnort-11", false);
        addSound("[1/3] Harnort: N-no! NO! NO, NO! You idiot, you blundering HUMAN! A hundred lives lost in vain to your destructiveness!!", "riseofthequartron-harnort-12", false);
        addSound("[2/3] Harnort: Why did they ever open the doors?! You all deserve to burn apart! You've ruined everything!!", "riseofthequartron-harnort-13", false);
        addSound("[3/3] Harnort: The Quartron's energy spike is overloading the lab's quartz! This entire room is going to explode and kill everyone in it! ARE YOU PROUD?!", "riseofthequartron-harnort-14", false);
        addSound("[1/9] Nasea: ...you've got some serious explaining to do. First you disappear to go get that Tattle flower, or whatever it was.", "riseofthequartron-nasea-9", false);
        addSound("[1/6] Nasea: Coming up behind you.", "riseofthequartron-nasea-10", true);
        addSound("[2/6] Nasea: This is the newest mining annex, but we've hit a bit of a brick wall. Well...a gravel wall.", "riseofthequartron-nasea-11", true);
        addSound("[3/6] Nasea: So, if you can open up this area, you'll get access to the mine and whatever you find in it.", "riseofthequartron-nasea-12", true);
        addSound("[4/6] Nasea: You see the gravel wall? See if you can't dig through this for us.", "riseofthequartron-nasea-13", true);
        addSound("[5/6] Nasea: Hm! Clean work there. Pretty impressive, I'd say. So, as promised, you have your mine access, and...", "riseofthequartron-nasea-14", true);
        addSound("[6/6] Nasea: ...the core you showed me folded up into a ring. Really high-tech stuff, but I can't use it, so you can take it. Just head inside now.", "riseofthequartron-nasea-15", true);

        //From the bottom
        addSound("[1/3] Nakoba: Oh, you have some troll hair? I just love the smell so much! I can never get it myself because I can't fight.", "fromthebottom-nakoba-1", false);
        addSound("[1/2] Dobile: Oh, that's the exact egg I've been looking for! In exchange for the egg, I'll trade you the cap of a leprechaun!", "fromthebottom-dobile-1", false);
        addSound("[1/3] Dorroc: A leprechaun's cap? How did you find that? I must have it! Don't worry, I have something to offer in exchange.", "fromthebottom-dorroc-1", false);
        addSound("[1/2] Lodog: Excuse me, but what is that book you're carrying? Oh, I was right! It's the diary of the Hero of Wynn!", "fromthebottom-lodog-1", false);
        addSound("[1/2] Govid: Is that Lethirath's Claw? How did you manage to get it? No matter, I have to have it. How much do you want for it?", "fromthebottom-govid-1", false);
        addSound("[1/2] Dohstaj: Amazing! That's a Golden Phoenix Feather! I can't believe you have one of these! This will cure my father and increase his life expectancy by 10 years!", "fromthebottom-dohstaj-1", false);
        addSound("[1/2] Syni: What is that blue dust you have? No, it can't be. The dust of the Fallen Angel Star? It must've been hard to get that. I doubt there is much left. I ask you to give it to me.", "fromthebottom-syni-1", false);
        addSound("[1/3] Yobon: That amulet, it used to belong to me. It looks like it has found its way home.", "fromthebottom-yobon-1", false);
        addSound("[1/3] Nakoba: Could it be? No, impossible. How have you acquired a Horn of Entia, the Elven God? I've been after this for years!", "fromthebottom-nakoba-2", false);

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
        message = message.replace(" ", "");
        message = getActualText(message);
     //   message = getTextInbetween(message, "\\n\\n");

       /*
        message = getTextAfterSplit(message, "clearedallpotioneffects");
        message = getTextAfterSplit(message, "yourquestswillnotauto-trackanymore");
        message = getTextBeforeSplit(message, "pressshifttocontinue");
        message = getTextBeforeSplit(message, "presssneaktocontinue");

        message = getTextAfterSplit(message, "/queststartbeacon");
        message = "[" + message;
          */
        message = message.replaceAll("[^abcdefghijklmnopqrstuvwxyz?!0123456789/]", "");

        return message;
    }

    private static String getActualText(String message) {
        if (!message.contains("pressshifttocontinue")) {
            if (message.contains("[")) {
                message = message.replace("[", "awlrs");
                message = getTextAfterSplit(message, "awlrs");
                message = "[" + message;
            }

            return message;
        }
        message = getTextBeforeSplit(message, "\\n\\npressshifttocontinue");
        message = getTextAfterSplit(message, "\\n\\n");
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
